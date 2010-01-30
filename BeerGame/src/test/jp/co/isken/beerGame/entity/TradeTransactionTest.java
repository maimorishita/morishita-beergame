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

	public void test���חʂ̗ݐς��v�Z����() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long result = TradeTransaction.calcAmount(10L, role, TransactionType.����.name());
		assertEquals("���א�������Ă��܂��B", 42, result.intValue());
	}

	public void test�󒍗ʂ̗ݐς��v�Z����() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltOrdered = TradeTransaction.calcAmount(10L, role, TransactionType.��.name());
		assertEquals("�󒍐�������Ă��܂��B", 24, rltOrdered.intValue());
	}

	public void test�o�חʂ̗ݐς��v�Z����() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long result = TradeTransaction.calcAmount(10L, role, TransactionType.�o��.name());
		assertEquals("�o�א�������Ă��܂��B", 24, result.intValue());
	}

	public void test�݌ɗʂ��Z�o����() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltStock = TradeTransaction.calcAmountStock(10L, role);
		assertEquals("�݌ɐ�������Ă��܂��B", 30, rltStock.intValue());
		rltStock = TradeTransaction.calcAmountStock(0L, role);
		assertEquals("�݌ɐ�������Ă��܂��B", 12, rltStock.intValue());
	}

	public void test�󒍎c�ʂ��Z�o����() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 3L);
		Long rltStock = TradeTransaction.calcAmountRemain(5L, role);
		assertEquals("�󒍐��ʂ�����Ă��܂��B", -6, rltStock.intValue());
	}

	public void test�݌ɗʂ����X�g�Ŏ擾����() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 2L);
		Map<Long, Long> rltStock = TradeTransaction.getStockList(5L, role);
		assertEquals("���X�g�o�͂�����Ă��܂��B", 6, rltStock.size());
		assertEquals("�݌v�݌ɂ��Ԉ���Ă��܂��B", 12, rltStock.get(0L).intValue());
		assertEquals("�݌v�݌ɂ��Ԉ���Ă��܂��B", 12, rltStock.get(1L).intValue());
		assertEquals("�݌v�݌ɂ��Ԉ���Ă��܂��B", 14, rltStock.get(2L).intValue());
		assertEquals("�݌v�݌ɂ��Ԉ���Ă��܂��B", 8, rltStock.get(3L).intValue());
		assertEquals("�݌v�݌ɂ��Ԉ���Ă��܂��B", 2, rltStock.get(4L).intValue());
		// 5�T�ڈȍ~�̓f�[�^���Ȃ��̂ŁA�S�T�ڂƓ����ɂȂ��Ă���
		assertEquals("�݌v�݌ɂ��Ԉ���Ă��܂��B", -4, rltStock.get(5L).intValue());
	}

	public void test���[���ƃQ�[�����������ɂ��č݌ɂ��擾����() throws Exception {
		Map<Long, Long> map = TradeTransaction.getStockAmount("NOAH",RoleType.���Q.name());
		assertEquals("�݌ɂ��Z�o����T�̐��Ɍ�肪����܂�", 6, map.size());
		assertEquals("�����݌ɂɌ�肪����܂�", 12, map.get(0L).intValue());
		assertEquals("�P�T�ڂ̍݌ɂɌ�肪����܂�", 12, map.get(1L).intValue());
		assertEquals("�Q�T�ڂ̍݌ɂɌ�肪����܂�", 12, map.get(2L).intValue());
		assertEquals("�R�T�ڂ̍݌ɂɌ�肪����܂�", 6, map.get(3L).intValue());
		assertEquals("�S�T�ڂ̍݌ɂɌ�肪����܂�", 0, map.get(4L).intValue());
		assertEquals("�T�T�ڂ̍݌ɂɌ�肪����܂�", -6, map.get(5L).intValue());
		
		map = TradeTransaction.getStockAmount("NOAH", RoleType.���[�J.name());
		assertEquals("�݌ɂ��Z�o����T�̐��Ɍ�肪����܂�", 6, map.size());
		assertEquals("�����݌ɂɌ�肪����܂�", 12, map.get(0L).intValue());
		assertEquals("�P�T�ڂ̍݌ɂɌ�肪����܂�", 12, map.get(1L).intValue());
		assertEquals("�Q�T�ڂ̍݌ɂɌ�肪����܂�", 14, map.get(2L).intValue());
		assertEquals("�R�T�ڂ̍݌ɂɌ�肪����܂�", 16, map.get(3L).intValue());
		assertEquals("�S�T�ڂ̍݌ɂɌ�肪����܂�", 18, map.get(4L).intValue());
		assertEquals("�T�T�ڂ̍݌ɂɌ�肪����܂�", 20, map.get(5L).intValue());
	}

	public void test���׎󒍏o�ה����̃g�����U�N�V�������i��������() throws Exception {
		// ��������
		BasicService service = BasicService.getService();
		Game game = service.findByPK(Game.class, 8L);
		for (Role role : game.getRoles()) {
			if (role.isDisposable()) {
				role.disposeAllMessage();
			}
		}
		Role wholeSeller = game.getRole(RoleType.������);
		wholeSeller.getUpper().send(TransactionType.�o��, "4");
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		// �{����
		TradeTransactionService.getService().addTransactions(wholeSeller, 2L);
		Extractor e = new Extractor(TradeTransaction.class);
		e.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> transactions = service.findByExtractor(e);
		assertEquals("�g�����U�N�V�����̌����Ɍ�肪����܂�", count + 4, transactions.size());

		assertEquals("�ŏI�T���擾�ł��Ă��܂���", 1L, transactions.get(0).getWeek().longValue());
		assertEquals("������TradeTransaction���擾�ł��Ă��܂���", TransactionType.����.name(), transactions.get(0).getTransactionType());
		assertEquals("�擾�����������Ɍ�肪����܂�", 2L, transactions.get(0).getAmount().longValue());

		assertEquals("�ŏI�T���擾�ł��Ă��܂���", 1L, transactions.get(1).getWeek().longValue());
		assertEquals("������TradeTransaction���擾�ł��Ă��܂���", TransactionType.�o��.name(), transactions.get(1).getTransactionType());
		assertEquals("�擾�����o�א��Ɍ�肪����܂�", 4L, transactions.get(1).getAmount().longValue());

		assertEquals("�ŏI�T���擾�ł��Ă��܂���", 1L, transactions.get(2).getWeek().longValue());
		assertEquals("������TradeTransaction���擾�ł��Ă��܂���", TransactionType.��.name(), transactions.get(2).getTransactionType());
		assertEquals("�擾�����󒍐��Ɍ�肪����܂�", 4L, transactions.get(2).getAmount().longValue());

		assertEquals("�ŏI�T���擾�ł��Ă��܂���", 1L, transactions.get(3).getWeek().longValue());
		assertEquals("������TradeTransaction���擾�ł��Ă��܂���", TransactionType.����.name(), transactions.get(3).getTransactionType());
		assertEquals("�擾�������א��Ɍ�肪����܂�", 4L, transactions.get(3).getAmount().longValue());
	}
	
	public void test�i�������̌��؂��s��() throws Exception {
		BasicService service = BasicService.getService();
		TradeTransaction transaction = new TradeTransaction();
		transaction.setAmount(-1L);
		Game game = service.findByPK(Game.class, 1L);
		transaction.setRole(game.getRole(RoleType.������));
		transaction.setTransactionType(TransactionType.����.name());
		transaction.setWeek(1L);
		Messages msgs = service.validate(transaction, WhenVerifier.INSERT);
		assertTrue("Amount�Ƀ}�C�i�X�l����͂ł��Ă��܂�", msgs.hasError());
	}
}
