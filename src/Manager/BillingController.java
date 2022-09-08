package Manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import Manager.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class BillingController implements Initializable {
	
	 @FXML
	    private ComboBox<?> staffCombo;
	

	//@FXML
	  //  private ComboBox<?> inventoryCombo;
	 

	    @FXML
	    private TableView<BillingTable> invTable;

	    @FXML
	    private TableColumn<BillingTable, String> id;

	    @FXML
	    private TableColumn<BillingTable, String> details;
	    
	    @FXML
	    private TextField filter;
	    
	    @FXML
	    private TextField txtId;

	    @FXML
	    private TextField txtDetails;
	    
	    @FXML
	    private TextField txtClient; 
	    
	    @FXML
	    private TextField txtDuration;
	    
	    @FXML
	    private TextField txtPhone;

	    @FXML
	    private TextField txtPassport;
	    
	    @FXML
	    private DatePicker datePicker;
	    
	    @FXML
	    private PieChart pie;
	    
	
	  Connection con = null;
	    PreparedStatement pst = null;
	    ResultSet rs;
	    
	    final ObservableList options = FXCollections.observableArrayList();
	    final ObservableList options1 = FXCollections.observableArrayList();
	    final ObservableList oblist = FXCollections.observableArrayList();
	    ObservableList<PieChart.Data> piechartdata;
	    String productid;
	@Override 
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			StaffComboBox();
		//	InventoryComboBox();
			loadDatafromDatabase();
			setCellTable();
			FilterList();
			TableToTextField();
			pieChart(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public void StaffComboBox() throws Exception{
	con = DBConnector.getConnection();
	rs = con.createStatement().executeQuery("SELECT  `staff` FROM `stafftable` WHERE 1");
	while(rs.next()) {
		options.add(rs.getString("staff"));
	}
	staffCombo.setItems(options);
	rs.close();
	con.close();
}



public void loadDatafromDatabase() throws Exception{
	
	oblist.clear();
	try {
		 con = DBConnector.getConnection();
		rs = con.createStatement().executeQuery("SELECT `id`, `details` FROM `inventory` WHERE 1");
		
		while(rs.next()) {
			oblist.add(new BillingTable(rs.getString("id"),rs.getString("details")));
			 
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}

	invTable.setItems(oblist);
}



public void setCellTable() {
	details.setCellValueFactory(new PropertyValueFactory<>("details"));
	id.setCellValueFactory(new PropertyValueFactory<>("id")); 				
}

private void FilterList() {
	
	FilteredList<BillingTable> filteredData = new FilteredList<>(oblist, b -> true);
	
	filter.textProperty().addListener((observable, oldValue, newValue) ->{
		filteredData.setPredicate(BillingTable -> {
			if(newValue == null || newValue.isEmpty()) {
				return true;
			}
			
			String lowerCaseFilter = newValue.toLowerCase();
			
			if(BillingTable.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
				return true;
			} else if(BillingTable.getDetails().toLowerCase().indexOf(lowerCaseFilter) != -1) {
				return true;
			}
			else 
				return false; 
		});
	});
	
	SortedList<BillingTable> sortedData = new SortedList<>(filteredData);
	
	sortedData.comparatorProperty().bind(invTable.comparatorProperty());
	
	invTable.setItems(sortedData);
}

private void TableToTextField() {

	invTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			BillingTable mt =invTable.getItems().get(invTable.getSelectionModel().getSelectedIndex());
			txtId.setText(mt.getId());
			txtDetails.setText(mt.getDetails());
		}
	});

}


