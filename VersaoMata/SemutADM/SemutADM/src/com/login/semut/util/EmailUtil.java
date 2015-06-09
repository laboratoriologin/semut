package com.login.semut.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.topsys.exception.TSSystemException;

public class EmailUtil {

	/*
	 * caso queira mudar o servidor e a porta, so enviar para o contrutor os
	 * valor como string
	 */
	/*
	 * public EnviarEmail(String mailSMTPServer, String mailSMTPServerPort) { //
	 * Para // Servidor this.mailSMTPServer = mailSMTPServer;
	 * this.mailSMTPServerPort = mailSMTPServerPort; }
	 */

	public static void enviar(String to, String message) {


		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Constantes.REMETENTE, Constantes.SENHA_GMAIL);
			}
		});

		try {
			
			Message msg = new MimeMessage(session);

			// Setando o destinatário
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Setando a origem do email
			msg.setFrom(new InternetAddress(Constantes.REMETENTE));
			// Setando o assunto
			msg.setSubject(Constantes.ASSUNTO_EMAIL);
			// Setando o conteúdo/corpo do email
			msg.setContent(message, "text/html; charset=utf-8");

			Transport.send(msg);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}

// clase que retorna uma autenticacao para ser enviada e verificada pelo
// servidor smtp
class SimpleAuth extends Authenticator {
	public String username = null;
	public String password = null;

	public SimpleAuth(String user, String pwd) {
		username = user;
		password = pwd;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}

}