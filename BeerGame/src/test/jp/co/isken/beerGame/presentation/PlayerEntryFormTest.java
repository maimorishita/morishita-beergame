package jp.co.isken.beerGame.presentation;

import jp.co.isken.beerGame.entity.BusinessMasterLoader;
import jp.co.isken.beerGame.entity.MasterLoader;
import jp.co.isken.beerGame.entity.NumberingLoader;
import jp.co.isken.beerGame.entity.TransactionLoader;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class PlayerEntryFormTest extends DataLoadingTestCase {

	private PlayerEntryForm form;

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		BusinessMasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
		form = new PlayerEntryForm();
	}

	public void testƒvƒŒƒCƒ„[‚ğ“o˜^‚·‚é() throws Exception {
		form.setPlayerName("¡ˆä’q–¾");
		assertTrue("ƒQ[ƒ€‚É“o˜^‚·‚é‚Ì‚É¸”s‚µ‚Ü‚µ‚½B", form.EntryPlayer());
	}
}
