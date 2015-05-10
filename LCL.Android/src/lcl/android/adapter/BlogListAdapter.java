package lcl.android.adapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lcl.android.R;
import lcl.android.entity.BlogEntity;
import lcl.android.ui.BlogDetail;
import lcl.android.utility.StringUtils;
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

public class BlogListAdapter extends BaseAdapter {

	private List<BlogEntity> list;
	private Context ctx;
	LayoutInflater inflater;

	public BlogListAdapter(Context context, List<BlogEntity> list) {
		this.list = list;
		this.ctx = context;
		inflater = LayoutInflater.from(ctx);
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

		final BlogEntity msg = list.get(position);
		holder.getTxtTitle().setText(replaceBlank(msg.GetBlogTitle()));
		holder.getTxtContent().setText(replaceBlank(msg.GetSummary()));
		holder.getImage().setImageResource(R.drawable.def);

		row.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 显示内容页
				int id = position;
				System.out.println("id " + id);
				Intent intent = new Intent(ctx, BlogDetail.class);
				Bundle bundle = new Bundle();
				bundle.putString("title", replaceBlank(msg.GetBlogTitle()));
				bundle.putString("url", msg.GetBlogUrl().trim());
				bundle.putString("detail", msg.GetSummary());
				bundle.putString("date",
						StringUtils.DateToChineseString(msg.GetUpdateTime()));

				bundle.putInt("img", R.drawable.def);

				intent.putExtras(bundle);
				ctx.startActivity(intent);
			}
		});

		return row;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		BlogEntity blog = list.get(position);
		return blog.GetBlogId();
	}

	/**
	 * 得到数据
	 * 
	 * @return
	 */
	public List<BlogEntity> GetData() {
		return list;
	}

	/**
	 * 插入
	 * 
	 * @param list
	 */
	public void InsertData(List<BlogEntity> list) {
		this.list.addAll(0, list);
		this.notifyDataSetChanged();
	}

	/**
	 * 增加数据
	 * 
	 * @param list
	 */
	public void AddMoreData(List<BlogEntity> list) {
		this.list.addAll(list);
		this.notifyDataSetChanged();
	}

	/**
	 * 移除数据
	 * 
	 * @param entity
	 */
	public void RemoveData(BlogEntity entity) {
		for (int i = 0, len = this.list.size(); i < len; i++) {
			if (this.list.get(i).GetBlogId() == entity.GetBlogId()) {
				this.list.remove(i);
				this.notifyDataSetChanged();
				break;
			}
		}
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