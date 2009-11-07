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
		assertEquals("ゲームが取得できません。","Alliance of Valiant Arms", form.getGame().getName());

		form = new PreGameForm();
		form.setTeamName("Alliance of Valiant Arms");
		assertFalse(form.addGame());
	}

	public void testプレイヤーを登録する() throws Exception {
		form.setPlayerName("今井智明");
		form.setGameId(1L);
		form.setRoleName("小売り");
		assertTrue("ゲームに登録するのに失敗しました。", form.addPlayer());
		assertEquals("ゲームが取得できません。","アベベ", form.getGame().getName());
		assertEquals("ロールが取得できません。","小売り", form.getRole().getName());
		assertEquals("プレイヤーが取得できません。","今井智明", form.getRole().getPlayer().getName());
	}
	
//	public void testゲームが選択された際にロールを取得する() throws Exception {
//		
//	}
	/**
	 * 待機画面からゲームの開始画面へ遷移するテスト
	 */
	public void testIsEnableToStartGame() throws Exception{
		BasicService service = BasicService.getService();
		Role role =  service.findByPK(Role.class, 11L);
		Game game1 = service.findByPK(Game.class, 5L);
		Game game2 = service.findByPK(Game.class, 6L);
		//正常系
		form.setGame(game1);
		form.setRole(role);
		assertTrue(form.isEnableToStartGame());
		//小売りの第１週のテスト
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), role));
		extractor.add(Condition.eq(new Property(TradeTransaction.WEEK), 0L));
		extractor.addOrder(Order.asc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		assertEquals(2, list.size());
		assertEquals("在庫", list.get(0).getTransactionType());
		assertEquals(12, list.get(0).getAmount().intValue());
		assertEquals("発注", list.get(1).getTransactionType());
		assertEquals(4, list.get(1).getAmount().intValue());
		//画面表示のテスト
		assertEquals(0, form.getInbound().intValue());
		assertEquals(4, form.getAcceptOrder().intValue());
		assertEquals(0, form.getOutbound().intValue());
		assertEquals(0, form.getRemain().intValue());
		assertEquals(12, form.getStock().intValue());
		//異常系
		form.setGame(game2);
		assertFalse(form.isEnableToStartGame());
	}	
	
	public void test初期設定を行う() throws Exception{
		BasicService service = BasicService.getService();
		Role role =  service.findByPK(Role.class, 11L);
		Game game1 = service.findByPK(Game.class, 5L);
		//Game game2 = service.findByPK(Game.class, 6L);
		//正常系
		form.setGame(game1);
		form.setRole(role);
		form.isEnableToStartGame();
		
		//初期表示のテスト
		assertEquals(4L, form.getAcceptOrder().longValue());
		assertEquals(12L, form.getStock().longValue());
		
		//初期在庫のテスト
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), role));
		extractor.add(Condition.eq(new Property(TradeTransaction.TRANSACTION_TYPE), "在庫"));
		extractor.addOrder(Order.asc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		assertEquals(12L, list.get(0).getAmount().longValue());
	}
	
	public void test一回目の発注処理テスト() throws Exception{
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
		extractor.add(Condition.eq(new Property(TradeTransaction.TRANSACTION_TYPE), "発注"));
		List<TradeTransaction> list = BasicService.getService().findByExtractor(extractor);
		assertEquals(1, list.size());
		assertEquals(126, list.get(0).getAmount().intValue());
		
		//小売りの第2週のテスト
		extractor = new Extractor(TradeTransaction.class);
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), role));
		extractor.add(Condition.eq(new Property(TradeTransaction.WEEK), 1L));
		extractor.addOrder(Order.asc(new Property(TradeTransaction.ID)));
		list = service.findByExtractor(extractor);
		assertEquals(4, list.size());
		assertEquals("発注", list.get(0).getTransactionType());
		assertEquals(126, list.get(0).getAmount().intValue());
		assertEquals("入荷", list.get(1).getTransactionType());
		assertEquals(10, list.get(1).getAmount().intValue());
		assertEquals("受注", list.get(2).getTransactionType());
		assertEquals(8, list.get(2).getAmount().intValue());
		assertEquals("出荷", list.get(3).getTransactionType());
		assertEquals(5, list.get(3).getAmount().intValue());
		//画面表示のテスト
		assertEquals(10, form.getInbound().intValue());
		assertEquals(8, form.getAcceptOrder().intValue());
		assertEquals(5, form.getOutbound().intValue());
		assertEquals(3, form.getRemain().intValue());
		assertEquals(3, form.getRemain().intValue());
	}
	
	public void test発注の処理のテスト() throws VersionUnmuchException, MessagesIncludingException{
		BasicService service = BasicService.getService();
		Role role =  service.findByPK(Role.class, 11L);
		Game game1 = service.findByPK(Game.class, 5L);
		form.setGame(game1);
		form.setRole(role);
		form.setOrder("126");
		form.orderSet();
		//小売りの第１週のテスト
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), role));
		extractor.add(Condition.eq(new Property(TradeTransaction.WEEK), 1L));
		extractor.addOrder(Order.asc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		assertEquals(3, list.size());
		assertEquals("入荷", list.get(0).getTransactionType());
		assertEquals(10, list.get(0).getAmount().intValue());
		assertEquals("受注", list.get(1).getTransactionType());
		assertEquals(8, list.get(1).getAmount().intValue());
		assertEquals("出荷", list.get(2).getTransactionType());
		assertEquals(5, list.get(2).getAmount().intValue());
		//画面表示のテスト
		assertEquals(10, form.getInbound().intValue());
		assertEquals(8, form.getAcceptOrder().intValue());
		assertEquals(5, form.getOutbound().intValue());
		assertEquals(3, form.getRemain().intValue());
	}
	
}
