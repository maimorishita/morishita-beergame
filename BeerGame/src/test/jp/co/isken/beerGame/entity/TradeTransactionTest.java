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
	public void test入荷量を計算する(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		int result = TradeTransaction.calcAmount(10,role,"入荷");
		assertEquals("入荷数が誤っています。",20, result);
	}
	public void test出荷量を計算する(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		int result = TradeTransaction.calcAmount(10,role,"出荷");
		assertEquals("出荷数が誤っています。",20, result);
	}
	public void test在庫量を算出する(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		int rltStock = TradeTransaction.calcAmountStock(10,role);
		assertEquals("在庫数が誤っています。",0, rltStock);
	}
	public void test受注量を計算する(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		int rｌｔOrdered = TradeTransaction.calcAmount(10,role,"受注");
		assertEquals("受注数が誤っています。",25, rｌｔOrdered);
	}
	public void test受注残量を算出する(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		int rltStock = TradeTransaction.calcAmountRemain(10,role);
		assertEquals("受注数量が誤っています。",5, rltStock);
	}
	public void test在庫量をリストで取得する(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Map<Integer,Integer> rltStock = TradeTransaction.getStockList(10,role);
		assertEquals("リスト出力が誤っています。",10, rltStock.size());
		assertEquals("累計在庫が間違っています。",10, rltStock.get(1).intValue());
		assertEquals("累計在庫が間違っています。",20, rltStock.get(2).intValue());
	}
	
	public void testロールとゲーム名を引数にして在庫を取得する(){
		//TODO 小売りを小売に修正する？ 10/4 森下
		Map<Integer, Integer> list = TradeTransaction.getStockAmount("test", "小売り");
		assertEquals(3, list.size());
		assertEquals(5, list.get(1).intValue());
		assertEquals(10, list.get(2).intValue());
		assertEquals(15, list.get(3).intValue());
	}
	
}
