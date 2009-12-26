package jp.co.isken.beerGame.entity;

import java.util.ArrayList;
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
		Role role = BasicService.getService().findByPK(Role.class, 3L);
		assertEquals("�ŏI�T�łȂ��T���擾����܂����B", 3L, role.getLastWeek("����").longValue());
	}

	public void test���ݏT���擾����e�X�g() throws Exception {
		BasicService service = BasicService.getService();
		Role role = service.findByPK(Role.class, 1L);
		assertEquals("���ݏT�łȂ��T���擾����܂����B", 4L, role.getCurrentWeek("����").longValue());

//		role = service.findByPK(Role.class, 6L);
//		// ��T�ڂ̂��߁A����L�^���Ȃ��ꍇ
//		assertEquals("���ݏT�łȂ��T���擾����܂����B", 1L, role.getCurrentWeek("����").longValue());
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

	// TODO 2009/11/29 imai �^�C���A�E�g���Ȃ��Ɖ������Ȃ��Ȃ邽�߁A�R�����g�A�E�g
//	public void test�L���[�̃��b�Z�[�W��������������Ă��邩�m�F����() throws Exception {
//		BasicService service = BasicService.getService();
//		Role supplier1 = service.findByPK(Role.class, 2L);
//		supplier1.send(TransactionType.����, "hogehoge");
//		supplier1.send(TransactionType.����, "hohogege");
//		Role supplier2 = service.findByPK(Role.class, 3L);
//		supplier2.disposeAllMessage();
//		String ret = supplier2.receive(TransactionType.��);
//		assertNull("���b�Z�[�W���j������Ă��܂���B", ret);
//	}

	// TODO 2009/11/29 imai �^�C���A�E�g���Ȃ��Ɖ������Ȃ��Ȃ邽�߁A�R�����g�A�E�g
//	public void test�قȂ�Q�[���ԂŃ��b�Z�[�W�𑗎�M�ł��Ȃ�����() throws Exception {
//		BasicService service = BasicService.getService();
//		Role supplier2 = service.findByPK(Role.class, 8L);
//		supplier2.disposeAllMessage();
//		// �Q�[��3�̉�1�������𑗐M����
//		Role supplier1 = service.findByPK(Role.class, 2L);
//		supplier1.send(TransactionType.����, "Hoge");
//		// �Q�[��4�̉�2���󒍂���M����
//		supplier2 = service.findByPK(Role.class, 8L);
//		String ret = supplier2.receive(TransactionType.��);
//		assertNull("���b�Z�[�W���擾�ł��Ă��܂�", ret);
//	}

	public void test�����̎���L�^��o�^����() throws Exception {
		BasicService service = BasicService.getService();
		// �����̎���L�^��o�^����
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		Role role = BasicService.getService().findByPK(Role.class, 3L);
		role.order(2L);
		assertEquals("�擾����������Ă��܂��B", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		assertEquals("���Ɍ�肪����܂�", 2L, tradeTransaction.getAmount().longValue());
		assertEquals("���[�����Ԉ���Ă܂��B", 3L, tradeTransaction.getRole().getId().longValue());
		assertEquals("�T���Ԉ���Ă܂��B", 4L, tradeTransaction.getWeek().longValue());
		assertEquals("�����ʂ��Ԉ���Ă܂��B", TransactionType.����.name(), tradeTransaction.getTransactionType());
	}

	public void test�󒍂̎���L�^��o�^����() throws Exception {
		BasicService service = BasicService.getService();
		// ��������
		Role supplier2 = BasicService.getService().findByPK(Role.class, 3L);
		supplier2.disposeAllMessage();
		// �{����
		Role supplier1 = service.findByPK(Role.class, 2L);
		supplier1.order(8L);
		// Role8L�̑�O�T���甭�������󂯎��
		// �󒍂̎���L�^��o�^����
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		supplier2 = BasicService.getService().findByPK(Role.class, 3L);
		supplier2.acceptOrder();
		assertEquals("�擾����������Ă��܂��B", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		assertEquals("���Ɍ�肪����܂�", 8L, tradeTransaction.getAmount().longValue());
		assertEquals("���[�����Ԉ���Ă܂��B", 3L, tradeTransaction.getRole().getId().longValue());
		assertEquals("�T���Ԉ���Ă܂��B", 5L, tradeTransaction.getWeek().longValue());
		assertEquals("�����ʂ��Ԉ���Ă܂��B", TransactionType.��.name(), tradeTransaction.getTransactionType());
	}
	
	public void test���������擾����() throws Exception {
		BasicService service = BasicService.getService();
		// ��������
		Role supplier2 = BasicService.getService().findByPK(Role.class, 3L);
		supplier2.disposeAllMessage();
		// �{����
		Role supplier1 = service.findByPK(Role.class, 2L);
		supplier1.order(8L);
		supplier2 = BasicService.getService().findByPK(Role.class, 3L);
		assertEquals("������������Ă��܂��B", 8L , supplier2.getOrderAmount().longValue());
	}

	public void test���ׂ̎���L�^��o�^����() throws Exception {
		BasicService service = BasicService.getService();
		// ���[�J�[���牵2�ւ̓��ׂ�z�肷��
		// ��������
		Role supplier2 = BasicService.getService().findByPK(Role.class, 9L);
		supplier2.disposeAllMessage();
		// �{����
		// ���[�J�[���󒍋L�^�����Ƃɏo�ׂ���
		Role maker = BasicService.getService().findByPK(Role.class, 10L);
		maker.outbound();
		// ���[�J�[����̏o�׃f�[�^���󂯂ē��ׂ̎���L�^��o�^����
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		supplier2 = BasicService.getService().findByPK(Role.class, 9L);
		supplier2.inbound();
		assertEquals("�擾����������Ă��܂��B", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		// ���ׂ̃g�����U�N�V���������������ꂽ���H
		assertEquals("���Ɍ�肪����܂�", 4L, tradeTransaction.getAmount().longValue());
		assertEquals("���[�����Ԉ���Ă܂��B", 9L, tradeTransaction.getRole().getId().longValue());
		assertEquals("�T���Ԉ���Ă܂��B", 1L, tradeTransaction.getWeek().longValue());
		assertEquals("�����ʂ��Ԉ���Ă܂��B", TransactionType.����.name(), tradeTransaction.getTransactionType());
	}

	public void test�o�ׂ̎���L�^��o�^����() throws Exception {
		BasicService service = BasicService.getService();
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		// ���[�J�[���󒍋L�^�����Ƃɏo�ׂ���
		Role maker = BasicService.getService().findByPK(Role.class, 10L);
		maker.outbound();
		// �g�����U�N�V�����̐���1�����Ă��邩�H
		assertEquals("�擾����������Ă��܂��B", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		// �o�ׂ̃g�����U�N�V���������������ꂽ���H
		assertEquals("���Ɍ�肪����܂�", 4L, tradeTransaction.getAmount().longValue());
		assertEquals("���[�����Ԉ���Ă܂��B", 10L, tradeTransaction.getRole().getId().longValue());
		assertEquals("�T���Ԉ���Ă܂��B", 1L, tradeTransaction.getWeek().longValue());
		assertEquals("�����ʂ��Ԉ���Ă܂��B", TransactionType.�o��.name(), tradeTransaction.getTransactionType());
	}

	public void test�㗬�̃��[�����擾����() throws Exception {
		BasicService service = BasicService.getService();
		Role market = service.findByPK(Role.class, 5L);
		assertEquals("���[�����s��ł͂���܂���", RoleType.�s��.name(), market.getName());
		Role wholeSeller = market.getUpper();
		assertEquals("�擾�������[����ID�Ɍ�肪����܂�", 1L, wholeSeller.getId().longValue());
		assertEquals("�s��̏㗬�ł��鏬������擾�ł��Ă��܂���", RoleType.������.name(), wholeSeller.getName());
		Role supplier1 = wholeSeller.getUpper();
		assertEquals("�擾�������[����ID�Ɍ�肪����܂�", 2L, supplier1.getId().longValue());
		assertEquals("������̏㗬�ł��鉵�P���擾�ł��Ă��܂���", RoleType.���P.name(), supplier1.getName());
		Role supplier2 = supplier1.getUpper();
		assertEquals("�擾�������[����ID�Ɍ�肪����܂�", 3L, supplier2.getId().longValue());
		assertEquals("���P�̏㗬�ł��鉵�Q���擾�ł��Ă��܂���", RoleType.���Q.name(), supplier2.getName());
		Role maker = supplier2.getUpper();
		assertEquals("�擾�������[����ID�Ɍ�肪����܂�", 4L, maker.getId().longValue());
		assertEquals("���Q�̏㗬�ł��郁�[�J���擾�ł��Ă��܂���", RoleType.���[�J.name(), maker.getName());
		Role factory = maker.getUpper();
		assertEquals("�擾�������[����ID�Ɍ�肪����܂�", 6L, factory.getId().longValue());
		assertEquals("���[�J�̏㗬�ł���H����擾�ł��Ă��܂���", RoleType.�H��.name(), factory.getName());
		try {
			factory.getUpper();
			fail("�H��̏㗬���擾�ł��Ă��܂�");
		} catch (RuntimeException e) {
			assertTrue(true);
		}
	}

	public void test�����̃��[�����擾����() throws Exception {
		BasicService service = BasicService.getService();
		Role factory = service.findByPK(Role.class, 6L);
		assertEquals("���[�����H��ł͂���܂���", RoleType.�H��.name(), factory.getName());
		Role maker = factory.getDowner();
		assertEquals("�擾�������[����ID�Ɍ�肪����܂�", 4L, maker.getId().longValue());
		assertEquals("���[�J�̉����ł��鉵�Q���擾�ł��Ă��܂���", RoleType.���[�J.name(), maker.getName());
		Role supplier2 = maker.getDowner();
		assertEquals("�擾�������[����ID�Ɍ�肪����܂�", 3L, supplier2.getId().longValue());
		assertEquals("���[�J�̉����ł��鉵�Q���擾�ł��Ă��܂���", RoleType.���Q.name(), supplier2.getName());
		Role supplier1 = supplier2.getDowner();
		assertEquals("�擾�������[����ID�Ɍ�肪����܂�", 2L, supplier1.getId().longValue());
		assertEquals("���Q�̉����ł��鉵�P���擾�ł��Ă��܂���", RoleType.���P.name(), supplier1.getName());
		Role wholeSeller = supplier1.getDowner();
		assertEquals("�擾�������[����ID�Ɍ�肪����܂�", 1L, wholeSeller.getId().longValue());
		assertEquals("���P�̉����ł��鏬������擾�ł��Ă��܂���", RoleType.������.name(), wholeSeller.getName());
		Role market = wholeSeller.getDowner();
		assertEquals("�擾�������[����ID�Ɍ�肪����܂�", 5L, market.getId().longValue());
		assertEquals("������̉����ł���s����擾�ł��Ă��܂���", RoleType.�s��.name(), market.getName());
		try {
			market.getDowner();
			fail("�s��̉������擾�ł��Ă��܂�");
		} catch (RuntimeException e) {
			assertTrue(true);
		}
	}

	//TODO 2009/11/29 imai&yoshioka �ꎞ�I�ȃX�^�u����[��
	public void test�s�ꂩ�珬����ւ̔������Œ�l�ŕԋp����() throws Exception {
		BasicService service =  BasicService.getService();
		Role wholeSeller = service.findByPK(Role.class, 1L);
		assertEquals("�s�ꂩ��̔����l������Ă��܂��B", 4L, wholeSeller.getOrderAmount().longValue());
	}
	
	//TODO 2009/11/29 imai&yoshioka �ꎞ�I�ȃX�^�u����[��
	public void test���[�J����̔����������H�ꂪ�o�ׂ���() throws Exception {
		BasicService service =  BasicService.getService();
		Role maker = service.findByPK(Role.class, 4L);
		maker.disposeAllMessage();
		maker.order(5L);		
		assertEquals("�H�ꂩ��̏o�גl������Ă��܂��B", 5L, maker.getInboundAmount().longValue());
	}
	
	public void test�e�g�����U�N�V�������擾�ł���() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 3L);
		TradeTransaction tradeTransaction = role.getTransaction(TransactionType.����);
		assertEquals("���������ʂ��擾�o���Ă��܂���B",14L,tradeTransaction.getAmount().longValue());
		assertEquals("�������T���擾�o���Ă��܂���B",4L,tradeTransaction.getWeek().longValue());
		
		tradeTransaction = role.getTransaction(TransactionType.��);
		assertEquals("���������ʂ��擾�o���Ă��܂���B",20L,tradeTransaction.getAmount().longValue());
		assertEquals("�������T���擾�o���Ă��܂���B",4L,tradeTransaction.getWeek().longValue());
		
		tradeTransaction = role.getTransaction(TransactionType.�o��);
		assertEquals("���������ʂ��擾�o���Ă��܂���B",20L,tradeTransaction.getAmount().longValue());
		assertEquals("�������T���擾�o���Ă��܂���B",4L,tradeTransaction.getWeek().longValue());
	}
	
	public void test�Q�[��NOAH�̃��[���̃L���[�����ׂč폜����() throws Exception {
		BasicService service =  BasicService.getService();
		List<Role> roles = new ArrayList<Role>();
		roles.add(service.findByPK(Role.class, 1L));
		roles.add(service.findByPK(Role.class, 2L));
		roles.add(service.findByPK(Role.class, 3L));
		roles.add(service.findByPK(Role.class, 4L));
		for (Role role : roles) {
			role.disposeAllMessage();
		}
	}
	
	public void test�s��ƍH��ȊO�̃��[�����擾����(){
		BasicService service =  BasicService.getService();
		Game game = service.findByPK(Game.class, 1L);
		List<Role> roles = Role.getRoles(game);
		assertEquals(4, roles.size());
		assertEquals("������", roles.get(0).getName());
		assertEquals("���P", roles.get(1).getName());
		assertEquals("���Q", roles.get(2).getName());
		assertEquals("���[�J", roles.get(3).getName());	
	}
}