public void pdf(javafx.event.ActionEvent event)throws IOException, ClassNotFoundException, SQLException {
	if(validateFields()) {
		if(stockNumber()) {
	con = DBConnector.getConnection();

	String staff = (String) staffCombo.getValue();
	String client = txtClient.getText();
	String registrationID = txtId.getText();
	String carDetails = txtDetails.getText();
	String ad = null;
	try {
	//Save as
	FileChooser FC = new FileChooser();
	FC.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF(.pdf)", "*"));
	FC.setTitle("Save your file");
	File file = FC.showSaveDialog(null);
	if(file!=null) {
		try {
			//pdf();
			PrintStream prints = new PrintStream(file);
			prints.println();
			prints.flush();
			
		 ad = file.getAbsolutePath();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
			System.out.print("oops");
		}
		
		//Updating Databases
	    rs = con.createStatement().executeQuery("SELECT `price`, `stock` FROM `inventory` WHERE id = '"+registrationID+"'");
	    String price = null;
	    String stock = null;
	    while(rs.next()) {
	    	price = rs.getString("price");
	    	stock = rs.getString("stock");
	    }
	    double priceInt = Integer.parseInt(price);
	    String duration = txtDuration.getText();
	    int durationInt = Integer.parseInt(duration);
	    priceInt = priceInt*durationInt*0.2;
	    int stockInt = Integer.parseInt(stock);
	    stockInt = stockInt - 1;
	    stock = Integer.toString(stockInt);
	    System.out.print(price+stock);
	    
	    String sql = "Update inventory set  stock = '"+stock+"' where id = '"+registrationID+"' ";
		   pst = con.prepareStatement(sql);
		   pst.execute();
	    
	    
	    con = DBConnector.getConnection();
	    rs = con.createStatement().executeQuery("SELECT * FROM `stafftable` WHERE staff = '"+staff+"'");
	    
	    String id = null;
		String staff1 = null;
		String department = null;
		String cars = null;
		String salary = null;
	   while(rs.next()) {
		   id = rs.getString("id");
		   staff1 = rs.getString("staff");
		   department = rs.getString("department");
	    	cars = rs.getString("cars");
	    	salary = rs.getString("salary");
	    }
	   
	   int carInt = Integer.parseInt(cars);
	   carInt = carInt + 1;
	   cars = Integer.toString(carInt);
	   
	   Double salaryInt = Double.parseDouble(salary);
	   salaryInt = salaryInt + priceInt;
	   salary = Double.toString(salaryInt);
	   System.out.print(salary);
	   String sql1 = "Update stafftable set  cars = '"+cars+"', salary = '"+salary+"' where staff = '"+staff+"' ";
	   pst = con.prepareStatement(sql1);
	   pst.execute();
		
	//	String file_name = "C:\\Users\\Rumana\\Desktop\\CsIaPdfs//Bill.pdf";
		Document my_pdf = new Document();
		PdfWriter.getInstance(my_pdf, new FileOutputStream(ad+".pdf"));
		my_pdf.open();
		
		Paragraph para1 = new Paragraph("Bill Contract");
		my_pdf.add(para1);
		my_pdf.add(new Paragraph(" "));
		
		
		Paragraph para2 = new Paragraph("This Car Rental Agreement is entered into between [CAR OWNER] and [RENTER] (collectively the “Parties”) and outlines the respective rights and obligations of the Parties relating to the rental of a car.");
		my_pdf.add(para2);
		my_pdf.add(new Paragraph(" "));
		my_pdf.add(new Paragraph(" "));
		
		
		Paragraph para3 = new Paragraph("This bill is created by employee: "+staff+" and is billed to the client: "+client+".");
		my_pdf.add(para3);
		my_pdf.add(new Paragraph("Client Phone Number: "+txtPhone.getText()));
		my_pdf.add(new Paragraph("Client Passport Number: "+txtPassport.getText()));
		my_pdf.add(new Paragraph(" "));
		
		my_pdf.add(new Paragraph("1. Identification of The Rented Vehicle"));
		Paragraph para4 = new Paragraph("Owner hereby agrees to rent to Renter a passenger vehicle identified as follows:");
		my_pdf.add(para4);
		Paragraph para5 = new Paragraph("Registration ID: "+registrationID);
		my_pdf.add(para5);
		Paragraph para6 = new Paragraph("Car Details: "+carDetails);
		my_pdf.add(para6);
		priceInt = priceInt/0.2;
		my_pdf.add(new Paragraph("Price of the Rental is "+priceInt));
		my_pdf.add(new Paragraph(" "));
		
		
		Paragraph para10 = new Paragraph("2. Rental Term");
		my_pdf.add(para10);
		Paragraph para7 = new Paragraph("The term of this Car Rental Agreement runs from the date and hour of vehicle pickup as indicated just above the signature line at the bottom of this agreement until the return of the vehicle to Owner, and completion of all terms of this agreement by both Parties.");
		my_pdf.add(para7);
		Paragraph para8 = new Paragraph("The Parties may shorten or extend the estimate term of rental by mutual consent.");
		my_pdf.add(para8);
		Paragraph para14 = new Paragraph("Duration of time is: "+txtDuration.getText()+" days");
		my_pdf.add(para14);
		my_pdf.add(new Paragraph("Contract Start Date: "+datePicker.getValue()));
		my_pdf.add(new Paragraph(" "));
		
		my_pdf.add(new Paragraph("3. Scope of Use"));
		Paragraph para9 = new Paragraph("Renter will use the Rented Vehicle only for personal or routine business use, and operate the Rented Vehicle only on properly maintained roads and parking lots.  Renter will comply with all applicable laws relating to holding of licensure to operate the vehicle, and pertaining to operation of motor vehicles.  Renter will not sublease the Rental Vehicle or use it as a vehicle for hire.  Renter will not take the vehicle outside the UAE.");
		my_pdf.add(para9);
		my_pdf.add(new Paragraph(" "));

		
		my_pdf.add(new Paragraph("4. Milage"));
		Paragraph para11 = new Paragraph("Mileage of the Rental Vehicle is 500km at the time of commencement of this Car Rental Agreement.  Mileage on the vehicle will be limited as follows: 500km.  Any mileage on the vehicle in excess of this limitation will be subject to an excess mileage surcharge of AED 10 per Kilometer.");
		my_pdf.add(para11);
		my_pdf.add(new Paragraph(" "));
		
		
		my_pdf.add(new Paragraph("5. Insurance"));
		Paragraph para12 = new Paragraph("Renter must provide to Owner with proof of insurance that would cover damage to the Rental Vehicle at the time this Car Rental Agreement is signed, as well as personal injury to the Renter, passengers in the Rented Vehicle, and other persons or property.  If the Rental Vehicle is damaged or destroyed while it is in the possession of Renter, Renter agrees to pay any required insurance deductible and also assign all rights to collect insurance proceeds to Owner.");
		my_pdf.add(para12);
		my_pdf.add(new Paragraph(" "));
		
		
		my_pdf.add(new Paragraph("6. Security Deposit"));
		Paragraph para13 = new Paragraph("Renter will be required to provide a security deposit to Owner in the amount of [DOLLAR AMOUNT] (“Security Deposit”) to be used in the event of loss or damage to the Rental Vehicle during the term of this agreement.  Owner may, in lieu of collection of a security deposit, place a hold on a credit card in the same amount.  In the event of damage to the Rental Vehicle, Owner will apply this Security Deposit to defray the costs of necessary repairs or replacement.  If the cost for repair or replacement of damage to the Rental Vehicle exceeds the amount of the Security Deposit, Renter will be responsible for payment to the Owner of the balance of this cost.");
		my_pdf.add(para13);
		my_pdf.add(new Paragraph(" "));
		
		
		
		my_pdf.close();
		
		
		Alert alert=new Alert(AlertType.INFORMATION);
	    alert.setTitle("PDF Generation");
	   alert.setHeaderText("Information Dialogue");
	    alert.setContentText("PDF Generated Successfully");
	   
	    alert.showAndWait();
	    
	    

		
	}} catch(Exception e ) {
		e.printStackTrace();
	}
	}
}
}
	    
	 

