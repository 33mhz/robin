package in.pnutrob.client.alpha;

import android.os.Bundle;

import in.lib.Constants;
import in.lib.adapter.ViewPageAdapter;
import in.lib.utils.Views.Injectable;
import in.pnutrob.client.alpha.base.BaseActivity;
import in.pnutrob.client.alpha.fragment.StorageBrowserFragment;

@Injectable
public class StorageBrowserActivity extends BaseActivity
{
	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle storage = new Bundle();
		storage.putString(Constants.EXTRA_TITLE, getString(R.string.storage_title));

		setPageAdapter(new ViewPageAdapter(this, getFragmentManager(), getViewPager()));
		getPageAdapter().addPage(StorageBrowserFragment.class, storage);
		getViewPager().setAdapter(getPageAdapter());

		// Set the initial position.
		// TODO: Handle override from Extras
		getViewPager().setCurrentItem(0);
		getPageAdapter().onPageSelected(0);
	}
}
