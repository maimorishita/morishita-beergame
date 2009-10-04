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
	
	public void test�Q���\�ȃQ�[���̃��X�g���擾����() throws Exception{
		List<Game> list = Game.getWaitingGameList();
		assertEquals(2, list.size());
	}	
	
	public void test���ׂẴ`�[�����擾����() throws Exception{
		List<Game> list = Game.getAll();
		assertEquals(4, list.size());
	}

	public void testGame�ɕR�Â����ׂẴ��[�����擾����() throws Exception{
		Game game = BasicService.getService().findByPK(Game.class,3L);
		List<Role> list = Game.getRoles(game);
		assertEquals(4, list.size());
	}

	public void test�`�[��������Q�[�����擾����(){
		Game game = Game.getGameByName("�A�x�x");
		assertEquals("�A�x�x" ,game.getName() );
	}
	
	public void test���[�����擾����(){
		Game game = BasicService.getService().findByPK(Game.class,4L);
		Role role = game.getRole("������");
		assertEquals("������", role.getName());
	}
}
