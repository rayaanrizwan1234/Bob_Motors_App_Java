package Manager;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class emailSendingController extends EmailConnect {
	
	@FXML 
	private Button sendButton; 
	
	@FXML 
	private TextField to;
	
	@FXML 
	private TextField subject;
	
	@FXML 
	private TextField text;
	
	
	@FXML 
	public void sendEmailButton (ActionEvent e) throws IOException {
		emailSendingController.compile("csiatest12@gmail.com",to.getText() , subject.getText(), text.getText());
		Alert alert=new Alert(AlertType.INFORMATION);
		    alert.setTitle("Email");
		    alert.setHeaderText("Information Dialogue");
		    alert.setContentText("Email Sent Successfully");
		   
		    alert.showAndWait();
		    System.out.println("Email = Sent Successfully");
	}
	

	
	public void clear(javafx.event.ActionEvent event)throws IOException {

		to.clear();
		subject.clear();
		text.clear();
	}


	
	

}
