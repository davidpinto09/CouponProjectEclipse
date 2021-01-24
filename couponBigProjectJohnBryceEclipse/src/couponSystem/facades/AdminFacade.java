package couponSystem.facades;

import java.util.Collection;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.exceptions.DAOException;
import couponSystem.exceptions.FacadeException;
import couponSystem.javaBeans.Company;
import couponSystem.javaBeans.Customer;
import couponSystem.jdbc.dao.classes.CompaniesDBDAO;
import couponSystem.jdbc.dao.classes.CouponsDBDAO;
import couponSystem.jdbc.dao.classes.CustomersDBDAO;
import couponSystem.jdbc.dao.interfaces.CompaniesDAO;
import couponSystem.jdbc.dao.interfaces.CouponsDAO;
import couponSystem.jdbc.dao.interfaces.CustomersDAO;

public class AdminFacade extends ClientFacade {
	public static final String ADMIN_EMAIL = "admin@admin.com";
	public static final String ADMIN_PASSWORD = "admin";

	public AdminFacade(CompaniesDAO companiesDAO, CustomersDAO customersDAO, CouponsDAO couponsDAO) {
		super(companiesDAO, customersDAO, couponsDAO);
	}

	@Override
	public boolean login(String email, String password) {

		return email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD);
	}

	/**
	 * Add company to the database if company not exists
	 *
	 * @param company to add
	 * @throws CouponSystemException
	 * @throws DAOException          data access object exception
	 * @throws FacadeException       facade exception
	 */
	public void addCompany(Company company) throws CouponSystemException {
		if (!companiesDAO.isCompanyInDatabase(company.getCompanyName(), company.getCompanyEmail())) {

			companiesDAO.addCompany(company);

		} else {

			throw new FacadeException(
					"Adding Company " + company.getCompanyName() + " failed - Company already exists in database");
		}

	}

	/**
	 * Update the all fields of company without changing @param companyId and @param
	 * companyName of actual Company
	 *
	 * @param company to update
	 * @throws CouponSystemException
	 * @throws DAOException          data access object exception
	 * @throws FacadeException
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		if (companiesDAO.isCompanyInDatabase(company.getCompanyName(), company.getCompanyEmail())) {
			companiesDAO.updateCompany(company);
		} else {

			throw new FacadeException(
					"Updating Company " + company.getCompanyName() + " failed - Company does not exist in database");
		}
	}

	/**
	 * Delete from database the Company from the parameter received with all the
	 * Company Coupons and all Purchase Coupons
	 *
	 * @param companyID actual Company to delete
	 * @throws CouponSystemException
	 * @throws DAOException          data access object exception
	 * @throws FacadeException
	 */
	
	public void deleteCompany(int companyID) throws CouponSystemException {

		couponsDAO.deleteCouponPurchaseByCompanyId(companyID);
		couponsDAO.deleteCoupon(CouponsDBDAO.deleteCouponByCompanyID, companyID);
		companiesDAO.deleteCompany(companyID);

	}

	/**
	 * Get all the Companies from DataBase
	 *
	 * @return list of companies
	 * @throws CouponSystemException
	 * @throws DAOException          data access object exception
	 * @throws FacadeException
	 */
	public Collection<Company> getAllCompanies() throws CouponSystemException {

		return companiesDAO.getAllCompanies();
	}

	/**
	 * Get a specific Company
	 *
	 * @param companyID actual Company to get
	 * @return A specific Company
	 * @throws CouponSystemException
	 * @throws DAOException          data access object exception
	 * @throws FacadeException
	 */
	public Company getOneCompany(int companyID) throws CouponSystemException {

		return companiesDAO.getOneCompany(companyID);
	}

	/**
	 * Add customer to the database if customer not exists
	 *
	 * @param customer to add
	 * @throws CouponSystemException
	 * @throws DAOException          data access object exception
	 * @throws FacadeException       facade exception
	 */
	public void addCustomer(Customer customer) throws CouponSystemException, FacadeException {
		if (!customersDAO.isCustomerInDataBase(customer.getCustomerEmail())) {

			customersDAO.addCustomer(customer);
		} else {
			throw new FacadeException("Adding Customer " + customer.getCustomerFirstName() + " "
					+ customer.getCustomerLastName() + " failed - Customer already exists");
		}
	}

	/**
	 * Update the all fields of customer without changing @param customerId of
	 * actual customer
	 *
	 * @param customer to update
	 * @throws DAOException    data access object exception
	 * @throws FacadeException if customer does not exist
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {
		if (customersDAO.isCustomerInDataBase(customer.getCustomerEmail())) {
			customersDAO.updateCustomer(customer);

		} else {

			throw new FacadeException("Updating Customer " + customer.getCustomerFirstName() + " "
					+ customer.getCustomerLastName() + " failed - Customer does not exist in database");
		}
	}

	/**
	 * Delete from database the Customer from the parameter received with all the
	 * Purchase Coupons
	 *
	 * @param customerID actual Customer to delete
	 * @throws CouponSystemException
	 * @throws DAOException          data access object exception
	 */
	public void deleteCustomer(int customerID) throws CouponSystemException {
		couponsDAO.deleteCouponPurchase(CouponsDBDAO.deleteCouponPurchaseByCustomerID, customerID);
		customersDAO.deleteCustomer(customerID);

	}

	/**
	 * Get all the Customer from DataBase
	 *
	 * @return list of Customer
	 * @throws CouponSystemException
	 * @throws DAOException          data access object exception
	 * @throws FacadeException
	 */
	public Collection<Customer> getAllCustomers() throws CouponSystemException {

		return customersDAO.getAllCustomers();
	}

	/**
	 * Get a specific Customer
	 *
	 * @param customerID to get
	 * @return A specific Customer
	 * @throws CouponSystemException
	 * @throws DAOException          data access object exception
	 * @throws FacadeException
	 */
	public Customer getOneCustomer(int customerID) throws CouponSystemException {

		return customersDAO.getOneCustomer(customerID);
	}

}
