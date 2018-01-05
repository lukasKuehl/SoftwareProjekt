package controller;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author Sascha Leonardo
 * @Ergänzung Lukas Kühl
 */

class MailStrg {	
	
	//Ergänzung der Paramter message und iconPath
	protected void sendMail(String username, String password,
			String senderAddress, String recipientsAddress, String subject, String message, String iconPath ){
		
		Authenticator auth = getAuthenticator(username, password);
		Properties properties = getProperties();

		// Hier wird mit den Properties und dem Authenticator eine Session erzeugt
		Session session = Session.getDefaultInstance(properties, auth);

		// Message senden
		sendMessage(session, senderAddress, recipientsAddress, subject, message, iconPath);

	}

	/**
	 * Eigenschaften des Mail-Servers werden gesetzt
	 * @return
	 */
	protected Properties getProperties() {
		Properties properties = new Properties();
		// Den Properties wird die ServerAdresse hinzugefügt
		properties.put("mail.smtp.host", "smtp.web.de");
		// !!Wichtig!! Falls der SMTP-Server eine Authentifizierung verlangt
		// muss an dieser Stelle die Property auf "true" gesetzt
		// werden
		properties.put("mail.smtp.auth", "true");
		// Port setzen
		properties.put("mail.smtp.port", "587");
		// Protokoll festlegen
		properties.put("mail.transport.protocol", "smtp");
		// Verschlüsselung festlegen
		properties.put("mail.smtp.starttls.enable", "true");
		
		//Ergänzung um aufgetretene Handshake-Exception zu vermeiden
		properties.put("mail.smtp.ssl.trust", "smtp.web.de");
		return properties;
	}

	/**
	 * Nachricht wird gesendet
	 * @param session
	 * @param senderAddress
	 * @param recipientsAddress
	 * @param subject
	 */
	protected void sendMessage(Session session, String senderAddress,
			String recipientsAddress, String subject, String message, String iconPath) {
		try {
			// Eine neue Message erzeugen
			Message msg = new MimeMessage(session);

			// Hier werden die Absender- und Empfängeradressen gesetzt
			msg.setFrom(new InternetAddress(senderAddress, "Einsatzplanverwaltung"));

			// msg.addRecipient(Message.RecipientType.TO, new
			// InternetAddress(recipientsAddress));
			msg.setRecipient(RecipientType.TO, new InternetAddress(
					recipientsAddress));

			// Der Betreff wird gesetzt
			msg.setSubject(subject);

			// multipart message erstellen, in dem Text und Attachment gepuffert
			// werden
			Multipart multipart = new MimeMultipart();

			// Der Text wird gesetzt
			multipart.addBodyPart(getText(message));

			// Das Attachment wird hinzugefügt
			multipart.addBodyPart(getAttachment(iconPath));

			// Text und Attachment zur Nachricht hinzufügen
			msg.setContent(multipart);	
			
			// Senden der Nachricht
			Transport.send(msg);

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Nachrichtentext wird gesetzt
	 * @return
	 */
	protected BodyPart getText(String message) {
		try {
			// message part erstellen
			BodyPart messageBodyPart = new MimeBodyPart();

			// Mailtext hinzufügen
			String text = message;
			messageBodyPart.setText(text);

			// Daten zurückgeben
			return messageBodyPart;
		} catch (MessagingException me) {
			me.printStackTrace();
			return null;
		}

	}

	/**
	 * Angang wird hinzugefügt
	 * @return
	 */
	protected BodyPart getAttachment(String iconPath) {
		try {
			// message part erstellen
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart = new MimeBodyPart();

			// Anhang hinzufügen
			// Datei in Fileobjekt speichern
			String filename= iconPath;
			System.out.println(filename + " wird gesendet...");
			File file = new File(filename);
			
			// Datenquelle verknüpfen/registrieren
			DataSource source = new FileDataSource(file);
			
			// Datenquelle übergeben
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(file.getName());
			
			// Rückgabe vom BodyPart
			return messageBodyPart;
		} catch (MessagingException me) {
			me.printStackTrace();
			return null;
		}

	}

	/**
	 * Die Methode erzeugt ein MailAuthenticator Objekt aus den beiden
	 * Parametern user und passwort des Mailaccounts.
	 * 
	 * @param user
	 * @param password
	 */
	protected Authenticator getAuthenticator(final String user, final String password) {
		try {
			Authenticator auth = new Authenticator() {
				/**
				 * Diese Methode gibt ein neues PasswortAuthentication Objekt
				 * zurueck.
				 * 
				 * @see javax.mail.Authenticator#getPasswordAuthentication()
				 */
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			};
			return auth;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

