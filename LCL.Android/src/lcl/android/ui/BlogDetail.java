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

	private String blogTitle;// 标题
	private String blogAuthor;// 作者
	private String blogDate;// 发表时间
	private String blogUrl;// 文章链接
	private String blogdetail;// 文章简介

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 防止休眠
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.blog_detail);
		InitialData();
	}

	ProgressDialog progressDialog;

	/**
	 * 初始化
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void InitialData() {
		blogTitle = getIntent().getStringExtra("title");
		blogAuthor = "罗敏贵";
		blogDate = getIntent().getStringExtra("date");
		blogUrl = getIntent().getStringExtra("url");
		blogdetail = getIntent().getStringExtra("detail");

		try {
			System.out.println("BlogDetail url: " + blogUrl);
			// 返回
			ImageView mBack = (ImageView) findViewById(R.id.listview_context_back);
			mBack.setOnClickListener(UIHelper.finish(this));
			// 分享
			ImageView mFavorite = (ImageView) findViewById(R.id.listview_context_share);
			mFavorite.setOnClickListener(shareClickListener);

			// webView
			webView = (WebView) this
					.findViewById(R.id.listview_context_details);
			webView.getSettings().setDefaultTextEncodingName("utf-8");// 避免中文乱码
			webView.getSettings().setJavaScriptEnabled(true);
			webView.setKeepScreenOn(true);
			webView.getSettings().setBuiltInZoomControls(true);

			webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
			UIHelper.addWebImageShow(this, webView);
			// 读取用户设置：是否加载文章图片--默认有wifi下始终加载图片
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

						// 添加点击图片放大支持
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
				progressDialog.setMessage("正在加载...");

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
			LogUtils.Error("ContextActivity InitialData error ：", e);
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
			// 分享到
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
			String blogInfo = "作者: " + blogAuthor + "   发表时间:" + blogDate;

			String detail = HttpUtil.geturl(url, null);
			if (detail != "0" && detail.length() > 3) {
				blogdetail = HtmlRegexpUtil.readHtmlDiv(detail);
			} else {
				blogdetail = blogdetail + "<br/><a href=" + blogUrl
						+ ">原文地址</a><br/><br/> ";
			}
			htmlContent = htmlContent.replace("#title#", blogTitle)
					.replace("#time#", blogInfo)
					.replace("#content#", blogdetail);
		} catch (Exception e) {
			LogUtils.Error("ContextActivity GetDetailByUrl error ：", e);
		}
		return htmlContent;
	}
}
