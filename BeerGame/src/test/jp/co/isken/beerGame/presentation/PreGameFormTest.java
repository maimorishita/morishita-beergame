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

	public void test�Q�[����o�^����() {
		form.setTeamName("Alliance of Valiant Arms");
		form.setOwnerName("Ryoji Yoshioka");
		assertTrue(form.addGame());
		
		form = new PreGameForm();
		form.setTeamName("Alliance of Valiant Arms");
		assertFalse(form.addGame());
	}
	
	public void test�v���C���[��o�^����() throws Exception {
		form.setPlayerName("����q��");
		assertTrue("�Q�[���ɓo�^����̂Ɏ��s���܂����B", form.addPlayer());
	}
}
