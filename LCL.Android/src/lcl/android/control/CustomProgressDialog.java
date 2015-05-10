package lcl.android.control;

import lcl.android.R;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class CustomProgressDialog extends Dialog {
	public Context context;

	public CustomProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	public CustomProgressDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.context = context;
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		this.setCancelable(false);
		View view = LayoutInflater.from(context).inflate(
				R.layout.loading_buffer, null);
		this.setContentView(view);
	}

}
