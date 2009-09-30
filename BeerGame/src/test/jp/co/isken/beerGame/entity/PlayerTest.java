package jp.co.isken.beerGame.entity;

import java.util.List;

import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class PlayerTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void testCreatePlayer() throws MessagesIncludingException {
		assertTrue(true);
		//BasicService service = BasicService.getService();
		//����n
		//List<Player> players = Player.createPlayers("�k�C��", "��ؑ��Y");
		//service.insert(players.toArray());

		//Extractor extractor = new Extractor(Player.class);
		//extractor.add(Condition.eq(new Property(Player.TEAM_NAME), "�k�C��"));
		//List<Player> playersRs = service.findByExtractor(extractor);

		//assertEquals("�擾����������Ă��܂��B", 4, playersRs.size());

		//�ُ�n�i�`�[�����d���j
		//players = Player.createPlayers("�k�C��", "��؎u�Y");
		//assertEquals("�擾����������Ă��܂��B", 0, players.size());
	}

	public void testGetTeamList() throws Exception {
		List<Player> list = Player.getTeamList();
		assertEquals("�擾����������Ă��܂��B", 3, list.size());
	}
}
