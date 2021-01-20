package couponSystem.loginManager;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.facades.AdminFacade;
import couponSystem.facades.ClientFacade;
import couponSystem.facades.CompanyFacade;
import couponSystem.facades.CustomerFacade;
import couponSystem.jdbc.dao.classes.CompaniesDBDAO;
import couponSystem.jdbc.dao.classes.CouponsDBDAO;
import couponSystem.jdbc.dao.classes.CustomersDBDAO;

public class LoginManager {


    public static LoginManager loginManagerInstance = null;

    private LoginManager() {

    }

    public static void main(String[] args) {


        LoginManager loginManager = LoginManager.getLoginManagerInstance();


        CompanyFacade companyFacade = (CompanyFacade) loginManager.login("company134@gmail.com", "company3", ClientType.COMPANY);
        if (companyFacade != null) {
            try {
                System.out.println(companyFacade.getCompanyDetails());
            } catch (CouponSystemException daoException) {
                daoException.printStackTrace();
            }
        } else {
            System.out.println("Login not success");
        }
    }

    public static LoginManager getLoginManagerInstance() {
        if (loginManagerInstance == null) {
            loginManagerInstance = new LoginManager();
        }
        return loginManagerInstance;
    }

    public ClientFacade login(String email, String password, ClientType clientType) {
        if (clientType != null) {
            switch (clientType) {

                case ADMINISTRATOR:


                    ClientFacade adminFacade = new AdminFacade(new CompaniesDBDAO(), new CustomersDBDAO(), new CouponsDBDAO());
                    if (adminFacade.login(email, password)) {
                        System.out.println("You logged as admin");
                        return adminFacade;
                    }

                    break;

                case COMPANY:

                    ClientFacade companyFacade = new CompanyFacade(new CompaniesDBDAO(), new CustomersDBDAO(), new CouponsDBDAO());
                    if (companyFacade.login(email, password)) {

                        return companyFacade;
                    } else {
                        System.out.println("No company with this email and password");
                    }
                    break;


                case CUSTOMER:

                    ClientFacade customerFacade = new CustomerFacade(new CompaniesDBDAO(), new CustomersDBDAO(), new CouponsDBDAO());
                    if (customerFacade.login(email, password)) {

                        return customerFacade;
                    }

                    break;
                default:
                    return null;
            }
        }
        return null;
    }

    public enum ClientType {
        ADMINISTRATOR, COMPANY, CUSTOMER
    }
}
