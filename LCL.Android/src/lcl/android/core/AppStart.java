package lcl.android.core;

import lcl.android.R;
import lcl.android.ui.BaseActivity;
import lcl.android.ui.Main;
import lcl.android.utility.LogUtils;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

public class AppStart extends BaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		final View view = View.inflate(this, R.layout.splash, null);
		setContentView(view);
		try {
			TextView title = (TextView) this.findViewById(R.id.txt_title);
			PackageInfo info = getPackageInfo();
			title.setText("���¿Ƽ� V " + info.versionName);

			// ����չʾ������
			AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
			aa.setDuration(2000);
			view.startAnimation(aa);
			aa.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationEnd(Animation arg0) {
					redirectTo();
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationStart(Animation animation) {
				}
			});
		} catch (Exception e) {
			LogUtils.Info("AppStart onCreate error��" + e.getMessage());
		}
	}

	/**
	 * ��ȡApp��װ����Ϣ
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	/**
	 * ��ת��...
	 */
	private void redirectTo() {
		Intent intent = new Intent(this, Main.class);
		startActivity(intent);
		finish();
	}
}
