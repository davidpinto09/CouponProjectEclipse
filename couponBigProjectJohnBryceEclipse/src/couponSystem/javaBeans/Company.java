package couponSystem.javaBeans;

import java.util.ArrayList;
import java.util.List;

public class Company {

	private int companyId;

	private String companyName;
	private String companyEmail;
	private String companyPassword;
	private List<Coupon> companyCoupons = new ArrayList<>();

	public Company(String companyName, String companyEmail, String companyPassword) {

		this.companyName = companyName;
		this.companyEmail = companyEmail;
		this.companyPassword = companyPassword;
	}

	public Company(int companyId, String companyName, String companyEmail, String companyPassword) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyEmail = companyEmail;
		this.companyPassword = companyPassword;
	}

	public Company(int companyId) {
		this.companyId = companyId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyPassword() {
		return companyPassword;
	}

	public void setCompanyPassword(String companyPassword) {
		this.companyPassword = companyPassword;
	}

	public List<Coupon> getCompanyCoupons() {
		return companyCoupons;
	}

	public void setCompanyCoupons(List<Coupon> companyCoupons) {
		this.companyCoupons = companyCoupons;
	}

	@Override
	public String toString() {
		String toString = "Company{" + "companyId=" + companyId + ", companyName='" + companyName + '\''
				+ ", companyEmail='" + companyEmail + '\'' + ", companyPassword='" + companyPassword + '\''
				+ ", companyCoupons=";
		for (Coupon coupon : companyCoupons) {

			toString += "\n" + coupon.toString();
		}

		return toString;
	}
}
