package Manager;

import java.sql.SQLException;

public class ModelTable {
String id,staff,department,cars,salary;
public ModelTable(String id,String staff,String department,String cars,String salary) {
	this.id = id;
	this.staff = staff;
	this.department = department;
	this.cars = cars;
	this.salary = salary;
}

public String getId() { 
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getStaff() {
	return staff;
}
public void setStaff(String staff) {
	this.staff = staff;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public String getCars() {
	return cars;
}
public void setCars(String cars) {
	this.cars = cars;
}
public String getSalary() {
	return salary;
}
public void setSalary(String salary) {
	this.salary = salary;
}

}
