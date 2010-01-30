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
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
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
		BasicService service = BasicService.getService();
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		form.setPlayerName("����");
		form.setGameId(1L);
		form.setRoleName("������");
		assertTrue("�Q�[���ɓo�^����̂Ɏ��s���܂����B", form.addPlayer());
		assertEquals("�擾����������Ă��܂��B", count + 5, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
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
		form.setRoleName(RoleType.������.name());
		assertFalse("�v���C���[������͂��Ȃ��œo�^�ł��Ă��܂��B", form.addPlayer());
		// �v���C���[�����p�X�y�[�X
		form.setGameId(1L);
		form.setPlayerName(" ");
		form.setRoleName(RoleType.������.name());
		// �v���C���[���S�p�X�y�[�X
		form.setGameId(1L);
		form.setPlayerName("�@");
		form.setRoleName(RoleType.������.name());
		assertFalse("�v���C���[�����S�p�X�y�[�X�œo�^�ł��Ă��܂��B", form.addPlayer());
		// ���[�������͂Ȃ�
		form.setGameId(1L);
		form.setPlayerName("����q��");
		form.setRoleName("");
		assertFalse("���[����I�����Ȃ��œo�^�ł��Ă��܂��B", form.addPlayer());
	}

	public void testIsEnableToStartGame() throws Exception {
		// �ҋ@��ʂ���Q�[���̊J�n��ʂ֑J�ڂ���e�X�g
		BasicService service = BasicService.getService();
		Role wholeSaller = service.findByPK(Role.class, 16L);
		wholeSaller.disposeAllMessage();
		Role supplier1 = service.findByPK(Role.class, 17L);
		// TODO 2010/01/04 imai & ogasawara send�ɐ���n�����A������Transaction����擾����悤�ɏC������
		supplier1.send(TransactionType.�o��, "4");
		Game game = service.findByPK(Game.class, 4L);
		// ����n
		form.setGame(game);
		form.setRole(wholeSaller);
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
		assertEquals("��ʂ̒��c���Ɍ�肪����܂�", 12L, form.getRemain().longValue());
		// �ُ�n
		Game game2 = service.findByPK(Game.class, 3L);
		form.setGame(game2);
		assertFalse(form.isEnableToStartGame());
	}

	public void test���I���̃��[�����擾����() throws Exception {
		form.setGameId(3L);
		Set<RoleType> set = form.getWaitingRoleList();
		assertEquals("���I���̃��[���̐��Ɍ�肪����܂�", 3, set.size());
	}

	public void testgetGameAll() throws Exception {
		List<Game> games = form.getGameAll();
		assertEquals("���ׂẴQ�[���̐��Ɍ�肪����܂�", 10, games.size());
	}

	public void test�`�[���ƃ��[����I��Ń��O�C������() throws Exception {
		form.setGameId(1L);
		form.setRoleName("���P");
		assertTrue(form.login());
		assertEquals("���P", form.getRole().getName());
		assertEquals("���}��", form.getRole().getPlayer().getName());
		// �����\���̃e�X�g
		assertEquals(20L, form.getInbound().longValue());
		assertEquals(26L, form.getAcceptOrder().longValue());
		assertEquals(22L, form.getOutbound().longValue());
		assertEquals(-4L, form.getRemain().longValue());
		// �ُ�n  ���[�����쐬����Ă��Ȃ��̂ɁA���O�C�����悤�Ƃ����ꍇ
		form = new PreGameForm();
		form.setGameId(3L);
		form.setRoleName("���[�J");
		assertFalse(form.login());
		assertTrue("�G���[���������Ă��܂���", form.getMessage().hasError());
		assertEquals("�G���[���b�Z�[�W������Ă��܂��B", "errors.invalid.login", 
				form.getMessage().get("").get(0).getKey());
		// �ُ�n  �S�̃��[���������Ă��Ȃ��Ƃ��ɁA���O�C�����悤�Ƃ����ꍇ
		form = new PreGameForm();
		form.setGameId(3L);
		form.setRoleName("������");
		assertFalse(form.login());
		assertTrue("�G���[���������Ă��܂���", form.getMessage().hasError());
		assertEquals("�G���[���b�Z�[�W������Ă��܂��B", "errors.invalid.start", 
				form.getMessage().get("").get(0).getKey());
	}

	public void test�Q�[���̏I�����������() throws Exception {
		BasicService service = BasicService.getService();
		// ��������
		Role wholeSeller = service.findByPK(Role.class, 22L);
		wholeSeller.disposeAllMessage();
		wholeSeller.getUpper().send(TransactionType.�o��, "1");
		// �{����
		form.setGame(service.findByPK(Game.class, 5L));
		form.setOrder("10");
		form.setRole(wholeSeller);
		assertFalse("�Q�[�����I������Ă��܂���B", form.order());
	}

	public void test�Q�[���o�^���̌��؂�����() throws Exception {
		form.setTeamName(" ");
		form.setOwnerName("Ryoji Yoshioka");
		assertFalse("�Q�[�����o�^�ł��Ă��܂�", form.addGame());
		assertTrue("�G���[���������Ă��܂���", form.getMessage().hasError());
		form.setTeamName("Alliance of Valiant Arms");
		form.setOwnerName(" ");
		assertFalse("�Q�[�����o�^�ł��Ă��܂�", form.addGame());
		assertTrue("�G���[���������Ă��܂���", form.getMessage().hasError());
	}

	public void test�������̌��؂�����() throws Exception {
		BasicService service = BasicService.getService();
		// ��������
		Game game = service.findByPK(Game.class, 1L);
		Role wholeSeller = game.getRole(RoleType.������);
		wholeSeller.disposeAllMessage();
		wholeSeller.getUpper().send(TransactionType.�o��, "1");
		// �{����
		// ���������u�����N
		form.setGame(game);
		form.setRole(wholeSeller);
		form.setOrder("");
		form.order();
		assertTrue("�G���[���������Ă��܂���", form.getMessage().hasError());
		// ���������}�C�i�X
		form.setGame(game);
		form.setRole(game.getRole(RoleType.������));
		form.setOrder("-1");
		form.order();
		assertTrue("�G���[���������Ă��܂���", form.getMessage().hasError());
	}
	
	public void test�������s��() throws Exception {
		BasicService service = BasicService.getService();
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		Game game = service.findByPK(Game.class, 1L);
		Role wholeSeller = game.getRole(RoleType.������);
		wholeSeller.disposeAllMessage();
		wholeSeller.getUpper().send(TransactionType.�o��, "1");
		form.setGame(game);
		form.setRole(wholeSeller);
		form.setOrder("10");
		form.order();
		Extractor e = new Extractor(TradeTransaction.class);
		e.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> transactions = service.findByExtractor(e);
		assertEquals("�擾��������Ă��܂�", count + 4, transactions.size());
		assertEquals("����̎�ނ�����Ă��܂�", TransactionType.����.name(), transactions.get(0).getTransactionType());
		assertEquals("����̒l������Ă��܂�", 10L, transactions.get(0).getAmount().longValue());
		assertEquals("����̎�ނ�����Ă��܂�", TransactionType.�o��.name(), transactions.get(1).getTransactionType());
		assertEquals("����̒l������Ă��܂�", 4L, transactions.get(1).getAmount().longValue());
		assertEquals("����̎�ނ�����Ă��܂�", TransactionType.��.name(), transactions.get(2).getTransactionType());
		assertEquals("����̒l������Ă��܂�", 4L, transactions.get(2).getAmount().longValue());
		assertEquals("����̎�ނ�����Ă��܂�", TransactionType.����.name(), transactions.get(3).getTransactionType());
		assertEquals("����̒l������Ă��܂�", 1L, transactions.get(3).getAmount().longValue());
	}
	
	public void test������Ԃ��������쐬����Ă��邩�m�F����() throws Exception {
		//TODO 2010/01/19 yoshioka �e�X�g��邩�Y�ݒ�
		assertTrue(true);
	}
	
	public void test�r�����ʕ\���p��Owner���ǂ������肷��(){
		BasicService service = BasicService.getService();
		Game game = service.findByPK(Game.class, 1L);
		Role wholeSeller = game.getRole(RoleType.������);
		form.setRole(wholeSeller);
		assertTrue("Owner�Ƃ��Ĕ��肳��Ă��܂���B",form.isOwner());
		Role maker = game.getRole(RoleType.���[�J);
		form.setRole(maker);
		assertFalse("Owner�Ƃ��Ĕ��肳��Ă��܂��Ă��܂��B",form.isOwner());
	}
}
