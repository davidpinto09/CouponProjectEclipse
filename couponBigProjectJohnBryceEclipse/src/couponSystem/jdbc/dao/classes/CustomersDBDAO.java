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
import couponSystem.javaBeans.Customer;
import couponSystem.jdbc.connectionPool.ConnectionPool;
import couponSystem.jdbc.dao.interfaces.CustomersDAO;

public class CustomersDBDAO implements CustomersDAO {

	private ConnectionPool connectionPool;

	public ConnectionPool getConnectionPool() throws CouponSystemException {

		if (connectionPool == null) {

			connectionPool = ConnectionPool.getConnectionPoolInstance();
		}
		return connectionPool;
	}

	@Override
	public boolean isCustomerExists(String customerEmail, String customersPassword) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();

			String sql = "SELECT CUSTOMER_EMAIL, CUSTOMER_PASSWORD FROM CUSTOMERS WHERE CUSTOMER_EMAIL = " + "\""
					+ customerEmail + "\"" + " AND CUSTOMER_PASSWORD = " + "\"" + customersPassword + "\"";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSet.next();

		} catch (SQLException sqlException) {

			throw new DAOException(sqlException.getMessage());
		} finally {
			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}

	}

	@Override
	public boolean isCustomerInDataBase(String customerEmail) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();

			String sql = "SELECT CUSTOMER_EMAIL FROM CUSTOMERS WHERE CUSTOMER_EMAIL = " + "\"" + customerEmail + "\"";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			ResultSet actualCustomer = preparedStatement.executeQuery();

			return actualCustomer.next();

		} catch (SQLException sqlException) {

			throw new DAOException(sqlException.getMessage());
		} finally {
			getConnectionPool().restoreConnection(connection);
		}

	}

	@Override
	public void addCustomer(Customer customer) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();

			String sql = "INSERT INTO CUSTOMERS VALUES(?,?,?,?,?)";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, customer.getCustomerId());
			preparedStatement.setString(2, customer.getCustomerFirstName());
			preparedStatement.setString(3, customer.getCustomerLastName());
			preparedStatement.setString(4, customer.getCustomerEmail());
			preparedStatement.setString(5, customer.getCustomerPassword());

			if (preparedStatement.executeUpdate() != 0) {
				for (Customer oneCustomer : getAllCustomers()) {
					if (oneCustomer.getCustomerEmail().equals(customer.getCustomerEmail())) {
						System.out.println("The Customer with name : " + oneCustomer.getCustomerFirstName() + " "
								+ oneCustomer.getCustomerLastName() + " has been added and has received the id "
								+ oneCustomer.getCustomerId());
						break;
					}

				}
			}

		} catch (SQLException sqlException) {

			throw new DAOException("Adding Customer " + customer.getCustomerFirstName() + " " + " has failed,\n",
					sqlException);
		} finally {
			if (connection != null) {

				getConnectionPool().restoreConnection(connection);
			}
		}

	}

	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();

			String sql = "UPDATE CUSTOMERS SET CUSTOMER_FIRST_NAME=?, CUSTOMER_LAST_NAME = ? , CUSTOMER_EMAIL=?,CUSTOMER_PASSWORD=? WHERE CUSTOMER_ID = "
					+ customer.getCustomerId();

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, customer.getCustomerFirstName());
			preparedStatement.setString(2, customer.getCustomerLastName());
			preparedStatement.setString(3, customer.getCustomerEmail());
			preparedStatement.setString(4, customer.getCustomerPassword());

			int actualCustomerRow = preparedStatement.executeUpdate();
			if (actualCustomerRow == 0) {
				throw new DAOException("Updating Customer " + customer.getCustomerFirstName() + " "
						+ customer.getCustomerLastName() + " failed because is not in the DataBase");
			} else {
				System.out.println("Updating " + customer.getCustomerFirstName() + " " + customer.getCustomerLastName()
						+ " worked.");
			}

		} catch (SQLException sqlException) {

			throw new DAOException("Updating Customer " + customer + " has failed", sqlException);
		} finally {
			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}

	}

	@Override
	public void deleteCustomer(int customerId) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();

			String sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = " + customerId;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			int actualCustomerRow = preparedStatement.executeUpdate();
			if (actualCustomerRow == 0) {
				throw new DAOException(
						"Deleting Customer with id " + customerId + " failed because it is not in the DataBase");
			} else {
				System.out.println("Deleting Customer with id " + customerId + "  success");
			}
		} catch (SQLException sqlException) {
			throw new DAOException("Deleting Customer with id" + customerId + " has failed", sqlException);
		} finally {
			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}
	}

	@Override
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		List<Customer> allCustomers = new ArrayList<>();
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();

			String sql = "SELECT * FROM CUSTOMERS";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int customerId = resultSet.getInt("CUSTOMER_ID");
				String customerFirstName = resultSet.getString("CUSTOMER_FIRST_NAME");
				String customerLastName = resultSet.getString("CUSTOMER_LAST_NAME");
				String customerEmail = resultSet.getString("CUSTOMER_EMAIL");
				String customerPassword = resultSet.getString("CUSTOMER_PASSWORD");

				Customer actualCustomer = new Customer(customerId, customerFirstName, customerLastName, customerEmail,
						customerPassword);
				allCustomers.add(actualCustomer);

			}
			if (allCustomers.size() == 0) {
				throw new DAOException("Getting all Customers failed - there is no customers in table");
			}

		} catch (SQLException sqlException) {
			throw new DAOException("Getting all Customers has failed", sqlException);
		} finally {
			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}
		return allCustomers;
	}

	@Override
	public Customer getOneCustomer(int customerId) throws CouponSystemException {

		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();

			String sql = "SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID = " + customerId;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {

				String customerFirstName = resultSet.getString("CUSTOMER_FIRST_NAME");
				String customerLastName = resultSet.getString("CUSTOMER_LAST_NAME");
				String customerEmail = resultSet.getString("CUSTOMER_EMAIL");
				String customerPassword = resultSet.getString("CUSTOMER_PASSWORD");

				return new Customer(customerId, customerFirstName, customerLastName, customerEmail, customerPassword);

			}
		} catch (SQLException sqlException) {

			throw new DAOException("Getting Customer with id " + customerId + " has failed", sqlException);
		} finally {
			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}
		throw new DAOException("Getting Customer with id " + customerId + " has failed - is not in the DataBase");
	}
}
