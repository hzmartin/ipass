package ipass.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppUtilTest {
	// 支付宝支付方式
	enum AlipayType {
		PC("AlipayAll.Alipay"), WAP("AlipayAll.AlipayWap"), SDK("AlpayAll.AlipaySdk");

		private String remark;

		AlipayType(String remark) {
			this.remark = remark;
		}

		public String getRemark() {
			return remark;
		}
	}

	@Test
	public void test() {
		System.out.println(AlipayType.PC.ordinal());
	}

}
