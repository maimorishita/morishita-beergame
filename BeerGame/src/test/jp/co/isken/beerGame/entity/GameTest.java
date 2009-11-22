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

	public void test�Q���\�ȃQ�[���̃��X�g���擾����() throws Exception {
		List<Game> list = Game.getWaitingGameList();
		assertEquals(3, list.size());
	}

	public void test���ׂẴ`�[�����擾����() throws Exception {
		List<Game> list = Game.getAll();
		assertEquals(6, list.size());
	}

	public void testGame�ɕR�Â����ׂẴ��[�����擾����() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 3L);
		List<Role> list = Game.getRoles(game);
		assertEquals(4, list.size());
	}

	public void test�`�[��������Q�[�����擾����() throws Exception {
		Game game = Game.getGameByName("�A�x�x");
		assertEquals("�A�x�x", game.getName());
	}

	public void test���[�����擾����() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 4L);
		Role role = game.getRole("������");
		assertEquals("������", role.getName());
	}

	public void testGameCreate() throws Exception {
		Game g = Game.create("�e�X�g");
		assertEquals("�e�X�g", g.getName());
	}

	public void testIsEnableToStartGame() throws Exception {
		Game game1 = BasicService.getService().findByPK(Game.class, 5L);
		Game game2 = BasicService.getService().findByPK(Game.class, 6L);
		assertTrue(game1.isEnableToStart());
		assertFalse(game2.isEnableToStart());
	}
	
	public void test�Q�[���ɕR�Â����g�p�̃��[�����擾����() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 2L);
		Set<RoleType> set = game.getUnusedRoles();
		
		for (RoleType roleType : set) {
			System.out.println(roleType.name()); 
		}
		
		assertEquals("���g�p�̃��[�����擾�ł��Ă��܂���",3, set.size());
	}
}
