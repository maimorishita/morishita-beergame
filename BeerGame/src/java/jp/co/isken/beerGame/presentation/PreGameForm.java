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
		role.setName("������");
		Player player = new Player();
		player.setName(getOwnerName());
		player.setIsOwner(true);
		player.setGame(game);
		role.setPlayer(player);
		try {
			game.save();
			player.save();
			role.save();
			this.setGame(game);
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
			msgs.add("", new Message(e.getMessage()));
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
		// ��ʂ���Ă΂�邯�ǁA���܂̂Ƃ��돈���Ȃ�
	}

	public boolean isEnableToStartGame() {
		if (this.getGame().isEnableToStart()) {
			try {
				// orderSet();
				this.getRole().initAmount(12L, 4L);
				// TODO TradeTransaction����擾����悤�ύX���܂��傤�ˁ@�������X��
				this.setStock(12L);
				this.setAcceptOrder(4L);
				this.setInbound(0L);
				this.setOutbound(0L);
				this.setRemain(0L);
			} catch (VersionUnmuchException e) {
				throw new RuntimeException(e);
			} catch (MessagesIncludingException e) {
				throw new RuntimeException(e);
			}
			return true;
		}
		return false;
	}

	public void order() {
		String orderQuantity = this.getOrder();
		try {
			//���̏T�̔���
			this.getRole().order(Long.parseLong(orderQuantity));
			//���̏T�̓��ׁA�󒍁A�o��
			this.getRole().inbound();
			this.getRole().acceptOrder();
			this.getRole().outbound();
			//TODO �L���[���炶��Ȃ��ATradeTransaction����擾����悤�ύX���܂��傤�ˁ@�������X��
//			this.setInbound(this.getRole().getInboundCount());
//			this.setOutbound(this.getRole().getOutboundCount());
//			this.setAcceptOrder(this.getRole().getOrderCount());
			// FIXME 2009/11/29 imai yoshioka TradeTransaction����擾����悤�ɏC�����邱��
			// ��̏������ƃL���[���Ȃ����߁A�������~�܂��Ă��܂�
			this.setInbound(5L);
			this.setOutbound(5L);
			this.setAcceptOrder(1L);
			this.setRemain(TradeTransaction.calcAmountRemain(this.getRole().getWeek(TransactionType.��.name()), this.getRole()));
		} catch (NumberFormatException e) {
			throw new RuntimeException(e);
		} catch (VersionUnmuchException e) {
			throw new RuntimeException(e);
		} catch (MessagesIncludingException e) {
			throw new RuntimeException(e);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Game> getGameAll() {
		return Game.getAll();
	}

	public boolean login() {
		this.setGame(BasicService.getService().findByPK(Game.class,
				this.getGameId()));
		RoleType type = RoleType.getRoleTypeByName(this.getRoleName());
		this.setRole(Role.getRole(this.getGame(), type));
		if (this.getRole() != null) {
			//��P�T����X�^�[�g����ꍇ�́A�����ݒ���s���@morishita
			if (this.getRole().getWeek(TransactionType.�o��.name()) == 1L) {
				return this.isEnableToStartGame();
			}
		}
		return false;
	}
}