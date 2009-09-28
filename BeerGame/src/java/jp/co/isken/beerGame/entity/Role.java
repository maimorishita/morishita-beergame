
package jp.co.isken.beerGame.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * ���[����Hibernate�}�b�s���O�N���X
**/
public class Role extends jp.co.isken.beerGame.entity.base.BaseRole {
    private static final long serialVersionUID = 1L;

	public static Map<String, Role> createRoles() {
		
		Map<String, Role> ret = new HashMap<String, Role>();
		
		//���P���C���X�^���X��
		Role distributor1 = new Role();	
		distributor1.setName("���P");
		ret.put("���P", distributor1);
			
		//���Q���C���X�^���X��
		Role distributor2 = new Role();
		distributor2.setName("���Q");
		ret.put("���Q", distributor2);
		
		//�������C���X�^���X��
		Role retailer = new Role();
		retailer.setName("����");
		ret.put("����", retailer);
		
		//���[�J�[���C���X�^���X��
		Role manufacturer = new	Role();
		manufacturer.setName("���[�J�[");
		ret.put("���[�J�[", manufacturer);
		
		return ret;
	}
}