private boolean validateFields() {
	if(txtId.getText().isEmpty() | txtDetails.getText().isEmpty() |
			txtClient.getText().isEmpty() | txtDuration.getText().isEmpty()|txtPhone.getText().isEmpty()|txtPassport.getText().isEmpty()) {
		AlertBox.displaye();
		
		return false;
	}
	return true;
}

public void clearData(javafx.event.ActionEvent event) {
	txtId.clear();
	txtDetails.clear();
	txtClient.clear();
	txtDuration.clear();
	txtPhone.clear();
	txtPassport.clear();
}

public void pieChart() {
piechartdata = FXCollections.observableArrayList();
	ArrayList<String> make = new ArrayList<String>();
	ArrayList<Double> stock = new ArrayList<Double>();
	try {
	    con = DBConnector.getConnection();
		rs = con.createStatement().executeQuery("SELECT `make`,`stock` FROM `inventory` WHERE 1");
		while(rs.next()) {
			
			piechartdata.add(new PieChart.Data(rs.getString("make"), rs.getDouble("stock")));
			make.add(rs.getString("make"));
			stock.add(rs.getDouble("stock"));
		}
		System.out.print(stock);
		pie.setData(piechartdata);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
private boolean stockNumber() throws SQLException {
    con = DBConnector.getConnection();
try {
	rs = con.createStatement().executeQuery("SELECT `stock` FROM `inventory` WHERE id = '"+txtId.getText()+"'");
	int stockInt = 0;
	while(rs.next()) {
		String stock = rs.getString("stock");
		stockInt = Integer.parseInt(stock);
	}
	if (stockInt==0) {
		Alert alert=new Alert(AlertType.ERROR);
	    alert.setTitle("Car Stock");
	   alert.setHeaderText("Error Dialogue");
	    alert.setContentText("Stock for car: "+txtId.getText()+" is 0!");
	    alert.showAndWait();
	    return false;
	} 
		
}catch(Exception e) {
	e.printStackTrace();
}
return true;

}
}