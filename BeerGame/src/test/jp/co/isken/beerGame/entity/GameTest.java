package jp.co.isken.beerGame.entity;

import java.util.List;

import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class GameTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void test�Q���\�ȃQ�[���̃��X�g���擾����() throws Exception {
		List<Game> list = Game.getWaitingGameList();
		assertEquals(0, list.size());
	}

	public void test���ׂẴ`�[�����擾����() throws Exception {
		List<Game> list = Game.getAll();
		assertEquals(1, list.size());
	}

	public void testGame�ɕR�Â����ׂẴ��[�����擾����() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 1L);
		List<Role> list = Game.getRoles(game);
		assertEquals(6, list.size());
	}

	public void test�`�[��������Q�[�����擾����() throws Exception {
		Game game = Game.getGameByName("NOAH");
		assertEquals("NOAH", game.getName());
	}

	public void test���[�����擾����() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 1L);
		Role role = game.getRole("������");
		assertEquals("������", role.getName());
	}

	public void testGameCreate() throws Exception {
		Game game = Game.create("�e�X�g", "�g��");
		assertEquals("�e�X�g", game.getName());
		assertEquals(RoleType.������.name(), game.getOwner().getName());
		assertEquals("�g��", game.getOwner().getPlayer().getName());
		assertTrue(game.getOwner().getPlayer().isIsOwner());
	}

	public void testIsEnableToStartGame() throws Exception {
		Game game1 = BasicService.getService().findByPK(Game.class, 1L);
		assertTrue(game1.isEnableToStart());
		//TODO 2009/12/20 imai,yoshioka �e�X�g�f�[�^�C���̈׈ꎞ�I�ɃR�����g�A�E�g
		//Game game2 = BasicService.getService().findByPK(Game.class, 6L);
		//assertFalse(game2.isEnableToStart());
	}
	
	public void test�Q�[���ɕR�Â����g�p�̃��[�����擾����() throws Exception {
		//TODO 2009/12/20 imai,yoshioka �e�X�g�f�[�^�C���̈׈ꎞ�I�ɃR�����g�A�E�g
//		Game game = BasicService.getService().findByPK(Game.class, 1L);
//		Set<RoleType> set = game.getUnusedRoles();
//		assertEquals("���g�p�̃��[�����擾�ł��Ă��܂���",3, set.size());
	}
}
