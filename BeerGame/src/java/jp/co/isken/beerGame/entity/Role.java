
package jp.co.isken.beerGame.entity;

import java.util.List;

import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;


/**
 * ロールのHibernateマッピングクラス
**/
public class Role extends jp.co.isken.beerGame.entity.base.BaseRole {
    private static final long serialVersionUID = 1L;
    
    
    public int getWeek(){
		Extractor extractor = new Extractor(TradeTransaction.class);
		//TODO 外部キーを使えるようにスキーマ定義を変更する 09/10/4 森下
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE_ID), this.getId()));
		extractor.addOrder(Order.desc(new Property(TradeTransaction.WEEK)));
		extractor.setLimit(1);
		List<TradeTransaction> list = BasicService.getService().findByExtractor(extractor);
		return list.get(0).getWeek().intValue();
	}

}
