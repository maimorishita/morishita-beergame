package jp.co.isken.beerGame.entity;

import java.util.List;
import java.util.Map;

import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.ExtractValue;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.extractor.Sum;
import jp.rough_diamond.commons.service.BasicService;
import junit.framework.TestCase;

public class TradeTransactionTest extends TestCase {
	
	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}	
	public void test入荷量を計算する(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		TradeTransaction tTran = new TradeTransaction();
		int result = tTran.calcAmount(10,role,"入荷");
		assertEquals("入荷数が誤っています。",20, result);
	}
	public void test出荷量を計算する(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		TradeTransaction tTran = new TradeTransaction();
		int result = tTran.calcAmount(10,role,"出荷");
		assertEquals("出荷数が誤っています。",20, result);
	}
	public void test在庫量を算出する(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		TradeTransaction tTran = new TradeTransaction();
		int rltStock = tTran.calcAmountStock(10,role);
		assertEquals("在庫数が誤っています。",0, rltStock);
	}
	public void test受注量を計算する(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		TradeTransaction tTran = new TradeTransaction();
		int rｌｔOrdered = tTran.calcAmount(10,role,"受注");
		assertEquals("受注数が誤っています。",25, rｌｔOrdered);
	}
	public void test受注残量を算出する(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		TradeTransaction tTran = new TradeTransaction();
		int rltStock = tTran.calcAmountRemain(10,role);
		assertEquals("受注数量が誤っています。",5, rltStock);
	}
	public void test在庫量をリストで取得する(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		TradeTransaction tTran = new TradeTransaction();
		Map<Integer,Integer> rltStock = tTran.getStockList(10,role);
		assertEquals("リスト出力が誤っています。",10, rltStock.size());
		assertEquals("累計在庫が間違っています。",10, rltStock.get(1).intValue());
		assertEquals("累計在庫が間違っています。",20, rltStock.get(2).intValue());
	}
	
}
