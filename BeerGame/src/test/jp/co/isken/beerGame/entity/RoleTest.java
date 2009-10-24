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
		int ret = role.getWeek();
		assertEquals("�e�X�g���s���恙", 3, ret);
	}

	// TODO 2009/10/24 imai �Ȃ������b�Z�[�W���c���Ă��܂��̂ŁA�R�����g�A�E�g�B���ꂩ�C�����āB
//	public void testMQ�𑗎�M����() throws Exception {
//		Role role = BasicService.getService().findByPK(Role.class, 3L);
//		while(true) {
//			String ret = role.receive(SendType.��);
//			if (ret == null) {
//				break;
//			} else {
//				System.out.println("�c�����F " + ret);
//			}
//		}
//
//		BasicService service = BasicService.getService();
//		// ��1���甭���𑗐M����
//		Role supplier1 = service.findByPK(Role.class, 2L);
//		supplier1.send(SendType.����, "Hoge");
//		// ��2����󒍂���M����
//		Role supplier2 = service.findByPK(Role.class, 3L);
//		String ret = supplier2.receive(SendType.��);
//		assertEquals("���b�Z�[�W�Ɍ�肪����܂�", "Hoge", ret);
//	}
	
	public void test�قȂ�Q�[���ԂŃ��b�Z�[�W�𑗎�M�ł��Ȃ�����() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 3L);
		while(true) {
			String ret = role.receive(TransactionType.��);
			if (ret == null) {
				break;
			} else {
				System.out.println("�c�����F " + ret);
			}
		}

		// TODO �Q�[�� + ���[�� + �����ʂň�ӂ̃L���[�ɂ��邱��
		BasicService service = BasicService.getService();
		// ��1���甭���𑗐M����
		Role supplier1 = service.findByPK(Role.class, 2L);
		supplier1.send(TransactionType.����, "Hoge");
		// ��2����󒍂���M����
		Role supplier2 = service.findByPK(Role.class, 1L);
		String ret = supplier2.receive(TransactionType.��);
		assertNull("���b�Z�[�W���擾�ł��Ă��܂�", ret);
	}

	public void test�����̎���L�^��o�^����() throws Exception {
		BasicService service = BasicService.getService();
		// �����̎���L�^��o�^����
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		Role role = BasicService.getService().findByPK(Role.class, 2L);
		role.order(2L);
		assertEquals("�擾����������Ă��܂��B", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		assertEquals("���Ɍ�肪����܂�", 2L, tradeTransaction.getAmount().longValue());
		assertEquals("���[�����Ԉ���Ă܂��B", 2L, tradeTransaction.getRole().getId().longValue());
		assertEquals("�T���Ԉ���Ă܂��B", 1L, tradeTransaction.getWeek().longValue());
		assertEquals("�����ʂ��Ԉ���Ă܂��B", "����", tradeTransaction.getTransactionType());
	}
}
