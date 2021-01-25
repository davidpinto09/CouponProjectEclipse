package couponSystem.program;

import couponSystem.exceptions.CouponSystemException;
import couponSystem.test.Test;

public class Program {

	public static void main(String[] args) {

		try {
			Test.testAll();
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
