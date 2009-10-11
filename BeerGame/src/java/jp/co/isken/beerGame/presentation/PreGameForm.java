//$Id: BeansTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation;

import java.util.List;

import jp.co.isken.beerGame.entity.Game;
import jp.co.isken.beerGame.entity.Player;
import jp.co.isken.beerGame.entity.Role;
import jp.rough_diamond.commons.resource.Message;
import jp.rough_diamond.commons.resource.Messages;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.framework.transaction.VersionUnmuchException;

/**
 * @see jp.co.isken.beerGame.presentation.BasePreGameForm
 **/
public class PreGameForm extends
		jp.co.isken.beerGame.presentation.base.BasePreGameForm {
	private static final long serialVersionUID = 1L;

	public void init() {
		this.setOwnerName(null);
		this.setTeamName(null);
		this.setPlayerName(null);
		this.setGameId(0L);
	}

	public boolean judgeGameMode() {
		return this.isNewGame();
	}

	public boolean addGame() {
		Game game = Game.create(this.getTeamName());
		Role role = new Role();
		role.setName("è¨îÑÇË");
		Player player = new Player();
		player.setName(getOwnerName());
		player.setIsOwner(true);
		player.setGame(game);
		role.setPlayer(player);
		try {
			game.save();
			player.save();
			role.save();			
			return true;
		} catch (VersionUnmuchException e) {
			Messages msgs = new Messages();
			msgs.add("", new Message(e.getMessage()));
			this.setMessage(msgs);
			return false;
		} catch (MessagesIncludingException e) {
			Messages msgs = new Messages();
			msgs.add("", new Message(e.getMessage()));
			this.setMessage(msgs);
			return false;
		}
	}

	public boolean addPlayer() {
		// Ç∆ÇËÇ†Ç¶Ç∏ìÆÇ©Ç∑ÇÊÇ§Ç…é¿ëï
		Game game = BasicService.getService().findByPK(Game.class, this.getGameId());
		Player player = new Player();
		player.setName(getPlayerName());
		player.setIsOwner(false);
		player.setGame(game);
		Role role = new Role();
		role.setName(this.getRoleName());
		role.setPlayer(player);
		try {
			player.save();
			role.save();
		} catch (VersionUnmuchException e) {
			Messages msgs = new Messages();
			msgs.add("", new Message(e.getMessage()));
			this.setMessage(msgs);
			return false;
		} catch (MessagesIncludingException e) {
			Messages msgs = new Messages();
			msgs.add("", new Message(e.getMessage()));
			this.setMessage(msgs);
			return false;
		}
		return true;
	}

	public List<Game> getWaitingGameList() {
		return Game.getWaitingGameList();
	}

	public List<Role> getWaitingRoleList() {
//		Game game = Game.getGameByName(this.getTeamName());
//		return Role.getWaitingRoleList(game);
		return BasicService.getService().findAll(Role.class);
	}

	public void selectGame() {
		// TODO Auto-generated method stub
		
	}
}