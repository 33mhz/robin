package in.pnutrob.client.alpha;

import android.os.Bundle;

import in.lib.Constants;
import in.lib.adapter.ViewPageAdapter;
import in.lib.utils.Views.Injectable;
import in.pnutrob.client.alpha.base.BaseActivity;
import in.pnutrob.client.alpha.fragment.SearchPostFragment;
import in.pnutrob.client.alpha.fragment.SearchUserFragment;

@Injectable
public class SearchResultsActivity extends BaseActivity
{
	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		String term = getIntent().getStringExtra(Constants.EXTRA_SEARCH_TERM);

		Bundle searchBundle = new Bundle(getIntent().getExtras());
		searchBundle.putString(Constants.EXTRA_TITLE, getString(R.string.search_results));

		setPageAdapter(new ViewPageAdapter(this, getFragmentManager(), getViewPager()));
		getPageAdapter().addPage(SearchPostFragment.class, searchBundle);
		getPageAdapter().addPage(SearchUserFragment.class, searchBundle);
		getViewPager().setAdapter(getPageAdapter());

		// Set the initial position.
		// TODO: Handle override from Extras
		getViewPager().setCurrentItem(0);
		getPageAdapter().onPageSelected(0);
	}
}
