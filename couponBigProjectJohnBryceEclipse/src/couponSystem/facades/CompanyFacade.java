package couponSystem.facades;

import java.util.Collection;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.exceptions.DAOException;
import couponSystem.exceptions.FacadeException;
import couponSystem.javaBeans.Company;
import couponSystem.javaBeans.Coupon;
import couponSystem.jdbc.dao.classes.CouponsDBDAO;
import couponSystem.jdbc.dao.interfaces.CompaniesDAO;
import couponSystem.jdbc.dao.interfaces.CouponsDAO;
import couponSystem.jdbc.dao.interfaces.CustomersDAO;

public class CompanyFacade extends ClientFacade {

	private int companyID;

	public CompanyFacade(CompaniesDAO companiesDAO, CustomersDAO customersDAO, CouponsDAO couponsDAO) {
		super(companiesDAO, customersDAO, couponsDAO);
	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	@Override
	public boolean login(String email, String password) {
		boolean isExist = false;
		try {

			if (companiesDAO.isCompanyExists(email, password)) {
				isExist = true;
				
				//maybe make a function getOneCompanyByEmail(email) to not run into all companies a lot of times
				for (Company oneCompany : companiesDAO.getAllCompanies()) {

					if (oneCompany.getCompanyEmail().equalsIgnoreCase(email)) {

						this.setCompanyID(oneCompany.getCompanyId());
						break;

					}
				}

			}

		} catch (CouponSystemException daoException) {
			daoException.printStackTrace();
		}
		return isExist;
	}

	/**
	 * add coupon to the Data base if the coupon is not exist to the actual company
	 *
	 * @param coupon to add
	 * @throws CouponSystemException
	 * @throws DAOException          if the coupon exist in the DataBase
	 * @throws FacadeException
	 */
	public void addCoupon(Coupon coupon) throws CouponSystemException {

		if (couponsDAO.isCouponExist(this.companyID, coupon.getCouponTitle())) {
			throw new FacadeException("The coupon you are trying to add already exists");
		} else {

			couponsDAO.addCoupon(coupon);
		}

	}

	/**
	 * Update the actual coupon without the @param coupon_id and @param company_name
	 * for the actual company
	 *
	 * @param coupon to update
	 * @throws CouponSystemException
	 * @throws DAOException          if the coupon is not exist in the DataBase.
	 * @throws FacadeException
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {

		couponsDAO.updateCoupon(coupon);

	}

	/**
	 * Delete coupon from DataBase for actual company and all purchased coupons
	 *
	 * @param couponId coupon to delete
	 * @throws CouponSystemException
	 * @throws DAOException          if the coupon is not exist in DataBase.
	 * @throws FacadeException
	 */
	public void deleteCoupon(int couponId) throws CouponSystemException {
		couponsDAO.deleteCouponPurchase(CouponsDBDAO.deleteCouponPurchaseByCouponID, couponId);
		couponsDAO.deleteCoupon(CouponsDBDAO.deleteCouponByCouponID, couponId);

	}

	/**
	 * Get all the coupons from the DataBase for this company
	 *
	 * @return a list of coupons
	 * @throws CouponSystemException
	 * @throws DAOException          if there no coupons in DataBase.
	 * @throws FacadeException
	 */
	public Collection<Coupon> getAllCompanyCoupons() throws CouponSystemException {

		return couponsDAO.getCouponsBy(CouponsDBDAO.getAllCompanyCoupons, this.companyID, null);

	}

	/**
	 * Get all the company coupons by coupon category
	 *
	 * @param category category to set
	 * @return collection of coupons by category
	 * @throws CouponSystemException
	 * @throws DAOException          if the actual company has not coupon with the
	 *                               specified category
	 * @throws FacadeException
	 */
	public Collection<Coupon> getAllCompanyCouponsByCategory(Coupon.Category category) throws CouponSystemException {

		return couponsDAO.getCouponsBy(CouponsDBDAO.getAllCompanyCouponsByCategory, this.companyID,
				category.ordinal() + 1);

	}

	/**
	 * Get all the coupons below or equals to @param maxPrice for the actual Company
	 *
	 * @param maxPrice price to set
	 * @return list of coupons for the actual Company
	 * @throws CouponSystemException
	 * @throws DAOException          if the company has not coupons
	 * @throws FacadeException       if the company has not coupons below or equal
	 *                               to @param maxPrice
	 */
	public Collection<Coupon> getAllCompanyCouponsByPrice(double maxPrice) throws CouponSystemException {

		return couponsDAO.getCouponsBy(CouponsDBDAO.getAllCompanyCouponsByMaxPrice, this.companyID, maxPrice);

	}

	/**
	 * This method receives the data of the actual company from DataBase
	 *
	 * @return details of actual company
	 * @throws CouponSystemException
	 * @throws FacadeException       if the actualCompany does not exists
	 */
	public Company getCompanyDetails() throws CouponSystemException {
		Company companyToReturn = companiesDAO.getOneCompany(this.getCompanyID());
		if (companyToReturn != null) {
			return companyToReturn;
		}
		throw new FacadeException("Get company details in facade failed");
	}

}
