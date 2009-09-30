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

	public void testêVãKÉQÅ[ÉÄÇí«â¡Ç∑ÇÈ() {
		form.setTeamName("Alliance of Valiant Arms");
		form.setOwnerName("Ryoji Yoshioka");
		assertTrue(form.addGame());
		
		//form = new PreGameForm();
		//form.setTeamName("Alliance of Valiant Arms");
		//form.setOwnerName("Ryoji Yoshioka");
		//assertFalse(form.addGame());
	}
}
