package jp.co.isken.beerGame.entity;

import java.util.ArrayList;
import java.util.List;

import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
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
}
