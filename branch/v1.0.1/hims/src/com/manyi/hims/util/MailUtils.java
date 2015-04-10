package com.manyi.hims.util;

import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.manyi.hims.Global;

public class MailUtils {
	
	public static boolean setSendMail(String to_mail_address, String setText,String send_mail_address,String subject) {
		//System.out.println("send_mail_address-------------"+send_mail_address);
		boolean flag=false;
		try {
			
			send_mail_address = Global.MAIL_USERNAME;
			
			String password = Global.MAIL_PASSWORD; // 发行人邮箱密码
//			String smtp_server = "smtp.exmail.qq.com";
			String smtp_server = Global.SMTP_SERVER;
			String from_mail_address = send_mail_address;
			Authenticator auth = new PopupAuthenticator1(send_mail_address, password);
			Properties mailProps = new Properties();
			mailProps.put("mail.smtp.auth", "true");
			mailProps.put("username", send_mail_address);
			mailProps.put("password", password);
			mailProps.put("mail.smtp.host", smtp_server);
			mailProps.put("mail.smtp.localhost", "localHostAdress");
			//Session mailSession = Session.getDefaultInstance(mailProps, auth);
			Session mailSession = Session.getInstance(mailProps, auth);  
			MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(from_mail_address));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to_mail_address));
			message.setSubject(subject);
			MimeMultipart multi = new MimeMultipart("related");
			BodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setDataHandler(new javax.activation.DataHandler(
					setText, "text/html;charset=UTF-8"));
			//textBodyPart.setText(setText);
			multi.addBodyPart(textBodyPart);
			message.setContent(multi);
			message.saveChanges();
			Transport.send(message);
			flag = true;
		} catch (Exception ex) {
			System.err.println("邮件发送失败的原因是：" + ex.getMessage());
			System.err.println("具体错误原因：");
			ex.printStackTrace(System.err);
		}
		return flag;
	}
	
	
}

class PopupAuthenticator1 extends Authenticator {
	private String username;
	private String password;

	public PopupAuthenticator1(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.username, this.password);
	}
	
}
