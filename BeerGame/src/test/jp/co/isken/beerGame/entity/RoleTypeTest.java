package jp.co.isken.beerGame.entity;

import junit.framework.TestCase;

public class RoleTypeTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void testƒ[ƒ‹–¼æ“¾() throws Exception {
		assertEquals(RoleType.‰µ‚P, RoleType.valueOf("‰µ‚P"));
	}
	
	public void testã—¬‚ğæ“¾‚·‚é() throws Exception {
		assertEquals(RoleType.‰µ‚Q, RoleType.‰µ‚P.getUpper());
	}
}
