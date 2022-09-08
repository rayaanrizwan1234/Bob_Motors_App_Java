package Manager;
import javax.mail.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import java.util.Properties;
public class EmailConnect {
	public static void compile(String from1, String to1, String Subject, String text) {
      Properties properties = createConfiguration();
      SmtpAuthenticator authentication = new SmtpAuthenticator();
      Session session = Session.getDefaultInstance(properties, authentication);
      try {
      MimeMessage message = new MimeMessage(session);
          message.setFrom(new InternetAddress(from1));
          message.addRecipient(Message.RecipientType.TO,
                  new InternetAddress(to1));
          message.setSubject(Subject);
          message.setText(text);
          Transport.send(message);

          System.out.println("Message sent successfully");
      } catch (MessagingException mex) {
          mex.printStackTrace();
     }
  }
  private static Properties createConfiguration() {
      return new Properties() {
          {
              put("mail.smtp.auth", "true");
              put("mail.smtp.host", "smtp.gmail.com");
              put("mail.smtp.port", "587");
              put("mail.smtp.starttls.enable", "true");
          }
      };
  }
  private static class SmtpAuthenticator extends Authenticator {

      private String username = "csiatest12@gmail.com";
      private String password = "eragon96";
      @Override
      public PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username, password);
      }
  }
 

}
