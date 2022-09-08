package application;



import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Manager.AlertBox;
import Manager.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class MainController implements Initializable {
@FXML
private Label lblStatus;

@FXML
private TextField txtUserName;

@FXML
private PasswordField txPassword;

@FXML
public ComboBox<String> combobox;

@FXML
private TextField txttest;


Connection con;
PreparedStatement pst;
ResultSet rs;


ObservableList<String> list = FXCollections.observableArrayList("Client","Employee","Manager");
@Override
public void initialize(URL location, ResourceBundle resources) {
	// TODO Auto-generated method stub
	combobox.setItems(list);
}

int i = 1;
int n = 3;
public void Login(javafx.event.ActionEvent event) throws Exception {
	String uname = txtUserName.getText();
	String passw = txPassword.getText();
	String pos = combobox.getValue();
	if(uname.equals("") || passw.equals("") || combobox.getValue().equals("")) {
		Alert alert=new Alert(AlertType.INFORMATION);
	    alert.setTitle("Login Credentials");
	    alert.setHeaderText("Information Dialogue");
	    alert.setContentText("Please fill in all text fields!");
	    alert.showAndWait();
	}else {

			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost/login?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","" );
				
pst = con.prepareStatement("select * from  login where username=? and password=? and position = ?");
			
			pst.setString(1, uname);
			pst.setString(2, passw);
			pst.setString(3, pos);
			rs = pst.executeQuery();
				
				if(rs.next()) {
					if(combobox.getValue().equals("Manager")) {
				((Node)event.getSource()).getScene().getWindow().hide();
						lblStatus.setText("Success");
						Stage primaryStage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("/Manager/Manager.fxml"));
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("/Manager/button.css").toExternalForm());
						System.out.print("ewefwfefw");
						primaryStage.setScene(scene);
						primaryStage.show();	
					} else if(combobox.getValue().equals("Client")){
					((Node)event.getSource()).getScene().getWindow().hide();
					lblStatus.setText("Success");
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/Manager/Client.fxml"));
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("/Manager/button.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.show();
				}
				else if(combobox.getValue().equals("Employee")){
					((Node)event.getSource()).getScene().getWindow().hide();
					lblStatus.setText("Success");
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/Manager/Employee.fxml"));
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("/Manager/button.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			}
				else {
					n = n-i;
					lblStatus.setText("Failed");
					AlertBox.display("The username/password you have entered is incorrect", "You have "+n+" tries left.");
					if (n == 0) {
					close();
					}
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		
	}
}



public void close() {
System.exit(n);
} 

public void SignUp(javafx.event.ActionEvent event) throws IOException {
	try {
		
	Stage primaryStage = new Stage();
	Parent root = FXMLLoader.load(getClass().getResource("/application/SignUp.fxml"));
	Scene scene = new Scene(root);
	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	primaryStage.setScene(scene);
	primaryStage.show();
	
	}catch(Exception e) {
		e.printStackTrace();
	} 
	
}



public static boolean lengthCheck(String string) {
	char[]x = string.toCharArray();
	boolean found = true;
	if(x.length>5) {
		found = false;;
	}
	return found;
}
}


