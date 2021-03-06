package in.pnutrob.client.alpha;

import android.os.Bundle;

import in.lib.Constants;
import in.lib.adapter.ViewPageAdapter;
import in.lib.utils.Views.Injectable;
import in.pnutrob.client.alpha.base.BaseActivity;
import in.pnutrob.client.alpha.fragment.ThreadFragment;

@Injectable
public class ThreadActivity extends BaseActivity
{
	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle threadBundle = new Bundle(getIntent().getExtras());
		threadBundle.putString(Constants.EXTRA_TITLE, "Thread");

		setPageAdapter(new ViewPageAdapter(this, getFragmentManager(), getViewPager()));
		getPageAdapter().addPage(ThreadFragment.class, threadBundle);
		getViewPager().setAdapter(getPageAdapter());

		// Set the initial position.
		// TODO: Handle override from Extras
		getViewPager().setCurrentItem(0);
		getPageAdapter().onPageSelected(0);
	}
}
