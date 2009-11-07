package jp.co.isken.beerGame.presentation;

import java.util.List;

import jp.co.isken.beerGame.entity.BusinessMasterLoader;
import jp.co.isken.beerGame.entity.Game;
import jp.co.isken.beerGame.entity.MasterLoader;
import jp.co.isken.beerGame.entity.NumberingLoader;
import jp.co.isken.beerGame.entity.Role;
import jp.co.isken.beerGame.entity.TradeTransaction;
import jp.co.isken.beerGame.entity.TransactionLoader;
import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;
import jp.rough_diamond.framework.transaction.VersionUnmuchException;
import junit.framework.TestCase;

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
		assertEquals("�Q�[�����擾�ł��܂���B","Alliance of Valiant Arms", form.getGame().getName());

		form = new PreGameForm();
		form.setTeamName("Alliance of Valiant Arms");
		assertFalse(form.addGame());
	}

	public void test�v���C���[��o�^����() throws Exception {
		form.setPlayerName("����q��");
		form.setGameId(1L);
		form.setRoleName("������");
		assertTrue("�Q�[���ɓo�^����̂Ɏ��s���܂����B", form.addPlayer());
		assertEquals("�Q�[�����擾�ł��܂���B","�A�x�x", form.getGame().getName());
		assertEquals("���[�����擾�ł��܂���B","������", form.getRole().getName());
		assertEquals("�v���C���[���擾�ł��܂���B","����q��", form.getRole().getPlayer().getName());
	}
	
//	public void test�Q�[�����I�����ꂽ�ۂɃ��[�����擾����() throws Exception {
//		
//	}
	/**
	 * �ҋ@��ʂ���Q�[���̊J�n��ʂ֑J�ڂ���e�X�g
	 */
	public void testIsEnableToStartGame() throws Exception{
		BasicService service = BasicService.getService();
		Role role =  service.findByPK(Role.class, 11L);
		Game game1 = service.findByPK(Game.class, 5L);
		Game game2 = service.findByPK(Game.class, 6L);
		//����n
		form.setGame(game1);
		form.setRole(role);
		assertTrue(form.isEnableToStartGame());
		//������̑�P�T�̃e�X�g
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), role));
		extractor.add(Condition.eq(new Property(TradeTransaction.WEEK), 0L));
		extractor.addOrder(Order.asc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		assertEquals(2, list.size());
		assertEquals("�݌�", list.get(0).getTransactionType());
		assertEquals(12, list.get(0).getAmount().intValue());
		assertEquals("����", list.get(1).getTransactionType());
		assertEquals(4, list.get(1).getAmount().intValue());
		//��ʕ\���̃e�X�g
		assertEquals(0, form.getInbound().intValue());
		assertEquals(4, form.getAcceptOrder().intValue());
		assertEquals(0, form.getOutbound().intValue());
		assertEquals(0, form.getRemain().intValue());
		assertEquals(12, form.getStock().intValue());
		//�ُ�n
		form.setGame(game2);
		assertFalse(form.isEnableToStartGame());
	}	
	
	public void test�����ݒ���s��() throws Exception{
		BasicService service = BasicService.getService();
		Role role =  service.findByPK(Role.class, 11L);
		Game game1 = service.findByPK(Game.class, 5L);
		//Game game2 = service.findByPK(Game.class, 6L);
		//����n
		form.setGame(game1);
		form.setRole(role);
		form.isEnableToStartGame();
		
		//�����\���̃e�X�g
		assertEquals(4L, form.getAcceptOrder().longValue());
		assertEquals(12L, form.getStock().longValue());
		
		//�����݌ɂ̃e�X�g
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), role));
		extractor.add(Condition.eq(new Property(TradeTransaction.TRANSACTION_TYPE), "�݌�"));
		extractor.addOrder(Order.asc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		assertEquals(12L, list.get(0).getAmount().longValue());
	}
	
	public void test���ڂ̔��������e�X�g() throws Exception{
		BasicService service = BasicService.getService();
		Role role =  service.findByPK(Role.class, 11L);
		Game game1 = service.findByPK(Game.class, 5L);
		form.setGame(game1);
		form.setRole(role);
		form.isEnableToStartGame();
		form.setOrder("126");
		form.order();
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), role));
		extractor.add(Condition.eq(new Property(TradeTransaction.WEEK), 1L));
		extractor.add(Condition.eq(new Property(TradeTransaction.TRANSACTION_TYPE), "����"));
		List<TradeTransaction> list = BasicService.getService().findByExtractor(extractor);
		assertEquals(1, list.size());
		assertEquals(126, list.get(0).getAmount().intValue());
		
		//������̑�2�T�̃e�X�g
		extractor = new Extractor(TradeTransaction.class);
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), role));
		extractor.add(Condition.eq(new Property(TradeTransaction.WEEK), 1L));
		extractor.addOrder(Order.asc(new Property(TradeTransaction.ID)));
		list = service.findByExtractor(extractor);
		assertEquals(4, list.size());
		assertEquals("����", list.get(0).getTransactionType());
		assertEquals(126, list.get(0).getAmount().intValue());
		assertEquals("����", list.get(1).getTransactionType());
		assertEquals(10, list.get(1).getAmount().intValue());
		assertEquals("��", list.get(2).getTransactionType());
		assertEquals(8, list.get(2).getAmount().intValue());
		assertEquals("�o��", list.get(3).getTransactionType());
		assertEquals(5, list.get(3).getAmount().intValue());
		//��ʕ\���̃e�X�g
		assertEquals(10, form.getInbound().intValue());
		assertEquals(8, form.getAcceptOrder().intValue());
		assertEquals(5, form.getOutbound().intValue());
		assertEquals(3, form.getRemain().intValue());
		assertEquals(3, form.getRemain().intValue());
	}
	
	public void test�����̏����̃e�X�g() throws VersionUnmuchException, MessagesIncludingException{
		BasicService service = BasicService.getService();
		Role role =  service.findByPK(Role.class, 11L);
		Game game1 = service.findByPK(Game.class, 5L);
		form.setGame(game1);
		form.setRole(role);
		form.setOrder("126");
		form.orderSet();
		//������̑�P�T�̃e�X�g
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), role));
		extractor.add(Condition.eq(new Property(TradeTransaction.WEEK), 1L));
		extractor.addOrder(Order.asc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		assertEquals(3, list.size());
		assertEquals("����", list.get(0).getTransactionType());
		assertEquals(10, list.get(0).getAmount().intValue());
		assertEquals("��", list.get(1).getTransactionType());
		assertEquals(8, list.get(1).getAmount().intValue());
		assertEquals("�o��", list.get(2).getTransactionType());
		assertEquals(5, list.get(2).getAmount().intValue());
		//��ʕ\���̃e�X�g
		assertEquals(10, form.getInbound().intValue());
		assertEquals(8, form.getAcceptOrder().intValue());
		assertEquals(5, form.getOutbound().intValue());
		assertEquals(3, form.getRemain().intValue());
	}
	
}
