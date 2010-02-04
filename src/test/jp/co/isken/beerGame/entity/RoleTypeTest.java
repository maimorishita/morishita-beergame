package jp.co.isken.beerGame.entity;

import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class RoleTypeTest extends DataLoadingTestCase {

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
		assertEquals(RoleType.������, RoleType.�s��.getUpper());
		assertEquals(RoleType.���P, RoleType.������.getUpper());
		assertEquals(RoleType.���Q, RoleType.���P.getUpper());
		assertEquals(RoleType.���[�J, RoleType.���Q.getUpper());
		assertEquals(RoleType.�H��, RoleType.���[�J.getUpper());
		try {
			RoleType.�H��.getUpper();
			fail("�H��̏㗬���擾�ł��Ă��܂�");
		} catch (RuntimeException e) {
			assertTrue(true);
		}
	}

	public void test�������擾����() throws Exception {
		assertEquals(RoleType.���[�J, RoleType.�H��.getDowner());
		assertEquals(RoleType.���Q, RoleType.���[�J.getDowner());
		assertEquals(RoleType.���P, RoleType.���Q.getDowner());
		assertEquals(RoleType.������, RoleType.���P.getDowner());
		assertEquals(RoleType.�s��, RoleType.������.getDowner());
		try {
			RoleType.�s��.getDowner();
			fail("�s��̉������擾�ł��Ă��܂�");
		} catch (RuntimeException e) {
			assertTrue(true);
		}
	}
}
