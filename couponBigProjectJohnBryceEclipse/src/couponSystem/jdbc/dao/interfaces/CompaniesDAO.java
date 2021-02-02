package couponSystem.jdbc.dao.interfaces;

import java.sql.SQLException;
import java.util.Collection;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.exceptions.DAOException;
import couponSystem.javaBeans.Company;

public interface CompaniesDAO {

	/**
	 * Verify in DataBase if the Company exists for login
	 *
	 * @param companyEmail    the actual email of company equalsIgnoreCase
	 * @param companyPassword the actual password of company must be equals
	 * @return boolean
	 * @throws CouponSystemException
	 * @throws DAOException          if the sql execution failed
	 * @throws SQLException          if the sql execution failed
	 */

	boolean isCompanyExists(String companyEmail, String companyPassword) throws CouponSystemException;

	/**
	 * Verify if company is in database to proceed to addCompany or updateCompany or
	 * deleteCompany
	 *
	 * @param companyName  Actual Name of the company
	 * @param companyEmail Actual Email of the company
	 * @return true if the company parameters match with at least 1 object in
	 *         database else false
	 * @throws CouponSystemException
	 * @throws DAOException          if the sql execution failed
	 * @throws SQLException          if the sql execution failed
	 */
	boolean isCompanyInDatabase(String companyName, String companyEmail) throws CouponSystemException;

	/**
	 * Add the Company in parameter to the DataBase
	 *
	 * @param company to add
	 * @throws CouponSystemException
	 * @throws DAOException          if data from parameter does not accord the
	 *                               table values
	 * @throws SQLException          if the sql execution failed
	 */
	void addCompany(Company company) throws CouponSystemException;

	/**
	 * Update Company that already exists in DataBase
	 *
	 * @param company to update
	 * @throws CouponSystemException
	 * @throws DAOException          if company does not exists in DataBase
	 * @throws SQLException          if the sql execution failed
	 */
	void updateCompany(Company company) throws CouponSystemException;

	/**
	 * Delete the Company in DataBase
	 *
	 * @param companyId id from company to delete
	 * @throws CouponSystemException
	 * @throws DAOException          if company does not exists in DataBase
	 * @throws SQLException          if the sql execution failed
	 */
	void deleteCompany(int companyId) throws CouponSystemException;

	/**
	 * Get all the Companies in DatBase
	 *
	 * @return collection of all companies
	 * @throws CouponSystemException
	 * @throws DAOException          if there is no Company in DataBase
	 * @throws SQLException          if the sql execution failed
	 */
	Collection<Company> getAllCompanies() throws CouponSystemException;

	/**
	 * Get a specific company
	 *
	 * @param companyId of company to get
	 * @return specific company
	 * @throws CouponSystemException
	 * @throws DAOException          if there is no Company with the specify
	 *                               companyId in DataBase
	 * @throws SQLException          if the sql execution failed
	 */
	Company getOneCompany(int companyId) throws CouponSystemException;

}
