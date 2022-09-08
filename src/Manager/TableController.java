package Manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.cj.xdevapi.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.EventHandler;

public class TableController implements Initializable {
	 @FXML
	    private TableView<ModelTable> table;

	    @FXML
	    private TableColumn<ModelTable, String> id;

	    @FXML
	    private TableColumn<ModelTable, String> staff;

	    @FXML
	    private TableColumn<ModelTable, String> department;

	    @FXML
	    private TableColumn<ModelTable, String> cars;

	    @FXML
	    private TableColumn<ModelTable, String> salary;
	    
	    @FXML
	    private TextField txtStaffName;

	    @FXML
	    private TextField txtDepartment;

	    @FXML
	    private TextField txtCarsSold;
	    
	    @FXML
	    private TextField txtID;

	    @FXML
	    private TextField txtSalary;
	    
	    @FXML
	    private Label lblID;

	    @FXML
	    private Label lblStaffName;

	    @FXML
	    private Label lblDepartment;

	    @FXML
	    private Label lblCarsSold;

	    @FXML
	    private Label lblSalary;
	    
	    @FXML
	    private BarChart<String, Double> chart;

	    @FXML
	    private CategoryAxis x;

	    @FXML
	    private NumberAxis y;


	    Connection con = null;
	    PreparedStatement pst = null;
	    ResultSet rs;
	    
	    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
	    
	   

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setCellTable();
		loadDatafromDatabase();
		TableToTextField();
	}
	
	public void loadDatafromDatabase() {
		oblist.clear();
		try {
			 con = DBConnector.getConnection();
			rs = con.createStatement().executeQuery("SELECT * FROM `stafftable`");
			
			while(rs.next()) {
				oblist.add(new ModelTable(rs.getString("id"),rs.getString("staff"),rs.getString("department"),
						rs.getString("cars"),rs.getString("salary")));
				 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		table.setItems(oblist);
	}
	
	public void setCellTable() {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		staff.setCellValueFactory(new PropertyValueFactory<>("staff"));
		department.setCellValueFactory(new PropertyValueFactory<>("department"));
		cars.setCellValueFactory(new PropertyValueFactory<>("cars"));
		salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
		
		
	}
	
	public void addEmployee(javafx.event.ActionEvent event) throws SQLException {
	
		boolean isIdEmpty = Manager.TextFieldValidation.isTextFieldNotEmpty(txtID, lblID, "ID field is required");
		boolean isStaffNameEmpty = Manager.TextFieldValidation.isTextFieldNotEmpty(txtStaffName, lblStaffName, "Staff Name field is required");
		boolean isDepartmentEmpty = Manager.TextFieldValidation.isTextFieldNotEmpty(txtDepartment, lblDepartment, "Department field is required");
		boolean isCarsSoldEmpty = Manager.TextFieldValidation.isTextFieldNotEmpty(txtCarsSold, lblCarsSold, "Cars Sold field is required");
		boolean isSalaryEmpty = Manager.TextFieldValidation.isTextFieldNotEmpty(txtSalary, lblSalary, "Salary field is required");
		
		
		String sql = "insert into stafftable(id, staff, department, cars, salary) Values(?,?,?,?,?)";
		String id = txtID.getText();
		String staff = txtStaffName.getText();
		String department = txtDepartment.getText();
		String cars = txtCarsSold.getText();
		String salary = txtSalary.getText();
		if(validateFields() || Validation.lengthCheck(salary) || Validation.lengthCheck(cars)|| Validation.typCheck(staff) || Validation.typCheck(department)) {
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			pst.setString(2, staff);
			pst.setString(3, department);
			pst.setString(4, cars);
			pst.setString(5, salary);
			
			int i = pst.executeUpdate();
			if(i==1) {
				AlertBox.displaya();
				setCellTable();
				loadDatafromDatabase();
				
	
			}
			
		} catch(SQLException e) {
			e.printStackTrace();		
		}
		finally {
			pst.close();
		}
	}else{
		Alert alert=new Alert(AlertType.ERROR);
        alert.setTitle("Data Insert");
        alert.setHeaderText("ERROR Dialogue");
        alert.setContentText("Sorry! Length Check");
        
        alert.showAndWait();
        
        System.out.println("Too Long");
		}
	}
	
	public void cleardata(javafx.event.ActionEvent event) {
		txtID.clear();
		txtStaffName.clear();
		txtDepartment.clear();
		txtCarsSold.clear();
		txtSalary.clear();
	} 
	public void Validation() {
		if(txtSalary.getText().length() != 0 || txtSalary.getText().isEmpty())
			lblSalary.setText("Salary is required");
		
		if(txtID.getText().length() != 0 || txtID.getText().isEmpty()) {
			lblID.setText("ID field is empty");
		}
		if(txtStaffName.getText().length() != 0 || txtStaffName.getText().isEmpty()) {
			lblStaffName.setText("Staff Name field is empty");
		}
		if(txtDepartment.getText().length() != 0 || txtDepartment.getText().isEmpty()) {
			lblDepartment.setText("Department field is empty");
		}
		if(txtCarsSold.getText().length() != 0 || txtCarsSold.getText().isEmpty()) {
			lblCarsSold.setText("ID field is empty");
		}
	}

	private boolean validateFields() {
		if(txtSalary.getText().isEmpty() | txtStaffName.getText().isEmpty() |
				txtDepartment.getText().isEmpty() | txtID.getText().isEmpty()|txtCarsSold.getText().isEmpty() ) {
			AlertBox.displaye();
			
			return false;
		}
		return true;
	}
	
	
	public void handleUpdateProduct(javafx.event.ActionEvent event) {
		String sql = "UPDATE `stafftable` SET staff =? , department = ?, cars = ?, salary= ? where id = ?";
		if(validateFields()) {
		try {
	
			String id = txtID.getText();
			String staff = txtStaffName.getText();
			String department = txtDepartment.getText();
			String cars = txtCarsSold.getText();
			String salary = txtSalary.getText();
		
			pst = con.prepareStatement(sql);
			
			pst.setString(1, staff);
			pst.setString(2, department);
			pst.setString(3, cars);
			pst.setString(4, salary); 
			pst.setString(5, id);
			
			int i = pst.executeUpdate();
			if(i==1) {
				AlertBox.displayu();
				
				loadDatafromDatabase();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	}
	
	public void Edit(javafx.event.ActionEvent event) {
		if(validateFields()) {
		try {
			con = DBConnector.getConnection();
			
			String id = txtID.getText();
			String staff = txtStaffName.getText();
			String department = txtDepartment.getText();
			String cars = txtCarsSold.getText();
			String salary = txtSalary.getText();
			String sql = "Update stafftable set id = '"+id+"', staff = '"+staff+"', department = '"+department+"',"
					+ ""
					+ "cars = '"+cars+"', salary= '"+salary+"' where id = '"+id+"' ";
		pst = con.prepareStatement(sql);
		pst.execute();  
		AlertBox.displayu();
		loadDatafromDatabase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	private void TableToTextField() {
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ModelTable mt = table.getItems().get(table.getSelectionModel().getSelectedIndex());
				txtID.setText(mt.getId());
				txtStaffName.setText(mt.getStaff());
				txtDepartment.setText(mt.getDepartment());
				txtCarsSold.setText(mt.getCars());
				txtSalary.setText(mt.getSalary());
			}
		});

}
	
	public void loadChart(javafx.event.ActionEvent event) {
		chart.getData().clear();
		String sql = "SELECT  `staff`, `cars` FROM `stafftable` WHERE 1";
		XYChart.Series<String,Double> series = new XYChart.Series<String,Double>();
		
		try {
			con = DBConnector.getConnection();
		 rs = con.createStatement().executeQuery(sql);
			while(rs.next()) {
				series.getData().add(new XYChart.Data<>(rs.getString(1),rs.getDouble(2)));
				series.setName("Cars Sold");
			}
			chart.getData().add(series);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}  
	
	public void SendEmail(javafx.event.ActionEvent event) {
		
		try {
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Parent root;
			root = FXMLLoader.load(getClass().getResource("/Manager/Email.fxml"));
			Scene scene = new Scene(root,500,600);
			primaryStage.setScene(scene);
			primaryStage.show();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void pdfButton(javafx.event.ActionEvent event) throws IOException, ClassNotFoundException {
		try {
			// SQL Query
			con = DBConnector.getConnection();
			rs = con.createStatement().executeQuery("SELECT `id`, `staff`, `department`, `cars`, `salary` FROM `stafftable` WHERE 1");
			String ad = null;
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
				
			//	String file_name = "C:\\Users\\Rumana\\Desktop\\CsIaPdfs//Bill.pdf";
				Document my_pdf = new Document();
				PdfWriter.getInstance(my_pdf, new FileOutputStream(ad+".pdf"));
				
			my_pdf.open();
			
			// Create columns
			PdfPTable my_report_table = new PdfPTable(5);
			
			//cell
			PdfPCell table_cell;
			
			
			table_cell = new PdfPCell(new Phrase("id"));
			my_report_table.addCell(table_cell);
			
		
			table_cell = new PdfPCell(new Phrase("staff"));
			my_report_table.addCell(table_cell);
			
		
			table_cell = new PdfPCell(new Phrase("department"));
			my_report_table.addCell(table_cell);
			
			
			table_cell = new PdfPCell(new Phrase("cars"));
			my_report_table.addCell(table_cell);
			
			
			table_cell = new PdfPCell(new Phrase("salary"));
			my_report_table.addCell(table_cell);
			
			while(rs.next()) {
				
				String id = rs.getString("id");
				table_cell = new PdfPCell(new Phrase(id));
				my_report_table.addCell(table_cell);
				
				String staff = rs.getString("staff");
				table_cell = new PdfPCell(new Phrase(staff));
				my_report_table.addCell(table_cell);
				
				String department = rs.getString("department");
				table_cell = new PdfPCell(new Phrase(department));
				my_report_table.addCell(table_cell);
				
				String cars = rs.getString("cars");
				table_cell = new PdfPCell(new Phrase(cars));
				my_report_table.addCell(table_cell);
				
				String salary = rs.getString("salary");
				table_cell = new PdfPCell(new Phrase(salary));
				my_report_table.addCell(table_cell);
			}
			
			my_pdf.add(my_report_table);
			my_pdf.add(Image.getInstance("C:\\Users\\Rumana\\Desktop\\CS IA//Logo.png"));
			my_pdf.close();
			
			rs.close();
			con.close();
			
			Alert alert=new Alert(AlertType.INFORMATION);
		    alert.setTitle("PDF Generation");
		    alert.setHeaderText("Information Dialogue");
		    alert.setContentText("PDF Generated Successfully");
		   
		    alert.showAndWait();
		    
		    System.out.println("PDF = Generated Successfully");
			
			}} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Delete(javafx.event.ActionEvent event) throws SQLException {		
			if(validateFields())
				try {
				con = DBConnector.getConnection();
				String sql = "DELETE FROM `stafftable` WHERE id = '"+txtID.getText()+"'";
				pst = con.prepareStatement(sql);
				pst.execute();
				Alert alert=new Alert(AlertType.INFORMATION);
			    alert.setTitle("Staff Update");
			    alert.setHeaderText("Information Dialogue");
			    alert.setContentText("Staff Deleted");	   
			    alert.showAndWait();
			    loadDatafromDatabase();} catch(Exception e) {
			    	e.printStackTrace();
			    }
		}
		
	}


