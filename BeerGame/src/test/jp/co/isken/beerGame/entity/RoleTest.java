package jp.co.isken.beerGame.entity;

import java.util.List;

import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class RoleTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void test�ŏI�T���擾����() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 7L);
		assertEquals("�e�X�g���s���恙", 4L, role.getWeek("����").longValue());
	}

	public void test���ݏT���擾����e�X�g() throws Exception {
		BasicService service = BasicService.getService();
		Role role = service.findByPK(Role.class, 9L);
		assertEquals("���ݏT�łȂ��T���擾����܂����B", 4L, role.getCurrentWeek("����").longValue());

		role = service.findByPK(Role.class, 6L);
		// ��T�ڂ̂��߁A����L�^���Ȃ��ꍇ
		assertEquals("���ݏT�łȂ��T���擾����܂����B", 1L, role.getCurrentWeek("����").longValue());
	}

	public void testMQ�𑗎�M����() throws Exception {
		BasicService service = BasicService.getService();
		Role role = service.findByPK(Role.class, 3L);
		role.disposeAllMessage();
		// ��1���甭���𑗐M����
		Role supplier1 = service.findByPK(Role.class, 2L);
		supplier1.send(TransactionType.����, "Hoge");
		// ��2����󒍂���M����
		Role supplier2 = service.findByPK(Role.class, 3L);
		String ret = supplier2.receive(TransactionType.��);
		assertEquals("���b�Z�[�W�Ɍ�肪����܂�", "Hoge", ret);
	}
	
	// TODO 2009/11/07 ogasawara,yoshioka �e�X�g���d���Ȃ�̂ŁC��U�R�����g�A�E�g���܂�
//	public void test�L���[�̃��b�Z�[�W��������������Ă��邩�m�F����() throws Exception {
//		BasicService service = BasicService.getService();
//		Role supplier1 = service.findByPK(Role.class, 2L);
//		supplier1.send(TransactionType.����, "hogehoge");
//		supplier1.send(TransactionType.����, "hohogege");
//		supplier1.disposeAllMessage();
//		Role supplier2 = service.findByPK(Role.class, 3L);
//		String ret = supplier2.receive(TransactionType.��);
//		assertNull("���b�Z�[�W���j������Ă��܂���B", ret);		
//	}
	
	// TODO 2009/11/07 imai ��̃e�X�g�𒼂�����A�������������Ȃ�
	// Queue���Ⴄ�͂���������Ȃ��͂��Ȃ̂ɁA���Ă��܂�
//	public void test�قȂ�Q�[���ԂŃ��b�Z�[�W�𑗎�M�ł��Ȃ�����() throws Exception {
//		BasicService service = BasicService.getService();
//		Role role = service.findByPK(Role.class, 1L);
//		role.disposeAllMessage();
//		// TODO �Q�[�� + ���[�� + �����ʂň�ӂ̃L���[�ɂ��邱��
//		// ��1���甭���𑗐M����
//		Role supplier1 = service.findByPK(Role.class, 2L);
//		supplier1.send(TransactionType.����, "Hoge");
//		// ��2����󒍂���M����
//		Role supplier2 = service.findByPK(Role.class, 1L);
//		String ret = supplier2.receive(TransactionType.��);
//		assertNull("���b�Z�[�W���擾�ł��Ă��܂�", ret);
//	}

	public void test�����̎���L�^��o�^����() throws Exception {
		BasicService service = BasicService.getService();
		// �����̎���L�^��o�^����
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		Role role = BasicService.getService().findByPK(Role.class, 9L);
		role.order(2L);
		assertEquals("�擾����������Ă��܂��B", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		assertEquals("���Ɍ�肪����܂�", 2L, tradeTransaction.getAmount().longValue());
		assertEquals("���[�����Ԉ���Ă܂��B", 9L, tradeTransaction.getRole().getId().longValue());
		assertEquals("�T���Ԉ���Ă܂��B", 4L, tradeTransaction.getWeek().longValue());
		assertEquals("�����ʂ��Ԉ���Ă܂��B", TransactionType.����.name(), tradeTransaction.getTransactionType());
	}

	public void test�󒍂̎���L�^��o�^����() throws Exception {
		BasicService service = BasicService.getService();
		// Role8L�̑�O�T���甭�������󂯎��
		// �󒍂̎���L�^��o�^����
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		Role role = BasicService.getService().findByPK(Role.class, 9L);
		role.acceptOrder();
		assertEquals("�擾����������Ă��܂��B", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		assertEquals("���Ɍ�肪����܂�", 8L, tradeTransaction.getAmount().longValue());
		assertEquals("���[�����Ԉ���Ă܂��B", 9L, tradeTransaction.getRole().getId().longValue());
		assertEquals("�T���Ԉ���Ă܂��B", 4L, tradeTransaction.getWeek().longValue());
		assertEquals("�����ʂ��Ԉ���Ă܂��B", TransactionType.��.name(), tradeTransaction.getTransactionType());
	}
	
	public void test���������擾����() throws Exception {
		// TODO MQ�Ŏ擾���邪�AMQ�������̈�DB������������Ă܂��B
		Role role = BasicService.getService().findByPK(Role.class, 9L);
		assertEquals("������������Ă��܂��B", 8L , role.getOrderCount().longValue());
	}

	public void test���ׂ̎���L�^��o�^����() throws Exception {
		BasicService service = BasicService.getService();
		// Role10L�̑�O�T����o�א����󂯎��
		// ���ׂ̎���L�^��o�^����
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		Role role = BasicService.getService().findByPK(Role.class, 9L);
		role.inbound();
		assertEquals("�擾����������Ă��܂��B", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		assertEquals("���Ɍ�肪����܂�", 10L, tradeTransaction.getAmount().longValue());
		assertEquals("���[�����Ԉ���Ă܂��B", 9L, tradeTransaction.getRole().getId().longValue());
		assertEquals("�T���Ԉ���Ă܂��B", 4L, tradeTransaction.getWeek().longValue());
		assertEquals("�����ʂ��Ԉ���Ă܂��B", TransactionType.����.name(), tradeTransaction.getTransactionType());
	}

	public void test�o�ׂ̎���L�^��o�^����() throws Exception {
		BasicService service = BasicService.getService();
		// Role10L�̑�O�T����o�א����󂯎�� 10L
		// ���ݍ݌ɂ��v�Z���� 25L
		// Role8L�̔��������󂯎�� 5L
		// �o�ׂ̎���L�^��o�^���� 5L
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		Role role = BasicService.getService().findByPK(Role.class, 9L);
		role.outbound();
		assertEquals("�擾����������Ă��܂��B", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		assertEquals("���Ɍ�肪����܂�", 5L, tradeTransaction.getAmount().longValue());
		assertEquals("���[�����Ԉ���Ă܂��B", 9L, tradeTransaction.getRole().getId().longValue());
		assertEquals("�T���Ԉ���Ă܂��B", 4L, tradeTransaction.getWeek().longValue());
		assertEquals("�����ʂ��Ԉ���Ă܂��B", TransactionType.�o��.name(), tradeTransaction.getTransactionType());
	}
}
