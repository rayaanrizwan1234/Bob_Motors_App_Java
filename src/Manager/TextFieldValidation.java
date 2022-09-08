package Manager;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TextFieldValidation {
public static boolean isTextFieldNotEmpty(TextField tf) {
	boolean b = false;
	if(tf.getText().length() != 0 || tf.getText().isEmpty())
		b = true;
	return b; 
}
public static boolean isTextFieldNotEmpty(TextField tf, Label lb, String errorMessage) {
	boolean b = true;
	String msg = null;
	if(!isTextFieldNotEmpty(tf)) {
		b = false;
		msg = errorMessage;
	}
	lb.setText(msg);
	return b; 
}
}
