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
		Long result = TradeTransaction.calcAmount(10L, role, TransactionType.入荷.name());
		assertEquals("入荷数が誤っています。", 28, result.intValue());
	}

	public void test出荷量を計算する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long result = TradeTransaction.calcAmount(10L, role, TransactionType.出荷.name());
		assertEquals("出荷数が誤っています。", 16, result.intValue());
	}

	public void test在庫量を算出する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltStock = TradeTransaction.calcAmountStock(10L, role);
		assertEquals("在庫数が誤っています。", 24, rltStock.intValue());
	}

	public void test受注量を計算する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rｌｔOrdered = TradeTransaction.calcAmount(10L, role, 	TransactionType.受注.name());
		assertEquals("受注数が誤っています。", 16, rｌｔOrdered.intValue());
	}

	public void test受注残量を算出する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltStock = TradeTransaction.calcAmountRemain(10L, role);
		assertEquals("受注数量が誤っています。", 0, rltStock.intValue());
	}

	public void test在庫量をリストで取得する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 2L);
		Map<Long, Long> rltStock = TradeTransaction.getStockList(10L, role);
		assertEquals("リスト出力が誤っています。", 10, rltStock.size());
		assertEquals("累計在庫が間違っています。", 12, rltStock.get(1L).intValue());
		assertEquals("累計在庫が間違っています。", 14, rltStock.get(2L).intValue());
		assertEquals("累計在庫が間違っています。", 8, rltStock.get(3L).intValue());
		assertEquals("累計在庫が間違っています。", 2, rltStock.get(4L).intValue());
		// 5週目以降はデータがないので、４週目と同じになっている
		assertEquals("累計在庫が間違っています。", 2, rltStock.get(5L).intValue());
	}

	public void testロールとゲーム名を引数にして在庫を取得する() throws Exception {
		Map<Long, Long> list = TradeTransaction.getStockAmount("NOAH", RoleType.卸２.name());
		assertEquals("在庫を算出する週の数に誤りがあります", 4, list.size());
		assertEquals("１週目の在庫に誤りがあります", 12, list.get(1L).intValue());
		assertEquals("２週目の在庫に誤りがあります", 12, list.get(2L).intValue());
		assertEquals("３週目の在庫に誤りがあります", 6, list.get(3L).intValue());
		assertEquals("４週目の在庫に誤りがあります", 0, list.get(4L).intValue());
	}
}
