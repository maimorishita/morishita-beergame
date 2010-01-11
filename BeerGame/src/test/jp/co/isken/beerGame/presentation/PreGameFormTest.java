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

	public void test正しく遷移すること() throws Exception {
		form.setNewGame(true);
		assertTrue("ゲーム登録画面に遷移しませんでした。", form.judgeGameMode());

		form = new PreGameForm();
		form.setNewGame(false);
		assertFalse("プレイヤー登録画面に遷移しませんでした。", form.judgeGameMode());
	}

	public void testゲームを登録する() throws Exception {
		form.setTeamName("Alliance of Valiant Arms");
		form.setOwnerName("Ryoji Yoshioka");
		assertTrue(form.addGame());
		assertEquals("ゲームが取得できません。", "Alliance of Valiant Arms", form.getGame().getName());
		assertEquals("ロール名が取得できません。", "小売り", form.getRole().getName());
		assertEquals("ロールの取得数が誤っています。", 3, Game.getRoles(form.getGame()).size());

		form = new PreGameForm();
		form.setTeamName("Alliance of Valiant Arms");
		assertFalse(form.addGame());
	}

	public void testプレイヤーを登録する() throws Exception {
		BasicService service = BasicService.getService();
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		form.setPlayerName("今井");
		form.setGameId(1L);
		form.setRoleName("小売り");
		assertTrue("ゲームに登録するのに失敗しました。", form.addPlayer());
		assertEquals("取得件数が誤っています。", count + 5, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		assertEquals("ゲームが取得できません。", "NOAH", form.getGame().getName());
		assertEquals("ロールが取得できません。", "小売り", form.getRole().getName());
		assertEquals("プレイヤーが取得できません。", "今井", form.getRole().getPlayer().getName());
	}

	public void testプレイヤー登録時の検証をする() throws Exception {
		// ゲーム選択なし
		form.setGameId(0L);
		form.setPlayerName("今井智明");
		form.setRoleName("小売り");
		assertFalse("ゲームを選択しないで登録できています。", form.addPlayer());
		// プレイヤー名入力なし
		form.setGameId(1L);
		form.setPlayerName("");
		form.setRoleName(RoleType.小売り.name());
		assertFalse("プレイヤー名を入力しないで登録できています。", form.addPlayer());
		// プレイヤー名半角スペース
		form.setGameId(1L);
		form.setPlayerName(" ");
		form.setRoleName(RoleType.小売り.name());
		// プレイヤー名全角スペース
		form.setGameId(1L);
		form.setPlayerName("　");
		form.setRoleName(RoleType.小売り.name());
		assertFalse("プレイヤー名が全角スペースで登録できています。", form.addPlayer());
		// ロール名入力なし
		form.setGameId(1L);
		form.setPlayerName("今井智明");
		form.setRoleName("");
		assertFalse("ロールを選択しないで登録できています。", form.addPlayer());
	}

	public void testIsEnableToStartGame() throws Exception {
		// 待機画面からゲームの開始画面へ遷移するテスト
		BasicService service = BasicService.getService();
		Role wholeSaller = service.findByPK(Role.class, 16L);
		wholeSaller.disposeAllMessage();
		Role supplier1 = service.findByPK(Role.class, 17L);
		// TODO 2010/01/04 imai & ogasawara sendに数を渡さず、内部でTransactionから取得するように修正する
		supplier1.send(TransactionType.出荷, "4");
		Game game = service.findByPK(Game.class, 4L);
		// 正常系
		form.setGame(game);
		form.setRole(wholeSaller);
		assertTrue("ゲームが開始できていません", form.isEnableToStartGame());
		// 小売りの第１週のテスト
		TradeTransaction transaction = form.getRole().getTransaction(TransactionType.在庫);
		assertEquals("在庫トランザクションが作成されていません", TransactionType.在庫.name(), transaction.getTransactionType());
		assertEquals("在庫数に誤りがあります", 12L, transaction.getAmount().longValue());
		transaction = form.getRole().getTransaction(TransactionType.入荷);
		assertEquals("入荷トランザクションが作成されていません", TransactionType.入荷.name(), transaction.getTransactionType());
		assertEquals("受注数に誤りがあります", 4L, transaction.getAmount().longValue());
		transaction = form.getRole().getTransaction(TransactionType.受注);
		assertEquals("受注トランザクションが作成されていません", TransactionType.受注.name(), transaction.getTransactionType());
		assertEquals("受注数に誤りがあります", 5L, transaction.getAmount().longValue());
		transaction = form.getRole().getTransaction(TransactionType.出荷);
		assertEquals("出荷トランザクションが作成されていません", TransactionType.出荷.name(), transaction.getTransactionType());
		assertEquals("出荷数に誤りがあります", 4L, transaction.getAmount().longValue());
		// 画面表示のテスト
		assertEquals("画面の入荷数に誤りがあります", 4L, form.getInbound().longValue());
		assertEquals("画面の受注数に誤りがあります", 5L, form.getAcceptOrder().longValue());
		assertEquals("画面の出荷数に誤りがあります", 4L, form.getOutbound().longValue());
		assertEquals("画面の注文残数に誤りがあります", 0L, form.getRemain().longValue());
		assertEquals("画面の在庫数に誤りがあります", 12L, form.getStock().longValue());
		// 異常系
		Game game2 = service.findByPK(Game.class, 3L);
		form.setGame(game2);
		assertFalse(form.isEnableToStartGame());
	}

	public void test未選択のロールを取得する() throws Exception {
		form.setGameId(3L);
		Set<RoleType> set = form.getWaitingRoleList();
		assertEquals("未選択のロールの数に誤りがあります", 3, set.size());
	}

	public void testgetGameAll() throws Exception {
		List<Game> games = form.getGameAll();
		assertEquals("すべてのゲームの数に誤りがあります", 8, games.size());
	}

	public void testチームとロールを選んでログインする() throws Exception {
		form.setGameId(1L);
		form.setRoleName("卸１");
		assertTrue(form.login());
		assertEquals("卸１", form.getRole().getName());
		assertEquals("小笠原", form.getRole().getPlayer().getName());
		// 初期表示のテスト
		assertEquals(2L, form.getAcceptOrder().longValue());
		assertEquals(17L, form.getStock().longValue());
		// 初期在庫のテスト
		assertEquals(12L, form.getRole().getTransaction(TransactionType.在庫).getAmount().longValue());
		// 異常系
		form = new PreGameForm();
		form.setGameId(3L);
		form.setRoleName("メーカ");
		assertFalse(form.login());
	}

	public void testゲームの終了判定をする() throws Exception {
		BasicService service = BasicService.getService();
		form.setGame(service.findByPK(Game.class, 5L));
		form.setOrder("10");
		form.setRole(service.findByPK(Role.class, 22L));
		assertFalse("ゲームが終了されていません。", form.order());
	}

	public void testゲーム登録時の検証をする() throws Exception {
		form.setTeamName(" ");
		form.setOwnerName("Ryoji Yoshioka");
		assertFalse("ゲームが登録できています", form.addGame());
		assertTrue("エラーが発生していません", form.getMessage().hasError());
		form.setTeamName("Alliance of Valiant Arms");
		form.setOwnerName(" ");
		assertFalse("ゲームが登録できています", form.addGame());
		assertTrue("エラーが発生していません", form.getMessage().hasError());
	}

	public void test発注時の検証をする() throws Exception {
		BasicService service = BasicService.getService();
		// 発注数がブランク
		Game game = service.findByPK(Game.class, 1L);
		form.setGame(game);
		form.setRole(game.getRole(RoleType.小売り));
		form.setOrder("");
		form.order();
		assertTrue("エラーが発生していません", form.getMessage().hasError());
		// 発注数がマイナス
		form.setGame(game);
		form.setRole(game.getRole(RoleType.小売り));
		form.setOrder("-1");
		form.order();
		assertTrue("エラーが発生していません", form.getMessage().hasError());
	}
}
