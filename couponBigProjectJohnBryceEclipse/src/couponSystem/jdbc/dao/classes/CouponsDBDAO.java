package couponSystem.jdbc.dao.classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.exceptions.DAOException;
import couponSystem.javaBeans.Coupon;
import couponSystem.jdbc.connectionPool.ConnectionPool;
import couponSystem.jdbc.dao.interfaces.CouponsDAO;

public class CouponsDBDAO implements CouponsDAO {

	/**
	 * Delete queries
	 */
	public static final String deleteCouponByCompanyID = "DELETE FROM COUPONS WHERE COMPANY_ID = ";
	public static final String deleteCouponByCouponID = "DELETE FROM COUPONS WHERE COUPON_ID = ";
	public static final String deleteCouponPurchaseByCouponID = "DELETE FROM CUSTOMERS_VS_COUPONS WHERE COUPON_ID = ";
	public static final String deleteCouponPurchaseByCustomerID = "DELETE FROM CUSTOMERS_VS_COUPONS WHERE CUSTOMER_ID = ";

	/**
	 * Get Company Coupons Queries
	 */
	public static final String getAllCompanyCouponsByTitle = "SELECT * FROM COUPONS CO WHERE CO.COMPANY_ID = @ AND CO.COUPON_TITLE = #";
	public static final String getAllCompanyCoupons = "SELECT * FROM COUPONS CO WHERE CO.COMPANY_ID = ";
	public static final String getAllCompanyCouponsByCategory = "SELECT * FROM COUPONS CO WHERE CO.COMPANY_ID = @ AND CO.CATEGORY_ID= #";
	public static final String getAllCompanyCouponsByMaxPrice = "SELECT * FROM COUPONS CO WHERE CO.COMPANY_ID = @ AND CO.COUPON_PRICE >= # ";

	/**
	 * Get Customer Coupons Queries
	 */
	public static final String getAllCustomerCoupons = "SELECT CO.COUPON_ID, CO.COMPANY_ID, CO.CATEGORY_ID , CO.COUPON_TITLE, CO.COUPON_DESCRIPTION,CO.COUPON_START_DATE,CO.COUPON_END_DATE, COUNT(*) `COUPON_AMOUNT` ,CO.COUPON_PRICE,CO.COUPON_IMAGE FROM COUPONS CO JOIN CUSTOMERS_VS_COUPONS CUCO ON CUCO.COUPON_ID = CO.COUPON_ID WHERE CUCO.CUSTOMER_ID = ";
	public static final String getAllCustomerCouponsByCategory = "SELECT  CO.COUPON_ID, CO.COMPANY_ID, CO.CATEGORY_ID , CO.COUPON_TITLE, CO.COUPON_DESCRIPTION,CO.COUPON_START_DATE,CO.COUPON_END_DATE ,COUNT(*) `COUPON_AMOUNT` ,CO.COUPON_PRICE,CO.COUPON_IMAGE FROM COUPONS CO JOIN CUSTOMERS_VS_COUPONS CUCO ON CO.COUPON_ID = CUCO.COUPON_ID JOIN CATEGORIES CAT ON CAT.CATEGORY_ID = CO.CATEGORY_ID JOIN CUSTOMERS CU ON CU.CUSTOMER_ID = CUCO.CUSTOMER_ID  WHERE CUCO.customer_id = @ AND CAT.CATEGORY_ID = # ";
	public static final String getAllCustomerCouponsByMaxPrice = "SELECT CO.COUPON_ID, CO.COMPANY_ID, CO.CATEGORY_ID , CO.COUPON_TITLE, CO.COUPON_DESCRIPTION,CO.COUPON_START_DATE,CO.COUPON_END_DATE ,COUNT(*) `COUPON_AMOUNT` ,CO.COUPON_PRICE,CO.COUPON_IMAGE FROM COUPONS CO JOIN CUSTOMERS_VS_COUPONS CUCO ON CO.COUPON_ID = CUCO.COUPON_ID JOIN CATEGORIES CAT ON CAT.CATEGORY_ID = CO.CATEGORY_ID JOIN CUSTOMERS CU ON CU.CUSTOMER_ID = CUCO.CUSTOMER_ID WHERE CUCO.CUSTOMER_ID = @ AND CO.COUPON_PRICE <= # ";

	private ConnectionPool connectionPool;

	public ConnectionPool getConnectionPool() throws CouponSystemException {

		if (connectionPool == null) {

			connectionPool = ConnectionPool.getConnectionPoolInstance();
		}
		return connectionPool;
	}

	@Override
	public boolean isCouponExist(int companyId, String couponTitle) throws CouponSystemException {
		Connection connection = null;

		String sql = CouponsDBDAO.getAllCompanyCouponsByTitle;
		sql = sql.replaceFirst("@", String.valueOf(companyId));
		sql = sql.replaceFirst("#", "\"" + couponTitle + "\"");
		try {
			connection = getConnectionPool().getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			throw new DAOException("isCouponExist failed ", e);
		} finally {

			if (connection != null) {

				getConnectionPool().restoreConnection(connection);

			}
		}

	}

