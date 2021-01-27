package couponSystem.test;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.facades.AdminFacade;
import couponSystem.facades.CompanyFacade;
import couponSystem.javaBeans.Company;
import couponSystem.javaBeans.Coupon;
import couponSystem.javaBeans.Coupon.Category;
import couponSystem.javaBeans.Customer;
import couponSystem.jdbc.connectionPool.ConnectionPool;
import couponSystem.jdbc.dao.classes.CouponsDBDAO;
import couponSystem.jdbc.dao.interfaces.CouponsDAO;
import couponSystem.job.CouponExpirationDailyJob;
import couponSystem.loginManager.LoginManager;
import couponSystem.loginManager.LoginManager.ClientType;

/**
 * @author David Pinto
 */
public class Test {

	public static void testAll() throws CouponSystemException {
		ConnectionPool connectionPool = ConnectionPool.getConnectionPoolInstance();
		CouponsDAO couponsDAO = new CouponsDBDAO();
		LoginManager loginManager = LoginManager.getLoginManagerInstance();
		Thread couponJob = new Thread(new CouponExpirationDailyJob(couponsDAO, false));
		couponJob.start();

		try {

			// adminMethods(loginManager);
			//companyMethods(loginManager);

			// CustomerFacade

		} finally {
			couponJob.interrupt();
			connectionPool.closeAllConnections();

		}
	}

	public static void adminMethods(LoginManager loginManager) throws CouponSystemException {
		AdminFacade adminFacade = (AdminFacade) loginManager.login(AdminFacade.ADMIN_EMAIL, AdminFacade.ADMIN_PASSWORD,
				ClientType.ADMINISTRATOR);

		// Hard-Coded 10 new Companies
		Company company1 = new Company("company1", "company1@gmail.com", "company1pass");
		Company company2 = new Company("company2", "company2@gmail.com", "company2pass");
		Company company3 = new Company("company3", "company3@gmail.com", "company3pass");
		Company company4 = new Company("company4", "company4@gmail.com", "company4pass");
		Company company5 = new Company("company5", "company5@gmail.com", "company5pass");
		Company company6 = new Company("company6", "company6@gmail.com", "company6pass");
		Company company7 = new Company("company7", "company7@gmail.com", "company7pass");
		Company company8 = new Company("company8", "company8@gmail.com", "company8pass");
		Company company9 = new Company("company9", "company9@gmail.com", "company9pass");
		Company company10 = new Company("company10", "company10@gmail.com", "company10pass");

		// Copy of company10 to check if system add twice the same company
		Company company11 = new Company("company10", "company10@gmail.com", "company10pass");

		// Add all companies to Database
		adminFacade.addCompany(company1);
		adminFacade.addCompany(company2);
		adminFacade.addCompany(company3);
		adminFacade.addCompany(company4);
		adminFacade.addCompany(company5);
		adminFacade.addCompany(company6);
		adminFacade.addCompany(company7);
		adminFacade.addCompany(company8);
		adminFacade.addCompany(company9);
		adminFacade.addCompany(company10);
		adminFacade.addCompany(company11);

		// Get one Company from Database
		Company company12 = adminFacade.getOneCompany(3);

//change all parameters of company from database via setters

		company12.setCompanyEmail("updatedCompany@gmail.com");
		company12.setCompanyPassword("updatedPass");

//update company from Database
		adminFacade.updateCompany(company12);

//delete existing company
		adminFacade.deleteCompany(7);

//Delete a non existing company
		adminFacade.deleteCompany(30);

//Get all companies from database
		System.out.println(adminFacade.getAllCompanies());

//Hard-Code 10 new Customers
		Customer customer1 = new Customer("First1", "Last1", "customer1@gmail.com", "cus1pass");
		Customer customer2 = new Customer("First2", "Last2", "customer2@gmail.com", "cus2pass");
		Customer customer3 = new Customer("First3", "Last3", "customer3@gmail.com", "cus3pass");
		Customer customer4 = new Customer("First4", "Last4", "customer4@gmail.com", "cus4pass");
		Customer customer5 = new Customer("First5", "Last5", "customer5@gmail.com", "cus5pass");
		Customer customer6 = new Customer("First6", "Last6", "customer6@gmail.com", "cus6pass");
		Customer customer7 = new Customer("First7", "Last7", "customer7@gmail.com", "cus7pass");
		Customer customer8 = new Customer("First8", "Last8", "customer8@gmail.com", "cus8pass");
		Customer customer9 = new Customer("First9", "Last9", "customer9@gmail.com", "cus9pass");
		Customer customer10 = new Customer("First10", "Last10", "customer10@gmail.com", "cus10pass");

		// Copy of customer10 to check if system add twice the same company
		Customer customer11 = new Customer("First10", "Last10", "customer10@gmail.com", "cus10pass");

		// Add all Customers to database
		adminFacade.addCustomer(customer1);
		adminFacade.addCustomer(customer2);
		adminFacade.addCustomer(customer3);
		adminFacade.addCustomer(customer4);
		adminFacade.addCustomer(customer5);
		adminFacade.addCustomer(customer6);
		adminFacade.addCustomer(customer7);
		adminFacade.addCustomer(customer8);
		adminFacade.addCustomer(customer9);
		adminFacade.addCustomer(customer10);
		adminFacade.addCustomer(customer11);

//Get one Customer from Database
		Customer customer12 = adminFacade.getOneCustomer(3);

//change all parameters of customer from database via setters

		customer12.setCustomerFirstName("updateFirst");
		customer12.setCustomerLastName("UpdateLast");
		customer12.setCustomerEmail("updateCustomerEmail@gmail.com");
		customer12.setCustomerPassword("updatepass");

//Update customer from database
		adminFacade.updateCustomer(customer12);

//Delete an existing Customer
		adminFacade.deleteCustomer(7);

//Delete a non existing Customer
		adminFacade.deleteCustomer(30);

//Get All Customer
		System.out.println(adminFacade.getAllCustomers());
	}

