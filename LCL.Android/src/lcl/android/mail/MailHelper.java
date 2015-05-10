package lcl.android.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import lcl.android.utility.LogUtils;

public class MailHelper {
	/**
	 * �ʼ����ͳ���
	 * 
	 * @param to
	 *            ������
	 * @param subject
	 *            �ʼ�����
	 * @param content
	 *            �ʼ�����
	 * @throws Exception
	 * @throws MessagingException
	 */
	public static void sendEmail(String to, String subject, String content) {
		try {
			String host = "smtp.163.com";
			String address = "minguiluo@163.com";
			String from = "minguiluo@163.com";
			String password = "4485083";// ����
			if ("".equals(to) || to == null) {
				to = "271391233@qq.com";
			}
			String port = "25";
			SendEmail(host, address, from, password, to, port, subject, content);
		} catch (MessagingException ex) {
			LogUtils.Error("MailHelper sendEmail MessagingException: ", ex);
		} catch (Exception e) {
			LogUtils.Error("MailHelper sendEmail Exception: ", e);
		}
	}

	/**
	 * �ʼ����ͳ���
	 * 
	 * @param host
	 *            �ʼ������� �磺smtp.qq.com
	 * @param address
	 *            �����ʼ��ĵ�ַ �磺545099227@qq.com
	 * @param from
	 *            ���ԣ� wsx2miao@qq.com
	 * @param password
	 *            ������������
	 * @param to
	 *            ������
	 * @param port
	 *            �˿ڣ�QQ:25��
	 * @param subject
	 *            �ʼ�����
	 * @param content
	 *            �ʼ�����
	 * @throws Exception
	 */
	public static void SendEmail(String host, String address, String from,
			String password, String to, String port, String subject,
			String content) throws Exception {
		Multipart multiPart;
		String finalString = "";

		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", address);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);
		DataHandler handler = new DataHandler(new ByteArrayDataSource(
				finalString.getBytes(), "text/plain"));
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setDataHandler(handler);
		multiPart = new MimeMultipart();
		InternetAddress toAddress;
		toAddress = new InternetAddress(to);
		message.addRecipient(Message.RecipientType.TO, toAddress);
		message.setSubject(subject);
		message.setContent(multiPart);
		message.setText(content);
		Transport transport = session.getTransport("smtp");
		transport.connect(host, address, password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
}
