package in.pnutrob.client.alpha;

import android.os.Bundle;

import in.lib.Constants;
import in.lib.adapter.ViewPageAdapter;
import in.lib.utils.Views.Injectable;
import in.pnutrob.client.alpha.base.BaseActivity;
import in.pnutrob.client.alpha.fragment.settings.AdditionalSettingsFragment;
import in.pnutrob.client.alpha.fragment.settings.AppearanceSettingsFragment;
import in.pnutrob.client.alpha.fragment.settings.GeneralSettingsFragment;
import in.pnutrob.client.alpha.fragment.settings.NotificationSettingsFragment;

@Injectable
public class SettingsActivity extends BaseActivity
{
	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle generalBundle = new Bundle();
		generalBundle.putString(Constants.EXTRA_TITLE, getString(R.string.general));

		Bundle notificationBundle = new Bundle();
		notificationBundle.putString(Constants.EXTRA_TITLE, getString(R.string.notifications));

		Bundle appearanceBundle = new Bundle();
		appearanceBundle.putString(Constants.EXTRA_TITLE, getString(R.string.appearance));

		Bundle additionalBundle = new Bundle();
		additionalBundle.putString(Constants.EXTRA_TITLE, getString(R.string.additional));

		setPageAdapter(new ViewPageAdapter(this, getFragmentManager(), getViewPager()));
		getPageAdapter().addPage(GeneralSettingsFragment.class, generalBundle);
		getPageAdapter().addPage(NotificationSettingsFragment.class, notificationBundle);
		getPageAdapter().addPage(AppearanceSettingsFragment.class, appearanceBundle);
		getPageAdapter().addPage(AdditionalSettingsFragment.class, additionalBundle);
		getViewPager().setAdapter(getPageAdapter());

		// Set the initial position.
		// TODO: Handle override from Extras
		setPage(0);
	}

	public void setPage(int pageIndex)
	{
		getViewPager().setCurrentItem(pageIndex);
		getPageAdapter().onPageSelected(pageIndex);
	}
}
