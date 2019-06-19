package com.aibibang.demo.mail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @author: Truman.P.Du 
 * @since: 2016年1月28日 上午9:59:46 
 * @version: v1.0
 * @description:发送邮件公共类
 */
public class MailUtil {

	protected final Logger logger = LoggerFactory.getLogger(MailUtil.class);

	public boolean send(Mail mail) {
		// 发送email
		HtmlEmail email = new HtmlEmail();
		try {
			// 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
			email.setHostName(mail.getHost());
			// 字符编码集的设置
			email.setCharset(Mail.ENCODEING);
			// 收件人的邮箱
			email.addTo(mail.getReceiver());
			// 发送人的邮箱
			email.setFrom(mail.getSender(), mail.getName());
			// 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
			email.setAuthentication(mail.getUsername(), mail.getPassword());
			// 要发送的邮件主题
			email.setSubject(mail.getSubject());
			// 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
			email.setMsg(mail.getMessage());
			email.setDebug(true);
			email.setSmtpPort(25);
			// 发送
			email.send();
			logger.debug(mail.getSender() + " 发送邮件到 " + mail.getReceiver());
			return true;
		} catch (EmailException e) {
			e.printStackTrace();
			logger.error(mail.getSender() + " 发送邮件到 " + mail.getReceiver()+ " 失败");
			return false;
		}
	}
	public static void main(String[] args) {
		Mail mail = new Mail();
		mail.setHost("******"); // 设置邮件服务器
		mail.setSender("******@gmail.com");
		mail.setReceiver("******@gmail.com"); // 接收人
		mail.setUsername("******"); // 登录账号,一般都是和邮箱名一样吧
		mail.setPassword("******"); // 发件人邮箱的登录密码
		mail.setSubject("aaaaaaaaa");
		mail.setMessage("bbbbbbbbbbbbbbbbb");
		new MailUtil().send(mail);
	}
}

