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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Blog extends Fragment {

	public View rootView;
	private MyListView listView;
	private List<Message> messages;
	private ListItemAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.blog, container, false);

		InitialControls();
		BindControls();

		return rootView;
	}

	/**
	 * 初始化列表
	 */
	private void InitialControls() {
		listView = (MyListView) rootView.findViewById(R.id.list_data);

		messages = ApiClient.GetBlogArticleList(rootView, true);
		adapter = new ListItemAdapter(getActivity(), messages);
		listView.setAdapter(adapter);
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
							// 获取网络数据
							List<Message> msges = ApiClient.GetBlogArticleList(
									rootView, false);
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
									"BlogFragment BindControls  listView.onRefresh error ：",
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

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}