package in.lib.manager;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.callumtaylor.asynchttp.AsyncHttpClient;
import net.callumtaylor.asynchttp.obj.entity.JsonEntity;
import net.callumtaylor.asynchttp.obj.entity.MultiPartEntity;
import net.callumtaylor.asynchttp.obj.entity.RequestEntity;
import net.callumtaylor.asynchttp.response.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import in.controller.handler.ImageUploadResponseHandler;
import in.data.annotation.Annotation;
//import in.data.annotation.FileAnnotation;
import in.lib.Constants;
import in.lib.utils.Debug;
import in.model.DraftMessage;
import in.model.DraftPost;
import in.model.Post;

public class APIManager
{
	private static APIManager instance;

	public static APIManager getInstance()
	{
		if (instance == null)
		{
			synchronized (APIManager.class)
			{
				if (instance == null)
				{
					instance = new APIManager();
				}
			}
		}

		return instance;
	}

	public AsyncHttpClient authenticate(String username, String password, AsyncHttpResponseHandler response)
	{
		RequestEntity data = new RequestEntity();
		data.add("client_id", Constants.CLIENT_TOKEN);
		data.add("password_grant_secret", Constants.PASSWORD_GRANT);
		data.add("grant_type", "password");
		data.add("scope", Constants.API_SCOPES);
		data.add("username", username);
		data.add("password", password);

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_AUTH);
		client.post(data, response);

		return client;
	}

	public AsyncHttpClient getTimeLine(String lastId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair(Constants.API_COUNT, "20"));

		if (lastId.equals("last_read"))
		{
			params.add(new BasicNameValuePair(Constants.API_SINCE_ID, lastId));
		}
		else
		{
			params.add(new BasicNameValuePair(Constants.API_BEFORE_ID, lastId));
		}

		params.add(new BasicNameValuePair(Constants.API_INCLUDE_DELETED, "0"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_DIRECTED_POSTS, SettingsManager.getInstance().isNonFollowingMentionEnabled() ? "1" : "0"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_POST_RAW, "1"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_BOOKMARKED, "1"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_REPOSTERS, "1"));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		if (SettingsManager.getInstance().isUnifiedTimelineEnabled())
		{
			client.get(Constants.API_UNIFIED_TIMELINE_STREAM, params, response);
		}
		else
        {
			client.get(Constants.API_TIMELINE_STREAM, params, response);
		}

		return client;
	}

	public AsyncHttpClient getThreadStart(String threadId, String postId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair(Constants.API_COUNT, "-20"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_DELETED, "0"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_POST_RAW, "1"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_BOOKMARKED, "1"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_REPOSTERS, "1"));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(String.format(Constants.API_POST_THREAD, threadId), params, response);

		return client;
	}

	public AsyncHttpClient getInteractions(String lastId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair(Constants.API_COUNT, "20"));
		params.add(new BasicNameValuePair(Constants.API_BEFORE_ID, lastId));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_POST_RAW, "1"));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));
		params.add(new BasicNameValuePair(Constants.API_ACTIONS, "repost,follow,bookmark"));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(Constants.API_USER_ACTIONS, params, response);

		return client;
	}

	public AsyncHttpClient getUserStream(String userId, String lastId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair(Constants.API_COUNT, "20"));

		if (lastId.equals("last_read"))
		{
			params.add(new BasicNameValuePair(Constants.API_SINCE_ID, lastId));
		}
		else
		{
			params.add(new BasicNameValuePair(Constants.API_BEFORE_ID, lastId));
		}

		params.add(new BasicNameValuePair(Constants.API_INCLUDE_DELETED, "0"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_DIRECTED_POSTS, SettingsManager.getInstance().isNonFollowingMentionEnabled() ? "1" : "0"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_POST_RAW, "1"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_BOOKMARKED, "1"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_REPOSTERS, "1"));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(String.format(Constants.API_USER_POSTS, userId), params, response);

		return client;
	}

	public AsyncHttpClient getUserFiles(String lastId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();

//		params.add(new BasicNameValuePair(Constants.API_COUNT, "20"));
		params.add(new BasicNameValuePair(Constants.API_BEFORE_ID, lastId));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(String.format(Constants.API_USER_FILES, "me"), params, response);

		return client;
	}

	public AsyncHttpClient getUserStarred(String userId, String lastId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();

//		params.add(new BasicNameValuePair(Constants.API_COUNT, "20"));
		params.add(new BasicNameValuePair(Constants.API_BEFORE_ID, lastId));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(String.format(Constants.API_USER_BOOKMARKED, userId), params, response);

		return client;
	}

	public AsyncHttpClient getUserMentions(String userId, String lastId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair(Constants.API_COUNT, "20"));
		params.add(new BasicNameValuePair(Constants.API_BEFORE_ID, lastId));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_DELETED, "0"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_POST_RAW, "1"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_BOOKMARKED, "1"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_REPOSTERS, "1"));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(String.format(Constants.API_USER_MENTIONS, userId), params, response);

		return client;
	}

	public AsyncHttpClient getUser(String userId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();
		return getUser(userId, accessToken, response);
	}

	public AsyncHttpClient getUser(String userId, String accessToken, AsyncHttpResponseHandler response)
	{
		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair(Constants.API_INCLUDE_RAW, "1"));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(String.format(Constants.API_USER_DETAILS, userId), params, response);

		return client;
	}

	public AsyncHttpClient getFollowers(String userId, String lastId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair(Constants.API_COUNT, "20"));
		params.add(new BasicNameValuePair(Constants.API_BEFORE_ID, "" + lastId));
