
package jp.co.isken.beerGame.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * ロールのHibernateマッピングクラス
**/
public class Role extends jp.co.isken.beerGame.entity.base.BaseRole {
    private static final long serialVersionUID = 1L;

	public static Map<String, Role> createRoles() {
		
		Map<String, Role> ret = new HashMap<String, Role>();
		
		//卸１をインスタンス化
		Role distributor1 = new Role();	
		distributor1.setName("卸１");
		ret.put("卸１", distributor1);
			
		//卸２をインスタンス化
		Role distributor2 = new Role();
		distributor2.setName("卸２");
		ret.put("卸２", distributor2);
		
		//小売をインスタンス化
		Role retailer = new Role();
		retailer.setName("小売");
		ret.put("小売", retailer);
		
		//メーカーをインスタンス化
		Role manufacturer = new	Role();
		manufacturer.setName("メーカー");
		ret.put("メーカー", manufacturer);
		
		return ret;
	}
}
