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

	public void test³‚µ‚­‘JˆÚ‚·‚é‚±‚Æ() throws Exception {
		form.setNewGame(true);
		assertTrue("ƒQ[ƒ€“o˜^‰æ–Ê‚É‘JˆÚ‚µ‚Ü‚¹‚ñ‚Å‚µ‚½B", form.judgeGameMode());

		form = new PreGameForm();
		form.setNewGame(false);
		assertFalse("ƒvƒŒƒCƒ„[“o˜^‰æ–Ê‚É‘JˆÚ‚µ‚Ü‚¹‚ñ‚Å‚µ‚½B", form.judgeGameMode());
	}

	public void testƒQ[ƒ€‚ğ“o˜^‚·‚é() throws Exception {
		form.setTeamName("Alliance of Valiant Arms");
		form.setOwnerName("Ryoji Yoshioka");
		assertTrue(form.addGame());
		assertEquals("ƒQ[ƒ€‚ªæ“¾‚Å‚«‚Ü‚¹‚ñB","Alliance of Valiant Arms", form.getGame().getName());

		form = new PreGameForm();
		form.setTeamName("Alliance of Valiant Arms");
		assertFalse(form.addGame());
	}

	public void testƒvƒŒƒCƒ„[‚ğ“o˜^‚·‚é() throws Exception {
		form.setPlayerName("¡ˆä’q–¾");
		form.setGameId(1L);
		form.setRoleName("¬”„‚è");
		assertTrue("ƒQ[ƒ€‚É“o˜^‚·‚é‚Ì‚É¸”s‚µ‚Ü‚µ‚½B", form.addPlayer());
		assertEquals("ƒQ[ƒ€‚ªæ“¾‚Å‚«‚Ü‚¹‚ñB","ƒAƒxƒx", form.getGame().getName());
		assertEquals("ƒ[ƒ‹‚ªæ“¾‚Å‚«‚Ü‚¹‚ñB","¬”„‚è", form.getRole().getName());
		assertEquals("ƒvƒŒƒCƒ„[‚ªæ“¾‚Å‚«‚Ü‚¹‚ñB","¡ˆä’q–¾", form.getRole().getPlayer().getName());
	}
	
//	public void testƒQ[ƒ€‚ª‘I‘ğ‚³‚ê‚½Û‚Éƒ[ƒ‹‚ğæ“¾‚·‚é() throws Exception {
//		
//	}
	
	public void testIsEnableToStartGame() throws Exception{
		BasicService service = BasicService.getService();
		Role role =  service.findByPK(Role.class, 11L);
		Game game1 = service.findByPK(Game.class, 5L);
		Game game2 = service.findByPK(Game.class, 6L);
		//³íŒn
		form.setGame(game1);
		form.setRole(role);
		assertTrue(form.isEnableToStartGame());
		//¬”„‚è‚Ì‘æ‚PT‚ÌƒeƒXƒg
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), role));
		extractor.add(Condition.eq(new Property(TradeTransaction.WEEK), 1L));
		extractor.addOrder(Order.asc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		assertEquals(3, list.size());
		assertEquals("“ü‰×", list.get(0).getTransactionType());
		assertEquals(10, list.get(0).getAmount().intValue());
		assertEquals("ó’", list.get(1).getTransactionType());
		assertEquals(5, list.get(1).getAmount().intValue());
		assertEquals("o‰×", list.get(2).getTransactionType());
		assertEquals(5, list.get(2).getAmount().intValue());
		//ˆÙíŒn
		form.setGame(game2);
		assertFalse(form.isEnableToStartGame());
	}	
	
	public void test”­’”‚·‚é() throws Exception{
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
		extractor.add(Condition.eq(new Property(TradeTransaction.TRANSACTION_TYPE), "”­’"));
		List<TradeTransaction> list = BasicService.getService().findByExtractor(extractor);
		assertEquals(1, list.size());
		assertEquals(126, list.get(0).getAmount().intValue());
		
		//¬”„‚è‚Ì‘æ2T‚ÌƒeƒXƒg
		extractor = new Extractor(TradeTransaction.class);
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), role));
		extractor.add(Condition.eq(new Property(TradeTransaction.WEEK), 2L));
		extractor.addOrder(Order.asc(new Property(TradeTransaction.ID)));
		list = service.findByExtractor(extractor);
		assertEquals(3, list.size());
		assertEquals("“ü‰×", list.get(0).getTransactionType());
		assertEquals(10, list.get(0).getAmount().intValue());
		assertEquals("ó’", list.get(1).getTransactionType());
		assertEquals(5, list.get(1).getAmount().intValue());
		assertEquals("o‰×", list.get(2).getTransactionType());
		assertEquals(5, list.get(2).getAmount().intValue());
	}

}

		
		
	
