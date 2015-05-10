package lcl.android.ui;

import java.io.InputStream;

import lcl.android.R;
import lcl.android.core.AppConfig;
import lcl.android.core.AppContext;
import lcl.android.utility.HtmlRegexpUtil;
import lcl.android.utility.HttpUtil;
import lcl.android.utility.LogUtils;
import lcl.android.utility.UIHelper;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class BlogDetail extends BaseActivity {

	private String blogTitle;// ����
	private String blogAuthor;// ����
	private String blogDate;// ����ʱ��
	private String blogUrl;// ��������
	private String blogdetail;// ���¼��

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��ֹ����
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.blog_detail);
		InitialData();
	}

	ProgressDialog progressDialog;

	/**
	 * ��ʼ��
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void InitialData() {
		blogTitle = getIntent().getStringExtra("title");
		blogAuthor = "������";
		blogDate = getIntent().getStringExtra("date");
		blogUrl = getIntent().getStringExtra("url");
		blogdetail = getIntent().getStringExtra("detail");

		try {
			System.out.println("BlogDetail url: " + blogUrl);
			// ����
			ImageView mBack = (ImageView) findViewById(R.id.listview_context_back);
			mBack.setOnClickListener(UIHelper.finish(this));
			// ����
			ImageView mFavorite = (ImageView) findViewById(R.id.listview_context_share);
			mFavorite.setOnClickListener(shareClickListener);

			// webView
			webView = (WebView) this
					.findViewById(R.id.listview_context_details);
			webView.getSettings().setDefaultTextEncodingName("utf-8");// ������������
			webView.getSettings().setJavaScriptEnabled(true);
			webView.setKeepScreenOn(true);
			webView.getSettings().setBuiltInZoomControls(true);

			webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
			UIHelper.addWebImageShow(this, webView);
			// ��ȡ�û����ã��Ƿ��������ͼƬ--Ĭ����wifi��ʼ�ռ���ͼƬ
			if (blogUrl.length() > 0) {
				blogUrl = blogUrl.trim().replace("\n", "");
				if (blogUrl.toLowerCase().startsWith("http:")) {
					boolean isLoadImage;
					AppContext ac = (AppContext) getApplication();
					if (AppContext.NETTYPE_WIFI == ac.getNetworkType()) {
						isLoadImage = true;
					} else {
						isLoadImage = ac.isLoadImage();
					}
					String htmlContent = GetDetailByUrl(blogUrl);
					if (isLoadImage) {
						htmlContent = htmlContent.replaceAll(
								"(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
						htmlContent = htmlContent.replaceAll(
								"(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");

						// ��ӵ��ͼƬ�Ŵ�֧��
						htmlContent = htmlContent
								.replaceAll("(<img[^>]+src=\")(\\S+)\"",
										"$1$2\" onClick=\"javascript:mWebViewImageListener.onImageClick('$2')\"");
					} else {
						htmlContent = htmlContent.replaceAll(
								"<\\s*img\\s+([^>]*)\\s*>", "");
					}
					webView.loadDataWithBaseURL(AppConfig.LOCAL_PATH,
							htmlContent, AppConfig.mimeType,
							AppConfig.encoding, null);
				} else if (blogUrl.length() > 0) {
					TextView uititle = (TextView) findViewById(R.id.listview_context_title);
					uititle.setText(blogTitle);
					webView.loadUrl(blogUrl);
				}
				progressDialog = new ProgressDialog(this);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setMessage("���ڼ���...");

				webView.setWebViewClient(new MyWebViewClient() {
					@Override
					public void onPageFinished(WebView view, final String url) {
						progressDialog.dismiss();
					}

					@Override
					public void onPageStarted(WebView view, String url,
							Bitmap favicon) {
						if (!progressDialog.isShowing()) {
							progressDialog.show();
						}
					}
				});
			}

		} catch (Exception e) {
			LogUtils.Error("ContextActivity InitialData error ��", e);
		}
	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.startsWith("http")) {
				progressDialog.show();
				view.loadUrl(url);
				return true;
			} else {
				return false;
			}
		}
	}

	private View.OnClickListener shareClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			// ����
			UIHelper.showShareMore(BlogDetail.this, blogTitle, blogUrl);
		}
	};

	public String GetDetailByUrl(String url) {
		if (url.length() == 0)
			return "";
		String htmlContent = "";
		try {
			InputStream in = getAssets().open("Template/NewsDetail.html");
			byte[] temp = HttpUtil.readInputStream(in);
			htmlContent = new String(temp);
			String blogInfo = "����: " + blogAuthor + "   ����ʱ��:" + blogDate;

			String detail = HttpUtil.geturl(url, null);
			if (detail != "0" && detail.length() > 3) {
				blogdetail = HtmlRegexpUtil.readHtmlDiv(detail);
			} else {
				blogdetail = blogdetail + "<br/><a href=" + blogUrl
						+ ">ԭ�ĵ�ַ</a><br/><br/> ";
			}
			htmlContent = htmlContent.replace("#title#", blogTitle)
					.replace("#time#", blogInfo)
					.replace("#content#", blogdetail);
		} catch (Exception e) {
			LogUtils.Error("ContextActivity GetDetailByUrl error ��", e);
		}
		return htmlContent;
	}
}
