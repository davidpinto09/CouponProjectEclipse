package couponSystem.jdbc.dao.interfaces;

import java.util.Collection;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.exceptions.DAOException;
import couponSystem.javaBeans.Coupon;

public interface CouponsDAO {

	/**
	 * Before that a company can adds a coupon this method verifies that the title
	 * of the coupon to add is not related to the company who tries to add This
	 * method verifies if the company created a coupon with using the title exists in
	 * database
	 *
	 * @param companyId   company who wants to add
	 * @param couponTitle actual coupon title to add
	 * @return true if company already has created the coupon, false if it isn't.
	 * @throws CouponSystemException
	 * @throws DAOException          data access object exception if the process of
	 *                               finding the coupon fail
	 */
	boolean isCouponExist(int companyId, String couponTitle) throws CouponSystemException;

	/**
	 * Add the Coupon in parameter to the DataBase
	 *
	 * @param coupon to add
	 * @throws CouponSystemException
	 * @throws DAOException          if data from parameter does not accord the
	 *                               table values
	 */
	void addCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * Add the Coupon Purchase to the DataBase by customerId and couponId
	 *
	 * @param customerId customer who purchased
	 * @param couponId   coupon purchased
	 * @throws CouponSystemException
	 * @throws DAOException          if data from parameter does not accord the
	 *                               table values
	 */
	void addCouponPurchase(int customerId, int couponId) throws CouponSystemException;

	/**
	 * Update Coupon that already exists in DataBase
	 *
	 * @param coupon coupon to update
	 * @throws CouponSystemException
	 * @throws DAOException          if coupon does not exists in DataBase
	 */
	void updateCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * Delete one or many coupons from the DataBase,can be through Coupon ID or
	 * Company ID according on the sql String query called.
	 *
	 * @param currentId: couponId||companyID
	 * @throws CouponSystemException
	 * @throws DAOException          if coupon does not exists in DataBase
	 */
	void deleteCoupon(String sql, int currentId) throws CouponSystemException;

	/**
	 * Delete the coupon purchase history from the DataBase, can be through Coupon
	 * ID or Customer ID according on the sql String query called.
	 *
	 * @param currentId: couponID||customerID
	 * @throws CouponSystemException
	 * @throws DAOException          if coupon does not exists in DataBase
	 */

	void deleteCouponPurchase(String sql, int currentId) throws CouponSystemException;

	/**
	 * Delete all the coupons purchased by companyID in DataBase
	 *
	 * @param companyID to delete
	 * @throws CouponSystemException
	 * @throws DAOException          if coupon does not exists in DataBase
	 */

	void deleteCouponPurchaseByCompanyId(int companyID) throws CouponSystemException;

	/**
	 * Get all the Company Coupons in DatBase
	 *
	 * @return collection of all coupons of the company
	 * @throws CouponSystemException
	 * @throws DAOException          if there is no Coupons in DataBase
	 */

	Collection<Coupon> getCouponsBy(String sql, int id, Object searchParameter) throws CouponSystemException;

	/**
	 * Get a specific Coupon
	 *
	 * @param couponId of coupon to get
	 * @return specific coupon
	 * @throws CouponSystemException
	 * @throws DAOException          if there is no Coupons with the specify
	 *                               couponId in DataBase
	 */
	Coupon getOneCoupon(int couponId) throws CouponSystemException;

	 boolean isPurchaseExist(int couponId) throws CouponSystemException ;
	/**
	 * @param customerId
	 * @param couponId
	 * @return boolean
	 * @throws CouponSystemException
	 * @throws DAOException
	 */
	boolean isPurchaseExist(int customerId, int couponId) throws CouponSystemException;

	/**
	 *
	 * @return
	 * @throws CouponSystemException
	 * @throws DAOException
	 */
	Collection<Coupon> getAllCoupons() throws CouponSystemException;
}
