package in.pnutrob.client.alpha;

import android.os.Bundle;

import in.lib.Constants;
import in.lib.adapter.ViewPageAdapter;
import in.lib.utils.Views.Injectable;
import in.pnutrob.client.alpha.base.BaseActivity;
import in.pnutrob.client.alpha.fragment.DraftPostsFragment;

@Injectable
public class DraftsActivity extends BaseActivity
{
	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle args = new Bundle();

		if (getIntent().getExtras() != null)
		{
			args.putAll(getIntent().getExtras());
		}

		Bundle draftPostBundle = new Bundle(args);
		draftPostBundle.putString(Constants.EXTRA_TITLE, "Post Drafts");

		setPageAdapter(new ViewPageAdapter(this, getFragmentManager(), getViewPager()));
		getPageAdapter().addPage(DraftPostsFragment.class, draftPostBundle);
		getViewPager().setAdapter(getPageAdapter());

		// Set the initial position.
		// TODO: Handle override from Extras
		getViewPager().setCurrentItem(0);
		getPageAdapter().onPageSelected(0);
	}
}
