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

	public void testプレイヤーを登録する() throws Exception {
		form.setPlayerName("今井智明");
		assertTrue("ゲームに登録するのに失敗しました。", form.EntryPlayer());
	}
}
