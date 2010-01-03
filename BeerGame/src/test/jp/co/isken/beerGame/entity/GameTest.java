package jp.co.isken.beerGame.entity;

import java.util.List;
import java.util.Set;

import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class GameTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	// TODO 2010/01/03 yoshioka RoleType�ɏC������B������Enum�ɂ������Ȃ��B
	public void test�Q���\�ȃQ�[���̃��X�g���擾����() throws Exception {
		List<Game> list = Game.getWaitingGameList();
		assertEquals("�Q���\�ȃQ�[���̐��Ɍ�肪����܂�", 1, list.size());
	}

	public void test���ׂẴ`�[�����擾����() throws Exception {
		List<Game> list = Game.getAll();
		assertEquals("���ׂẴ`�[���̐��Ɍ�肪����܂�", 6, list.size());
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
		Game game2 = BasicService.getService().findByPK(Game.class, 3L);
		assertFalse(game2.isEnableToStart());
	}

	public void test�Q�[���ɕR�Â����g�p�̃��[�����擾����() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 3L);
		Set<RoleType> set = game.getUnusedRoles();
		assertEquals("���g�p�̃��[�����擾�ł��Ă��܂���", 3, set.size());
	}

	public void test�Q�[���̏I�����������() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 1L);
		assertTrue("�Q�[�����I�����Ă��܂���", game.IsGameOver(37L));
		assertFalse("�Q�[�����I�����Ă��܂��܂����B", game.IsGameOver(game.getRole(
				RoleType.������.name()).getCurrentWeek(TransactionType.����.name())));
	}
}
