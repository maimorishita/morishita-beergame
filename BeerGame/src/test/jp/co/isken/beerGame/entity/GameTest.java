package jp.co.isken.beerGame.entity;

import java.util.List;

import jp.rough_diamond.commons.service.BasicService;
import junit.framework.TestCase;

public class GameTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}
	
	public void test参加可能なゲームのリストを取得する() throws Exception{
		List<Game> list = Game.getWaitingGameList();
		assertEquals(2, list.size());
	}	
	
	public void testすべてのチームを取得する() throws Exception{
		List<Game> list = Game.getAll();
		assertEquals(4, list.size());
	}

	public void testGameに紐づくすべてのロールを取得する() throws Exception{
		Game game = BasicService.getService().findByPK(Game.class,3L);
		List<Role> list = Game.getRoles(game);
		assertEquals(4, list.size());
	}

	public void testチーム名からゲームを取得する(){
		Game game = Game.getGameByName("アベベ");
		assertEquals("アベベ" ,game.getName() );
	}
	
	public void testロールを取得する(){
		Game game = BasicService.getService().findByPK(Game.class,4L);
		Role role = game.getRole("小売り");
		assertEquals("小売り", role.getName());
	}
}
