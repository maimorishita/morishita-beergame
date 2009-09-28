package jp.co.isken.beerGame.presentation;

import jp.co.isken.beerGame.entity.BusinessMasterLoader;
import jp.co.isken.beerGame.entity.MasterLoader;
import jp.co.isken.beerGame.entity.NumberingLoader;
import jp.co.isken.beerGame.entity.Player;
import jp.co.isken.beerGame.entity.TransactionLoader;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class PlayerEntryFormTest extends DataLoadingTestCase {

	private PlayerEntryForm form;

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		BusinessMasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
		form = new PlayerEntryForm();
	}

	public void testEntryPlayer() throws Exception {
		BasicService service = BasicService.getService();
		Player player = service.findByPK(Player.class, 1L);
		player.setName("ç°à‰íqñæ");
		form.setPlayer(player);
		assertTrue("ÉQÅ[ÉÄÇ…ìoò^Ç∑ÇÈÇÃÇ…é∏îsÇµÇ‹ÇµÇΩÅB", form.EntryPlayer());
	}
}
