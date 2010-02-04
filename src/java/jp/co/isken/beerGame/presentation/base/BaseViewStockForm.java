//$Id: BeansBaseTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation.base;
import java.io.Serializable;

import java.util.*;

/**
 * �c�����Q�Ƃ���
**/
@SuppressWarnings("all")
abstract public class BaseViewStockForm extends jp.rough_diamond.framework.web.struts.BaseForm implements Serializable {
    /**
     * �Q�[��
    **/
    private  jp.co.isken.beerGame.entity.Game   game;

    /**
     * �Q�[��ID
    **/
    private  Long   gameId;

    /**
     * ���[��
    **/
    private  jp.co.isken.beerGame.entity.Role   role;

    /**
     * ���[��ID
    **/
    private  Long   roleId;

    /**
     * �`�[����
    **/
    private  String   teamName;

    /**
     * �Q�[�����擾����
     * @return �Q�[��
    **/
    public jp.co.isken.beerGame.entity.Game getGame() {
        return this.game;
    }

    /**
     * �Q�[��ID���擾����
     * @return �Q�[��ID
    **/
    public Long getGameId() {
        return this.gameId;
    }

    /**
     * ���[�����擾����
     * @return ���[��
    **/
    public jp.co.isken.beerGame.entity.Role getRole() {
        return this.role;
    }

    /**
     * ���[��ID���擾����
     * @return ���[��ID
    **/
    public Long getRoleId() {
        return this.roleId;
    }

    /**
     * �`�[�������擾����
     * @return �`�[����
    **/
    public String getTeamName() {
        return this.teamName;
    }


    /**
     * �Q�[����ݒ肷��
     * @param game �Q�[��
    **/
    public void setGame(jp.co.isken.beerGame.entity.Game game) {
        this.game = game;
    }

    /**
     * �Q�[��ID��ݒ肷��
     * @param gameId �Q�[��ID
    **/
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    /**
     * ���[����ݒ肷��
     * @param role ���[��
    **/
    public void setRole(jp.co.isken.beerGame.entity.Role role) {
        this.role = role;
    }

    /**
     * ���[��ID��ݒ肷��
     * @param roleId ���[��ID
    **/
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * �`�[������ݒ肷��
     * @param teamName �`�[����
    **/
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[�Q�[��:");
      buf.append(game + "]");
      buf.append("[�Q�[��ID:");
      buf.append(gameId + "]");
      buf.append("[���[��:");
      buf.append(role + "]");
      buf.append("[���[��ID:");
      buf.append(roleId + "]");
      buf.append("[�`�[����:");
      buf.append(teamName + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
