package jp.co.isken.beerGame.entity;

import java.util.List;

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
}
