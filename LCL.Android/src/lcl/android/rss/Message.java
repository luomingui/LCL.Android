package lcl.android.rss;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * http://www.cnblogs.com/jyan/articles/2554799.html
 */
public class Message implements Comparable<Message> {
	static SimpleDateFormat FORMATTER = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	private String title;
	private String link;
	private String description;
	private Date date;

	public Message() {
		this.title = "";
		this.link = "";
		this.description = "";
	}

	public Message(String title, String url, String content) {
		this.title = title;
		this.link = url;
		this.description = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title.trim();
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description.trim();
	}

	public String getDate() {
		return FORMATTER.format(this.date);
	}

	public void setDate(String date) {
		while (!date.endsWith("00")) {
			date += "0";
		}
		try {
			this.date = FORMATTER.parse(date.trim());
		} catch (ParseException e) {
			this.date = new Date();
		}
	}

	public Message copy() {
		Message copy = new Message();
		copy.title = title;
		copy.link = link;
		copy.description = description;
		copy.date = date;
		return copy;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Title: ");
		sb.append(title);
		sb.append('\n');
		sb.append("Date: ");
		sb.append(this.getDate());
		sb.append('\n');
		sb.append("Link: ");
		sb.append(link);
		sb.append('\n');
		sb.append("Description: ");
		sb.append(description);
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Message) {
			Message u = (Message) obj;
			return this.link == u.link && this.title == u.title;
		}
		return super.equals(obj);
	}

	public int compareTo(Message another) {
		if (another == null)
			return 1;
		return another.date.compareTo(date);
	}
}