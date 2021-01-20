package couponSystem.javaBeans;

import java.util.ArrayList;
import java.util.List;

public class Customer {

	private int customerId;
	private String customerFirstName, customerLastName, customerEmail, customerPassword;
	private List<Coupon> customerCoupons = new ArrayList<>();

	public Customer(int customerId, String customerFirstName, String customerLastName, String customerEmail,
			String customerPassword) {
		setCustomerId(customerId);
		setCustomerFirstName(customerFirstName);
		setCustomerLastName(customerLastName);
		setCustomerEmail(customerEmail);
		setCustomerPassword(customerPassword);
	}

	public Customer(String customerFirstName, String customerLastName, String customerEmail, String customerPassword) {

		setCustomerFirstName(customerFirstName);
		setCustomerLastName(customerLastName);
		setCustomerEmail(customerEmail);
		setCustomerPassword(customerPassword);

	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	public List<Coupon> getCustomerCoupons() {
		return customerCoupons;
	}

	public void setCustomerCoupons(List<Coupon> customerCoupons) {
		this.customerCoupons = customerCoupons;
	}

	@Override
	public String toString() {
		return "Customer{" + "customerId=" + customerId + ", customerFirstName='" + customerFirstName + '\''
				+ ", customerLastName='" + customerLastName + '\'' + ", customerEmail='" + customerEmail + '\''
				+ ", customerPassword='" + customerPassword + '\'' + ", customerCoupons=" + customerCoupons + '}';
	}
}
