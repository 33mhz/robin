package in.pnutrob.client.alpha.dialog;

import android.os.Bundle;

import in.lib.Constants;
import in.lib.manager.UserManager;
import in.lib.utils.Views;
import in.lib.utils.Views.Injectable;
import in.model.DraftPost;
import in.model.Post;
import in.data.entity.MentionEntity;
import in.pnutrob.client.alpha.R;

@Injectable
public class ReplyPostDialog extends NewPostDialog
{
	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Views.inject(this);
	}

	@Override public void retrieveArguments(Bundle instances)
	{
		super.retrieveArguments(instances);

		String tempTitle = getString(R.string.reply_post);

		if (instances != null)
		{
			if (instances.containsKey(Constants.EXTRA_POST))
			{
				Post post = instances.getParcelable(Constants.EXTRA_POST);
				tempTitle = String.format(getString(R.string.reply_to), post.getPoster().getUsername());

				StringBuilder postText = new StringBuilder();

                String username = UserManager.getInstance().getUser().getUsername().toLowerCase();
                if (!post.getPoster().getUsername().equalsIgnoreCase(username)) {
                    postText.append("@").append(post.getPoster().getUsername());
                }

				if (instances.getBoolean(Constants.EXTRA_REPLY_ALL, false))
				{
					for (MentionEntity mention : post.getPostText().getMentions())
					{
						if (!mention.getName().equalsIgnoreCase(username) && !mention.getName().equalsIgnoreCase(post.getPoster().getUsername())) {
                        	postText.append(" @").append(mention.getName());
                        }
					}
				}

				if (instances.containsKey(Constants.EXTRA_REPLY_EXTRA))
				{
					postText.append(" ").append(instances.getString(Constants.EXTRA_REPLY_EXTRA));
				}

				postText.append(" ");
				((DraftPost)getDraft()).setPostText(postText.toString());
				((DraftPost)getDraft()).setReplyId(post.getId());
			}
		}

		if (instances.containsKey(Constants.EXTRA_TITLE))
		{
			tempTitle = instances.getString(Constants.EXTRA_TITLE);
		}

		setTitle(tempTitle);
	}
}
