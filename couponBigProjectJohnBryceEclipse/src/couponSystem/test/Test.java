package couponSystem.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.facades.AdminFacade;
import couponSystem.facades.CompanyFacade;
import couponSystem.facades.CustomerFacade;
import couponSystem.javaBeans.Company;
import couponSystem.javaBeans.Coupon;
import couponSystem.javaBeans.Coupon.Category;
import couponSystem.javaBeans.Customer;
import couponSystem.jdbc.connectionPool.ConnectionPool;
import couponSystem.job.CouponExpirationDailyJob;
import couponSystem.loginManager.LoginManager;
import couponSystem.loginManager.LoginManager.ClientType;

/**
 * @author David Pinto
 */
public class Test {
	/*
	 * This main method test all the actions by Client Type[Admin,Company,Customer]
	 * Each method in main is configured as comment.
	 * 
	 * In each method you will find the login - if login success, facade != null,
	 * you will be able to execute the specifique facade actions
	 * 
	 * -------- PLEASE NOTICE -------- 
	 * Each mainFacadeMethod(admin,company,customer) can have internal subMethods and they can be commented 
	 * 
	 * 
	 * Structure:
	 * 
	 * AdminMethods():
	 * - adminLogin
	 * - adminCompanyMethods() -> all admin performances related to company 
	 * - Creation of 5 companies hardCoded 
	 * - Add duplicate company
	 * - Update company allowed fields 
	 * - Delete one company - Get all companies
	 * 
	 * - adminCustomerMethods() -> all admin performances related to customer
	 * - Creation of 5 customers hardCoded 
	 * - Add duplicate customer
	 * - Update customer allowed fields 
	 * - Delete one customer 
	 * - Get all customers
	 * 
	 * CompanyMethods():
	 *  - CompanyLogin
	 *  - Creation of coupons for company who logged in
	 *  - Add duplicate coupon 
	 *  - Update coupon
	 *  - Delete coupon
	 *  - Get company coupons (one single method who prints all coupons, all coupons for specific category, a coupons below or equals to specific price)
	 *  - Get company details
	 *  
	 *  CustomerMethods():
	 *  - CustomerLogin
	 *  - Purchase coupon
	 *  - Purchase duplicate coupon 
	 *  - Get customer coupons (one single method who prints all coupons, all coupons for specific category, a coupons below or equals to specific price)
	 *  - Get customer details 
	 */

