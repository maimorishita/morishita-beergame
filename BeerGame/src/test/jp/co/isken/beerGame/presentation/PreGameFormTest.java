package jp.co.isken.beerGame.presentation;

import java.util.List;
import java.util.Set;

import jp.co.isken.beerGame.entity.BusinessMasterLoader;
import jp.co.isken.beerGame.entity.Game;
import jp.co.isken.beerGame.entity.MasterLoader;
import jp.co.isken.beerGame.entity.NumberingLoader;
import jp.co.isken.beerGame.entity.Role;
import jp.co.isken.beerGame.entity.RoleType;
import jp.co.isken.beerGame.entity.TradeTransaction;
import jp.co.isken.beerGame.entity.TransactionLoader;
import jp.co.isken.beerGame.entity.TransactionType;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class PreGameFormTest extends DataLoadingTestCase {

	private PreGameForm form;

	protected void setUp() throws Exception {
		MasterLoader.init();
		BusinessMasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
		form = new PreGameForm();
	}

	public void test�������J�ڂ��邱��() throws Exception {
		form.setNewGame(true);
		assertTrue("�Q�[���o�^��ʂɑJ�ڂ��܂���ł����B", form.judgeGameMode());

		form = new PreGameForm();
		form.setNewGame(false);
		assertFalse("�v���C���[�o�^��ʂɑJ�ڂ��܂���ł����B", form.judgeGameMode());
	}

	public void test�Q�[����o�^����() throws Exception {
		form.setTeamName("Alliance of Valiant Arms");
		form.setOwnerName("Ryoji Yoshioka");
		assertTrue(form.addGame());
		assertEquals("�Q�[�����擾�ł��܂���B", "Alliance of Valiant Arms", form.getGame().getName());
		assertEquals("���[�������擾�ł��܂���B", "������", form.getRole().getName());
		assertEquals("���[���̎擾��������Ă��܂��B", 3, Game.getRoles(form.getGame()).size());

		form = new PreGameForm();
		form.setTeamName("Alliance of Valiant Arms");
		assertFalse(form.addGame());
	}

	public void test�v���C���[��o�^����() throws Exception {
		form.setPlayerName("����");
		form.setGameId(1L);
		form.setRoleName("������");
		assertTrue("�Q�[���ɓo�^����̂Ɏ��s���܂����B", form.addPlayer());
		assertEquals("�Q�[�����擾�ł��܂���B", "NOAH", form.getGame().getName());
		assertEquals("���[�����擾�ł��܂���B", "������", form.getRole().getName());
		assertEquals("�v���C���[���擾�ł��܂���B", "����", form.getRole().getPlayer().getName());
	}

	public void test�v���C���[�o�^���̌��؂�����() throws Exception {
		// �Q�[���I���Ȃ�
		form.setGameId(0L);
		form.setPlayerName("����q��");
		form.setRoleName("������");
		assertFalse("�Q�[����I�����Ȃ��œo�^�ł��Ă��܂��B", form.addPlayer());
		// �v���C���[�����͂Ȃ�
		form.setGameId(1L);
		form.setPlayerName("");
		form.setRoleName("������");
		assertFalse("�v���C���[������͂��Ȃ��œo�^�ł��Ă��܂��B", form.addPlayer());
		// �v���C���[�����͂Ȃ�
		form.setGameId(1L);
		form.setPlayerName("����q��");
		form.setRoleName("");
		assertFalse("���[����I�����Ȃ��œo�^�ł��Ă��܂��B", form.addPlayer());
	}

	public void testIsEnableToStartGame() throws Exception {
		// �ҋ@��ʂ���Q�[���̊J�n��ʂ֑J�ڂ���e�X�g
		BasicService service = BasicService.getService();
		Role role = service.findByPK(Role.class, 16L);
		Game game = service.findByPK(Game.class, 4L);
		// ����n
		form.setGame(game);
		form.setRole(role);
		assertTrue("�Q�[�����J�n�ł��Ă��܂���", form.isEnableToStartGame());
		// ������̑�P�T�̃e�X�g
		TradeTransaction transaction = form.getRole().getTransaction(TransactionType.�݌�);
		assertEquals("�݌Ƀg�����U�N�V�������쐬����Ă��܂���", TransactionType.�݌�.name(), transaction.getTransactionType());
		assertEquals("�݌ɐ��Ɍ�肪����܂�", 12L, transaction.getAmount().longValue());
		transaction = form.getRole().getTransaction(TransactionType.����);
		assertEquals("���׃g�����U�N�V�������쐬����Ă��܂���", TransactionType.����.name(), transaction.getTransactionType());
		assertEquals("�󒍐��Ɍ�肪����܂�", 4L, transaction.getAmount().longValue());
		transaction = form.getRole().getTransaction(TransactionType.��);
		assertEquals("�󒍃g�����U�N�V�������쐬����Ă��܂���", TransactionType.��.name(), transaction.getTransactionType());
		assertEquals("�󒍐��Ɍ�肪����܂�", 4L, transaction.getAmount().longValue());
		transaction = form.getRole().getTransaction(TransactionType.�o��);
		assertEquals("�o�׃g�����U�N�V�������쐬����Ă��܂���", TransactionType.�o��.name(), transaction.getTransactionType());
		assertEquals("�o�א��Ɍ�肪����܂�", 4L, transaction.getAmount().longValue());
		// ��ʕ\���̃e�X�g
		assertEquals("��ʂ̓��א��Ɍ�肪����܂�", 4L, form.getInbound().longValue());
		assertEquals("��ʂ̎󒍐��Ɍ�肪����܂�", 4L, form.getAcceptOrder().longValue());
		assertEquals("��ʂ̏o�א��Ɍ�肪����܂�", 4L, form.getOutbound().longValue());
		assertEquals("��ʂ̒����c���Ɍ�肪����܂�", 0L, form.getRemain().longValue());
		assertEquals("��ʂ̍݌ɐ��Ɍ�肪����܂�", 12L, form.getStock().longValue());
		// �ُ�n
		 Game game2 = service.findByPK(Game.class, 3L);
		 form.setGame(game2);
		 assertFalse(form.isEnableToStartGame());
	}

	// // FIXME 2009/11/22 imai yoshioka MQ���g���悤�ɂ����̂ŁA���L�̈ꎞ�I�ȃR�[�f�B���O���ƕs����������Ă܂�
	// public void test���ڂ̔��������e�X�g() throws Exception {
	// BasicService service = BasicService.getService();
	// // ��������
	// Role supplier1 = BasicService.getService().findByPK(Role.class, 11L);
	// supplier1.disposeAllMessage();
	// // �{����
	// // ������̑�1�T�̃e�X�g
	// Role wholeSeller = service.findByPK(Role.class, 11L);
	// Game game1 = service.findByPK(Game.class, 5L);
	// form.setGame(game1);
	// form.setRole(wholeSeller);
	// // �����݌ɂ𓊓�����
	// form.isEnableToStartGame();
	// form.setOrder("126");
	// form.order();
	// Extractor extractor = new Extractor(TradeTransaction.class);
	// extractor.add(Condition.eq(new Property(TradeTransaction.ROLE),
	// wholeSeller));
	// extractor.add(Condition.eq(new Property(TradeTransaction.WEEK), 1L));
	// extractor.add(Condition.eq(new
	// Property(TradeTransaction.TRANSACTION_TYPE), "����"));
	// List<TradeTransaction> list =
	// BasicService.getService().findByExtractor(extractor);
	// assertEquals(1, list.size());
	// assertEquals(126L, list.get(0).getAmount().longValue());
	// //������̑�2�T�̃e�X�g
	// extractor = new Extractor(TradeTransaction.class);
	// extractor.add(Condition.eq(new Property(TradeTransaction.ROLE),
	// wholeSeller));
	// extractor.add(Condition.eq(new Property(TradeTransaction.WEEK), 1L));
	// extractor.addOrder(Order.asc(new Property(TradeTransaction.ID)));
	// list = service.findByExtractor(extractor);
	// assertEquals(4, list.size());
	// assertEquals("����", list.get(0).getTransactionType());
	// assertEquals(126, list.get(0).getAmount().intValue());
	// assertEquals("����", list.get(1).getTransactionType());
	// assertEquals(10, list.get(1).getAmount().intValue());
	// assertEquals("��", list.get(2).getTransactionType());
	// assertEquals(8, list.get(2).getAmount().intValue());
	// assertEquals("�o��", list.get(3).getTransactionType());
	// assertEquals(5, list.get(3).getAmount().intValue());
	// // ��ʕ\���̃e�X�g
	// assertEquals(10, form.getInbound().intValue());
	// assertEquals(8, form.getAcceptOrder().intValue());
	// assertEquals(5, form.getOutbound().intValue());
	// assertEquals(3, form.getRemain().intValue());
	// assertEquals(3, form.getRemain().intValue());
	// }
	//	
	// public void test�����̏����̃e�X�g() throws VersionUnmuchException,
	// MessagesIncludingException, JMSException {
	// BasicService service = BasicService.getService();
	// Role role = service.findByPK(Role.class, 11L);
	// Game game1 = service.findByPK(Game.class, 5L);
	// form.setGame(game1);
	// form.setRole(role);
	// form.setOrder("126");
	// form.orderSet();
	// // ������̑�P�T�̃e�X�g
	// Extractor extractor = new Extractor(TradeTransaction.class);
	// extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), role));
	// extractor.add(Condition.eq(new Property(TradeTransaction.WEEK), 1L));
	// extractor.addOrder(Order.asc(new Property(TradeTransaction.ID)));
	// List<TradeTransaction> list = service.findByExtractor(extractor);
	// assertEquals(3, list.size());
	// assertEquals("����", list.get(0).getTransactionType());
	// assertEquals(10, list.get(0).getAmount().intValue());
	// assertEquals("��", list.get(1).getTransactionType());
	// assertEquals(8, list.get(1).getAmount().intValue());
	// assertEquals("�o��", list.get(2).getTransactionType());
	// assertEquals(5, list.get(2).getAmount().intValue());
	// // ��ʕ\���̃e�X�g
	// assertEquals(10, form.getInbound().intValue());
	// assertEquals(8, form.getAcceptOrder().intValue());
	// assertEquals(5, form.getOutbound().intValue());
	// assertEquals(3, form.getRemain().intValue());
	// }

	public void test���I���̃��[�����擾����() throws Exception {
		form.setGameId(3L);
		Set<RoleType> set = form.getWaitingRoleList();
		assertEquals("���I���̃��[���̐��Ɍ�肪����܂�", 3, set.size());
	}

	public void testgetGameAll() throws Exception {
		List<Game> games = form.getGameAll();
		assertEquals("���ׂẴQ�[���̐��Ɍ�肪����܂�", 4, games.size());
	}

	public void test�`�[���ƃ��[����I��Ń��O�C������() {
		form.setGameId(1L);
		form.setRoleName("���P");
		assertTrue(form.login());
		assertEquals("���P", form.getRole().getName());
		assertEquals("���}��", form.getRole().getPlayer().getName());
		// �����\���̃e�X�g
		assertEquals(18L, form.getAcceptOrder().longValue());
		assertEquals(2L, form.getStock().longValue());

		// �����݌ɂ̃e�X�g
		assertEquals(12L, form.getRole().getTransaction(TransactionType.�݌�).getAmount().longValue());

		// �ُ�n
		form = new PreGameForm();
		form.setGameId(3L);
		form.setRoleName("���[�J");
		assertFalse(form.login());
	}
}
