package jp.co.isken.beerGame.presentation;

import jp.co.isken.beerGame.entity.BusinessMasterLoader;
import jp.co.isken.beerGame.entity.MasterLoader;
import jp.co.isken.beerGame.entity.NumberingLoader;
import jp.co.isken.beerGame.entity.TransactionLoader;
import junit.framework.TestCase;

public class PreGameFormTest extends TestCase {

	private PreGameForm form;
	
	protected void setUp() throws Exception {
		MasterLoader.init();
		BusinessMasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
		form = new PreGameForm();
	}

	public void testƒQ[ƒ€‚ğ“o˜^‚·‚é() {
		form.setTeamName("Alliance of Valiant Arms");
		form.setOwnerName("Ryoji Yoshioka");
		assertTrue(form.addGame());
		
		form = new PreGameForm();
		form.setTeamName("Alliance of Valiant Arms");
		assertFalse(form.addGame());
	}
	
	public void testƒvƒŒƒCƒ„[‚ğ“o˜^‚·‚é() throws Exception {
		form.setPlayerName("¡ˆä’q–¾");
		assertTrue("ƒQ[ƒ€‚É“o˜^‚·‚é‚Ì‚É¸”s‚µ‚Ü‚µ‚½B", form.addPlayer());
	}
}
