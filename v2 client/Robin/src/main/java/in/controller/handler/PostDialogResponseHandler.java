package in.controller.handler;

import android.support.v4.app.NotificationCompat;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import in.controller.handler.base.DialogResponseHandler;
import in.model.Post;
import in.pnutrob.client.alpha.R;

public class PostDialogResponseHandler extends DialogResponseHandler<Post>
{
	private int notificationId;

	protected PostDialogResponseHandler(Context context, int notificationId)
	{
		super(context);
		this.notificationId = notificationId;
	}

	public String getNotificationTitle()
	{
		return getContext().getString(R.string.sending_post_title);
	}

	public String getNotificationText()
	{
		return getContext().getString(R.string.sending_post);
	}

	public String getNotificationFinishText()
	{
		return getContext().getString(R.string.post_success);
	}

	@Override public void onSend()
	{
		NotificationCompat.Builder notification;
		notification = new NotificationCompat.Builder(getContext());
		notification.setContentTitle(getNotificationTitle());
		notification.setContentText(getNotificationText());
		notification.setContentIntent(PendingIntent.getActivity(getContext(), 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT));
		notification.setTicker(getNotificationText());
		notification.setSmallIcon(R.drawable.ic_notif_mention);
		notification.setProgress(0, 0, true);

		NotificationManager notificationManager = (NotificationManager)getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(notificationId, notification.build());
	}

	@Override public void onSuccess()
	{
		if (getContent() != null)
		{
			setResponse(new Post().createFrom(getContent()));
		}
	}

	@Override public void onFinish(boolean failed)
	{
		NotificationManager notificationManager = (NotificationManager)getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder notification = new NotificationCompat.Builder(getContext());
		notification.setSmallIcon(R.drawable.ic_notif_mention);
		notification.setWhen(System.currentTimeMillis());

		if (failed)
		{
			notification.setTicker("Failed to send");
			notificationManager.notify(notificationId, notification.build());
		}
		else
		{
			if (getContext() != null)
			{
				notification.setTicker(getNotificationFinishText());
				notification.setContentIntent(PendingIntent.getActivity(getContext(), 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT));
				notificationManager.notify(notificationId, notification.build());
				notificationManager.cancel(notificationId);
			}
		}
	}
}
