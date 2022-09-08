package Manager;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

public class reorderController implements Initializable {
	@FXML
    private ComboBox<?> comboID;
	
	  Connection con = null;
	    PreparedStatement pst = null;
	    ResultSet rs;
	    final ObservableList options = FXCollections.observableArrayList();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
comboID.setItems(options);
rs.close();
con.close();
	}
	
	public void reorder(javafx.event.ActionEvent event) throws SQLException {
		con = DBConnector.getConnection();
rs = con.createStatement().executeQuery("SELECT `stock` FROM `inventory` WHERE id = '"+comboID.getValue()+"'");
String stock = null;
int stockInt  = 0;
while(rs.next()) {
	stock = rs.getString("stock");
	stockInt = Integer.parseInt(stock);
}
stockInt = stockInt+1;
stock = Integer.toString(stockInt);

String sql = "Update inventory set  stock = '"+stock+"' where id = '"+comboID.getValue()+"' ";
   pst = con.prepareStatement(sql);
   pst.execute();
   
   
   Alert alert=new Alert(AlertType.INFORMATION);
   alert.setTitle("Return car");
   alert.setHeaderText("Information Dialogue");
   alert.setContentText("Product Updated: Car returned");
  
   alert.showAndWait();
	((Node)event.getSource()).getScene().getWindow().hide();
	}

	
}
