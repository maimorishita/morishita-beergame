package jp.co.isken.beerGame.entity;

import junit.framework.TestCase;

public class RoleTypeTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void test[ΌζΎ() throws Exception {
		assertEquals(RoleType.΅P, RoleType.valueOf("΅P"));
	}
	
	public void testγ¬πζΎ·ι() throws Exception {
		assertEquals(RoleType.΅Q, RoleType.΅P.getUpper());
		assertEquals(RoleType.[J, RoleType.΅Q.getUpper());
	}
}