	public static void testAll() throws CouponSystemException {
		ConnectionPool connectionPool = ConnectionPool.getConnectionPoolInstance();

		LoginManager loginManager = LoginManager.getLoginManagerInstance();

		Thread couponJob = new Thread(new CouponExpirationDailyJob());
		couponJob.start();

		try {
			adminMethods(loginManager);
		//	companyMethods(loginManager);
		//	customerMethods(loginManager);

		} catch (CouponSystemException couponSystemException) {
			System.err.println(couponSystemException.getMessage());
		} finally

		{

			try {
				couponJob.interrupt();
				couponJob.join();
				connectionPool.closeAllConnections();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/*
	 * Execute all the methods that admin can do coded
	 * 
	 * @param loginManager
	 * 
	 * @throws CouponSystemException
	 */
	private static void adminMethods(LoginManager loginManager) throws CouponSystemException {
		AdminFacade adminFacade = adminLogin(loginManager);
		if (adminFacade != null) {
			// adminMethodsCompany(adminFacade);

			// adminMethodsCustomer(adminFacade);
		}
	}

	public static AdminFacade adminLogin(LoginManager loginManager) {
		AdminFacade adminFacade = (AdminFacade) loginManager.login(AdminFacade.ADMIN_EMAIL, AdminFacade.ADMIN_PASSWORD,
				ClientType.ADMINISTRATOR);
		return adminFacade;
	}

	/*
	 * Execute hard coded methods that only admin can execute for company
	 * 
	 * @param adminFacade admin logged
	 * 
	 * @throws CouponSystemException
	 */
	public static void adminMethodsCompany(AdminFacade adminFacade) throws CouponSystemException {
		/*
		// Add hard coded companies
		 adminAddCompanies(adminFacade);

		// Add duplicate company - Take all companies in database. Store into a list and
		// add a duplicate of the last element
		 adminAddDuplicateCompany(adminFacade);

		adminUpdateCompany(adminFacade);

		// delete existing company
		 adminFacade.deleteCompany(7);

		// Get all companies from database
		 System.out.println(adminFacade.getAllCompanies());
		 */
	}

	public static void adminAddCompanies(AdminFacade adminFacade) throws CouponSystemException {
		// Hard-Coded new Companies
		Company company1 = new Company("company1", "company1@gmail.com", "company1pass");
		Company company2 = new Company("company2", "company2@gmail.com", "company2pass");
		Company company3 = new Company("company3", "company3@gmail.com", "company3pass");
		Company company4 = new Company("company4", "company4@gmail.com", "company4pass");
		Company company5 = new Company("company5", "company5@gmail.com", "company5pass");

		// Add all companies to Database
		adminFacade.addCompany(company1);
		adminFacade.addCompany(company2);
		adminFacade.addCompany(company3);
		adminFacade.addCompany(company4);
		adminFacade.addCompany(company5);
	}

	public static void adminUpdateCompany(AdminFacade adminFacade) throws CouponSystemException {
		// Get one Company from Database
		Company company4 = adminFacade.getOneCompany(4);

		// updating the fields allowed
		company4.setCompanyEmail("updatedCompany@gmail.com");
		company4.setCompanyPassword("updatedPass");

		// update company from Database
		adminFacade.updateCompany(company4);
	}

	public static void adminAddDuplicateCompany(AdminFacade adminFacade) throws CouponSystemException {
		List<Company> allCompaniesInDataBase = (ArrayList<Company>) adminFacade.getAllCompanies();
		adminFacade.addCompany(allCompaniesInDataBase.get(allCompaniesInDataBase.size() - 1));
	}

	/**
	 * Execute hard coded methods that only admin can execute for customer
	 * 
	 * @param adminFacade admin logged
	 * @throws CouponSystemException
	 */
	public static void adminMethodsCustomer(AdminFacade adminFacade) throws CouponSystemException {
		/*
		 * // Hard-Code new Customers adminAddCustomers(adminFacade);
		 * 
		 * // Add duplicate customer - Take all customers in database. Store into a list
		 * and add a duplicate of the last element
		 * adminAddDuplicateCustomer(adminFacade); // Get one Customer from Database
		 * adminUpdateCustomer(adminFacade);
		 * 
		 * // Delete an existing Customer adminFacade.deleteCustomer(7);
		 * 
		 * // Get All Customers in Database
		 * System.out.println(adminFacade.getAllCustomers());
		 */
	}

	public static void adminAddCustomers(AdminFacade adminFacade) throws CouponSystemException {
		for (int i = 1; i <= 8; i++) {

			Customer customer = new Customer("First" + i, "Last" + i, "customer" + i + "@gmail.com",
					"cus" + i + "pass");
			adminFacade.addCustomer(customer);

		}
	}

	public static void adminAddDuplicateCustomer(AdminFacade adminFacade) throws CouponSystemException {
		List<Customer> allCusotmersInDataBase = (ArrayList<Customer>) adminFacade.getAllCustomers();
		adminFacade.addCustomer(allCusotmersInDataBase.get(allCusotmersInDataBase.size() - 1));
	}

	public static void adminUpdateCustomer(AdminFacade adminFacade) throws CouponSystemException {
		Customer customer5 = adminFacade.getOneCustomer(5);

		// change all parameters of customer from database via setters

		customer5.setCustomerFirstName("updateFirst");
		customer5.setCustomerLastName("UpdateLast");
		customer5.setCustomerEmail("updateCustomerEmail@gmail.com");
		customer5.setCustomerPassword("updatepass");

		// Update customer from database
		adminFacade.updateCustomer(customer5);
	}

	public static void companyMethods(LoginManager loginManager) throws CouponSystemException {
		/*
		 * 3 valid logins. If the facade returns null is because login did not success
		 * (Email or password error, facade does not exist in database)
		 */
		/*
		 * CompanyFacade company1 = companyLogin(loginManager, "company1@gmail.com",
		 * "company1pass"); CompanyFacade company2 = companyLogin(loginManager,
		 * "company2@gmail.com", "company2pass"); CompanyFacade company3 =
		 * companyLogin(loginManager, "company3@gmail.com", "company3pass");
		 * 
		 * if (company1 != null) { // Hard Code Coupons creation for companies whit log
		 * in successful List<Coupon> couponCreationCompany1 =
		 * createHardCodeCouponForCompany(company1); //Add valid coupons to company
		 * addCouponsToCompany(company1, couponCreationCompany1);
		 * 
		 * //Add duplicate coupon to company
		 * addDuplicateCouponToCompany(company1,couponCreationCompany1);
		 * 
		 * // Updating an existing coupon for this company
		 * updateCouponForCompany(company1, 1);
		 * 
		 * 
		 * // Deleting valid coupon from this company deleteCouponForCompany(company1,
		 * 12);
		 * 
		 * //Print all list of coupons(All coupons,By Category, By maxPrice)
		 * printAllCompanyCoupons(company1, Category.ELECTRICITY, 150);
		 * 
		 * // Getting details for this company
		 * System.out.println(company3.getCompanyDetails()); }
		 */
		/*
		 * if (company2 != null) {
		 * 
		 * // Hard Code Coupons creation for companies whit log in successful
		 * List<Coupon> couponCreationCompany2 =
		 * createHardCodeCouponForCompany(company2);
		 * 
		 * //Add valid coupons to company addCouponsToCompany(company2,
		 * couponCreationCompany2);
		 * 
		 * //Add duplicate coupon to company
		 * addDuplicateCouponToCompany(company2,couponCreationCompany2);
		 * 
		 * // Updating an existing coupon for this company
		 * updateCouponForCompany(company2, 15);
		 * 
		 * 
		 * // Deleting valid coupon from this company deleteCouponForCompany(company2,
		 * 13);
		 * 
		 * //Print all list of coupons(All coupons,By Category, By maxPrice)
		 * printAllCompanyCoupons(company2, Category.SPORTS, 69.99);
		 * 
		 * // Getting details for this company
		 * System.out.println(company2.getCompanyDetails()); }
		 */
		/*
		 * if (company3 != null) {
		 * 
		 * // Hard Code Coupons creation for companies whit log in successful
		 * List<Coupon> couponCreationCompany3 =
		 * createHardCodeCouponForCompany(company3); //Add valid coupons to company
		 * addCouponsToCompany(company3, couponCreationCompany3);
		 * 
		 * //Add duplicate coupon to company
		 * addDuplicateCouponToCompany(company3,couponCreationCompany3);
		 * 
		 * // Updating an existing coupon for this company
		 * updateCouponForCompany(company3, 20);
		 * 
		 * 
		 * // Deleting valid coupon from this company deleteCouponForCompany(company3,
		 * 21);
		 * 
		 * //Print all list of coupons(All coupons,By Category, By maxPrice)
		 * printAllCompanyCoupons(company3, Category.FOOD, 200);
		 * 
		 * // Getting details for this company
		 * System.out.println(company3.getCompanyDetails()); }
		 */
	}

	public static CompanyFacade companyLogin(LoginManager loginManager, String email, String password) {
		CompanyFacade company1 = (CompanyFacade) loginManager.login(email, password, ClientType.COMPANY);
		return company1;
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
		System.out.println("ALL COMPANY COUPONS");
		System.out.println("======================================");
		System.out.println(company.getAllCompanyCoupons());
		System.out.println("======================================");
		// Getting all coupons by Category for this company
		System.out.println("ALL COMPANY COUPONS FOR SPECIFIC CATEGORY " + category.name());
		System.out.println("======================================");
		System.out.println(company.getAllCompanyCouponsByCategory(category));
		System.out.println("======================================");

		// Getting all coupons by maxPrice for this company
		System.out.println("ALL COMPANY COUPONS BELOW OR EQUALS TO PRICE " + maxPrice);
		System.out.println("======================================");
		System.out.println(company.getAllCompanyCouponsByPrice(maxPrice));
		System.out.println("======================================");

	}

	/**
	 * This method delete a specific coupon for this company who logged in
	 * 
	 * @param company  company who logged in
	 * @param couponId coupon reference to delete
	 * @throws CouponSystemException if coupon to delete does not belong to the
	 *                               company who logged in
	 */
	public static void deleteCouponForCompany(CompanyFacade company, int couponId) throws CouponSystemException {
		company.deleteCoupon(couponId);

	}

	/**
	 * This method update a specific coupon for the company who logged in. If the
	 * coupon has the same companyId as the company logged, The coupon will be
	 * updated
	 * 
	 * @param company  company who logged
	 * @param couponId coupon to update
	 * @throws CouponSystemException if coupon to update does not belong to tcompany
	 *                               who logged in
	 */

	public static void updateCouponForCompany(CompanyFacade company, int couponId) throws CouponSystemException {
		Coupon couponFromDataBase = company.getOneCoupon(couponId);

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
	 * This method takes the last coupon in the list of coupons of the company who
	 * logged and try to add a replica. Exception guaranteed.
	 * 
	 * @param company           company logged in
	 * @param companyCouponList list of coupons of company logged in
	 * @throws CouponSystemException duplicate coupon
	 */
	public static void addDuplicateCouponToCompany(CompanyFacade company, List<Coupon> companyCouponList)
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
	public static void addCouponsToCompany(CompanyFacade company, List<Coupon> companyCouponList)
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
	 * @throws CouponSystemException
	 */
	public static List<Coupon> createHardCodeCouponForCompany(CompanyFacade company) throws CouponSystemException {

		List<Coupon> coupons = new ArrayList<>();
		Coupon coupon1 = new Coupon(0, company.getCompanyID(), Category.CULTURE_AND_ENTERTAINMENT,
				"SHOW " + company.getCompanyDetails().getCompanyName(), "MADONNA", LocalDate.of(2021, 02, 15),
				LocalDate.of(2021, 02, 18), 100, 300.00, "posterMadonna.jpg");
		Coupon coupon2 = new Coupon(0, company.getCompanyID(), Category.ELECTRICITY,
				"TOASTER " + company.getCompanyDetails().getCompanyName(), "Toast the bread",
				LocalDate.of(2021, 01, 31), LocalDate.of(2022, 01, 31), 60, 29.90, "toaster.jpg");
		Coupon coupon3 = new Coupon(0, company.getCompanyID(), Category.FOOD,
				"HAMBURGER " + company.getCompanyDetails().getCompanyName(), "BBB 220G", LocalDate.of(2021, 01, 31),
				LocalDate.of(2021, 03, 1), 200, 29.90, "hamburger.jpg");
		Coupon coupon4 = new Coupon(0, company.getCompanyID(), Category.GAMES,
				"2k21 " + company.getCompanyDetails().getCompanyName(), "2k21 ultimate", LocalDate.of(2021, 01, 31),
				LocalDate.of(2021, 04, 1), 30, 100.00, "2kultimate.jpg");
		Coupon coupon5 = new Coupon(0, company.getCompanyID(), Category.LIFESTYLE_AND_HEALTH,
				"SPA " + company.getCompanyDetails().getCompanyName(), "Get Massgae", LocalDate.of(2021, 01, 31),
				LocalDate.of(2021, 05, 5), 100, 300.00, "massage.jpg");
		Coupon coupon6 = new Coupon(0, company.getCompanyID(), Category.RESTAURANT,
				"PIZZA " + company.getCompanyDetails().getCompanyName(), "2 Family Pizzas", LocalDate.of(2021, 01, 31),
				LocalDate.of(2021, 02, 21), 300, 65.00, "pizzas.jpg");
		Coupon coupon7 = new Coupon(0, company.getCompanyID(), Category.SHOPPING,
				"ADIDAS " + company.getCompanyDetails().getCompanyName(), "SPORT SHOES", LocalDate.of(2021, 01, 31),
				LocalDate.of(2022, 07, 10), 80, 120.00, "adidas.jpg");
		Coupon coupon8 = new Coupon(0, company.getCompanyID(), Category.SPORTS,
				"TICKETS DERBY " + company.getCompanyDetails().getCompanyName(), "MACCABI VS HAPOEL",
				LocalDate.of(2021, 06, 30), LocalDate.of(2021, 6, 1), 500, 70.00, "tickets.jpg");
		Coupon coupon9 = new Coupon(0, company.getCompanyID(), Category.VACATION,
				"FLY TICKETS " + company.getCompanyDetails().getCompanyName(), "To Eilat", LocalDate.of(2021, 01, 31),
				LocalDate.of(2021, 05, 1), 250, 600.00, "eilat.jpg");
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

	public static void customerMethods(LoginManager loginManager) throws CouponSystemException {

		/*
		 * 3 valid logins. If the facade returns null is because login did not success
		 * (Email or password error, facade does not exist in database) /
		 * 
		 * /* CustomerFacade customer1 = customerLogin(loginManager,
		 * "customer1@gmail.com", "cus1pass"); CustomerFacade customer2 =
		 * customerLogin(loginManager, "customer2@gmail.com", "cus2pass");
		 * CustomerFacade customer3 = customerLogin(loginManager, "customer3@gmail.com",
		 * "cus3pass");
		 * 
		 * if (customer1 != null) {
		 * 
		 * // Get coupon from database Coupon coupon1 = customer1.getOneCoupon(1);
		 * Coupon coupon2 = customer1.getOneCoupon(2); Coupon coupon3 =
		 * customer1.getOneCoupon(3);
		 * 
		 * // add purchase coupon to customer customer1.purchaseCoupon(coupon1);
		 * customer1.purchaseCoupon(coupon2); customer1.purchaseCoupon(coupon3);
		 * 
		 * printAllCustomerCoupons(customer3, Category.GAMES, 150);
		 * 
		 * System.out.println(customer3.getCustomerDetails()); }
		 */
		/*
		 * if (customer2 != null) { // Get coupon from database Coupon coupon1 =
		 * customer2.getOneCoupon(1); Coupon coupon2 = customer2.getOneCoupon(2); Coupon
		 * coupon3 = customer2.getOneCoupon(3);
		 * 
		 * // add purchase coupon to customer customer2.purchaseCoupon(coupon1);
		 * customer2.purchaseCoupon(coupon2); customer2.purchaseCoupon(coupon3);
		 * 
		 * printAllCustomerCoupons(customer3, Category.CULTURE_AND_ENTERTAINMENT, 150);
		 * 
		 * System.out.println(customer3.getCustomerDetails()); }
		 */
		/*
		 * if (customer3 != null) { // Get coupon from database Coupon coupon1 =
		 * customer3.getOneCoupon(1); Coupon coupon2 = customer3.getOneCoupon(2); Coupon
		 * coupon3 = customer3.getOneCoupon(3);
		 * 
		 * // add purchase coupon to customer customer3.purchaseCoupon(coupon1);
		 * customer3.purchaseCoupon(coupon2); customer3.purchaseCoupon(coupon3);
		 * 
		 * printAllCustomerCoupons(customer3, Category.SPORTS, 150);
		 * 
		 * System.out.println(customer3.getCustomerDetails()); }
		 */

	}

	public static CustomerFacade customerLogin(LoginManager loginManager, String email, String password) {
		CustomerFacade customer1 = (CustomerFacade) loginManager.login(email, password, ClientType.CUSTOMER);
		return customer1;
	}

	/**
	 * This method print all the coupons for the customer who logged in using 3
	 * methods from CustomerFacade: -getAllCustomerCoupons()
	 * -getAllCustomerCouponsByCategory() -getAllCustomerCouponsByMaxPrice()
	 * 
	 * @param customer who logged print a list with all coupons for this customer
	 * @param category print a new list of coupons depending of the category
	 * @param maxPrice print a new list of coupons cheaper or equals to maxPrice
	 * @throws CouponSystemException
	 */
	public static void printAllCustomerCoupons(CustomerFacade customer, Category category, double maxPrice)
			throws CouponSystemException {
		System.out.println(customer.getAllCustomerCoupons());
		System.out.println(customer.getAllCustomerCouponsByCategory(category));
		System.out.println(customer.getAllCustomerCouponsByPrice(maxPrice));
	}

}
