package jp.co.isken.beerGame.entity;

import java.util.List;
import java.util.Map;
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

	// TODO 2010/01/03 yoshioka RoleTypeに修正する。引数もEnumにしたいなぁ。
	public void test参加可能なゲームのリストを取得する() throws Exception {
		List<Game> list = Game.getWaitingGameList();
		assertEquals("参加可能なゲームの数に誤りがあります", 1, list.size());
	}

	public void testすべてのチームを取得する() throws Exception {
		List<Game> list = Game.getAll();
		assertEquals("すべてのチームの数に誤りがあります", 10, list.size());
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
		Game game2 = BasicService.getService().findByPK(Game.class, 3L);
		assertFalse(game2.isEnableToStart());
	}

	public void testゲームに紐づく未使用のロールを取得する() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 3L);
		Set<RoleType> set = game.getUnusedRoles();
		assertEquals("未使用のロールが取得できていません", 3, set.size());
	}

	public void testゲームの終了判定をする() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 1L);
		assertTrue("ゲームが終了していません", game.IsGameOver(38L));
		assertFalse("ゲームが終了しています", game.IsGameOver(37L));
		assertFalse("ゲームが終了してしまいました。", game.IsGameOver(game.getRole(RoleType.小売り.name()).getCurrentWeek(TransactionType.発注.name())));
	}
	
	public void test累計発注残数を取得する() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 1L);
		assertEquals("累計発注残数が誤っています", -10L, game.getRemain().longValue());
	}

	public void testゲーム単位で週ごとの発注残を取得する() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 10L);
		long[] weeklyRemain = game.getWeeklyRemain();
		assertEquals("発注残が誤っています", 38, weeklyRemain.length);
		assertEquals("発注残が誤っています", 0L, weeklyRemain[0]);
		assertEquals("発注残が誤っています", 0L, weeklyRemain[1]);
		assertEquals("発注残が誤っています", -10L, weeklyRemain[5]);
	}

	public void test累計在庫数を取得する() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 1L);
		assertEquals("累計在庫数が誤っています", 274L, game.getStock().longValue());
	}
	
	public void testゲーム単位で週ごとの在庫数を取得する() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 10L);
		long[] weeklyStock = game.getWeeklyStock();
		assertEquals("在庫数が誤っています", 38, weeklyStock.length);
		assertEquals("在庫数が誤っています", 48L, weeklyStock[0]);
		assertEquals("在庫数が誤っています", 48L, weeklyStock[1]);
		assertEquals("在庫数が誤っています", 50L, weeklyStock[5]);
		assertEquals("在庫数が誤っています", 186L, weeklyStock[36]);
		assertEquals("在庫数が誤っています", 185L, weeklyStock[37]);
	}
	
	public void testGetBalance() throws Exception {
		Game game = BasicService.getService().findByPK(Game.class, 10L);
		Map<RoleType, long[]> balance = game.getBalance();
		assertEquals("ロールの数に誤りがあります", 4, balance.keySet().size());
		assertEquals("週の数に誤りがあります", 38, balance.get(RoleType.小売り).length);
		assertEquals("週の数に誤りがあります", 38, balance.get(RoleType.卸１).length);
		assertEquals("週の数に誤りがあります", 38, balance.get(RoleType.卸２).length);
		assertEquals("週の数に誤りがあります", 38, balance.get(RoleType.メーカ).length);
	}
}
