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
		// 正常系
		PreGameForm form = new PreGameForm();
		form.setTeamName("北海道");
		form.setOwnerName("鈴木太郎");
		assertTrue("失敗しました。", form.addGame());
		// 異常系（重複）
		form = new PreGameForm();
		form.setTeamName("北海道");
		form.setOwnerName("鈴木志郎");
		assertFalse("成功してしまいました。", form.addGame());
	}

	public void testActiveMq() {
		// Activemq/bin直下のbatファイルを先にたたくこと
		//【D:\workspace\BeerGame\webapp\WEB-INF\lib\apache-activemq-5.2.01\bin\activemq.bat】
//		Sender.main(null);
//		Receiver.main(null);
	}

}
