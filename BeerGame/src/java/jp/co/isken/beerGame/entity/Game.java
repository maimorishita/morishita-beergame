package jp.co.isken.beerGame.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jp.rough_diamond.commons.di.DIContainerFactory;
import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.service.annotation.PostPersist;
import jp.rough_diamond.framework.transaction.VersionUnmuchException;

/**
 * ゲームのHibernateマッピングクラス
 **/
public class Game extends jp.co.isken.beerGame.entity.base.BaseGame {
	private static final long serialVersionUID = 1L;

	private Role owner;

	public Role getOwner() {
		return owner;
	}

	public static List<Game> getWaitingGameList() {
		BasicService service = BasicService.getService();
		List<Game> games = new ArrayList<Game>();
		for (Game game : service.findAll(Game.class)) {
			Extractor extractor = new Extractor(Player.class);
			extractor.add(Condition.eq(new Property(Player.GAME), game));
			List<Player> players = service.findByExtractor(extractor);
			if (players.size() < RoleType.values().length) {
				games.add(game);
			}
		}
		return games;
	}

	public static List<Game> getAll() {
		Extractor extractor = new Extractor(Game.class);
		extractor.addOrder(Order.asc(new Property(Game.ID)));
		return BasicService.getService().findByExtractor(extractor);
	}

	public List<Role> getRoles() {
		return getRoles(this);
	}

	public static List<Role> getRoles(Game game) {
		Extractor extractor = new Extractor(Role.class);
		extractor.add(Condition.eq(new Property(Role.PLAYER + "." + Player.GAME), game));
		List<Role> roles = BasicService.getService().findByExtractor(extractor);
		return roles;
	}

	public static Game getGameByName(String name) {
		Extractor extractor = new Extractor(Game.class);
		extractor.add(Condition.eq(new Property(Game.NAME), name));
		List<Game> list = BasicService.getService().findByExtractor(extractor);
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return new Game();
		}
	}

	public Role getRole(String roleName) {
		return this.getRole(RoleType.getRoleTypeByName(roleName));
	}

	public Role getRole(RoleType type) {
		BasicService service = BasicService.getService();
		Extractor e = new Extractor(Role.class);
		e.add(Condition.eq(new Property(Role.PLAYER + "." + Player.GAME), this));
		e.add(Condition.eq(new Property(Role.NAME), type.name()));
		List<Role> roles = service.findByExtractor(e);
		return (roles.size() == 0 ? null : roles.get(0));
	}

	public static Game create(String teamName, String ownerName) {
		Game game = new Game();
		game.setName(teamName);
		Player player = new Player();
		player.setName(ownerName);
		player.setIsOwner(true);
		player.setGame(game);
		Role role = new Role();
		role.setName(RoleType.小売り.name());
		role.setPlayer(player);
		game.owner = role;
		return game;
	}

	public boolean isEnableToStart() {
		if (Game.getRoles(this).size() == RoleType.values().length) {
			return true;
		}
		return false;
	}

	public Set<RoleType> getUnusedRoles() {
		Set<RoleType> set = RoleType.getAll();
		for (Role role : getRoles(this)) {
			set.remove(RoleType.valueOf(role.getName()));
		}
		return set;
	}

	
	@PostPersist
	public void CreateOwner() throws VersionUnmuchException, MessagesIncludingException {
		this.getOwner().save();
	}

	@PostPersist
	public void saveRoles() throws VersionUnmuchException, MessagesIncludingException {
		this.saveRole(RoleType.市場);
		this.saveRole(RoleType.工場);
	}

	private void saveRole(RoleType type) throws VersionUnmuchException, MessagesIncludingException {
		Player player = new Player();
		player.setName(type.name());
		player.setIsOwner(false);
		player.setGame(this);
		Role role = new Role();
		role.setName(type.name());
		role.setPlayer(player);
		role.save();
	}

	public boolean IsGameOver(Long week) {
		return DIContainerFactory.getDIContainer().getObject(Long.class, "lastWeekOfGame") < week;
	}
}
