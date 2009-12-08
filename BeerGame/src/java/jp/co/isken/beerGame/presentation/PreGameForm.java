//$Id: BeansTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation;

import java.util.List;
import java.util.Set;

import javax.jms.JMSException;

import jp.co.isken.beerGame.entity.Game;
import jp.co.isken.beerGame.entity.Player;
import jp.co.isken.beerGame.entity.Role;
import jp.co.isken.beerGame.entity.RoleType;
import jp.co.isken.beerGame.entity.TradeTransaction;
import jp.co.isken.beerGame.entity.TransactionType;
import jp.rough_diamond.commons.resource.Message;
import jp.rough_diamond.commons.resource.Messages;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.framework.transaction.VersionUnmuchException;

/**
 * @see jp.co.isken.beerGame.presentation.BasePreGameForm
 **/
public class PreGameForm extends jp.co.isken.beerGame.presentation.base.BasePreGameForm {
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
		role.setName("小売り");
		Player player = new Player();
		player.setName(getOwnerName());
		player.setIsOwner(true);
		player.setGame(game);
		role.setPlayer(player);
		
		Role marcket = new Role();
		marcket.setName("市場");
		Player mPlayer = new Player();
		mPlayer.setName("市場");
		mPlayer.setIsOwner(false);
		mPlayer.setGame(game);
		marcket.setPlayer(mPlayer);
		
		Role factory = new Role();
		factory.setName("工場");
		Player fPlayer = new Player();
		fPlayer.setName("工場");
		fPlayer.setIsOwner(false);
		fPlayer.setGame(game);
		factory.setPlayer(fPlayer);
		try {
			game.save();
			player.save();
			mPlayer.save();
			fPlayer.save();
			role.save();
			marcket.save();
			factory.save();
			this.setRole(role);
			this.setGame(game);
			return true;
		} catch (VersionUnmuchException e) {
			Messages msgs = new Messages();
			msgs.add("", new Message("errors.duplicate"));
			this.setMessage(msgs);
			return false;
		} catch (MessagesIncludingException e) {
			this.setMessage(e.getMessages());
			return false;
		}
	}

	public boolean addPlayer() {
		try {
			Game game = BasicService.getService().findByPK(Game.class, this.getGameId());
			Player player = new Player();
			player.setName(getPlayerName());
			player.setIsOwner(false);
			player.setGame(game);
			Role role = new Role();
			role.setName(this.getRoleName());
			role.setPlayer(player);
			player.save();
			role.save();
			this.setGame(game);
			this.setRole(role);
			return true;
		} catch (VersionUnmuchException e) {
			Messages msgs = new Messages();
			msgs.add("", new Message("errors.duplicate"));
			this.setMessage(msgs);
			return false;
		} catch (MessagesIncludingException e) {
			this.setMessage(e.getMessages());
			return false;
		}
	}

	public List<Game> getWaitingGameList() {
		return Game.getWaitingGameList();
	}

	public Set<RoleType> getWaitingRoleList() {
		Game game = BasicService.getService().findByPK(Game.class, this.getGameId());
		return (game == null) ? null : game.getUnusedRoles();
	}

	public void selectGame() {
		// 画面から呼ばれるけど、いまのところ処理なし
	}

	public boolean isEnableToStartGame() {
		if (this.getGame().isEnableToStart()) {
			try {
				// orderSet();
				this.getRole().initAmount(12L, 4L);
				// TODO TradeTransactionから取得するよう変更しましょうね　中原＆森下
				this.setStock(8L);
				this.setAcceptOrder(4L);
				this.setInbound(12L);
				this.setOutbound(4L);
				this.setRemain(0L);
				return true;
			} catch (VersionUnmuchException e) {
				Messages msgs = new Messages();
				msgs.add("", new Message("errors.duplicate"));
				this.setMessage(msgs);
			} catch (MessagesIncludingException e) {
				this.setMessage(e.getMessages());
			} catch (JMSException e) {
				throw new RuntimeException(e);
			}
		}
		return false;
	}

	public void order() {
		try {
			//この週の発注
			this.getRole().order(Long.parseLong(this.getOrder()));
			//次の週の入荷、受注、出荷
			this.getRole().inbound();
			this.getRole().acceptOrder();
			this.getRole().outbound();
			this.setInbound(getRole().getTransaction(TransactionType.入荷).getAmount().longValue());
			this.setOutbound(getRole().getTransaction(TransactionType.出荷).getAmount().longValue());
			this.setAcceptOrder(getRole().getTransaction(TransactionType.受注).getAmount().longValue());
			this.setRemain(TradeTransaction.calcAmountRemain(this.getRole().getWeek(TransactionType.受注.name()), this.getRole()));
			this.setStock(TradeTransaction.calcAmountStock(this.getRole().getWeek(TransactionType.入荷.name()), this.getRole()));
		} catch (NumberFormatException e) {
			Messages msgs = new Messages();
			msgs.add("", new Message("errors.invalid.orderamount"));
			this.setMessage(msgs);
		} catch (VersionUnmuchException e) {
			Messages msgs = new Messages();
			msgs.add("", new Message("errors.duplicate"));
			this.setMessage(msgs);
		} catch (MessagesIncludingException e) {
			this.setMessage(e.getMessages());
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Game> getGameAll() {
		return Game.getAll();
	}

	public boolean login() {
		this.setGame(BasicService.getService().findByPK(Game.class, this.getGameId()));
		RoleType type = RoleType.getRoleTypeByName(this.getRoleName());
		this.setRole(Role.getRole(this.getGame(), type));
		if (this.getRole() != null) {
			//第１週からスタートする場合は、初期設定を行う　morishita
			if (this.getRole().getWeek(TransactionType.出荷.name()) == 1L) {
				return this.isEnableToStartGame();
			}
			return this.getGame().isEnableToStart();
		}
		return false;
	}
}