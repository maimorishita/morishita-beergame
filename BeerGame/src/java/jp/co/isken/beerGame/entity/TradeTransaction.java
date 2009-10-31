
package jp.co.isken.beerGame.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.ExtractValue;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.extractor.Sum;
import jp.rough_diamond.commons.service.BasicService;

/**
 * ���L�^��Hibernate�}�b�s���O�N���X
**/
public class TradeTransaction extends jp.co.isken.beerGame.entity.base.BaseTradeTransaction {

	private static final long serialVersionUID = 1L;

	public static Map<Integer, Integer> getRemainAmount(String gameName,
			String roleName) {
		Game game = Game.getGameByName(gameName);
		Role role = game.getRole(roleName);
		return getRemainList(role.getWeek().intValue(), role);
	}

	private static Map<Integer, Integer> getRemainList(int week, Role role) {
		Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
		for(int i = 1 ; i <= week ; i++){
			ret.put(i, calcAmountRemain(i, role));
		}
		return ret;
	}

	public static int calcAmountRemain(int week, Role role) {
		return calcAmount(week, role, "受注") - calcAmount(week, role, "出荷");	}

	public static Map<Integer, Integer> getStockAmount(String gameName, String roleName) {
		Game game = Game.getGameByName(gameName);
		Role role = game.getRole(roleName);
		return getStockList(role.getWeek().intValue(), role);
	}

	public static Map<Integer, Integer> getStockList(int week, Role role) {
		Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
		for(int i = 1 ; i <= week ; i++){
			ret.put(i, calcAmountStock(i, role));
		}
		return ret;
	}

	public static int calcAmountStock(int week, Role role) {
		return calcAmount(week, role, "入荷") - calcAmount(week, role, "出荷");
	}

	public static int calcAmount(int week, Role role, String typeName) {
		Extractor ext = new Extractor(TradeTransaction.class);
		ext.addExtractValue(new ExtractValue(
				"sum", new Sum(new Property(TradeTransaction.AMOUNT))));
		ext.add(Condition.eq(new Property(TradeTransaction.ROLE), role));
		ext.add(Condition.le(new Property(TradeTransaction.WEEK), week));
		ext.add(Condition.eq(new Property(TradeTransaction.TRANSACTION_TYPE), typeName));
		List<Map<String, Long>> list = BasicService.getService().findByExtractor(ext);
		return list.get(0).get("sum").intValue();	
	}
}
