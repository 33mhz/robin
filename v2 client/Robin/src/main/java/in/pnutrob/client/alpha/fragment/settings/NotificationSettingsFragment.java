package in.pnutrob.client.alpha.fragment.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.lib.utils.Views;
import in.lib.utils.Views.Injectable;
import in.pnutrob.client.alpha.R;
import in.pnutrob.client.alpha.fragment.base.BaseFragment;

@Injectable
public class NotificationSettingsFragment extends BaseFragment
{
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.notification_settings_view, container, false);
		Views.inject(this, view);
		return view;
	}
}
