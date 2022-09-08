package Manager;

import java.security.KeyStore.PasswordProtection;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class EmailController {

    @FXML
    private TextField txtSendTo;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField txtMessage;

    @FXML
    private TextField txtSendFrom;



public void send(ActionEvent actionEvent) {
	SendEmail();
}

@SuppressWarnings("null")
public void SendEmail() {
	String to = txtSendTo.getText();
	String host = "smtp.gmail.com";
	String from = txtSendFrom.getText();
final String username = txtSendFrom.getText();
final String password = txtPassword.getText();

  Properties props = System.getProperties();
  
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable", "true");
props.put("mail.smtp.host", host);
props.put("mail.smtp.port", "587");

Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
});

try {
	MimeMessage m = new MimeMessage(session);
	m.setFrom(new InternetAddress(from));
	m.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
	m.setSubject(txtSubject.getText());
	m.setText(txtMessage.getText());
	
	Transport.send(m);
	Node sentBoolValue = null;
	sentBoolValue.setVisible(true);
	System.out.println("message sent");
} catch (MessagingException e) {
	e.printStackTrace();
}
}

}



// omgitzray23@gmail.com
// csiatest12@gmail.com
// eragon96
