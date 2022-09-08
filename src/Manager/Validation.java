package Manager;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Validation {
	
public static boolean typCheck(String string) {
	boolean found = true;
	char[]x = string.toCharArray();
	for(int i = 0; i<x.length;i++) {
		if(Character.isDigit(x[i])) {
			found = false;
			Alert alert=new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error Dialogue");
			alert.setContentText("Please fill in Correct information");
			alert.showAndWait();
		}
	}
	return found;
}

public static boolean lengthCheck(String string) {
	char[]x = string.toCharArray();
	boolean found = true;
	if(x.length>5) {
		found = false;;
	}
	return found;
}

public static boolean lengthCheck(int number) {
	boolean found = true;
	int count = 0;
	while(number!=0) {
		number = number/10;
		count++;
	}
	if(count>3) {
		found = false;
		Alert alert=new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error Dialogue");
		alert.setContentText("Information too long");
		alert.showAndWait();
	}
	return found;
}

public static boolean check(String string) {
	if(string.length()>5) {
		Alert alert=new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error Dialogue");
		alert.setContentText("Information too long");
		alert.showAndWait();
		return false;
	}
	return true;
}



}
