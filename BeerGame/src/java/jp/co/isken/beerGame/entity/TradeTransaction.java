
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

public class TradeTransaction extends jp.co.isken.beerGame.entity.base.BaseTradeTransaction {

	private static final long serialVersionUID = 1L;

	public static Map<Long, Long> getRemainAmount(String gameName,
			String roleName) {
		Game game = Game.getGameByName(gameName);
		Role role = game.getRole(roleName);
		return getRemainList(role.getLastWeek(TransactionType.出荷.name()), role);
	}

	private static Map<Long, Long> getRemainList(Long week, Role role) {
		Map<Long, Long> ret = new HashMap<Long, Long>();
		for(Long i = 1L ; i <= week ; i++){
			ret.put(i, calcAmountRemain(i, role));
		}
		return ret;
	}

	public static Long calcAmountRemain(Long week, Role role) {
		Long amount = calcAmount(week - 1, role, TransactionType.受注.name()) - calcAmount(week, role, TransactionType.出荷.name());
		return (amount < 0L ? 0L : amount);
	}

	public static Map<Long, Long> getStockAmount(String gameName, String roleName) {
		Game game = Game.getGameByName(gameName);
		Role role = game.getRole(roleName);
		return getStockList(role.getLastWeek(TransactionType.出荷.name()), role);
	}

	public static Map<Long, Long> getStockList(Long week, Role role) {
		Map<Long, Long> ret = new HashMap<Long, Long>();
		for(Long i = 1L ; i <= week ; i++){
			ret.put(i, calcAmountStock(i, role));
		}
		return ret;
	}

	public static Long calcAmountStock(Long week, Role role) {
		return (calcAmount(week, role, "入荷") + calcAmount(week, role, TransactionType.在庫.name())) - calcAmount(week, role, "出荷");
	}

	public static Long calcAmount(Long week, Role role, String typeName) {
		Extractor ext = new Extractor(TradeTransaction.class);
		ext.addExtractValue(new ExtractValue(
				"sum", new Sum(new Property(TradeTransaction.AMOUNT))));
		ext.add(Condition.eq(new Property(TradeTransaction.ROLE), role));
		ext.add(Condition.le(new Property(TradeTransaction.WEEK), week));
		ext.add(Condition.eq(new Property(TradeTransaction.TRANSACTION_TYPE), typeName));
		List<Map<String, Long>> list = BasicService.getService().findByExtractor(ext);
		return list.get(0).get("sum");	
	}
}
