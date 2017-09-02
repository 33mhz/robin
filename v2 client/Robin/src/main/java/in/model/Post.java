package in.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.lib.utils.Debug;
import in.lib.utils.SerialReaderUtil;
import in.lib.utils.SerialWriterUtil;
import lombok.Data;

@Data
public class Post extends Message
{
	protected String originalId;
	protected String threadId;
	protected String replyTo;
	protected User reposter;
	public boolean starred;
	public boolean repost;
    public boolean reposted;
	protected Boolean hasReplies = false;
	protected int replyCount;
	protected int repostCount;
	protected int starCount;
	protected List<User> reposters;
	protected List<User> starrers;
    protected long date;
	protected String dateStr;

	@Override public Post createFrom(JsonElement element)
	{
		if (element != null)
		{
			try
			{
				JsonObject postObject = element.getAsJsonObject();
				if (postObject.has("data"))
				{
					postObject = postObject.get("data").getAsJsonObject();
				}
				setOriginalId(postObject.get("id").getAsString());

				if (postObject.has("repost_of"))
				{
					setRepost(true);
					setReposter(new User().createFrom(postObject.get("user").getAsJsonObject()));
					postObject = postObject.get("repost_of").getAsJsonObject();
				}

				if (super.createFrom(postObject) != null)
				{
					this.threadId = postObject.get("thread_id").getAsString();
					this.replyCount = postObject.get("counts").getAsJsonObject().get("replies").getAsInt();
					this.hasReplies = replyCount > 0 || postObject.has("reply_to");
					this.starCount = postObject.get("counts").getAsJsonObject().get("bookmarks").getAsInt();
					this.repostCount = postObject.get("counts").getAsJsonObject().get("reposts").getAsInt();
					this.starred = postObject.has("you_bookmarked") && postObject.get("you_bookmarked").getAsBoolean();
                    this.reposted = postObject.has("you_reposted") && postObject.get("you_reposted").getAsBoolean();
					this.reposters = new User().createListFrom(postObject.get("reposted_by"));
					this.starrers = new User().createListFrom(postObject.get("bookmarked_by"));

					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
					format.setTimeZone(TimeZone.getTimeZone("UTC"));
					Date postDate = format.parse(postObject.get("created_at").getAsString());
					this.date = postDate.getTime();
					this.dateStr = android.text.format.DateUtils.getRelativeTimeSpanString(postDate.getTime(), System.currentTimeMillis(), android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE).toString();

					if (postObject.has("reply_to"))
					{
						this.replyTo = postObject.get("reply_to").getAsString();
					}

                    return this;
				}
			}
			catch (Exception e)
			{
				Debug.out(e);
			}
		}

		return null;
	}

	@Override public List<Post> createListFrom(JsonElement element)
	{
		try
		{
			JsonArray postArray = element.getAsJsonArray();
			ArrayList<Post> posts = new ArrayList<>(postArray.size());

			for (JsonElement postElement : postArray)
			{
				Post post = new Post().createFrom(postElement);

				if (post != null)
				{
					posts.add(post);
				}
			}

			return posts;
		}
		catch (Exception e)
		{
			Debug.out(e);
		}

		return null;
	}

	@Override public Post createFrom(Parcel parcel)
	{
		super.createFrom(parcel);
		return this;
	}

	@Override public String getVersion()
	{
		return "59abe07a-0ff2-4896-9991-e7a6bdf9f7f8";
	}

	@Override public Post read(SerialReaderUtil util)
	{
		if (super.read(util) != null)
		{
			try
			{
				String version = util.readString();
				if (!version.equals(getVersion())) return null;

				originalId = util.readString();
				threadId = util.readString();
				replyTo = util.readString();
				reposter = util.readModel(User.class);
				starred = util.readBoolean();
				repost = util.readBoolean();
				hasReplies = util.readBoolean();
                reposted = util.readBoolean();
                starred = util.readBoolean();
				replyCount = util.readInt();
				repostCount = util.readInt();
				starCount = util.readInt();
				reposters = util.readModelList(User.class);
				starrers = util.readModelList(User.class);
				date = util.readLong();
				dateStr = util.readString();

				return this;
			}
			catch (Exception e)
			{
				Debug.out(e);
			}
		}

		return null;
	}

	@Override public void write(SerialWriterUtil util)
	{
		super.write(util);

		try
		{
			util.writeString(getVersion());
			util.writeString(originalId);
			util.writeString(threadId);
			util.writeString(replyTo);
			util.writeModel(reposter);
			util.writeBoolean(starred);
			util.writeBoolean(repost);
            util.writeBoolean(reposted);
            util.writeBoolean(starred);
			util.writeBoolean(hasReplies);
			util.writeInt(replyCount);
			util.writeInt(repostCount);
			util.writeInt(starCount);
			util.writeModelList(reposters);
			util.writeModelList(starrers);
			util.writeLong(date);
			util.writeString(dateStr);
		}
		catch (Exception e)
		{
			Debug.out(e);
		}
	}

	public static final Parcelable.Creator<Post> CREATOR = new Creator<Post>()
	{
		@Override public Post[] newArray(int size)
		{
			return new Post[size];
		}

		@Override public Post createFromParcel(Parcel source)
		{
			return new Post().createFrom(source);
		}
	};

	@Override public boolean equals(Object object)
	{
		if (object == null)
		{
			return false;
		}

		if (object == this
		|| (object instanceof Post
		&& (((Post)object).getId().equals(getId()) || ((Post)object).getOriginalId().equals(getOriginalId()))))
		{
			return true;
		}

		return false;
	}
}
