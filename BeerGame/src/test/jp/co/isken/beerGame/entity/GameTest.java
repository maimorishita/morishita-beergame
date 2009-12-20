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

	public void test参加可能なゲームのリストを取得する() throws Exception {
		List<Game> list = Game.getWaitingGameList();
		assertEquals(0, list.size());
	}

	public void testすべてのチームを取得する() throws Exception {
		List<Game> list = Game.getAll();
		assertEquals(1, list.size());
	}

	public void testGameに紐づくすべてのロールを取得する() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 1L);
		List<Role> list = Game.getRoles(game);
		assertEquals(6, list.size());
	}

	public void testチーム名からゲームを取得する() throws Exception {
		Game game = Game.getGameByName("NOAH");
		assertEquals("NOAH", game.getName());
	}

	public void testロールを取得する() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 1L);
		Role role = game.getRole("小売り");
		assertEquals("小売り", role.getName());
	}

	public void testGameCreate() throws Exception {
		Game game = Game.create("テスト", "吉岡");
		assertEquals("テスト", game.getName());
		assertEquals(RoleType.小売り.name(), game.getOwner().getName());
		assertEquals("吉岡", game.getOwner().getPlayer().getName());
		assertTrue(game.getOwner().getPlayer().isIsOwner());
	}

	public void testIsEnableToStartGame() throws Exception {
		Game game1 = BasicService.getService().findByPK(Game.class, 1L);
		assertTrue(game1.isEnableToStart());
		//TODO 2009/12/20 imai,yoshioka テストデータ修正の為一時的にコメントアウト
		//Game game2 = BasicService.getService().findByPK(Game.class, 6L);
		//assertFalse(game2.isEnableToStart());
	}
	
	public void testゲームに紐づく未使用のロールを取得する() throws Exception {
		//TODO 2009/12/20 imai,yoshioka テストデータ修正の為一時的にコメントアウト
//		Game game = BasicService.getService().findByPK(Game.class, 1L);
//		Set<RoleType> set = game.getUnusedRoles();
//		assertEquals("未使用のロールが取得できていません",3, set.size());
	}
}
