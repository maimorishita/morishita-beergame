//$Id: BeansBaseTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation.base;
import java.io.Serializable;

import java.util.*;

/**
 * �v���C���[��o�^����
**/
@SuppressWarnings("all")
abstract public class BasePlayerEntryForm extends jp.rough_diamond.framework.web.struts.BaseForm implements Serializable {
    /**
     * �v���C���[��
    **/
    private  String   playerName;

    /**
     * �v���C���[�����擾����
     * @return �v���C���[��
    **/
    public String getPlayerName() {
        return this.playerName;
    }


    /**
     * �v���C���[����ݒ肷��
     * @param playerName �v���C���[��
    **/
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[�v���C���[��:");
      buf.append(playerName + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
