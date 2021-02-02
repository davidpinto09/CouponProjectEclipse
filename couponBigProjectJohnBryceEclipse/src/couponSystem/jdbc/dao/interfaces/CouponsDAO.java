package couponSystem.jdbc.dao.interfaces;

import java.sql.SQLException;
import java.util.Collection;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.exceptions.DAOException;
import couponSystem.javaBeans.Coupon;

/**
 * @author David Pinto
 *
 */
public interface CouponsDAO {

	/**
	 * Before that a company can adds a coupon this method verifies that the title
	 * of the coupon to add is not related to the company who tries to add This
	 * method verifies if the company created a coupon with using the title exists
	 * in database
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
	 * @param sql        one of the static final fields in Class CouponsDBDAO
	 * @param currentId: couponId||companyID
	 * @throws CouponSystemException
	 * @throws DAOException          if coupon does not exists in DataBase
	 */
	void deleteCoupon(String sql, int currentId) throws CouponSystemException;

	/**
	 * Delete the coupon purchase history from the DataBase, can be through Coupon
	 * ID or Customer ID according on the sql String query called.
	 * 
	 * @param sql        one of the static final fields in Class CouponsDBDAO
	 * @param currentId: couponID||customerID
	 * @throws CouponSystemException
	 * @throws DAOException          if coupon does not exists in DataBase
	 */

	void deleteCouponPurchase(String sql, int currentId) throws CouponSystemException;

	/**
	 * When Admin wants or has been asked to delete a company, this method will
	 * delete all coupons that have been purchase
	 *
	 * @param companyID company who admin wants to delete
	 * @throws CouponSystemException
	 * @throws DAOException          if coupon does not exists in DataBase
	 */

	void deleteCouponPurchaseByCompanyId(int companyID) throws CouponSystemException;

	/**
	 * This methods return collection of coupons depending of the static final sql
	 * in class CouponsDBDAO, the id sent (can be couponID,companyID or customerID)
	 * and the searchParameter if is not null it will search specific coupons to add
	 * into the collection.
	 * 
	 * @param sql             one of the static final fields in Class CouponsDBDAO
	 * @param id              can be couponID,companyID,customerID
	 * @param searchParameter if null it returns all coupons, else specific coupons
	 *                        depending of this parameter. Ex(price,
	 *                        Category,amount,...).
	 * @implNote the sql and the search parameter they must be related
	 * @return collection of coupons
	 * @throws DAOException if the collection is empty
	 * @throws SQLException if error in during sql execution
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

	/**
	 * This method verifies if a specific coupon identified as couponID has been
	 * purchased
	 * 
	 * @param couponId coupon purchased
	 * @return boolean true if the coupon has been purchased , else false
	 * @throws CouponSystemException
	 * @throws DAOException
	 */
	boolean isPurchaseExist(int couponId) throws CouponSystemException;

	/**
	 * This method verifies if customer identified as customerID has purchase a
	 * specific coupon identified as couponID
	 * 
	 * @param customerId specific customer who logged
	 * @param couponId   coupon to purchase
	 * @return boolean true if this customer has already purchase the coupon, else
	 *         false
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
