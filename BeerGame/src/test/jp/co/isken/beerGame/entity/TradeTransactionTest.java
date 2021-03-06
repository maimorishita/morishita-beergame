package jp.co.isken.beerGame.entity;

import java.util.List;
import java.util.Map;

import jp.co.isken.beerGame.entity.TradeTransaction.TradeTransactionService;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.resource.Messages;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.service.WhenVerifier;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class TradeTransactionTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void test入荷量の累積を計算する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long result = TradeTransaction.calcAmount(10L, role, TransactionType.入荷.name());
		assertEquals("入荷数が誤っています。", 42, result.intValue());
	}

	public void test受注量の累積を計算する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltOrdered = TradeTransaction.calcAmount(10L, role, TransactionType.受注.name());
		assertEquals("受注数が誤っています。", 24, rltOrdered.intValue());
	}

	public void test出荷量の累積を計算する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long result = TradeTransaction.calcAmount(10L, role, TransactionType.出荷.name());
		assertEquals("出荷数が誤っています。", 24, result.intValue());
	}

	public void test在庫量を算出する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltStock = TradeTransaction.calcAmountStock(10L, role);
		assertEquals("在庫数が誤っています。", 30, rltStock.intValue());
		rltStock = TradeTransaction.calcAmountStock(0L, role);
		assertEquals("在庫数が誤っています。", 12, rltStock.intValue());
	}

	public void test受注残量を算出する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 3L);
		Long rltStock = TradeTransaction.calcAmountRemain(5L, role);
		assertEquals("受注数量が誤っています。", -6, rltStock.intValue());
	}

	public void test在庫量をリストで取得する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 2L);
		Map<Long, Long> rltStock = TradeTransaction.getStockList(5L, role);
		assertEquals("リスト出力が誤っています。", 6, rltStock.size());
		assertEquals("累計在庫が間違っています。", 12, rltStock.get(0L).intValue());
		assertEquals("累計在庫が間違っています。", 12, rltStock.get(1L).intValue());
		assertEquals("累計在庫が間違っています。", 14, rltStock.get(2L).intValue());
		assertEquals("累計在庫が間違っています。", 8, rltStock.get(3L).intValue());
		assertEquals("累計在庫が間違っています。", 2, rltStock.get(4L).intValue());
		// 5週目以降はデータがないので、４週目と同じになっている
		assertEquals("累計在庫が間違っています。", -4, rltStock.get(5L).intValue());
	}

	public void testロールとゲーム名を引数にして在庫を取得する() throws Exception {
		Map<Long, Long> map = TradeTransaction.getStockAmount("NOAH",RoleType.卸２.name());
		assertEquals("在庫を算出する週の数に誤りがあります", 6, map.size());
		assertEquals("初期在庫に誤りがあります", 12, map.get(0L).intValue());
		assertEquals("１週目の在庫に誤りがあります", 12, map.get(1L).intValue());
		assertEquals("２週目の在庫に誤りがあります", 12, map.get(2L).intValue());
		assertEquals("３週目の在庫に誤りがあります", 6, map.get(3L).intValue());
		assertEquals("４週目の在庫に誤りがあります", 0, map.get(4L).intValue());
		assertEquals("５週目の在庫に誤りがあります", -6, map.get(5L).intValue());
		
		map = TradeTransaction.getStockAmount("NOAH", RoleType.メーカ.name());
		assertEquals("在庫を算出する週の数に誤りがあります", 6, map.size());
		assertEquals("初期在庫に誤りがあります", 12, map.get(0L).intValue());
		assertEquals("１週目の在庫に誤りがあります", 12, map.get(1L).intValue());
		assertEquals("２週目の在庫に誤りがあります", 14, map.get(2L).intValue());
		assertEquals("３週目の在庫に誤りがあります", 16, map.get(3L).intValue());
		assertEquals("４週目の在庫に誤りがあります", 18, map.get(4L).intValue());
		assertEquals("５週目の在庫に誤りがあります", 20, map.get(5L).intValue());
	}

	public void test入荷受注出荷発注のトランザクションを永続化する() throws Exception {
		// 初期処理
		BasicService service = BasicService.getService();
		Game game = service.findByPK(Game.class, 8L);
		for (Role role : game.getRoles()) {
			if (role.isDisposable()) {
				role.disposeAllMessage();
			}
		}
		Role wholeSeller = game.getRole(RoleType.小売り);
		wholeSeller.getUpper().send(TransactionType.出荷, "4");
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		// 本処理
		TradeTransactionService.getService().addTransactions(wholeSeller, 2L);
		Extractor e = new Extractor(TradeTransaction.class);
		e.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> transactions = service.findByExtractor(e);
		assertEquals("トランザクションの件数に誤りがあります", count + 4, transactions.size());

		assertEquals("最終週を取得できていません", 1L, transactions.get(0).getWeek().longValue());
		assertEquals("正しいTradeTransactionが取得できていません", TransactionType.発注.name(), transactions.get(0).getTransactionType());
		assertEquals("取得した発注数に誤りがあります", 2L, transactions.get(0).getAmount().longValue());

		assertEquals("最終週を取得できていません", 1L, transactions.get(1).getWeek().longValue());
		assertEquals("正しいTradeTransactionが取得できていません", TransactionType.出荷.name(), transactions.get(1).getTransactionType());
		assertEquals("取得した出荷数に誤りがあります", 4L, transactions.get(1).getAmount().longValue());

		assertEquals("最終週を取得できていません", 1L, transactions.get(2).getWeek().longValue());
		assertEquals("正しいTradeTransactionが取得できていません", TransactionType.受注.name(), transactions.get(2).getTransactionType());
		assertEquals("取得した受注数に誤りがあります", 4L, transactions.get(2).getAmount().longValue());

		assertEquals("最終週を取得できていません", 1L, transactions.get(3).getWeek().longValue());
		assertEquals("正しいTradeTransactionが取得できていません", TransactionType.入荷.name(), transactions.get(3).getTransactionType());
		assertEquals("取得した入荷数に誤りがあります", 4L, transactions.get(3).getAmount().longValue());
	}
	
	public void test永続化時の検証を行う() throws Exception {
		BasicService service = BasicService.getService();
		TradeTransaction transaction = new TradeTransaction();
		transaction.setAmount(-1L);
		Game game = service.findByPK(Game.class, 1L);
		transaction.setRole(game.getRole(RoleType.小売り));
		transaction.setTransactionType(TransactionType.入荷.name());
		transaction.setWeek(1L);
		Messages msgs = service.validate(transaction, WhenVerifier.INSERT);
		assertTrue("Amountにマイナス値を入力できています", msgs.hasError());
	}
}
