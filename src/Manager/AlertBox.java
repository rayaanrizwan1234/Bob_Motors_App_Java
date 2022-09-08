package Manager;

import java.awt.Label;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	public static void display(String title, String message) {
		Alert a1 = new Alert(Alert.AlertType.ERROR);
		a1.setTitle(title);
		a1.setContentText(message);
		a1.setHeaderText(null);
		a1.showAndWait();	
	}
public static void displaye() {

	 
	Alert a1 = new Alert(Alert.AlertType.ERROR);
	a1.setTitle("STATUS");
	a1.setContentText("Please fill in all TextFields");
	a1.setHeaderText(null);
	a1.showAndWait();
	
}
public static void displaya() {

	
	Alert a1 = new Alert(Alert.AlertType.INFORMATION);
	a1.setTitle("STATUS");
	a1.setContentText("Employee Added");
	a1.setHeaderText(null);
	a1.showAndWait();
	
}
public static void displayu() {

	
	Alert a1 = new Alert(Alert.AlertType.INFORMATION);
	a1.setTitle("STATUS");
	a1.setContentText("Employee Updated");
	a1.setHeaderText(null);
	a1.showAndWait();
	
}
public static void displayi() {

	
	Alert a1 = new Alert(Alert.AlertType.INFORMATION);
	a1.setTitle("STATUS");
	a1.setContentText("Product Added");
	a1.setHeaderText(null);
	a1.showAndWait();
	
}
public static void displayiu() {

	
	Alert a1 = new Alert(Alert.AlertType.INFORMATION);
	a1.setTitle("STATUS");
	a1.setContentText("Product Updated");
	a1.setHeaderText(null);
	a1.showAndWait();
	
}
}
