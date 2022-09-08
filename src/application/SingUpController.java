package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Manager.AlertBox;
import Manager.DBConnector;
import Manager.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class SingUpController implements Initializable{
	@FXML
    private TextField ManagerPassword;

    @FXML
    private TextField Username;

    @FXML
    private PasswordField Password;
    
    @FXML
    private ComboBox<String> positionCombo;
    
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs;
    ObservableList<String> list = FXCollections.observableArrayList("Client","Employee");
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
    	positionCombo.setItems(list);
	}
	
    public boolean ManagerPassword() throws SQLException {
    	
    	String ManPass = ManagerPassword.getText();
    	String realMan = null;
    	String no = "Manager";
    	con = DBConnector.getConnection();
    	rs = con.createStatement().executeQuery("SELECT  `password` FROM `login` WHERE position = '"+no+"'");
    	while(rs.next()) {
    		realMan = rs.getString("password");
    	}
    	if(ManPass == realMan) {
    		System.out.print(realMan+"1");
    		return true;
    	}
		System.out.print(realMan);

	    return false;
    }
    
    
    public void addUser(javafx.event.ActionEvent event) throws SQLException {
    	String sql = "insert into login(username, password, position) Values(?,?,?)";
    	String username = Username.getText();
    	String password = Password.getText();
    	String position = positionCombo.getValue();
    	if(Validation.lengthCheck(username)||Validation.lengthCheck(password)) {
    	try {	
        	con = DBConnector.getConnection();
    		pst = con.prepareStatement(sql);
    		pst.setString(1, username);
    		pst.setString(2, password);
    		pst.setString(3, position);
    		pst.executeUpdate();
    		pst.close();
    		
    		Alert alert=new Alert(AlertType.INFORMATION);
    	    alert.setTitle(" New User ");
    	    alert.setHeaderText("Information Dialogue");
    	    alert.setContentText("User Created Succesfully!");
    	    alert.showAndWait();
    		((Node)event.getSource()).getScene().getWindow().hide();

    	}catch(Exception e) {
    		
    		e.printStackTrace();
    		
    	}
	}else {
		Alert alert=new Alert(AlertType.ERROR);
        alert.setTitle("Validation Check");
        alert.setHeaderText("ERROR Dialogue");
        alert.setContentText("Sorry! Length Check");
        
        alert.showAndWait();
        
        System.out.println("Too Long");
	}
    }
    
    public void close(javafx.event.ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide();
    }
    
    public static boolean lengthCheck(String string) {
    	char[]x = string.toCharArray();
    	boolean found = true;
    	if(x.length>5) {
    		found = false;
    	}
    	return found;
    }
    
    public void addUser2(javafx.event.ActionEvent event) throws SQLException {
    	ManagerPassword();
    }
}

