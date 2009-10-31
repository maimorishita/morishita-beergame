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
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;
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
		extractor.add(Condition.eq(new Property(TradeTransaction.WEEK), 1L));
		extractor.addOrder(Order.asc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		assertEquals(3, list.size());
		assertEquals("入荷", list.get(0).getTransactionType());
		assertEquals(10, list.get(0).getAmount().intValue());
		assertEquals("受注", list.get(1).getTransactionType());
		assertEquals(5, list.get(1).getAmount().intValue());
		assertEquals("出荷", list.get(2).getTransactionType());
		assertEquals(5, list.get(2).getAmount().intValue());
		//異常系
		form.setGame(game2);
		assertFalse(form.isEnableToStartGame());
	}	
	
	public void test発注数する() throws Exception{
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
		extractor.add(Condition.eq(new Property(TradeTransaction.WEEK), 2L));
		extractor.addOrder(Order.asc(new Property(TradeTransaction.ID)));
		list = service.findByExtractor(extractor);
		assertEquals(3, list.size());
		assertEquals("入荷", list.get(0).getTransactionType());
		assertEquals(10, list.get(0).getAmount().intValue());
		assertEquals("受注", list.get(1).getTransactionType());
		assertEquals(5, list.get(1).getAmount().intValue());
		assertEquals("出荷", list.get(2).getTransactionType());
		assertEquals(5, list.get(2).getAmount().intValue());
	}

}

		
		
	