	public static void companyMethods(LoginManager loginManager) throws CouponSystemException {
		CompanyFacade company1 = (CompanyFacade) loginManager.login("company1@gmail.com", "company1pass",
				ClientType.COMPANY);
		CompanyFacade company2 = (CompanyFacade) loginManager.login("company2@gmail.com", "company2pass",
				ClientType.COMPANY);
		CompanyFacade company3 = (CompanyFacade) loginManager.login("updatedCompany@gmail.com", "updatedPass",
				ClientType.COMPANY);

		// Login with non existing company
		CompanyFacade company999 = (CompanyFacade) loginManager.login("company999@gmail.com", "company999pass",
				ClientType.COMPANY);

		// Hard Code Coupons creation for companies whit log in successful
		List<Coupon> couponCreationCompany1 = createHardCodeCouponForCompany(company1);
		List<Coupon> couponCreationCompany2 = createHardCodeCouponForCompany(company2);
		List<Coupon> couponCreationCompany3 = createHardCodeCouponForCompany(company3);

		addCouponsToCompany(company1, couponCreationCompany1);
		addCouponsToCompany(company2, couponCreationCompany2);
		addCouponsToCompany(company3, couponCreationCompany3);

		addDuplicateCouponToCompany(company1, couponCreationCompany1);
		addDuplicateCouponToCompany(company2, couponCreationCompany3);
		addDuplicateCouponToCompany(company3, couponCreationCompany3);

		// Updating an existing coupon for this company
		updateCouponForCompany(company1, 1);

		// Updating a non existing coupon for this company
		updateCouponForCompany(company1, 2);

		// Deleting valid coupon from this company
		deleteCouponForCompany(company2, 8);
		// Deleting non valid coupon from this company
		deleteCouponForCompany(company2, 6);

		printAllCompanyCoupons(company3, Category.ELECTRICITY, 150);
		// Getting details for this company
		System.out.println(company3.getCompanyDetails());
	}

	/**
	 * This method print all the coupons for the company who logged in using 3
	 * methods from CompanyFacade: -getAllCompanyCoupons()
	 * -getAllCompanyCouponsByCategory() -getAllCompanyCouponsByMaxPrice()
	 * 
	 * @param company  company who logged print a list with all coupons for this
	 *                 company
	 * @param category print a new list of coupons depending of the category
	 * @param maxPrice print a new list of coupons cheaper or equals to maxPrice
	 * @throws CouponSystemException
	 */
	public static void printAllCompanyCoupons(CompanyFacade company, Category category, double maxPrice)
			throws CouponSystemException {
		// Getting all coupons for this company
		System.out.println(company.getAllCompanyCoupons());

		// Getting all coupons by Category for this company
		System.out.println(company.getAllCompanyCouponsByCategory(category));

		// Getting all coupons by maxPrice for this company
		System.out.println(company.getAllCompanyCouponsByPrice(maxPrice));

	}

	private static void deleteCouponForCompany(CompanyFacade company, int couponId) throws CouponSystemException {
		company.deleteCoupon(couponId);

	}

	/**
	 * Company who logged update the coupon by couponId (In this case this method
	 * the modifications for the coupon has been hard coded)
	 * 
	 * @param company  company who logged
	 * @param couponId coupon to update
	 * @throws CouponSystemException
	 */

