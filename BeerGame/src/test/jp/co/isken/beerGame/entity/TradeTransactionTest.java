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

	public void test���חʂ��v�Z����() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long result = TradeTransaction.calcAmount(10L, role, TransactionType.����.name());
		assertEquals("���א�������Ă��܂��B", 28, result.intValue());
	}

	public void test�o�חʂ��v�Z����() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long result = TradeTransaction.calcAmount(10L, role, TransactionType.�o��.name());
		assertEquals("�o�א�������Ă��܂��B", 16, result.intValue());
	}

	public void test�݌ɗʂ��Z�o����() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltStock = TradeTransaction.calcAmountStock(10L, role);
		assertEquals("�݌ɐ�������Ă��܂��B", 24, rltStock.intValue());
	}

	public void test�󒍗ʂ��v�Z����() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long r����Ordered = TradeTransaction.calcAmount(10L, role, 	TransactionType.��.name());
		assertEquals("�󒍐�������Ă��܂��B", 16, r����Ordered.intValue());
	}

	public void test�󒍎c�ʂ��Z�o����() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltStock = TradeTransaction.calcAmountRemain(10L, role);
		assertEquals("�󒍐��ʂ�����Ă��܂��B", 0, rltStock.intValue());
	}

	public void test�݌ɗʂ����X�g�Ŏ擾����() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 2L);
		Map<Long, Long> rltStock = TradeTransaction.getStockList(10L, role);
		assertEquals("���X�g�o�͂�����Ă��܂��B", 10, rltStock.size());
		assertEquals("�݌v�݌ɂ��Ԉ���Ă��܂��B", 12, rltStock.get(1L).intValue());
		assertEquals("�݌v�݌ɂ��Ԉ���Ă��܂��B", 14, rltStock.get(2L).intValue());
		assertEquals("�݌v�݌ɂ��Ԉ���Ă��܂��B", 8, rltStock.get(3L).intValue());
		assertEquals("�݌v�݌ɂ��Ԉ���Ă��܂��B", 2, rltStock.get(4L).intValue());
		// 5�T�ڈȍ~�̓f�[�^���Ȃ��̂ŁA�S�T�ڂƓ����ɂȂ��Ă���
		assertEquals("�݌v�݌ɂ��Ԉ���Ă��܂��B", 2, rltStock.get(5L).intValue());
	}

	//TODO 2009/12/20 imai,yoshioka �e�X�g�f�[�^�C���̈׈ꎞ�I�ɃR�����g�A�E�g
//	public void test���[���ƃQ�[�����������ɂ��č݌ɂ��擾����() throws Exception {
//		// TODO ������������ɏC������H 10/4 �X��
//		Map<Long, Long> list = TradeTransaction.getStockAmount("test", "������");
//		assertEquals(3, list.size());
//		assertEquals(5, list.get(1L).intValue());
//		assertEquals(10, list.get(2L).intValue());
//		assertEquals(15, list.get(3L).intValue());
//	}
}
