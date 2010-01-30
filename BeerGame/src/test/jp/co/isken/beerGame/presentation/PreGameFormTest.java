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
		assertEquals("受注数に誤りがあります", 4L, transaction.getAmount().longValue());
		transaction = form.getRole().getTransaction(TransactionType.出荷);
		assertEquals("出荷トランザクションが作成されていません", TransactionType.出荷.name(), transaction.getTransactionType());
		assertEquals("出荷数に誤りがあります", 4L, transaction.getAmount().longValue());
		// 画面表示のテスト
		assertEquals("画面の入荷数に誤りがあります", 4L, form.getInbound().longValue());
		assertEquals("画面の受注数に誤りがあります", 4L, form.getAcceptOrder().longValue());
		assertEquals("画面の出荷数に誤りがあります", 4L, form.getOutbound().longValue());
		assertEquals("画面の注残数に誤りがあります", 12L, form.getRemain().longValue());
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
		assertEquals("すべてのゲームの数に誤りがあります", 10, games.size());
	}

	public void testチームとロールを選んでログインする() throws Exception {
		form.setGameId(1L);
		form.setRoleName("卸１");
		assertTrue(form.login());
		assertEquals("卸１", form.getRole().getName());
		assertEquals("小笠原", form.getRole().getPlayer().getName());
		// 初期表示のテスト
		assertEquals(20L, form.getInbound().longValue());
		assertEquals(26L, form.getAcceptOrder().longValue());
		assertEquals(22L, form.getOutbound().longValue());
		assertEquals(-4L, form.getRemain().longValue());
		// 異常系  ロールが作成されていないのに、ログインしようとした場合
		form = new PreGameForm();
		form.setGameId(3L);
		form.setRoleName("メーカ");
		assertFalse(form.login());
		assertTrue("エラーが発生していません", form.getMessage().hasError());
		assertEquals("エラーメッセージが誤っています。", "errors.invalid.login", 
				form.getMessage().get("").get(0).getKey());
		// 異常系  ４つのロールが揃っていないときに、ログインしようとした場合
		form = new PreGameForm();
		form.setGameId(3L);
		form.setRoleName("小売り");
		assertFalse(form.login());
		assertTrue("エラーが発生していません", form.getMessage().hasError());
		assertEquals("エラーメッセージが誤っています。", "errors.invalid.start", 
				form.getMessage().get("").get(0).getKey());
	}

	public void testゲームの終了判定をする() throws Exception {
		BasicService service = BasicService.getService();
		// 初期処理
		Role wholeSeller = service.findByPK(Role.class, 22L);
		wholeSeller.disposeAllMessage();
		wholeSeller.getUpper().send(TransactionType.出荷, "1");
		// 本処理
		form.setGame(service.findByPK(Game.class, 5L));
		form.setOrder("10");
		form.setRole(wholeSeller);
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
		// 初期処理
		Game game = service.findByPK(Game.class, 1L);
		Role wholeSeller = game.getRole(RoleType.小売り);
		wholeSeller.disposeAllMessage();
		wholeSeller.getUpper().send(TransactionType.出荷, "1");
		// 本処理
		// 発注数がブランク
		form.setGame(game);
		form.setRole(wholeSeller);
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
	
	public void test発注を行う() throws Exception {
		BasicService service = BasicService.getService();
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		Game game = service.findByPK(Game.class, 1L);
		Role wholeSeller = game.getRole(RoleType.小売り);
		wholeSeller.disposeAllMessage();
		wholeSeller.getUpper().send(TransactionType.出荷, "1");
		form.setGame(game);
		form.setRole(wholeSeller);
		form.setOrder("10");
		form.order();
		Extractor e = new Extractor(TradeTransaction.class);
		e.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> transactions = service.findByExtractor(e);
		assertEquals("取得数が誤っています", count + 4, transactions.size());
		assertEquals("取引の種類が誤っています", TransactionType.発注.name(), transactions.get(0).getTransactionType());
		assertEquals("取引の値が誤っています", 10L, transactions.get(0).getAmount().longValue());
		assertEquals("取引の種類が誤っています", TransactionType.出荷.name(), transactions.get(1).getTransactionType());
		assertEquals("取引の値が誤っています", 4L, transactions.get(1).getAmount().longValue());
		assertEquals("取引の種類が誤っています", TransactionType.受注.name(), transactions.get(2).getTransactionType());
		assertEquals("取引の値が誤っています", 4L, transactions.get(2).getAmount().longValue());
		assertEquals("取引の種類が誤っています", TransactionType.入荷.name(), transactions.get(3).getTransactionType());
		assertEquals("取引の値が誤っています", 1L, transactions.get(3).getAmount().longValue());
	}
	
	public void test初期状態が正しく作成されているか確認する() throws Exception {
		//TODO 2010/01/19 yoshioka テスト作るか悩み中
		assertTrue(true);
	}
	
	public void test途中結果表示用にOwnerかどうか判定する(){
		BasicService service = BasicService.getService();
		Game game = service.findByPK(Game.class, 1L);
		Role wholeSeller = game.getRole(RoleType.小売り);
		form.setRole(wholeSeller);
		assertTrue("Ownerとして判定されていません。",form.isOwner());
		Role maker = game.getRole(RoleType.メーカ);
		form.setRole(maker);
		assertFalse("Ownerとして判定されてしまっています。",form.isOwner());
	}
}