//		params.add(new BasicNameValuePair(Constants.API_INCLUDE_RAW, "1"));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(String.format(Constants.API_USER_FOLLOWERS, userId), params, response);

		return client;
	}

	public AsyncHttpClient getFollowings(String userId, String lastId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair(Constants.API_COUNT, "20"));
		params.add(new BasicNameValuePair(Constants.API_BEFORE_ID, lastId));
//		params.add(new BasicNameValuePair(Constants.API_INCLUDE_RAW, "1"));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(String.format(Constants.API_USER_FOLLOWING, userId), params, response);

		return client;
	}

	public AsyncHttpClient getChannels(String lastId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair(Constants.API_COUNT, "20"));
		params.add(new BasicNameValuePair(Constants.API_BEFORE_ID, lastId));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_CHANNEL_RAW, "1"));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));
		params.add(new BasicNameValuePair(Constants.API_CHANNEL_TYPES, Constants.CHANNEL_TYPES));
		params.add(new BasicNameValuePair(Constants.API_ALLOW_RECENT_MESSAGE, "1"));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(Constants.API_CHANNELS, params, response);

		return client;
	}

	public AsyncHttpClient getChannelMessages(String channelId, String lastId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair(Constants.API_COUNT, "20"));
		params.add(new BasicNameValuePair(Constants.API_BEFORE_ID, "" + lastId));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_DELETED, "0"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_MESSAGE_RAW, "1"));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(String.format(Constants.API_CHANNEL_MESSAGES, channelId), params, response);

		return client;
	}

	private AsyncHttpClient uploadImage(Context context, String accessToken, String path, AsyncHttpResponseHandler response)
	{
		try
		{
			MultiPartEntity params = new MultiPartEntity();
			List<Header> headers = new ArrayList<>();
			params.addPart("type", "robin.image.photo");
			params.addPart("kind", "image");
            // TODO use file's actual name for upload
			params.addPart("name", "robin_file_"+System.currentTimeMillis());
			headers.add(new BasicHeader("Authorization", "BEARER " + accessToken));

			params.addPart("content", getImageBody(context, path));

			AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL + "files");
			client.post(params, headers, response);

			return client;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public AsyncHttpClient postPost(Context context, final DraftPost post, final AsyncHttpResponseHandler response)
	{
		final Context applicationContext = context.getApplicationContext();
		String accessToken = UserManager.getInstance().getToken(post.getSelectedAccountId());

		if (TextUtils.isEmpty(accessToken))
		{
			accessToken = UserManager.getInstance().getAccessToken();
		}

		// Upload any pending images
		if (post.getImages().size() > 0)
		{
			uploadImage(applicationContext, accessToken, post.getImages().get(0), new ImageUploadResponseHandler(applicationContext, (int)(post.getDate() / 1000L))
			{
				@Override public void onSend()
				{
					super.onSend();

					int total = post.getImageCount();
					int current = total - post.getImages().size() + 1;
					sendNotification("Uploading image " + current + " of " + total);
				}

				@Override public void onFinish(boolean failed)
				{
					if (!failed)
					{
						if (getImage() != null)
						{
							post.getImages().remove(0);
							post.getAnnotations().add(getImage());
							int imageCount = (post.getImageCount() - post.getImages().size());

							postPost(applicationContext, post, response);
						}
					}
				}
			});

			return null;
		}

		// Add the links to the post
		/*if (post.getAnnotations() != null && post.getAnnotations().size() > 0)
		{
			int imageCount = 0;
			for (Annotation annotation : post.getAnnotations())
			{
				if (annotation instanceof FileAnnotation)
				{
					post.setPostText((post.getPostText() + " photos.app.net/{post_id}/" + ++imageCount).trim());
				}
			}
		}*/

		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_POST_RAW, "1"));

		JsonObject object = new JsonObject();
		object.addProperty(Constants.API_TEXT, post.getPostText());

		if (!TextUtils.isEmpty(post.getReplyId()))
		{
			object.addProperty(Constants.API_REPLY_TO, post.getReplyId());
		}

		JsonArray annotations = new JsonArray();
		if (post.getAnnotations() != null)
		{
			for (Annotation e : post.getAnnotations())
			{
				JsonObject annotation = e.toAnnotation().getAsJsonObject();
				if (annotation != null)
				{
					annotations.add(annotation);
				}
			}

			if (annotations.size() > 0)
			{
				object.add("raw", annotations);
			}
		}

		try
		{
			JsonEntity postData = new JsonEntity(object);

			AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
			client.post(Constants.API_POSTS, params, postData, response);

			return client;
		}
		catch (Exception e)
		{
			Debug.out(e);
		}

		return null;
	}

	public AsyncHttpClient postRepost(String postId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

		JsonObject object = new JsonObject();
		object.addProperty(Constants.API_REPOST, postId);

		try
		{
			JsonEntity postData = new JsonEntity(object);

			AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
			client.put(String.format(Constants.API_POST_REPOST, postId), params, postData, response);

			return client;
		}
		catch (Exception e)
		{
			Debug.out(e);
		}

		return null;
	}

    public AsyncHttpClient postUnrepost(String postId, AsyncHttpResponseHandler response)
    {
        String accessToken = UserManager.getInstance().getAccessToken();

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

        try
        {
            AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
            client.delete(String.format(Constants.API_POST_REPOST, postId), params, response);

            return client;
        }
        catch (Exception e)
        {
            Debug.out(e);
        }

        return null;
    }

    public AsyncHttpClient postBookmark(String postId, AsyncHttpResponseHandler response)
    {
        String accessToken = UserManager.getInstance().getAccessToken();

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

        JsonObject object = new JsonObject();

        try
        {
            JsonEntity postData = new JsonEntity(object);

            AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
            client.put(String.format(Constants.API_POST_BOOKMARK, postId), params, postData, response);

            return client;
        }
        catch (Exception e)
        {
            Debug.out(e);
        }

        return null;
    }

    public AsyncHttpClient postUnbookmark(String postId, AsyncHttpResponseHandler response)
    {
        String accessToken = UserManager.getInstance().getAccessToken();

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

        try
        {
            AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
            client.delete(String.format(Constants.API_POST_BOOKMARK, postId), params, response);

            return client;
        }
        catch (Exception e)
        {
            Debug.out(e);
        }

        return null;
    }

	public AsyncHttpClient postMessage(Context context, final DraftMessage message, final AsyncHttpResponseHandler response)
	{
		final Context applicationContext = context.getApplicationContext();
        String accessToken = UserManager.getInstance().getAccessToken();

		// buggy?
		//if (TextUtils.isEmpty(message.getSelectedAccountId()))
		//{
		//	accessToken = UserManager.getInstance().getAccessToken();
		//}

		// Upload any pending images
		if (message.getImages().size() > 0)
		{
			uploadImage(applicationContext, accessToken, message.getImages().get(0), new ImageUploadResponseHandler(applicationContext, (int)(message.getDate() / 1000L))
			{
				@Override public void onSend()
				{
					super.onSend();

					int total = message.getImageCount();
					int current = total - message.getImages().size() + 1;
					sendNotification("Uploading image " + current + " of " + total);
				}

				@Override public void onFinish(boolean failed)
				{
					if (!failed)
					{
						if (getImage() != null)
						{
							message.getImages().remove(0);
							message.getAnnotations().add(getImage());
							int imageCount = (message.getImageCount() - message.getImages().size());

							postMessage(applicationContext, message, response);
						}
					}
				}
			});

			return null;
		}
		// Add the links to the post
		//if (message.getAnnotations() != null && message.getAnnotations().size() > 0)
		//{
			//int imageCount = 0;
			//for (Annotation annotation : message.getAnnotations())
			//{
				// TODO: Handle other image uploads by placing links in the message
			//}
		//}

		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_MESSAGE_RAW, "1"));
        params.add(new BasicNameValuePair(Constants.API_UPDATE_MARKER, "1"));

		JsonObject object = new JsonObject();
		object.addProperty(Constants.API_TEXT, message.getPostText());
		object.addProperty(Constants.API_CHANNEL_ID, message.getChannelId());

		JsonArray annotations = new JsonArray();
		if (message.getAnnotations() != null)
		{
			for (Annotation e : message.getAnnotations())
			{
				JsonObject annotation = e.toAnnotation().getAsJsonObject();
				if (annotation != null)
				{
					annotations.add(annotation);
				}
			}

			if (annotations.size() > 0)
			{
				object.add("raw", annotations);
			}
		}
		try
		{
			JsonEntity postData = new JsonEntity(object);

			AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
			client.post(String.format(Constants.API_CHANNEL_MESSAGES, message.getChannelId()), params, postData, response);
			return client;
		}
		catch (Exception e)
		{
			Debug.out(e);
		}

		return null;
	}

	public AsyncHttpClient searchPosts(String searchTerm, String lastId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair(Constants.API_COUNT, "20"));
		params.add(new BasicNameValuePair(Constants.API_BEFORE_ID, "" + lastId));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_DIRECTED_POSTS, SettingsManager.getInstance().isNonFollowingMentionEnabled() ? "1" : "0"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_POST_RAW, "1"));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));
		params.add(new BasicNameValuePair("q", searchTerm));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(Constants.API_SEARCH_POST, params, response);

		return client;
	}

	public AsyncHttpClient searchUsers(String searchTerm, String lastId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair(Constants.API_COUNT, "20"));
		params.add(new BasicNameValuePair(Constants.API_BEFORE_ID, "" + lastId));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));
		params.add(new BasicNameValuePair("q", searchTerm));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(Constants.API_SEARCH_USER, params, response);

		return client;
	}

	public AsyncHttpClient getRobinPosts(String lastId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair(Constants.API_COUNT, "20"));
		params.add(new BasicNameValuePair(Constants.API_BEFORE_ID, "" + lastId));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_DIRECTED_POSTS, SettingsManager.getInstance().isNonFollowingMentionEnabled() ? "1" : "0"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_POST_RAW, "1"));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));
		params.add(new BasicNameValuePair("client_id", Constants.CLIENT_TOKEN));
        Debug.out(params);
		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(Constants.API_SEARCH_POST, params, response);

		return null;
	}

	public AsyncHttpClient getGlobalPosts(String lastId, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getAccessToken();

		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair(Constants.API_COUNT, "20"));
		params.add(new BasicNameValuePair(Constants.API_BEFORE_ID, "" + lastId));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_DELETED, "0"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_DIRECTED_POSTS, SettingsManager.getInstance().isNonFollowingMentionEnabled() ? "1" : "0"));
		params.add(new BasicNameValuePair(Constants.API_INCLUDE_POST_RAW, "1"));
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.get(Constants.API_POSTS + "/streams/global", params, response);

		return client;
	}

	private ContentBody getImageBody(Context c, String path)
	{
		Uri imageUri = Uri.parse(path);

		if (imageUri.getScheme().startsWith("content"))
		{
			try
			{
				ParcelFileDescriptor parcelFileDescriptor = c.getContentResolver().openFileDescriptor(imageUri, "r");
				final long size = parcelFileDescriptor.getStatSize();
				parcelFileDescriptor.close();
				InputStream fs = c.getContentResolver().openInputStream(imageUri);

				return new InputStreamBody(fs, "image/jpeg", "image.jpg")
				{
					@Override public long getContentLength()
					{
						return size;
					}
				};
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else if (imageUri.getScheme().startsWith("file"))
		{
			return new FileBody(new File(imageUri.getPath()), "image/jpeg");
		}

		return null;
	}

	public AsyncHttpClient deletePost(Post post, AsyncHttpResponseHandler response)
	{
		String accessToken = UserManager.getInstance().getToken(post.getPoster().getId());

		if (TextUtils.isEmpty(accessToken))
		{
			accessToken = UserManager.getInstance().getAccessToken();
		}

		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

		AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
		client.delete(Constants.API_POSTS + "/" + post.getId(), params, response);

		return client;
	}

    public AsyncHttpClient userFollow(String userId, AsyncHttpResponseHandler response)
    {
        String accessToken = UserManager.getInstance().getAccessToken();

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

        try
        {
            AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
            client.put(String.format(Constants.API_USER_FOLLOW, userId), params, response);

            return client;
        }
        catch (Exception e)
        {
            Debug.out(e);
        }

        return null;
    }

    public AsyncHttpClient userUnfollow(String userId, AsyncHttpResponseHandler response)
    {
        String accessToken = UserManager.getInstance().getAccessToken();

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(Constants.API_ACCESS_TOKEN, accessToken));

        try
        {
            AsyncHttpClient client = new AsyncHttpClient(Constants.API_URL);
            client.delete(String.format(Constants.API_USER_FOLLOW, userId), params, response);

            return client;
        }
        catch (Exception e)
        {
            Debug.out(e);
        }

        return null;
    }
}
