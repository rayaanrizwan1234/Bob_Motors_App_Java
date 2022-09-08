package Manager;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class InventoryTable {
	String price, category, type, make, details, stock, id; 
    private CheckBox checkbox;
    private Button button;
	public InventoryTable(  String price,String category, String type, String make, String details, String stock, String id ) {

	this.price = price;
	this.category = category;
	this.type = type;
	this.make = make; 
	this.details = details;
	this.stock = stock;
	this.id = id;
	this.checkbox = new CheckBox();
	this.button = new Button();
	button.setOnAction(e -> {
		
	});
	}

	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public CheckBox getCheckbox() {
		return checkbox;
	}
	public void setCheckbox(CheckBox checkbox) {
		this.checkbox = checkbox;
	}


	public Button getButton() {
		return button;
	}


	public void setButton(Button button) {
		this.button = button;
	}
	
		
}
