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
import jp.co.isken.beerGame.entity.TradeTransaction.TradeTransactionService;
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
public class PreGameForm extends
		jp.co.isken.beerGame.presentation.base.BasePreGameForm {
	private static final long serialVersionUID = 1L;

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
			Game game = BasicService.getService().findByPK(Game.class,
					this.getGameId());
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

	public List<List<TradeTransaction>> getDebagView() {
		List<List<TradeTransaction>> ret = new ArrayList<List<TradeTransaction>>();
		for (int i = 1; i < this.getRole().getLastWeek(
				TransactionType.入荷.name()).intValue(); i++) {
			ret.add(getTransaction(i));
		}
		return ret;
	}

	public List<Game> getGameAll() {
		return Game.getAll();
	}

	public List<Game> getWaitingGameList() {
		return Game.getWaitingGameList();
	}

	public Set<RoleType> getWaitingRoleList() {
		Game game = BasicService.getService().findByPK(Game.class,
				this.getGameId());
		return (game == null) ? null : game.getUnusedRoles();
	}

	public void init() {
		this.setOwnerName(null);
		this.setTeamName(null);
		this.setPlayerName(null);
		this.setGameId(0L);
	}

	public boolean isEnableToStartGame() {
		if (this.getGame().isEnableToStart()) {
			this.refreshView();
			return true;
		}
		return false;
	}

	public boolean judgeGameMode() {
		return this.isNewGame();
	}

	public boolean login() {
		BasicService service = BasicService.getService();
		this.setGame(service.findByPK(Game.class, this.getGameId()));
		if (this.getRoleId() == null) {
			this.setRole(this.getGame().getRole(this.getRoleName()));		
		} else {
			this.setRole(service.findByPK(Role.class, this.getRoleId()));
		}		
		if (this.getRole() == null) {
			Messages msgs = new Messages();
			msgs.add("", new Message("errors.invalid.login"));
			this.setMessage(msgs);
			return false;
		}
		// this.refreshView();
		if (this.isEnableToStartGame()) {
			return true;
		} else {
			Messages msgs = new Messages();
			msgs.add("", new Message("errors.invalid.start"));
			this.setMessage(msgs);
			return false;
		}
	}

	public boolean order() {
		try {
			// この週の発注
			TradeTransactionService.getService().addTransactions(
					this.getRole(), Long.parseLong(this.getOrder()));
			this.refreshView();
			if (this.getGame().IsGameOver(
					this.getRole().getCurrentWeek(TransactionType.発注.name()))) {
				return false;
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

	public void selectGame() {
		// 画面から呼ばれるけど、いまのところ処理なし
	}

	private List<TradeTransaction> getTransaction(int i) {
		Extractor e = new Extractor(TradeTransaction.class);
		e
				.add(Condition.eq(new Property(TradeTransaction.ROLE), this
						.getRole()));
		e.add(Condition.notEq(new Property(TradeTransaction.TRANSACTION_TYPE),
				"在庫"));
		e.add(Condition.eq(new Property(TradeTransaction.WEEK), new Long(i)));
		e.addOrder(Order.asc(new Property(TradeTransaction.ID)));
		return BasicService.getService().findByExtractor(e);
	}

	private void refreshView() {
		this.setInbound(getRole().getTransaction(TransactionType.入荷)
				.getAmount().longValue());
		this.setAcceptOrder(getRole().getTransaction(TransactionType.受注)
				.getAmount().longValue());
		this.setOutbound(getRole().getTransaction(TransactionType.出荷)
				.getAmount().longValue());
		this.setRemain(TradeTransaction.calcAmountRemain(this.getRole()
				.getLastWeek(TransactionType.受注.name()), this.getRole()));
	}

	public boolean isOwner() {
		return this.getRole().getPlayer().getIsOwner();
	}
}