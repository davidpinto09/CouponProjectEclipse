package couponSystem.javaBeans;

import java.time.LocalDate;
import java.util.Objects;

public class Coupon {

	private int couponId, companyId, couponAmount;
	private Category couponCategory;
	private String couponTitle, couponDescription, couponImage;
	private LocalDate couponStartDate, couponEndDate;
	private double couponPrice;

	public Coupon(int couponId, int companyId, Category couponCategory, String couponTitle, String couponDescription,
			LocalDate couponStartDate, LocalDate couponEndDate, int couponAmount, double couponPrice,
			String couponImage) {
		setCouponId(couponId);
		setCompanyId(companyId);
		setCouponAmount(couponAmount);
		setCouponCategory(couponCategory);
		setCouponTitle(couponTitle);
		setCouponDescription(couponDescription);
		setCouponImage(couponImage);
		setCouponStartDate(couponStartDate);
		setCouponEndDate(couponEndDate);
		setCouponPrice(couponPrice);
	}

	public Coupon(int companyId, Category couponCategory, String couponTitle, String couponDescription,
			LocalDate couponStartDate, LocalDate couponEndDate, int couponAmount, double couponPrice,
			String couponImage) {
		setCompanyId(companyId);
		setCouponAmount(couponAmount);
		setCouponCategory(couponCategory);
		setCouponTitle(couponTitle);
		setCouponDescription(couponDescription);
		setCouponImage(couponImage);
		setCouponStartDate(couponStartDate);
		setCouponEndDate(couponEndDate);
		setCouponPrice(couponPrice);
	}

	public int getCategoryId() {
		return this.couponCategory.ordinal() + 1;
	}

	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(int couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Category getCouponCategory() {
		return couponCategory;
	}

	public void setCouponCategory(Category couponCategory) {
		this.couponCategory = couponCategory;
	}

	public String getCouponTitle() {
		return couponTitle;
	}

	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}

	public String getCouponDescription() {
		return couponDescription;
	}

	public void setCouponDescription(String couponDescription) {
		this.couponDescription = couponDescription;
	}

	public String getCouponImage() {
		return couponImage;
	}

	public void setCouponImage(String couponImage) {
		this.couponImage = couponImage;
	}

	public LocalDate getCouponStartDate() {
		return couponStartDate;
	}

	public void setCouponStartDate(LocalDate couponStartDate) {
		this.couponStartDate = couponStartDate;
	}

	public LocalDate getCouponEndDate() {
		return couponEndDate;
	}

	public void setCouponEndDate(LocalDate couponEndDate) {
		this.couponEndDate = couponEndDate;
	}

	public double getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(double couponPrice) {
		this.couponPrice = couponPrice;
	}

	@Override
	public String toString() {
		return "Coupon{" + "couponId=" + couponId + ", companyId=" + companyId + ", couponAmount=" + couponAmount
				+ ", couponCategory=" + couponCategory.name() + ", couponTitle='" + couponTitle + '\''
				+ ", couponDescription='" + couponDescription + '\'' + ", couponImage='" + couponImage + '\''
				+ ", couponStartDate=" + couponStartDate + ", couponEndDate=" + couponEndDate + ", couponPrice="
				+ couponPrice + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Coupon))
			return false;
		Coupon coupon = (Coupon) o;
		return getCouponId() == coupon.getCouponId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCouponId());
	}

	public enum Category {

		FOOD, ELECTRICITY, RESTAURANT, VACATION, LIFESTYLE_AND_HEALTH, CULTURE_AND_ENTERTAINMENT, SPORTS, GAMES,
		SHOPPING,
	}

}
