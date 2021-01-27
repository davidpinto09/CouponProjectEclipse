package couponSystem.job;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.javaBeans.Coupon;
import couponSystem.jdbc.dao.classes.CouponsDBDAO;
import couponSystem.jdbc.dao.interfaces.CouponsDAO;

public class CouponExpirationDailyJob implements Runnable {
	private final CouponsDAO couponsDAO;
	public long dayInMilliseconds = 84600000;
	private boolean quit;

	public CouponExpirationDailyJob(CouponsDAO couponsDAO, boolean quit) {
		this.couponsDAO = couponsDAO;
		this.quit = quit;
	}

	@Override
	public void run() {

		while (!quit) {
			try {
				List<Coupon> allCoupons = (ArrayList<Coupon>) couponsDAO.getAllCoupons();
System.out.println("Searching for expired coupons.....");
				for (Coupon oneCoupon : allCoupons) {
					if (oneCoupon.getCouponEndDate().equals(LocalDate.now())
							|| oneCoupon.getCouponEndDate().isBefore(LocalDate.now())) {
						if (couponsDAO.isPurchaseExist(oneCoupon.getCouponId())) {
							couponsDAO.deleteCouponPurchase(CouponsDBDAO.deleteCouponPurchaseByCouponID,
									oneCoupon.getCouponId());
						}
						couponsDAO.deleteCoupon(CouponsDBDAO.deleteCouponByCouponID, oneCoupon.getCouponId());
					}
				}
			
				Thread.sleep(dayInMilliseconds);
				System.out.println("Coupons Expired deleted");
			} catch (CouponSystemException couponSystemException) {
				couponSystemException.printStackTrace();
			} catch (InterruptedException e) {
				System.out.println("Coupons Expired deleted");
				System.out.println("The CouponExpirationDailyJob is shooting down");
				quit = true;
			}
		}

	}

	public void stop() {
		System.out.println("The CouponExpirationDailyJob is shooting down");
		quit = true;
	}
}
