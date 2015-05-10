package lcl.android.ui;

import lcl.android.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class Works extends Fragment implements OnClickListener {

	public View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.work, container, false);

		InitFrm();

		return rootView;
	}

	public void InitFrm() {
		RelativeLayout layout_item1, layout_item2, layout_item3, layout_item4, layout_item5;

		layout_item1 = (RelativeLayout) rootView
				.findViewById(R.id.layout_item1);
		layout_item2 = (RelativeLayout) rootView
				.findViewById(R.id.layout_item2);
		layout_item3 = (RelativeLayout) rootView
				.findViewById(R.id.layout_item3);
		layout_item4 = (RelativeLayout) rootView
				.findViewById(R.id.layout_item4);
		layout_item5 = (RelativeLayout) rootView
				.findViewById(R.id.layout_item5);

		layout_item1.setOnClickListener(this);
		layout_item2.setOnClickListener(this);
		layout_item3.setOnClickListener(this);
		layout_item4.setOnClickListener(this);
		layout_item5.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(rootView.getContext(), BlogDetail.class);
		Bundle bundle = new Bundle();
		bundle.putString("detail", "正在加载....");
		bundle.putString("date", "");
		bundle.putInt("img", R.drawable.def);

		switch (v.getId()) {
		case R.id.layout_item1:
			bundle.putString("title", "排课系统（小学版）");
			bundle.putString("url",
					"file:///android_asset/Works/Syllabus/Index.html");
			break;
		case R.id.layout_item2:
			bundle.putString("title", "小区信息管理系统");
			bundle.putString("url",
					"file:///android_asset/Works/Village/Index.html");
			break;
		case R.id.layout_item3:
			bundle.putString("title", "企业档案管理系统");
			bundle.putString("url",
					"file:///android_asset/Works/Company/Index.html");
			break;
		case R.id.layout_item4:
			intent = new Intent(rootView.getContext(), WorksClcs.class);
			bundle = new Bundle();
			bundle.putString("worksCode", "CLCS");
			break;
		case R.id.layout_item5:
			bundle.putString("title", "客户关系管理系统（CRM）");
			bundle.putString("url",
					"file:///android_asset/Works/CRM/Index.html");
			break;
		}
		intent.putExtras(bundle);
		rootView.getContext().startActivity(intent);
	}
}
