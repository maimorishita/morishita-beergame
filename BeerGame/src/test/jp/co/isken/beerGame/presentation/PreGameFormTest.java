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
		assertEquals("�󒍐��Ɍ�肪����܂�", 5L, transaction.getAmount().longValue());
		transaction = form.getRole().getTransaction(TransactionType.�o��);
		assertEquals("�o�׃g�����U�N�V�������쐬����Ă��܂���", TransactionType.�o��.name(), transaction.getTransactionType());
		assertEquals("�o�א��Ɍ�肪����܂�", 4L, transaction.getAmount().longValue());
		// ��ʕ\���̃e�X�g
		assertEquals("��ʂ̓��א��Ɍ�肪����܂�", 4L, form.getInbound().longValue());
		assertEquals("��ʂ̎󒍐��Ɍ�肪����܂�", 5L, form.getAcceptOrder().longValue());
		assertEquals("��ʂ̏o�א��Ɍ�肪����܂�", 4L, form.getOutbound().longValue());
		assertEquals("��ʂ̒����c���Ɍ�肪����܂�", 0L, form.getRemain().longValue());
		assertEquals("��ʂ̍݌ɐ��Ɍ�肪����܂�", 12L, form.getStock().longValue());
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
		assertEquals("���ׂẴQ�[���̐��Ɍ�肪����܂�", 8, games.size());
	}

	public void test�`�[���ƃ��[����I��Ń��O�C������() throws Exception {
		form.setGameId(1L);
		form.setRoleName("���P");
		assertTrue(form.login());
		assertEquals("���P", form.getRole().getName());
		assertEquals("���}��", form.getRole().getPlayer().getName());
		// �����\���̃e�X�g
		assertEquals(2L, form.getAcceptOrder().longValue());
		assertEquals(17L, form.getStock().longValue());
		// �����݌ɂ̃e�X�g
		assertEquals(12L, form.getRole().getTransaction(TransactionType.�݌�).getAmount().longValue());
		// �ُ�n
		form = new PreGameForm();
		form.setGameId(3L);
		form.setRoleName("���[�J");
		assertFalse(form.login());
	}

	public void test�Q�[���̏I�����������() throws Exception {
		BasicService service = BasicService.getService();
		form.setGame(service.findByPK(Game.class, 5L));
		form.setOrder("10");
		form.setRole(service.findByPK(Role.class, 22L));
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
		// ���������u�����N
		Game game = service.findByPK(Game.class, 1L);
		form.setGame(game);
		form.setRole(game.getRole(RoleType.������));
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
}
