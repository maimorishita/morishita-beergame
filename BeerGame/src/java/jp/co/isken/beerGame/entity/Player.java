package jp.co.isken.beerGame.entity;

import java.util.ArrayList;
import java.util.List;

import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;

/**
 * プレイヤーのHibernateマッピングクラス
 **/
public class Player extends jp.co.isken.beerGame.entity.base.BasePlayer {
	private static final long serialVersionUID = 1L;

	public static List<Player> createPlayers(String teamName, String ownweName ) {
		
		if(!isDuplicatedTeamName(teamName)){
			return new ArrayList<Player>();
		}
		
		List<Player> ret = new ArrayList<Player>();
		for (int i = 0 ; i < 4 ;i++) {
			Player p = new Player();
			//p.setTeamName(teamName);
			p.setName("");
			ret.add(p);
		}
		ret.get(0).setIsOwner(true);
		ret.get(0).setName(ownweName);
		return ret;
	}

	private static boolean isDuplicatedTeamName(String teamName) {
		BasicService service = BasicService.getService();
		Extractor extractor = new Extractor(Player.class);
		//extractor.add(Condition.eq(new Property(Player.TEAM_NAME), teamName));
		List<Player> p = service.findByExtractor(extractor);
		if(p.size() == 0){
			return true;
		}
		return false;
	}

	public static List<Player> getTeamList() {
		BasicService service = BasicService.getService();
		Extractor extractor = new Extractor(Player.class);
		extractor.add(Condition.eq(new Property(Player.IS_OWNER), "Y"));
		return service.findByExtractor(extractor);
	}
}