package jp.co.isken.beerGame.entity;

import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class PlayerTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}
	
	public void testDummy() throws Exception {
		assertTrue(true);
	}
}
