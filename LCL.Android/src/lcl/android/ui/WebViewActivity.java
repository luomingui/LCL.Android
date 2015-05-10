package lcl.android.ui;

import lcl.android.R;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;

public class WebViewActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ∑¿÷π–›√ﬂ
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.viewpage);
		InitialData();
	}

	private WebView webview = null;
	private String url;

	private void InitialData() {
		url = getIntent().getStringExtra("url");
		webview = (WebView) findViewById(R.id.webview);
		webview.loadUrl(url);
	}
}
