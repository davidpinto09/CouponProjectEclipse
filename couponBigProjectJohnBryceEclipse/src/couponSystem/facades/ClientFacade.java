package couponSystem.facades;

import couponSystem.jdbc.dao.interfaces.CompaniesDAO;
import couponSystem.jdbc.dao.interfaces.CouponsDAO;
import couponSystem.jdbc.dao.interfaces.CustomersDAO;

public abstract class ClientFacade {

	protected CompaniesDAO companiesDAO;
	protected CustomersDAO customersDAO;
	protected CouponsDAO couponsDAO;

	public ClientFacade(CompaniesDAO companiesDAO, CustomersDAO customersDAO, CouponsDAO couponsDAO) {
		this.companiesDAO = companiesDAO;
		this.customersDAO = customersDAO;
		this.couponsDAO = couponsDAO;
	}

	/**
	 * This method give the access to a specific Client to the program
	 *
	 * @param email    from client
	 * @param password from client
	 * @return boolean
	 */
	public abstract boolean login(String email, String password);
}
