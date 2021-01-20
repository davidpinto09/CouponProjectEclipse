package couponSystem.jdbc.dao.interfaces;

import java.util.Collection;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.exceptions.DAOException;
import couponSystem.javaBeans.Customer;

public interface CustomersDAO {

	/**
	 * Verify in DataBase if the Customer exists
	 *
	 * @param customerEmail    the actual email of customer equalsIgnoreCase
	 * @param customerPassword the actual password of customer must be equals
	 * @return boolean
	 * @throws CouponSystemException
	 * @throws DAOException          if customer does not exists in DataBase
	 */
	boolean isCustomerExists(String customerEmail, String customerPassword) throws CouponSystemException;

	/**
	 * @param customerEmail
	 * @return
	 * @throws CouponSystemException
	 * @throws DAOException          if customer does not exists in DataBase
	 */
	boolean isCustomerInDataBase(String customerEmail) throws CouponSystemException;

	/**
	 * Add the Customer in parameter to the DataBase
	 *
	 * @param customer to add
	 * @throws CouponSystemException
	 * @throws DAOException          if data from parameter does not accord the
	 *                               table values
	 */
	void addCustomer(Customer customer) throws CouponSystemException;

	/**
	 * Update Customer that already exists in DataBase
	 *
	 * @param customer to update
	 * @throws CouponSystemException
	 * @throws DAOException          if customer does not exists in DataBase
	 */
	void updateCustomer(Customer customer) throws CouponSystemException;

	/**
	 * Delete the Customer in DataBase
	 *
	 * @param customerId to delete
	 * @throws CouponSystemException
	 * @throws DAOException          if customer does not exists in DataBase
	 */
	void deleteCustomer(int customerId) throws CouponSystemException;

	/**
	 * Get all the Customers in DatBase
	 *
	 * @return collection of all customers
	 * @throws CouponSystemException
	 * @throws DAOException          if customer does not exists in DataBase
	 */
	Collection<Customer> getAllCustomers() throws CouponSystemException;

	/**
	 * Get a specific customer
	 *
	 * @param customersId of customer to get
	 * @return specific customer
	 * @throws CouponSystemException
	 * @throws DAOException          if customer does not exists in DataBase
	 */
	Customer getOneCustomer(int customersId) throws CouponSystemException;

}
