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

	public void test参加可能なゲームのリストを取得する() throws Exception {
		List<Game> list = Game.getWaitingGameList();
		assertEquals(3, list.size());
	}

	public void testすべてのチームを取得する() throws Exception {
		List<Game> list = Game.getAll();
		assertEquals(6, list.size());
	}

	public void testGameに紐づくすべてのロールを取得する() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 3L);
		List<Role> list = Game.getRoles(game);
		assertEquals(4, list.size());
	}

	public void testチーム名からゲームを取得する() throws Exception {
		Game game = Game.getGameByName("アベベ");
		assertEquals("アベベ", game.getName());
	}

	public void testロールを取得する() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 4L);
		Role role = game.getRole("小売り");
		assertEquals("小売り", role.getName());
	}

	public void testGameCreate() throws Exception {
		Game g = Game.create("テスト");
		assertEquals("テスト", g.getName());
	}

	public void testIsEnableToStartGame() throws Exception {
		Game game1 = BasicService.getService().findByPK(Game.class, 5L);
		Game game2 = BasicService.getService().findByPK(Game.class, 6L);
		assertTrue(game1.isEnableToStart());
		assertFalse(game2.isEnableToStart());
	}
	
	public void testゲームに紐づく未使用のロールを取得する() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 2L);
		Set<RoleType> set = game.getUnusedRoles();
		
		for (RoleType roleType : set) {
			System.out.println(roleType.name()); 
		}
		
		assertEquals("未使用のロールが取得できていません",3, set.size());
	}
}
