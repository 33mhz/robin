package in.pnutrob.client;

import android.os.Bundle;

import in.lib.Constants;
import in.lib.adapter.ViewPageAdapter;
import in.lib.utils.Views.Injectable;
import in.pnutrob.client.base.BaseActivity;
import in.pnutrob.client.fragment.ChannelsFragment;

@Injectable
public class ChannelsActivity extends BaseActivity
{
	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle channelsBundle = new Bundle();
		channelsBundle.putString(Constants.EXTRA_TITLE, getString(R.string.channels));

		setPageAdapter(new ViewPageAdapter(this, getFragmentManager(), getViewPager()));
		getPageAdapter().addPage(ChannelsFragment.class, channelsBundle);
		getViewPager().setAdapter(getPageAdapter());

		// Set the initial position.
		// TODO: Handle override from Extras
		getViewPager().setCurrentItem(0);
		getPageAdapter().onPageSelected(0);
	}
}
