package Manager;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Manager.EnquireController.SmtpAuthenticator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EnquireController implements Initializable{
	
    @FXML
    private TextField txtClientName;

    @FXML
    private TextField txtClientNumber;
    
    @FXML
    private TextField txtClientEmail;

    @FXML
    private ComboBox<?> ComboID;
    
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs;
    final ObservableList options = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			comboID();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void comboID() throws SQLException {
		con = DBConnector.getConnection();
rs = con.createStatement().executeQuery("SELECT  `id` FROM `inventory` WHERE 1");
while(rs.next()) {
	options.add(rs.getString("id"));
}
ComboID.setItems(options);
rs.close();
con.close();
	}
	
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
	  public static class SmtpAuthenticator extends Authenticator {

	      private String username = "csiatest12@gmail.com";
	      private String password = "eragon96";
	      @Override
	      public PasswordAuthentication getPasswordAuthentication() {
	          return new PasswordAuthentication(username, password);
	      }
	  }
	  @FXML
	  public void Enquire(javafx.event.ActionEvent event) {
		emailSendingController.compile(txtClientEmail.getText(),"csiatest12@gmail.com" , "Product Enquiry", "Client Name: "+txtClientName.getText()+", Client PhoneNumber: "+txtClientNumber.getText()+", Client enquiring about Car: "+ComboID.getValue()+". Reply ASAP!");
		Alert alert=new Alert(AlertType.INFORMATION);
	    alert.setTitle("Email");
	    alert.setHeaderText("Information Dialogue");
	    alert.setContentText("Email Sent Successfully");
	   
	    alert.showAndWait();
	    System.out.println("Email = Sent Successfully"); 
	  }
	   
}
