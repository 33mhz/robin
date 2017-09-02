package in.pnutrob.client.alpha;

import android.os.Bundle;

import in.lib.Constants;
import in.lib.adapter.ViewPageAdapter;
import in.lib.utils.Views.Injectable;
import in.model.Channel;
import in.pnutrob.client.alpha.base.BaseActivity;
import in.pnutrob.client.alpha.fragment.ChannelMessagesFragment;

@Injectable
public class ChannelMessagesActivity extends BaseActivity
{
	private Channel channel;

	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (getIntent().getExtras().containsKey(Constants.EXTRA_CHANNEL))
		{
			channel = (Channel)getIntent().getExtras().getParcelable(Constants.EXTRA_CHANNEL);
		}
		else
		{
			// No channel set
			finish();
			return;
		}

		Bundle messageBundle = new Bundle(getIntent().getExtras());
		messageBundle.putString(Constants.EXTRA_TITLE, channel.getTitle() == null ? "Messages" : channel.getTitle());

		setPageAdapter(new ViewPageAdapter(this, getFragmentManager(), getViewPager()));
		getPageAdapter().addPage(ChannelMessagesFragment.class, messageBundle);
		getViewPager().setAdapter(getPageAdapter());

		// Set the initial position.
		// TODO: Handle override from Extras
		getViewPager().setCurrentItem(0);
		getPageAdapter().onPageSelected(0);
	}
}
