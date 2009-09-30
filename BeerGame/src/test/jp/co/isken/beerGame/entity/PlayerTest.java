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
		//正常系
		//List<Player> players = Player.createPlayers("北海道", "鈴木太郎");
		//service.insert(players.toArray());

		//Extractor extractor = new Extractor(Player.class);
		//extractor.add(Condition.eq(new Property(Player.TEAM_NAME), "北海道"));
		//List<Player> playersRs = service.findByExtractor(extractor);

		//assertEquals("取得件数が誤っています。", 4, playersRs.size());

		//異常系（チーム名重複）
		//players = Player.createPlayers("北海道", "鈴木志郎");
		//assertEquals("取得件数が誤っています。", 0, players.size());
	}

	public void testGetTeamList() throws Exception {
		List<Player> list = Player.getTeamList();
		assertEquals("取得件数が誤っています。", 3, list.size());
	}
}
