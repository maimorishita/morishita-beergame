package jp.co.isken.beerGame.entity;

import junit.framework.TestCase;

public class RoleTypeTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void test���[�����擾() throws Exception {
		assertEquals(RoleType.���P, RoleType.valueOf("���P"));
	}
	
	public void test�㗬���擾����() throws Exception {
		assertEquals(RoleType.���Q, RoleType.���P.getUpper());
	}
}
