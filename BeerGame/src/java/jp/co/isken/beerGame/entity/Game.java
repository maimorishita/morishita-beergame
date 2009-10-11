package jp.co.isken.beerGame.entity;

import java.util.ArrayList;
import java.util.List;

import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;

/**
 * ゲームのHibernateマッピングクラス
 **/
public class Game extends jp.co.isken.beerGame.entity.base.BaseGame {
	private static final long serialVersionUID = 1L;

	public static List<Game> getWaitingGameList() {
		BasicService service = BasicService.getService();
		List<Game> games = new ArrayList<Game>();
		for (Game game : service.findAll(Game.class)) {
			Extractor extractor = new Extractor(Player.class);
			extractor.add(Condition.eq(new Property(Player.GAME), game));
			List<Player> players = service.findByExtractor(extractor);
			if (players.size() < 4) {
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

	public static List<Role> getRoles(Game game) {
		BasicService service = BasicService.getService();
		Extractor extractor = new Extractor(Role.class);
		extractor.add(Condition.eq(
				new Property(Role.PLAYER + "." + Player.GAME), game));
		List<Role> roles = service.findByExtractor(extractor);
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
		Extractor extractor = new Extractor(Role.class);
		extractor.add(Condition.eq(new Property(Role.NAME), roleName));
		extractor.add(Condition.eq(
				new Property(Role.PLAYER + "." + Player.GAME), this));
		List<Role> list = BasicService.getService().findByExtractor(extractor);
		return list.get(0);
	}

	public static Game create(String teamName) {
		Game game = new Game();
		game.setName(teamName);
		return game;
	}

}
