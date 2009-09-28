package jp.co.isken.beerGame.presentation;

import jp.co.isken.beerGame.entity.BusinessMasterLoader;
import jp.co.isken.beerGame.entity.MasterLoader;
import jp.co.isken.beerGame.entity.NumberingLoader;
import jp.co.isken.beerGame.entity.TransactionLoader;
import junit.framework.TestCase;

public class PreGameFormTest extends TestCase {

	protected void setUp() throws Exception {
		MasterLoader.init();
		BusinessMasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void testAddGame() {
		// ����n
		PreGameForm form = new PreGameForm();
		form.setTeamName("�k�C��");
		form.setOwnerName("��ؑ��Y");
		assertTrue("���s���܂����B", form.addGame());
		// �ُ�n�i�d���j
		form = new PreGameForm();
		form.setTeamName("�k�C��");
		form.setOwnerName("��؎u�Y");
		assertFalse("�������Ă��܂��܂����B", form.addGame());
	}

	public void testActiveMq() {
		// Activemq/bin������bat�t�@�C�����ɂ���������
		//�yD:\workspace\BeerGame\webapp\WEB-INF\lib\apache-activemq-5.2.01\bin\activemq.bat�z
//		Sender.main(null);
//		Receiver.main(null);
	}

}
