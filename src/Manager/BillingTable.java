package Manager;

public class BillingTable {
String id, details;
	public BillingTable(String id, String details) {
		this.id=id;
		this.details=details;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
}
