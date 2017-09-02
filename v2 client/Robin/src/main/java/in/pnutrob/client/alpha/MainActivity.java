package in.pnutrob.client.alpha;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import in.lib.Constants;
import in.lib.adapter.ViewPageAdapter;
import in.lib.utils.Views.Injectable;
import in.pnutrob.client.alpha.base.BaseActivity;
import in.pnutrob.client.alpha.dialog.NewPostDialog;
import in.pnutrob.client.alpha.fragment.InteractionsParentFragment;
import in.pnutrob.client.alpha.fragment.RobinPostsFragment;
import in.pnutrob.client.alpha.fragment.TimelineFragment;
import in.pnutrob.client.alpha.fragment.GlobalPostsFragment;

@Injectable
public class MainActivity extends BaseActivity
{
	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

//		((ImageButton)getActionBar().getCustomView().findViewById(R.id.up_button)).setImageResource(ThemeHelper.getDrawableResource(this, R.attr.rbn_action_bar_menu_icon));
//		((ImageButton)getActionBar().getCustomView().findViewById(R.id.up_button)).setImageResource(R.drawable.ab_ic_menu_light);

		Bundle timelineBundle = new Bundle();
		timelineBundle.putString(Constants.EXTRA_TITLE, "Timeline");

		Bundle mentionsBundle = new Bundle();
		mentionsBundle.putString(Constants.EXTRA_TITLE, "Mentions");

		Bundle globalBundle = new Bundle();
		globalBundle.putString(Constants.EXTRA_TITLE, "Global Posts");

		Bundle robinBundle = new Bundle();
		robinBundle.putString(Constants.EXTRA_TITLE, "Robin Posts");

		setPageAdapter(new ViewPageAdapter(this, getFragmentManager(), getViewPager()));
		getPageAdapter().addPage(TimelineFragment.class, timelineBundle);
		getPageAdapter().addPage(InteractionsParentFragment.class, mentionsBundle);
		getPageAdapter().addPage(GlobalPostsFragment.class, globalBundle);
		getPageAdapter().addPage(RobinPostsFragment.class, robinBundle);
		getViewPager().setAdapter(getPageAdapter());

		// Set the initial position.
		// TODO: Handle override from Extras
		if (savedInstanceState == null)
		{
			setPage(0);
		}
	}

	public void setPage(int pageIndex)
	{
		getViewPager().setCurrentItem(pageIndex);
		getPageAdapter().onPageSelected(pageIndex);
	}

	@Override public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.menu_new_post)
		{
			Intent newPost = new Intent(this, NewPostDialog.class);
			startActivity(newPost);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override public void onUpSelected()
	{
		getSlidingMenu().showMenu(true);
	}
}
