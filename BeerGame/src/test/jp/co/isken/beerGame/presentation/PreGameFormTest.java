package jp.co.isken.beerGame.presentation;

import jp.co.isken.beerGame.entity.BusinessMasterLoader;
import jp.co.isken.beerGame.entity.Game;
import jp.co.isken.beerGame.entity.MasterLoader;
import jp.co.isken.beerGame.entity.NumberingLoader;
import jp.co.isken.beerGame.entity.TransactionLoader;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;
import junit.framework.TestCase;

public class PreGameFormTest extends DataLoadingTestCase {

	private PreGameForm form;

	protected void setUp() throws Exception {
		MasterLoader.init();
		BusinessMasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
		form = new PreGameForm();
	}

	public void test正しく遷移すること() throws Exception {
		form.setNewGame(true);
		assertTrue("ゲーム登録画面に遷移しませんでした。", form.judgeGameMode());

		form = new PreGameForm();
		form.setNewGame(false);
		assertFalse("プレイヤー登録画面に遷移しませんでした。", form.judgeGameMode());
	}

	public void testゲームを登録する() throws Exception {
		form.setTeamName("Alliance of Valiant Arms");
		form.setOwnerName("Ryoji Yoshioka");
		assertTrue(form.addGame());
		assertEquals("ゲームが取得できません。","Alliance of Valiant Arms", form.getGame().getName());

		form = new PreGameForm();
		form.setTeamName("Alliance of Valiant Arms");
		assertFalse(form.addGame());
	}

	public void testプレイヤーを登録する() throws Exception {
		form.setPlayerName("今井智明");
		form.setGameId(1L);
		form.setRoleName("小売り");
		assertTrue("ゲームに登録するのに失敗しました。", form.addPlayer());
		assertEquals("ゲームが取得できません。","アベベ", form.getGame().getName());
	}
	
//	public void testゲームが選択された際にロールを取得する() throws Exception {
//		
//	}
	
	public void testIsEnableToStartGame() throws Exception{
		Game game1 = BasicService.getService().findByPK(Game.class, 5L);
		Game game2 = BasicService.getService().findByPK(Game.class, 6L);
		form.setGame(game1);
		assertTrue(form.isEnableToStartGame());
		form.setGame(game2);
		assertFalse(form.isEnableToStartGame());
			}	
	}
