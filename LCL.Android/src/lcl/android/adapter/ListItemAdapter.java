package lcl.android.adapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lcl.android.R;
import lcl.android.rss.Message;
import lcl.android.ui.BlogDetail;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItemAdapter extends BaseAdapter {

	private List<Message> messages;
	private Context ctx;
	LayoutInflater inflater;

	public ListItemAdapter(Context context, List<Message> _messages) {
		messages = _messages;
		this.ctx = context;
		inflater = LayoutInflater.from(ctx);
	}

	@Override
	public int getCount() {
		return messages.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return messages.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	// / ListView Item设置
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolder holder;
		if (row == null) {
			row = inflater.inflate(R.layout.blog_listitem, null);
			holder = new ViewHolder(row);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}

		final Message msg = messages.get(position);
		holder.getTxtTitle().setText(replaceBlank(msg.getTitle()));
		holder.getTxtContent().setText(replaceBlank(msg.getDescription()));
		holder.getImage().setImageResource(R.drawable.def);

		row.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 显示内容页
				int id = position;
				System.out.println("id " + id);
				Intent intent = new Intent(ctx, BlogDetail.class);
				Bundle bundle = new Bundle();
				bundle.putString("title", replaceBlank(msg.getTitle()));
				bundle.putString("url", msg.getLink().trim());
				bundle.putString("detail", msg.getDescription());
				bundle.putString("date", msg.getDate());

				bundle.putInt("img", R.drawable.def);

				intent.putExtras(bundle);
				ctx.startActivity(intent);
			}
		});

		return row;
	}

	// 去除字符串中的空格、回车、换行符、制表符
	public String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	// layout.list_row
	class ViewHolder {
		View base;
		ImageView imgView;
		TextView txt_title;
		TextView txt_content;

		public ViewHolder(View base) {
			this.base = base;
		}

		public ImageView getImage() {
			if (imgView == null) {
				imgView = (ImageView) base.findViewById(R.id.img_row);
			}
			return imgView;
		}

		public TextView getTxtTitle() {
			if (txt_title == null) {
				txt_title = (TextView) base.findViewById(R.id.txt_title);
			}
			return txt_title;
		}

		public TextView getTxtContent() {
			if (txt_content == null) {
				txt_content = (TextView) base.findViewById(R.id.txt_content);
			}
			return txt_content;
		}
	}
}
