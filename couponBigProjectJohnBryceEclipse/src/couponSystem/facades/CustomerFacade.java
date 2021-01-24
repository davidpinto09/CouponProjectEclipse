package couponSystem.facades;

import java.time.LocalDate;
import java.util.Collection;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.exceptions.DAOException;
import couponSystem.exceptions.FacadeException;
import couponSystem.javaBeans.Coupon;
import couponSystem.javaBeans.Customer;
import couponSystem.jdbc.dao.classes.CouponsDBDAO;
import couponSystem.jdbc.dao.interfaces.CompaniesDAO;
import couponSystem.jdbc.dao.interfaces.CouponsDAO;
import couponSystem.jdbc.dao.interfaces.CustomersDAO;

public class CustomerFacade extends ClientFacade {

	private int customerId;

	public CustomerFacade(CompaniesDAO companiesDAO, CustomersDAO customersDAO, CouponsDAO couponsDAO) {
		super(companiesDAO, customersDAO, couponsDAO);


	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@Override
	public boolean login(String email, String password) {
		boolean isExist = false;
		try {
			if (customersDAO.isCustomerExists(email, password)) {
				isExist = true;
				for (Customer oneCustomer : customersDAO.getAllCustomers()) {

					if (oneCustomer.getCustomerEmail().equalsIgnoreCase(email)) {

						this.setCustomerId(oneCustomer.getCustomerId());

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
	 * The Coupon will be add only once to the actual Customer coupons if Customer
	 * does not purchase the same coupon before The Coupon will not be added in
	 * Customer coupons if his amount is less or equal to 0; If the end date of the
	 * coupon purchase is equal to actual date of purchase the coupon cannot be
	 * added If the coupon is added the amount of this coupon must be subtracted by
	 * -1
	 *
	 * @param coupon the coupon to purchase
	 * @throws CouponSystemException
	 * @throws DAOException
	 * @throws FacadeException
	 */
	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {

		if (!couponsDAO.isPurchaseExist(this.customerId, coupon.getCouponId())) {
			if (coupon.getCouponAmount() > 0) {
				if (coupon.getCouponEndDate().isBefore(LocalDate.now())) {
					couponsDAO.addCouponPurchase(this.customerId, coupon.getCouponId());
					coupon.setCouponAmount(coupon.getCouponAmount() - 1);
					couponsDAO.updateCoupon(coupon);
				} else {
					throw new FacadeException("The coupon you are trying to purchase has expired");
				}
			} else {
				throw new FacadeException("This coupon is no more available");
			}
		} else {
			throw new FacadeException("You have already purchase this coupon");
		}
	}

	/**
	 * This method return all available coupons for the actual Customer
	 *
	 * @return collection of coupons
	 * @throws CouponSystemException
	 * @throws DAOException
	 * @throws FacadeException
	 */
	public Collection<Coupon> getAllCustomerCoupons() throws CouponSystemException {

		return couponsDAO.getCouponsBy(CouponsDBDAO.getAllCustomerCoupons, this.customerId, null);

	}

	/**
	 * This method return all available coupons with the specified category for the
	 * actual Customer
	 *
	 * @param category category to set
	 * @return collection of coupon for specified category
	 * @throws CouponSystemException
	 * @throws DAOException
	 * @throws FacadeException
	 */
	public Collection<Coupon> getAllCustomerCouponsByCategory(Coupon.Category category) throws CouponSystemException {

		return couponsDAO.getCouponsBy(CouponsDBDAO.getAllCustomerCouponsByCategory, this.customerId,
				category.ordinal() + 1);

	}

	/**
	 * This method return all available coupons below or less than @param maxPrice
	 * for the actual Customer
	 *
	 * @param maxPrice price to set
	 * @return collection of coupons
	 * @throws CouponSystemException
	 * @throws DAOException
	 * @throws FacadeException
	 */
	public Collection<Coupon> getAllCustomerCouponsByPrice(double maxPrice) throws CouponSystemException {

		return couponsDAO.getCouponsBy(CouponsDBDAO.getAllCustomerCouponsByMaxPrice, this.customerId, maxPrice);

	}

	/**
	 * This method receives the data of the actual customer from DataBase
	 *
	 * @return details of actual customer
	 * @throws CouponSystemException
	 * @throws DAOException          if the actual Customer does not exists
	 * @throws FacadeException
	 */

	public Customer getCustomerDetails() throws CouponSystemException {

		return customersDAO.getOneCustomer(this.getCustomerId());
	}
}
