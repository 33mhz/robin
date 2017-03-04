package in.pnutrob.client;

import android.os.Bundle;

import in.lib.adapter.ViewPageAdapter;
import in.lib.utils.Views.Injectable;
import in.pnutrob.client.base.BaseActivity;
import in.pnutrob.client.fragment.MentionsFragment;
import in.pnutrob.client.fragment.ProfileFragment;
import in.pnutrob.client.fragment.UserFriendsFragment;

@Injectable
public class ProfileActivity extends BaseActivity
{
	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle args = new Bundle();

		if (getIntent().getExtras() != null)
		{
			args.putAll(getIntent().getExtras());
		}

		setPageAdapter(new ViewPageAdapter(this, getFragmentManager(), getViewPager()));
		getPageAdapter().addPage(ProfileFragment.class, args);
		getPageAdapter().addPage(MentionsFragment.class, args);
		getPageAdapter().addPage(UserFriendsFragment.class, args);
		getViewPager().setAdapter(getPageAdapter());

		// Set the initial position.
		// TODO: Handle override from Extras
		getViewPager().setCurrentItem(0);
		getPageAdapter().onPageSelected(0);
	}
}
