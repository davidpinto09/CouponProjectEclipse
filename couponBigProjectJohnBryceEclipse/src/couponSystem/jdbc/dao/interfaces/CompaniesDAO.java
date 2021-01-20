package couponSystem.jdbc.dao.interfaces;

import java.util.Collection;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.exceptions.DAOException;
import couponSystem.javaBeans.Company;

public interface CompaniesDAO {

	/**
	 * Verify in DataBase if the Company exists
	 *
	 * @param companyEmail    the actual email of company equalsIgnoreCase
	 * @param companyPassword the actual password of company must be equals
	 * @return boolean
	 * @throws CouponSystemException
	 * @throws DAOException          if company does not exists in DataBase
	 */

	boolean isCompanyExists(String companyEmail, String companyPassword) throws CouponSystemException;

	/**
	 * Verify in database
	 *
	 * @param companyName  Actual Name of the company
	 * @param companyEmail Actual Email of the company
	 * @return true if the company parameters match with at least 1 object in
	 *         database else false
	 * @throws CouponSystemException
	 * @throws DAOException          if data from parameter does not accord the
	 *                               table values
	 */
	boolean isCompanyInDatabase(String companyName, String companyEmail) throws CouponSystemException;

	/**
	 * Add the Company in parameter to the DataBase
	 *
	 * @param company to add
	 * @throws CouponSystemException
	 * @throws DAOException          if data from parameter does not accord the
	 *                               table values
	 */
	void addCompany(Company company) throws CouponSystemException;

	/**
	 * Update Company that already exists in DataBase
	 *
	 * @param company to update
	 * @throws CouponSystemException
	 * @throws DAOException          if company does not exists in DataBase
	 */
	void updateCompany(Company company) throws CouponSystemException;

	/**
	 * Delete the Company in DataBase
	 *
	 * @param companyId to delete
	 * @throws CouponSystemException
	 * @throws DAOException          if company does not exists in DataBase
	 */
	void deleteCompany(int companyId) throws CouponSystemException;

	/**
	 * Get all the Companies in DatBase
	 *
	 * @return collection of all companies
	 * @throws CouponSystemException
	 * @throws DAOException          if there is no Company in DataBase
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
	 */
	Company getOneCompany(int companyId) throws CouponSystemException;

}
