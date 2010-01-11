//$Id: BeansTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;

import jp.co.isken.beerGame.entity.Game;
import jp.co.isken.beerGame.entity.Player;
import jp.co.isken.beerGame.entity.Role;
import jp.co.isken.beerGame.entity.RoleType;
import jp.co.isken.beerGame.entity.TradeTransaction;
import jp.co.isken.beerGame.entity.TransactionType;
import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
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
		try {
			String teamName = FormUtil.trim(this.getTeamName());
			if (FormUtil.isNullOrEmpty(teamName) == true) {
				Messages msgs = new Messages();
				msgs.add("", new Message("errors.required", "チーム名"));
				this.setMessage(msgs);
				return false;
			}
			String ownerName = FormUtil.trim(this.getOwnerName());
			if (FormUtil.isNullOrEmpty(ownerName) == true) {
				Messages msgs = new Messages();
				msgs.add("", new Message("errors.required", "オーナー名"));
				this.setMessage(msgs);
				return false;
			}
			Game game = Game.create(teamName, ownerName);
			game.save();
			this.setRole(game.getRole(RoleType.小売り));
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
			player.setName(FormUtil.trim(this.getPlayerName()));
			player.setIsOwner(false);
			player.setGame(game);
			Role role = new Role();
			role.setName(this.getRoleName());
			role.setPlayer(player);
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
				this.getRole().initAmount();
				this.refreshView();
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

	public boolean order() {
		try {
			// この週の発注
			this.getRole().order(Long.parseLong(this.getOrder()));
			if (this.getGame().IsGameOver(this.getRole().getCurrentWeek(TransactionType.発注.name()))) {
				return false;
			} else {
				// 次の週の入荷、受注、出荷
				this.getRole().inbound();
				this.getRole().acceptOrder();
				this.getRole().outbound();
				this.refreshView();
			}
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
		return true;
	}

	private void refreshView() {
		this.setInbound(getRole().getTransaction(TransactionType.入荷).getAmount().longValue());
		this.setOutbound(getRole().getTransaction(TransactionType.出荷).getAmount().longValue());
		this.setAcceptOrder(getRole().getTransaction(TransactionType.受注).getAmount().longValue());
		this.setRemain(TradeTransaction.calcAmountRemain(this.getRole().getLastWeek(TransactionType.受注.name()), this.getRole()));
		this.setStock(TradeTransaction.calcAmountStock(this.getRole().getLastWeek(TransactionType.入荷.name()), this.getRole()));
	}

	public List<Game> getGameAll() {
		return Game.getAll();
	}

	public boolean login() {
		this.setGame(BasicService.getService().findByPK(Game.class, this.getGameId()));
		this.setRole(this.getGame().getRole(this.getRoleName()));
		if (this.getRole() != null) {
			//第１週からスタートする場合は、初期設定を行う morishita
			if (this.getRole().getLastWeek(TransactionType.出荷.name()) == 1L) {
				return this.isEnableToStartGame();
			}
			this.refreshView();
			return this.getGame().isEnableToStart();
		}
		return false;
	}

	public List<List<TradeTransaction>> getDebagView() {
		List<List<TradeTransaction>> ret = new ArrayList<List<TradeTransaction>>();
		for (int i = 1; i < this.getRole().getLastWeek(TransactionType.入荷.name()).intValue(); i++) {
			ret.add(getTransaction(i));
		}
		return ret;
	}

	private List<TradeTransaction> getTransaction(int i) {
		Extractor e = new Extractor(TradeTransaction.class);
		e.add(Condition.eq(new Property(TradeTransaction.ROLE), this.getRole()));
		e.add(Condition.notEq(new Property(TradeTransaction.TRANSACTION_TYPE), "在庫"));
		e.add(Condition.eq(new Property(TradeTransaction.WEEK), new Long(i)));
		e.addOrder(Order.asc(new Property(TradeTransaction.ID)));
		return BasicService.getService().findByExtractor(e);
	}
}