package jp.co.isken.beerGame.entity;

import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class RoleTypeTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void testロール名取得() throws Exception {
		assertEquals(RoleType.卸１, RoleType.valueOf("卸１"));
	}

	public void test上流を取得する() throws Exception {
		assertEquals(RoleType.小売り, RoleType.市場.getUpper());
		assertEquals(RoleType.卸１, RoleType.小売り.getUpper());
		assertEquals(RoleType.卸２, RoleType.卸１.getUpper());
		assertEquals(RoleType.メーカ, RoleType.卸２.getUpper());
		assertEquals(RoleType.工場, RoleType.メーカ.getUpper());
		try {
			RoleType.工場.getUpper();
			fail("工場の上流が取得できています");
		} catch (RuntimeException e) {
			assertTrue(true);
		}
	}

	public void test下流を取得する() throws Exception {
		assertEquals(RoleType.メーカ, RoleType.工場.getDowner());
		assertEquals(RoleType.卸２, RoleType.メーカ.getDowner());
		assertEquals(RoleType.卸１, RoleType.卸２.getDowner());
		assertEquals(RoleType.小売り, RoleType.卸１.getDowner());
		assertEquals(RoleType.市場, RoleType.小売り.getDowner());
		try {
			RoleType.市場.getDowner();
			fail("市場の下流が取得できています");
		} catch (RuntimeException e) {
			assertTrue(true);
		}
	}
}
