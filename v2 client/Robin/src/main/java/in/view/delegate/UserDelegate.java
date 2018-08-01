package in.view.delegate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.controller.handler.UserResponseHandler;
import in.lib.manager.APIManager;
import in.lib.utils.ViewUtils;

import in.controller.adapter.base.RobinAdapter;
import in.model.User;
import in.pnutrob.client.alpha.R;
import in.view.delegate.base.AdapterDelegate;
import in.view.holder.UserHolder;

public class UserDelegate extends AdapterDelegate<User> implements View.OnClickListener
{
	public UserDelegate(RobinAdapter<User> adapter)
	{
		super(adapter);
	}

	@Override public View getView(User item, int position, View convertView, ViewGroup parent, LayoutInflater inflater)
	{
		UserHolder holder;
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.user_view, parent, false);
			holder = new UserHolder(convertView);
            holder.getUserAction().setOnClickListener(this);
			convertView.setTag(holder);
		}
		else
		{
			holder = (UserHolder)convertView.getTag();
		}

        convertView.setTag(R.id.TAG_POSITION, position);
		holder.populate(item);
		return convertView;
	}

	@Override public void onClick(final View v)
	{
        View thing = ViewUtils.getParentWithTag(R.id.TAG_POSITION, v);
        if (thing == null) {
            return;
        }

        final int position = (Integer)thing.getTag(R.id.TAG_POSITION);
		final User item = getAdapter().getItem(position);
		if (v.getId() == R.id.user_action)
		{
			onFollowClick(v, item);
		}
	}

	private void onFollowClick(final View v, final User item)
	{
		if (!item.following) {
			APIManager.getInstance().userFollow(item.getId(), new UserResponseHandler() {
                // TODO change text on button
            });
		} else {
			APIManager.getInstance().userUnfollow(item.getId(), new UserResponseHandler() {
				// TODO change text on button
			});
		}
	}
}
