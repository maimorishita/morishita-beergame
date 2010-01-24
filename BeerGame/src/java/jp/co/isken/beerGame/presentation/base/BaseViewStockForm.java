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
     * �Q�[��ID
    **/
    private  Long   gameId;

    /**
     * �`�[����
    **/
    private  String   teamName;

    /**
     * �Q�[��ID���擾����
     * @return �Q�[��ID
    **/
    public Long getGameId() {
        return this.gameId;
    }

    /**
     * �`�[�������擾����
     * @return �`�[����
    **/
    public String getTeamName() {
        return this.teamName;
    }


    /**
     * �Q�[��ID��ݒ肷��
     * @param gameId �Q�[��ID
    **/
    public void setGameId(Long gameId) {
        this.gameId = gameId;
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
      buf.append("[�Q�[��ID:");
      buf.append(gameId + "]");
      buf.append("[�`�[����:");
      buf.append(teamName + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