	public static void updateCouponForCompany(CompanyFacade company, int couponId) throws CouponSystemException {
		Coupon couponFromDataBase = company.getCouponsDAO().getOneCoupon(couponId);

		// Coupon Modification via setters
		couponFromDataBase.setCouponAmount(700);
		couponFromDataBase.setCouponCategory(Category.GAMES);
		couponFromDataBase.setCouponTitle("PAINTBALL");
		couponFromDataBase.setCouponDescription("10 People tickets");
		couponFromDataBase.setCouponStartDate(LocalDate.of(2022, 01, 1));
		couponFromDataBase.setCouponEndDate(LocalDate.of(2023, 01, 1));
		couponFromDataBase.setCouponPrice(1000);
		couponFromDataBase.setCouponImage("updateCouponImage.jpg");
		company.updateCoupon(couponFromDataBase);
	}

	/**
	 * This method takes the last coupon in the list of coupons of the company
	 * logged and try to add a replica
	 * 
	 * @param company           company logged in
	 * @param companyCouponList list of coupons of company logged in
	 * @throws CouponSystemException
	 */
	private static void addDuplicateCouponToCompany(CompanyFacade company, List<Coupon> companyCouponList)
			throws CouponSystemException {
		Coupon lastCouponInListCoupon = companyCouponList.get(companyCouponList.size() - 1);
		company.addCoupon(lastCouponInListCoupon);

	}

	/**
	 * Company who logged in add the coupons created(In this case the coupons are
	 * hard coded)
	 * 
	 * @param company           company who logged in
	 * @param companyCouponList list of coupons to add
	 * @throws CouponSystemException
	 */
	private static void addCouponsToCompany(CompanyFacade company, List<Coupon> companyCouponList)
			throws CouponSystemException {

		for (Coupon couponToAdd : companyCouponList) {

			company.addCoupon(couponToAdd);

		}

	}

	/**
	 * This method create hard coded coupons for company who logged in
	 * 
	 * @param company company who logged
	 * @return list of coupons to add in the future
	 */
	private static List<Coupon> createHardCodeCouponForCompany(CompanyFacade company) {
		List<Coupon> coupons = new ArrayList<>();
		Coupon coupon1 = new Coupon(0, company.getCompanyID(), Category.CULTURE_AND_ENTERTAINMENT, "SHOW", "MADONNA",
				LocalDate.of(2021, 01, 31), LocalDate.of(2021, 02, 1), 100, 300.00, "posterMadonna.jpg");
		Coupon coupon2 = new Coupon(0, company.getCompanyID(), Category.ELECTRICITY, "TOASTER", "Toast the bread",
				LocalDate.of(2021, 01, 31), LocalDate.of(2022, 01, 31), 60, 29.90, "toaster.jpg");
		Coupon coupon3 = new Coupon(0, company.getCompanyID(), Category.FOOD, "HAMBURGER", "BBB 220G",
				LocalDate.of(2021, 01, 31), LocalDate.of(2021, 03, 1), 200, 29.90, "hamburger.jpg");
		Coupon coupon4 = new Coupon(0, company.getCompanyID(), Category.GAMES, "2k21", "2k21 ultimate",
				LocalDate.of(2021, 01, 31), LocalDate.of(2021, 04, 1), 30, 100.00, "2kultimate.jpg");
		Coupon coupon5 = new Coupon(0, company.getCompanyID(), Category.LIFESTYLE_AND_HEALTH, "SPA", "Get Massgae",
				LocalDate.of(2021, 01, 31), LocalDate.of(2021, 02, 1), 100, 300.00, "massage.jpg");
		Coupon coupon6 = new Coupon(0, company.getCompanyID(), Category.RESTAURANT, "PIZZA", "2 Family Pizzas",
				LocalDate.of(2021, 01, 31), LocalDate.of(2021, 02, 10), 300, 65.00, "pizzas.jpg");
		Coupon coupon7 = new Coupon(0, company.getCompanyID(), Category.SHOPPING, "ADIDAS", "SPORT SHOES",
				LocalDate.of(2021, 01, 31), LocalDate.of(2022, 02, 10), 80, 120.00, "adidas.jpg");
		Coupon coupon8 = new Coupon(0, company.getCompanyID(), Category.SPORTS, "TICKETS DERBY", "MACCABI VS HAPOEL",
				LocalDate.of(2021, 06, 30), LocalDate.of(2021, 07, 1), 500, 70.00, "tickets.jpg");
		Coupon coupon9 = new Coupon(0, company.getCompanyID(), Category.VACATION, "FLY TICKETS", "To Eilat",
				LocalDate.of(2021, 01, 31), LocalDate.of(2021, 05, 1), 250, 600.00, "eilat.jpg");
		coupons.add(coupon1);
		coupons.add(coupon2);
		coupons.add(coupon3);
		coupons.add(coupon4);
		coupons.add(coupon5);
		coupons.add(coupon6);
		coupons.add(coupon7);
		coupons.add(coupon8);
		coupons.add(coupon9);
		return coupons;
	}
}
