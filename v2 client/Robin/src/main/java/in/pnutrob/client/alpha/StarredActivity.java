package in.pnutrob.client.alpha;

import android.os.Bundle;

import in.lib.Constants;
import in.lib.adapter.ViewPageAdapter;
import in.lib.utils.Views.Injectable;
import in.pnutrob.client.alpha.base.BaseActivity;
import in.pnutrob.client.alpha.fragment.StarredFragment;

@Injectable
public class StarredActivity extends BaseActivity
{
	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle starredBundle = new Bundle();

		if (getIntent().getExtras() != null)
		{
			starredBundle.putAll(getIntent().getExtras());
		}

		starredBundle.putString(Constants.EXTRA_TITLE, getString(R.string.starred));

		setPageAdapter(new ViewPageAdapter(this, getFragmentManager(), getViewPager()));
		getPageAdapter().addPage(StarredFragment.class, starredBundle);
		getViewPager().setAdapter(getPageAdapter());

		// Set the initial position.
		// TODO: Handle override from Extras
		getViewPager().setCurrentItem(0);
		getPageAdapter().onPageSelected(0);
	}
}
