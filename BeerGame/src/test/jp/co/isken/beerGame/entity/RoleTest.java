package jp.co.isken.beerGame.entity;

import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class RoleTest extends DataLoadingTestCase {
	
	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}
	
	public void testÅIT‚ğæ“¾‚·‚é(){
		Role role = BasicService.getService().findByPK(Role.class, 7L);
		int ret = role.getWeek();
		assertEquals(3, ret);
	}
	
//	public void test‚ ‚éƒQ[ƒ€‚ÌƒvƒŒƒCƒ„[‚ª“o˜^‚³‚ê‚Ä‚¢‚È‚¢ƒ[ƒ‹‚ğæ“¾‚·‚é(){
//		List<Role> list = Role.getWaitingRoleList(BasicService.getService().findByPK(Game.class, 1L));
//		assertEquals(3, list);
//	}
	
}
