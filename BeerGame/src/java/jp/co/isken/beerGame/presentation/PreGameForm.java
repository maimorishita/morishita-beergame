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
	}

	public boolean judgeGameMode() {
		return this.isNewGame();
	}

	public boolean addGame() {
		Game game = new Game();
		game.setName(getTeamName());
		Role role = new Role();
		role.setName("小売り");
		Player player = new Player();
		player.setName(getOwnerName());
		player.setIsOwner(true);
		player.setGame(game);
		player.setRole(role);
		try {
			game.save();
			role.save();
			player.save();
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
		// TODO GameとRoleは、選択する形で使用しないといけない。
		// とりあえず動かすように実装
		Game game = BasicService.getService().findByPK(Game.class, 1L);
		Role role = new Role();
		role.setName("卸１");

		Player player = new Player();
		player.setName(getPlayerName());
		player.setIsOwner(false);
		player.setGame(game);
		player.setRole(role);
		try {
			role.save();
			player.save();
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
		// TODO ちょっと処理が複雑になるので、見ためだけ作るという事で全ロールを返却。
		return BasicService.getService().findAll(Role.class);
	}
}