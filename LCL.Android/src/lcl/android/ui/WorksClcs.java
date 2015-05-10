package lcl.android.ui;

import java.util.List;

import lcl.android.R;
import lcl.android.adapter.ListItemAdapter;
import lcl.android.api.ApiClient;
import lcl.android.control.MyListView;
import lcl.android.control.MyListView.OnRefreshListener;
import lcl.android.rss.Message;
import lcl.android.utility.LogUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class WorksClcs extends BaseActivity {

	private String worksCode;// 作品编号
	private MyListView listView;
	private List<Message> messages;
	private ListItemAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blog);
		try {
			InitialData();
			InitialControls();
			BindControls();

		} catch (Exception e) {
			LogUtils.Info("WorksClcs onCreate error：" + e.getMessage());
		}
	}

	/**
	 * 初始化列表
	 */
	private void InitialControls() {
		listView = (MyListView) findViewById(R.id.list_data);
		messages = GetMessagesList(true);
		adapter = new ListItemAdapter(this, messages);
		listView.setAdapter(adapter);
	}

	private void InitialData() {
		worksCode = this.getIntent().getStringExtra("worksCode");
	}

	private List<Message> GetMessagesList(boolean flg) {
		List<Message> msges;
		if (worksCode == "CLCS") {
			msges = ApiClient.GetClcsArticleList(this, flg);
			this.setTitle("采暖营业厅收费系统");
		} else if (worksCode == "CRM") {
			msges = ApiClient.GetClcsArticleList(this, flg);
			this.setTitle("客户关系管理系统");
		} else {
			msges = ApiClient.GetClcsArticleList(this, flg);
			this.setTitle("采暖营业厅收费系统");
		}
		return msges;
	}

	/**
	 * 绑定事件
	 */
	private void BindControls() {
		// 上拉刷新
		listView.setonRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						try {
							List<Message> msges = GetMessagesList(false);
							if (msges.size() > 0) {
								messages.clear();
								for (int i = 0; i < msges.size(); i++) {
									Message msg = msges.get(i);
									messages.add(msg);
								}
							}
							Log.i("debug", "ListView 下拉刷新..." + messages.size());
						} catch (Exception e) {
							LogUtils.Error(
									"WorksClcs BindControls  listView.onRefresh error ：",
									e);
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						adapter.notifyDataSetChanged();
						listView.onRefreshComplete();
					}
				}.execute(null, null, null);
			}
		});
	}
}