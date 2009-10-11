package jp.co.isken.beerGame.presentation;

import jp.co.isken.beerGame.entity.BusinessMasterLoader;
import jp.co.isken.beerGame.entity.MasterLoader;
import jp.co.isken.beerGame.entity.NumberingLoader;
import jp.co.isken.beerGame.entity.TransactionLoader;
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

		form = new PreGameForm();
		form.setTeamName("Alliance of Valiant Arms");
		assertFalse(form.addGame());
	}

	public void testƒvƒŒƒCƒ„[‚ğ“o˜^‚·‚é() throws Exception {
		form.setPlayerName("¡ˆä’q–¾");
		form.setGameId(1L);
		form.setRoleName("¬”„‚è");
		assertTrue("ƒQ[ƒ€‚É“o˜^‚·‚é‚Ì‚É¸”s‚µ‚Ü‚µ‚½B", form.addPlayer());
	}
	
	public void testƒQ[ƒ€‚ª‘I‘ğ‚³‚ê‚½Û‚Éƒ[ƒ‹‚ğæ“¾‚·‚é() throws Exception {
		
	}
}
