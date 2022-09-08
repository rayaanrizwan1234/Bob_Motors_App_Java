package Manager;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Manager.InventoryTable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class InventoryController implements Initializable {
	
	   @FXML
	    private TableView<InventoryTable> table;

	    @FXML
	    private TableColumn<InventoryTable, String> id;

	    @FXML
	    private TableColumn<InventoryTable, String> price;

	    @FXML
	    private TableColumn<InventoryTable, String> category;

	    @FXML
	    private TableColumn<InventoryTable, String> type;

	    @FXML
	    private TableColumn<InventoryTable, String> make;

	    @FXML
	    private TableColumn<InventoryTable, String> details;

	    @FXML
	    private TableColumn<InventoryTable, String> stock;
	    
	    @FXML
	    private TableColumn<?, ?> button;


	
	@FXML
    private TextField txtRegistrationID;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtMake;

    @FXML
    private TextField txtDetails;

    @FXML
    private TextField txtStock;

    @FXML
    private TextField txtPrice;
    
    @FXML
    private TextField filter;
    
    
    
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs;
    ObservableList<InventoryTable> oblist = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			loadDatafromDatabase();
			setCellTable();
			TableToTextField();
			FilterList();
			button.setCellValueFactory(new PropertyValueFactory<>("checkbox"));
			sendEmailButton();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}
	
	   public void loadDatafromDatabase() throws Exception{
				oblist.clear();
				try {
					 con = DBConnector.getConnection();
					rs = con.createStatement().executeQuery("SELECT * FROM `inventory`");
					
					while(rs.next()) {
						oblist.add(new InventoryTable(rs.getString("price"),rs.getString("category"),rs.getString("type"), rs.getString("make"),
								rs.getString("details"), rs.getString("stock"), rs.getString("id")));
						 
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
				table.setItems(oblist);
			}
	  
	  
	   
	    public void setCellTable() {

				price.setCellValueFactory(new PropertyValueFactory<>("price"));
				category.setCellValueFactory(new PropertyValueFactory<>("category"));
				type.setCellValueFactory(new PropertyValueFactory<>("type"));
				make.setCellValueFactory(new PropertyValueFactory<>("make"));
				details.setCellValueFactory(new PropertyValueFactory<>("details"));
				stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
				id.setCellValueFactory(new PropertyValueFactory<>("id")); 				
			}
	    
	    public void AddProduct(javafx.event.ActionEvent event) throws SQLException {
	    	
	    	String sql = "insert into inventory( price, category, type, make, details, stock, id) Values(?,?,?,?,?,?,?)";
	    	
			String price = txtPrice.getText();
			String category = txtCategory.getText();
			String type = txtType.getText();
			String make = txtMake.getText();
			String details = txtDetails.getText();
			String stock = txtStock.getText();
			String id = txtRegistrationID.getText();
	    	
			
		if(validateFields()) {
			try {
			pst = con.prepareStatement(sql);
			pst.setString(1, price);
			pst.setString(2, category);
			pst.setString(3, type);
			pst.setString(4, make);
			pst.setString(5, details);
			pst.setString(6, stock);
			pst.setString(7, id);
			
			
			int i = pst.executeUpdate();
			if(i==1) {
				
				try {
					loadDatafromDatabase();
					AlertBox.displayi();
					setCellTable();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}  catch(SQLException e) {
				e.printStackTrace();
			}
			finally {
				pst.close();
			}
		}
	    }
	    
	    private void TableToTextField() {

			table.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					InventoryTable mt = table.getItems().get(table.getSelectionModel().getSelectedIndex());
					txtRegistrationID.setText(mt.getId());
					txtCategory.setText(mt.getCategory());
					txtType.setText(mt.getType());
					txtMake.setText(mt.getMake());
					txtDetails.setText(mt.getDetails());
					txtStock.setText(mt.getStock());
					txtPrice.setText(mt.getPrice());
				}
			});

	}
	    
	    private boolean validateFields() {
			if(txtStock.getText().isEmpty() | txtDetails.getText().isEmpty() |
					txtMake.getText().isEmpty() | txtType.getText().isEmpty()|
					txtCategory.getText().isEmpty()|txtRegistrationID.getText().isEmpty()  ) {
				AlertBox.displaye();
				
				return false;
			}
			return true;
		}
	    
	 
	    
	    public void cleardata(javafx.event.ActionEvent event) {
	    	txtStock.clear();
	    	txtDetails.clear();
	    	txtMake.clear();
	    	txtType.clear();
	    	txtCategory.clear();
	    	txtRegistrationID.clear();
	    	txtPrice.clear();
		}
	    
		public void TableToTextField(javafx.event.ActionEvent event) {
			table.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					InventoryTable mt = table.getItems().get(table.getSelectionModel().getSelectedIndex());
					txtRegistrationID.setText(mt.getId());
					txtCategory.setText(mt.getCategory());
					txtType.setText(mt.getType());
					txtMake.setText(mt.getMake());
					txtDetails.setText(mt.getDetails());
					txtStock.setText(mt.getStock());
					txtPrice.setText(mt.getPrice());
				}
			});

	}
		
		public void Edit(javafx.event.ActionEvent event) throws Exception {
			if(validateFields()) {
			try {
				con = DBConnector.getConnection();
				
				String id = txtRegistrationID.getText();
				String price = txtPrice.getText();
				String category = txtCategory.getText();
				String type = 	txtType.getText();
				String make = txtMake.getText();
				String details = txtDetails.getText();
				String stock = txtStock.getText();
				
				String sql = "Update inventory set id = '"+id+"', price = '"+price+"', category = '"+category+"',type = '"+type+"', make= '"+make+"', details = '"+details+"', stock = '"+stock+"'  where  id = '"+id+"'";
			pst = con.prepareStatement(sql);
			pst.execute();  
			AlertBox.displayiu();
			loadDatafromDatabase();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		

		public void Delete(javafx.event.ActionEvent event) throws Exception {
			if(validateFields()) {
			try {
				String id = txtRegistrationID.getText();
				con = DBConnector.getConnection();
				String sql = "DELETE FROM `inventory` WHERE id = '"+id+"'";
			pst = con.prepareStatement(sql);
			pst.execute();  
			Alert alert=new Alert(AlertType.INFORMATION);
		    alert.setTitle("Product Update");
		    alert.setHeaderText("Information Dialogue");
		    alert.setContentText("Product Deleted");	   
		    alert.showAndWait();
		    loadDatafromDatabase();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}


		
		private void FilterList() {
			
			FilteredList<InventoryTable> filteredData = new FilteredList<>(oblist, b -> true);
			
			filter.textProperty().addListener((observable, oldValue, newValue) ->{
				filteredData.setPredicate(InventoryTable -> {
					if(newValue == null || newValue.isEmpty()) {
						return true;
					}
					
					String lowerCaseFilter = newValue.toLowerCase();
					
					if(InventoryTable.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if(InventoryTable.getMake().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					}
					else 
						return false; 
				});
			});
			
			SortedList<InventoryTable> sortedData = new SortedList<>(filteredData);
			
			sortedData.comparatorProperty().bind(table.comparatorProperty());
			
			table.setItems(sortedData);
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
		  private static class SmtpAuthenticator extends Authenticator {

		      private String username = "csiatest12@gmail.com";
		      private String password = "eragon96";
		      @Override
		      public PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication(username, password);
		      }
		  }
		  
		  @FXML 
			public void sendEmailButton () throws IOException, SQLException {
				ArrayList<Integer> stock1 = new ArrayList<Integer>();
int stockInt = 0;
			    con = DBConnector.getConnection();
rs = con.createStatement().executeQuery("SELECT `stock` FROM `inventory` WHERE 1");
while(rs.next()) {
	stock1.add(rs.getInt("stock"));
}
		for(int i = 0; i<stock1.size();i++) {
			if (stock1.get(i) == 0 ) {
				stockInt = stock1.get(i);
				String stock = Integer.toString(stockInt);
				rs = con.createStatement().executeQuery("SELECT `id` FROM `inventory` WHERE stock = '"+stock+"'");
				String id = null;
				while (rs.next()) {
					id = rs.getString("id");
				}
				InventoryController.compile("csiatest12@gmail.com","csiatest12@gmail.com" , "Car Stock", "Car ID: "+id+" is out of stock! Reorder!");
			    System.out.println("Email = Sent Successfully");
			}
					
			}
		
			}
		  
		  public void reorder(javafx.event.ActionEvent event) throws IOException {
			  Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/Manager/Reorder.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
		  }
		 
		  public void enquire(javafx.event.ActionEvent event) throws IOException {
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/Manager/ClientEnquire.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
		  }
}
