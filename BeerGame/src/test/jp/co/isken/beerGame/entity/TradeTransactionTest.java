package jp.co.isken.beerGame.entity;

import java.util.Map;

import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class TradeTransactionTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void test入荷量を計算する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long result = TradeTransaction.calcAmount(10L,role,TransactionType.入荷.name());
		assertEquals("入荷数が誤っています。",20, result.intValue());
	}

	public void test出荷量を計算する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long result = TradeTransaction.calcAmount(10L,role,TransactionType.出荷.name());
		assertEquals("出荷数が誤っています。",20, result.intValue());
	}

	public void test在庫量を算出する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltStock = TradeTransaction.calcAmountStock(10L,role);
		assertEquals("在庫数が誤っています。",0, rltStock.intValue());
	}

	public void test受注量を計算する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rｌｔOrdered = TradeTransaction.calcAmount(10L,role,TransactionType.受注.name());
		assertEquals("受注数が誤っています。",25, rｌｔOrdered.intValue());
	}

	public void test受注残量を算出する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltStock = TradeTransaction.calcAmountRemain(10L,role);
		assertEquals("受注数量が誤っています。",5, rltStock.intValue());
	}

	public void test在庫量をリストで取得する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Map<Long,Long> rltStock = TradeTransaction.getStockList(10L,role);
		assertEquals("リスト出力が誤っています。",10, rltStock.size());
		assertEquals("累計在庫が間違っています。",10, rltStock.get(1L).intValue());
		assertEquals("累計在庫が間違っています。",20, rltStock.get(2L).intValue());
	}

	public void testロールとゲーム名を引数にして在庫を取得する() throws Exception {
		// TODO 小売りを小売に修正する？ 10/4 森下
		Map<Long, Long> list = TradeTransaction.getStockAmount("test", "小売り");
		assertEquals(3, list.size());
		assertEquals(5, list.get(1L).intValue());
		assertEquals(10, list.get(2L).intValue());
		assertEquals(15, list.get(3L).intValue());
	}

}
