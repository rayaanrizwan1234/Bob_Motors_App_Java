package Manager;

import java.io.IOException;
import java.lang.System.Logger.Level;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.sun.glass.events.MouseEvent;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class ClientController {
	@FXML
    private BorderPane bp;

    @FXML
    private AnchorPane ap;

    @FXML
    private VBox vb;

    @FXML
    private Button homebtn;

    @FXML
    private Label lbl;

    @FXML
    private Button staffbtn;

    @FXML
    private Label lblEmail;

    @FXML
    private Button emailbtn;

    @FXML
    private Button inventorybtn;

    @FXML
    private Label lblBillings;

    @FXML
    private Button billingsbtn;
    
    public void loadPage1(javafx.event.ActionEvent event) throws Exception {
    	
    	lbl.setText("Staff page not available to employees");
    			 
    		}
    		public void loadPage2(javafx.event.ActionEvent event) throws Exception {
    			Parent root = null;
    			
    			 try {
    				root = FXMLLoader.load(getClass().getResource("/Manager/InventoryCLient.fxml"));
    			} catch (IOException e) {
    				e.printStackTrace();
    				
    			}
    		
    			bp.setCenter(root);
    	}
    		public void loadPage3(javafx.event.ActionEvent event) throws Exception {
    			lblBillings.setText("Not Available!");
    	}
    	
    		public void loadPage5(javafx.event.ActionEvent event) throws Exception {
    			Parent root = null;
    			
    			 try {
    				root = FXMLLoader.load(getClass().getResource("/Manager/Home2.fxml"));
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		
    			bp.setCenter(root);
    	}
    		public void loadPage6(javafx.event.ActionEvent event) throws Exception {
    		lblEmail.setText("Not Available!");
    	}
}
