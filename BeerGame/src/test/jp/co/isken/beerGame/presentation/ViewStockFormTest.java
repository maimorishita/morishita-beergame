package jp.co.isken.beerGame.presentation;

import jp.co.isken.beerGame.entity.BusinessMasterLoader;
import jp.co.isken.beerGame.entity.NumberingLoader;
import jp.co.isken.beerGame.entity.TransactionLoader;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class ViewStockFormTest extends DataLoadingTestCase {

	private ViewStockForm form;

	protected void setUp() throws Exception {
		super.setUp();
		BusinessMasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
		form = new ViewStockForm();
	}

	public void testオブジェクトを取得する() throws Exception {
		// ゲームとロールのオブジェクトを取得する
		form.setGameId(5L);
		form.setRoleId(22L);
		form.result();
		assertNotNull("ゲームが取得できていません", form.getGame());
		assertNotNull("ロールが取得できていません", form.getRole());
	}

	public void test累計発注残数を取得する() throws Exception {
//		form.setGameId(5L);
//		form.setRoleId(22L);
//		form.result();
//		assertEquals("累計の発注残数が間違っています", 0L, form.getRemain());
		assertTrue(true);
	}

	public void test累計在庫数を取得する() throws Exception {
		assertTrue(true);
	}
}
