package jp.co.isken.beerGame.presentation;

import jp.co.isken.beerGame.entity.BusinessMasterLoader;
import jp.co.isken.beerGame.entity.MasterLoader;
import jp.co.isken.beerGame.entity.NumberingLoader;
import jp.co.isken.beerGame.entity.TransactionLoader;
import jp.co.isken.beerGame.mq.Receiver;
import jp.co.isken.beerGame.mq.Sender;
import junit.framework.TestCase;

public class PreGameFormTest extends TestCase {

	protected void setUp() throws Exception {
		MasterLoader.init();
		BusinessMasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void testAddGame() {
		// ³íŒn
		PreGameForm form = new PreGameForm();
		form.setTeamName("–kŠC“¹");
		form.setOwnerName("—é–Ø‘¾˜Y");
		assertTrue("¸”s‚µ‚Ü‚µ‚½B", form.addGame());
		// ˆÙíŒnid•¡j
		form = new PreGameForm();
		form.setTeamName("–kŠC“¹");
		form.setOwnerName("—é–Øu˜Y");
		assertFalse("¬Œ÷‚µ‚Ä‚µ‚Ü‚¢‚Ü‚µ‚½B", form.addGame());
	}

	public void testActiveMq() {
		// Activemq/bin’¼‰º‚Ìbatƒtƒ@ƒCƒ‹‚ğæ‚É‚½‚½‚­‚±‚Æ
		//yD:\workspace\BeerGame\webapp\WEB-INF\lib\apache-activemq-5.2.01\bin\activemq.batz
//		Sender.main(null);
//		Receiver.main(null);
	}

}
