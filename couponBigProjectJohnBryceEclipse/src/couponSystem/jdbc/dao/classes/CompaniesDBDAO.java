package couponSystem.jdbc.dao.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.exceptions.DAOException;
import couponSystem.javaBeans.Company;
import couponSystem.jdbc.connectionPool.ConnectionPool;
import couponSystem.jdbc.dao.interfaces.CompaniesDAO;

public class CompaniesDBDAO implements CompaniesDAO {

	private ConnectionPool connectionPool;

	public ConnectionPool getConnectionPool() throws CouponSystemException {

		if (connectionPool == null) {

			connectionPool = ConnectionPool.getConnectionPoolInstance();
		}
		return connectionPool;
	}

	@Override
	public boolean isCompanyExists(String companyEmail, String companyPassword) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();
			String sql = "SELECT COMPANY_EMAIL, COMPANY_PASSWORD FROM COMPANIES WHERE COMPANY_EMAIL = " + "\""
					+ companyEmail + "\"" + " AND COMPANY_PASSWORD = " + "\"" + companyPassword + "\"";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			return rs.next();

		} catch (SQLException sqlException) {

			throw new DAOException(sqlException.getMessage());

		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}

		}

	}

	@Override
	public boolean isCompanyInDatabase(String companyName, String companyEmail) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();
			String sql = "SELECT COMPANY_NAME, COMPANY_EMAIL FROM COMPANIES WHERE COMPANY_NAME = " + "\"" + companyName
					+ "\"" + " AND COMPANY_EMAIL = " + "\"" + companyEmail + "\"";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			ResultSet actualCompany = preparedStatement.executeQuery();

			return actualCompany.next();

		} catch (SQLException sqlException) {

			throw new DAOException(sqlException.getMessage());

		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}

		}

	}

	@Override
	public void addCompany(Company company) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();
			String sql = "INSERT INTO COMPANIES VALUES(?,?,?,?)";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, company.getCompanyId());
			preparedStatement.setString(2, company.getCompanyName());
			preparedStatement.setString(3, company.getCompanyEmail());
			preparedStatement.setString(4, company.getCompanyPassword());

			preparedStatement.executeUpdate();

			System.out.println(company.getCompanyName() + "has been added");

		} catch (SQLException sqlException) {

			throw new DAOException("Saving Company " + company + " has failed,\n", sqlException);
		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}

		}

	}

	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();
			String sql = "UPDATE COMPANIES SET COMPANY_EMAIL=?,COMPANY_PASSWORD=? WHERE COMPANY_ID = "
					+ company.getCompanyId();

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, company.getCompanyEmail());
			preparedStatement.setString(2, company.getCompanyPassword());

			int actualCompanyRow = preparedStatement.executeUpdate();
			if (actualCompanyRow == 0) {
				throw new DAOException("Updating Company " + company + " failed because is not in the DataBase");
			}

		} catch (SQLException sqlException) {

			throw new DAOException("Updating Company " + company + " has failed", sqlException);
		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}

		}

	}

	@Override
	public void deleteCompany(int companyId) throws CouponSystemException {

		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();
			
			String sql = "DELETE FROM COMPANIES WHERE COMPANY_ID = " + companyId;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			int actualCompanyRow = preparedStatement.executeUpdate();

			if (actualCompanyRow == 0) {
				throw new DAOException(
						"Deleting Company with id " + companyId + " failed because it is not in the DataBase");
			}
		} catch (SQLException sqlException) {
			throw new DAOException("Deleting Company with id" + companyId + " has failed", sqlException);
		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}

		}
	}

	@Override
	public Collection<Company> getAllCompanies() throws CouponSystemException {

		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();
			List<Company> allCompanies = new ArrayList<>();
			String sql = "SELECT * FROM COMPANIES";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int companyId = resultSet.getInt("COMPANY_ID");
				String companyName = resultSet.getString("COMPANY_NAME");
				String companyEmail = resultSet.getString("COMPANY_EMAIL");
				String companyPassword = resultSet.getString("COMPANY_PASSWORD");

				Company actualCompany = new Company(companyId, companyName, companyEmail, companyPassword);
				allCompanies.add(actualCompany);

			}
			if (allCompanies.size() == 0) {
				throw new DAOException("Getting all Companies failed - there is no company in table");
			}
			return allCompanies;

		} catch (SQLException sqlException) {
			throw new DAOException("Getting all Companies has failed", sqlException);
		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}

		}

	}

	@Override
	public Company getOneCompany(int companyId) throws CouponSystemException {

		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();

			String sql = "SELECT * FROM COMPANIES WHERE COMPANY_ID = " + companyId;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String actualCompanyName = resultSet.getString("COMPANY_NAME");
				String actualCompanyEmail = resultSet.getString("COMPANY_EMAIL");
				String actualCompanyPassword = resultSet.getString("COMPANY_PASSWORD");

				return new Company(companyId, actualCompanyName, actualCompanyEmail, actualCompanyPassword);

			}
		} catch (SQLException sqlException) {
			throw new DAOException(" Getting one Companies has failed", sqlException);
		} finally {
			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}

		throw new DAOException("Getting Company with id " + companyId + " not found in DataBase");
	}
}
