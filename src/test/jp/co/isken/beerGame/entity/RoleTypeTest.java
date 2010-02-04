package jp.co.isken.beerGame.entity;

import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class RoleTypeTest extends DataLoadingTestCase {

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
		assertEquals(RoleType.¬”„‚è, RoleType.sê.getUpper());
		assertEquals(RoleType.‰µ‚P, RoleType.¬”„‚è.getUpper());
		assertEquals(RoleType.‰µ‚Q, RoleType.‰µ‚P.getUpper());
		assertEquals(RoleType.ƒ[ƒJ, RoleType.‰µ‚Q.getUpper());
		assertEquals(RoleType.Hê, RoleType.ƒ[ƒJ.getUpper());
		try {
			RoleType.Hê.getUpper();
			fail("Hê‚Ìã—¬‚ªæ“¾‚Å‚«‚Ä‚¢‚Ü‚·");
		} catch (RuntimeException e) {
			assertTrue(true);
		}
	}

	public void test‰º—¬‚ğæ“¾‚·‚é() throws Exception {
		assertEquals(RoleType.ƒ[ƒJ, RoleType.Hê.getDowner());
		assertEquals(RoleType.‰µ‚Q, RoleType.ƒ[ƒJ.getDowner());
		assertEquals(RoleType.‰µ‚P, RoleType.‰µ‚Q.getDowner());
		assertEquals(RoleType.¬”„‚è, RoleType.‰µ‚P.getDowner());
		assertEquals(RoleType.sê, RoleType.¬”„‚è.getDowner());
		try {
			RoleType.sê.getDowner();
			fail("sê‚Ì‰º—¬‚ªæ“¾‚Å‚«‚Ä‚¢‚Ü‚·");
		} catch (RuntimeException e) {
			assertTrue(true);
		}
	}
}
