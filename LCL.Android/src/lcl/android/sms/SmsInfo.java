package lcl.android.sms;

/**
 * class description：获取短信各种信息的类
 */
public class SmsInfo {
	/**
	 * 短信内容
	 */
	private String smsbody;
	/**
	 * 发送短信的电话号码
	 */
	private String phoneNumber;
	/**
	 * 发送短信的日期和时间
	 */
	private String date;
	/**
	 * 发送短信人的姓名
	 */
	private String name;
	/**
	 * 短信类型1是接收到的，2是已发出
	 */
	private String type;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("发送短信的电话号码: ").append(phoneNumber).append('\n');
		sb.append("短信内容: ").append(smsbody).append('\n');
		sb.append("短信类型1收2出: ").append(type).append('\n');
		return sb.toString();
	}

	public String getSmsbody() {
		return smsbody;
	}

	public void setSmsbody(String smsbody) {
		this.smsbody = smsbody;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}