	@Override
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		Connection connection = getConnectionPool().getConnection();
		try {
			connection = getConnectionPool().getConnection();
			String sql = "INSERT INTO COUPONS VALUES (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, coupon.getCouponId());
			ps.setInt(2, coupon.getCompanyId());
			ps.setInt(3, coupon.getCategoryId());
			ps.setString(4, coupon.getCouponTitle());
			ps.setString(5, coupon.getCouponDescription());
			ps.setDate(6, Date.valueOf(coupon.getCouponStartDate()));
			ps.setDate(7, Date.valueOf(coupon.getCouponEndDate()));
			ps.setInt(8, coupon.getCouponAmount());
			ps.setDouble(9, coupon.getCouponPrice());
			ps.setString(10, coupon.getCouponImage());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException("Adding coupon " + coupon.getCouponTitle() + " failed ", e);
		} finally {
			if (connection != null) {

				getConnectionPool().restoreConnection(connection);
			}
		}

	}

	@Override
	public void addCouponPurchase(int customerId, int couponId) throws CouponSystemException {
		Connection connection = getConnectionPool().getConnection();
		try {
			connection = getConnectionPool().getConnection();
			String sql = "INSERT INTO CUSTOMERS_VS_COUPONS VALUES (?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, customerId);
			ps.setInt(2, couponId);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException("AddCouponPurchase for customer with ID " + customerId + " and coupon ID " + couponId
					+ " failed." + e);
		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Connection connection = getConnectionPool().getConnection();
		try {
			connection = getConnectionPool().getConnection();
			String sql = "UPDATE COUPONS SET CATEGORY_ID=?, COUPON_TITLE=?,COUPON_DESCRIPTION=?, COUPON_START_DATE=?, "
					+ "COUPON_END_DATE=?,COUPON_AMOUNT=?, COUPON_PRICE=?, COUPON_IMAGE=? WHERE COUPON_ID="
					+ coupon.getCouponId();

			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setInt(1, coupon.getCategoryId());
			ps.setString(2, coupon.getCouponTitle());
			ps.setString(3, coupon.getCouponDescription());
			ps.setDate(4, Date.valueOf(coupon.getCouponStartDate()));
			ps.setDate(5, Date.valueOf(coupon.getCouponEndDate()));
			ps.setInt(6, coupon.getCouponAmount());
			ps.setDouble(7, coupon.getCouponPrice());
			ps.setString(8, coupon.getCouponImage());

			int actualCompanyRow = ps.executeUpdate();
			if (actualCompanyRow == 0) {
				throw new DAOException(
						"Updating Coupon " + coupon.getCouponId() + "failed because is not in the DataBase");
			}
		} catch (SQLException sqlException) {
			throw new DAOException("Updating Coupon " + coupon.getCouponId() + " has failed,\n", sqlException);

		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}

	}

	@Override
	public void deleteCoupon(String sql, int searchParameter) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql + searchParameter);
			int actualCustomerRow = preparedStatement.executeUpdate();
			if (actualCustomerRow == 0) {
				throw new DAOException("Deleting Coupon failed because it is not in the DataBase");
			}
		} catch (SQLException sqlException) {
			throw new DAOException("Deleting Coupon has failed", sqlException);
		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}
	}

	@Override
	public void deleteCouponPurchase(String sql, int searchParameter) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();
			PreparedStatement ps = connection.prepareStatement(sql + searchParameter);
			int actualCustomerRow = ps.executeUpdate();
			if (actualCustomerRow == 0) {
				throw new DAOException("Deleting Coupon purchase failed because it's not in the DataBase");
			}

		} catch (SQLException e) {
			throw new DAOException("Deleting coupon purchase failed." + e);
		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}
	}

	@Override
	public void deleteCouponPurchaseByCompanyId(int companyID) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();
			List<Integer> couponsIdList = new ArrayList<>();
			String sql = "SELECT COUPON_ID FROM COUPONS WHERE COMPANY_ID=" + companyID;
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				couponsIdList.add(rs.getInt("COUPON_ID"));
			}
			if (!couponsIdList.isEmpty()) {
				for (Integer couponID : couponsIdList) {
					String sql1 = "DELETE FROM CUSTOMERS_VS_COUPONS WHERE COUPON_ID=" + couponID;
					connection.prepareStatement(sql1);
					ps.executeUpdate();
				}
			}
		} catch (SQLException sqlException) {
			throw new DAOException("Deleting the coupons of company" + companyID + " has failed", sqlException);
		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}
	}

	@Override
	public List<Coupon> getCouponsBy(String sql, int ID, Object searchParameter) throws CouponSystemException {
		List<Coupon> allCoupons = new ArrayList<>();
		PreparedStatement ps;
		ResultSet rs;
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();
			if (searchParameter != null) {
				sql = sql.replaceFirst("@", String.valueOf(ID));
				sql = sql.replaceFirst("#", String.valueOf(searchParameter));
				System.out.println(sql);

				ps = connection.prepareStatement(sql + " GROUP BY CO.COUPON_ID");
			} else {

				ps = connection.prepareStatement(sql + ID + " GROUP BY CO.COUPON_ID");
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				int couponID = rs.getInt("COUPON_ID");
				int companyID = rs.getInt("COMPANY_ID");
				Coupon.Category category = getCategoryFromId(rs.getInt("CATEGORY_ID"));
				String couponTitle = rs.getString("COUPON_TITLE");
				String couponDescription = rs.getString("COUPON_DESCRIPTION");
				LocalDate couponStartDate = rs.getDate("COUPON_START_DATE").toLocalDate();
				LocalDate couponEndDate = rs.getDate("COUPON_END_DATE").toLocalDate();
				int couponAmount = rs.getInt("COUPON_AMOUNT");
				double couponPrice = rs.getDouble("COUPON_PRICE");
				String couponImage = rs.getString("COUPON_IMAGE");
				Coupon actualCoupon = new Coupon(couponID, companyID, category, couponTitle, couponDescription,
						couponStartDate, couponEndDate, couponAmount, couponPrice, couponImage);
				allCoupons.add(actualCoupon);
			}

			if (allCoupons.size() == 0) {
				throw new DAOException("Getting all coupons failed - there are no coupons in the table");
			}
		} catch (SQLException sqlException) {
			throw new DAOException("Getting all coupons has failed", sqlException);
		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}
		return allCoupons;

	}

	@Override
	public Coupon getOneCoupon(int couponId) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = getConnectionPool().getConnection();

			String sql = "SELECT * FROM COUPONS WHERE COUPON_ID=" + couponId;
			PreparedStatement ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int companyID = rs.getInt("COMPANY_ID");
				Coupon.Category category = getCategoryFromId(rs.getInt("CATEGORY_ID"));
				String couponTitle = rs.getString("COUPON_TITLE");
				String couponDescription = rs.getString("COUPON_DESCRIPTION");
				LocalDate couponStartDate = rs.getDate("COUPON_START_DATE").toLocalDate();
				LocalDate couponEndDate = rs.getDate("COUPON_END_DATE").toLocalDate();
				int couponAmount = rs.getInt("COUPON_AMOUNT");
				double couponPrice = rs.getDouble("COUPON_PRICE");
				String couponImage = rs.getString("COUPON_IMAGE");

				return new Coupon(companyID, category, couponTitle, couponDescription, couponStartDate, couponEndDate,
						couponAmount, couponPrice, couponImage);
			}

		} catch (SQLException sqlException) {
			throw new DAOException("Getting coupon with ID " + couponId + " has failed" + sqlException);
		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}
		throw new DAOException("Getting coupon with ID " + couponId + " has failed - it's not in the DataBase.");
	}

	@Override
	public boolean isPurchaseExist(int customerId, int couponId) throws CouponSystemException {

		Connection connection = null;

		try {
			connection = getConnectionPool().getConnection();
			String sql = "SELECT * FROM customers_vs_coupons WHERE CUSTOMER_ID = " + customerId + " AND COUPON_ID = "
					+ couponId;
			PreparedStatement ps = connection.prepareStatement(sql);
			int actualPurchase = ps.executeUpdate();
			return actualPurchase != 0;

		} catch (SQLException sqlException) {
			throw new DAOException("isPurchaseExist failed", sqlException);
		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}

	}

	@Override
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		Connection connection = null;
		List<Coupon> allCoupons = new ArrayList<>();
		try {
			connection = getConnectionPool().getConnection();
			String sql = "SELECT * FROM COUPONS ";
			PreparedStatement ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int couponId = rs.getInt("COUPON_ID");
				int companyID = rs.getInt("COMPANY_ID");
				Coupon.Category category = getCategoryFromId(rs.getInt("CATEGORY_ID"));
				String couponTitle = rs.getString("COUPON_TITLE");
				String couponDescription = rs.getString("COUPON_DESCRIPTION");
				LocalDate couponStartDate = rs.getDate("COUPON_START_DATE").toLocalDate();
				LocalDate couponEndDate = rs.getDate("COUPON_END_DATE").toLocalDate();
				int couponAmount = rs.getInt("COUPON_AMOUNT");
				double couponPrice = rs.getDouble("COUPON_PRICE");
				String couponImage = rs.getString("COUPON_IMAGE");

				allCoupons.add(new Coupon(couponId, companyID, category, couponTitle, couponDescription,
						couponStartDate, couponEndDate, couponAmount, couponPrice, couponImage));
			}

		} catch (SQLException sqlException) {
			throw new DAOException("Getting all coupons has failed", sqlException);
		} finally {

			if (connection != null) {
				getConnectionPool().restoreConnection(connection);
			}
		}
		if (allCoupons.isEmpty()) {
			throw new DAOException("Sorry but no coupons have been created in the database");
		}
		return allCoupons;
	}

	private Coupon.Category getCategoryFromId(int categoryID) {
		return Coupon.Category.values()[categoryID - 1];
	}
}
