package lcl.android.ui;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FrameworkList extends FragmentPagerAdapter {

	private ArrayList<Fragment> mFragments;

	public FrameworkList(FragmentManager fm) {
		super(fm);
		mFragments = new ArrayList<Fragment>();
		mFragments.add(new Blog());
		mFragments.add(new Framework());
		mFragments.add(new Works());
		mFragments.add(new Serve());
		mFragments.add(new About());
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}
}
