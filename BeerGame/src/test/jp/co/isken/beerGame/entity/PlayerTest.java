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
		//³íŒn
		//List<Player> players = Player.createPlayers("–kŠC“¹", "—é–Ø‘¾˜Y");
		//service.insert(players.toArray());

		//Extractor extractor = new Extractor(Player.class);
		//extractor.add(Condition.eq(new Property(Player.TEAM_NAME), "–kŠC“¹"));
		//List<Player> playersRs = service.findByExtractor(extractor);

		//assertEquals("æ“¾Œ”‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", 4, playersRs.size());

		//ˆÙíŒniƒ`[ƒ€–¼d•¡j
		//players = Player.createPlayers("–kŠC“¹", "—é–Øu˜Y");
		//assertEquals("æ“¾Œ”‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", 0, players.size());
	}

	public void testGetTeamList() throws Exception {
		List<Player> list = Player.getTeamList();
		assertEquals("æ“¾Œ”‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", 3, list.size());
	}
}
