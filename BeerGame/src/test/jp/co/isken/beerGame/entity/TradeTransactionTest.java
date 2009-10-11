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
	public void test���חʂ��v�Z����(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		int result = TradeTransaction.calcAmount(10,role,"����");
		assertEquals("���א�������Ă��܂��B",20, result);
	}
	public void test�o�חʂ��v�Z����(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		int result = TradeTransaction.calcAmount(10,role,"�o��");
		assertEquals("�o�א�������Ă��܂��B",20, result);
	}
	public void test�݌ɗʂ��Z�o����(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		int rltStock = TradeTransaction.calcAmountStock(10,role);
		assertEquals("�݌ɐ�������Ă��܂��B",0, rltStock);
	}
	public void test�󒍗ʂ��v�Z����(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		int r����Ordered = TradeTransaction.calcAmount(10,role,"��");
		assertEquals("�󒍐�������Ă��܂��B",25, r����Ordered);
	}
	public void test�󒍎c�ʂ��Z�o����(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		int rltStock = TradeTransaction.calcAmountRemain(10,role);
		assertEquals("�󒍐��ʂ�����Ă��܂��B",5, rltStock);
	}
	public void test�݌ɗʂ����X�g�Ŏ擾����(){
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Map<Integer,Integer> rltStock = TradeTransaction.getStockList(10,role);
		assertEquals("���X�g�o�͂�����Ă��܂��B",10, rltStock.size());
		assertEquals("�݌v�݌ɂ��Ԉ���Ă��܂��B",10, rltStock.get(1).intValue());
		assertEquals("�݌v�݌ɂ��Ԉ���Ă��܂��B",20, rltStock.get(2).intValue());
	}
	
	public void test���[���ƃQ�[�����������ɂ��č݌ɂ��擾����(){
		//TODO ������������ɏC������H 10/4 �X��
		Map<Integer, Integer> list = TradeTransaction.getStockAmount("test", "������");
		assertEquals(3, list.size());
		assertEquals(5, list.get(1).intValue());
		assertEquals(10, list.get(2).intValue());
		assertEquals(15, list.get(3).intValue());
	}
	
}
