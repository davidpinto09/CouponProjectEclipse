package couponSystem.test;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.facades.AdminFacade;
import couponSystem.javaBeans.Company;
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

		AdminFacade adminFacade = (AdminFacade)	loginManager.login(AdminFacade.ADMIN_EMAIL, AdminFacade.ADMIN_PASSWORD, ClientType.ADMINISTRATOR);
	
		//Hard-Coded 10 new Companies
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
		
		//Copy of company10 to check if system add twice the same company
		Company company11 = new Company("company10", "company10@gmail.com", "company10pass");
		
		//Add all companies to Database
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
		
//		//Get one Company from Database
//		Company company12 = adminFacade.getOneCompany(3);
//		
//		//change all parameters of company from database via setters
//		company12.setCompanyName("updatedCompanyName");
//		company12.setCompanyEmail("updatedCompany@gmail.com");
//		company12.setCompanyPassword("updatedPass");
//		company12.setCompanyId(15);
//		
//		//update company from Database
//		adminFacade.updateCompany(company12);
//		
//		//delete existing company
//		adminFacade.deleteCompany(7);
//		
//		//Delete a non existing company
//		adminFacade.deleteCompany(30);
//		
//		//Get all companies from database
//		System.out.println(adminFacade.getAllCompanies());
//		
//		//Hard-Code 10 new Customers
//		Customer customer1 = new Customer("First1", "Last1","customer1@gmail.com", "cus1pass");
//		Customer customer2 = new Customer("First2", "Last2","customer2@gmail.com", "cus2pass");
//		Customer customer3 = new Customer("First3", "Last3","customer3@gmail.com", "cus3pass");
//		Customer customer4 = new Customer("First4", "Last4","customer4@gmail.com", "cus4pass");
//		Customer customer5 = new Customer("First5", "Last5","customer5@gmail.com", "cus5pass");
//		Customer customer6 = new Customer("First6", "Last6","customer6@gmail.com", "cus6pass");
//		Customer customer7 = new Customer("First7", "Last7","customer7@gmail.com", "cus7pass");
//		Customer customer8 = new Customer("First8", "Last8","customer8@gmail.com", "cus8pass");
//		Customer customer9= new Customer("First9", "Last9","customer9@gmail.com", "cus9pass");
//		Customer customer10 = new Customer("First10", "Last10","customer10@gmail.com", "cus10pass");
//		
//		//Copy of customer10 to check if system add twice the same company
//		Customer customer11 = new Customer("First10", "Last10","customer10@gmail.com", "cus10pass");
//		
//		//Add all Customers to database
//		adminFacade.addCustomer(customer1);
//		adminFacade.addCustomer(customer2);
//		adminFacade.addCustomer(customer3);
//		adminFacade.addCustomer(customer4);
//		adminFacade.addCustomer(customer5);
//		adminFacade.addCustomer(customer6);
//		adminFacade.addCustomer(customer7);
//		adminFacade.addCustomer(customer8);
//		adminFacade.addCustomer(customer9);
//		adminFacade.addCustomer(customer10);
//		adminFacade.addCustomer(customer11);
//		
//		//Get one Customer from Database
//		Customer customer12 = adminFacade.getOneCustomer(3);
//		
//		//change all parameters of customer from database via setters
//		customer12.setCustomerId(15);
//		customer12.setCustomerFirstName("updateFirst");
//		customer12.setCustomerLastName("UpdateLast");
//		customer12.setCustomerEmail("updateCustomerEmail@gmail.com");
//		customer12.setCustomerPassword("updatepass");
//		
//		//Update customer from database
//		adminFacade.updateCustomer(customer12);
//		
//		//Delete an existing Customer
//		adminFacade.deleteCustomer(7);
//		
//		//Delete a non existing Customer
//		adminFacade.deleteCustomer(30);
//		
//		//Get All Customer
//		System.out.println(adminFacade.getAllCustomers());
//		
		
		} catch (Exception e) {

		} finally {
			try {
				couponJob.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connectionPool.closeAllConnections();

		}
	}

	

}
