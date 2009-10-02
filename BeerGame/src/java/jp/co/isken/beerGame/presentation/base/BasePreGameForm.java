//$Id: BeansBaseTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation.base;
import java.io.Serializable;

import java.util.*;

/**
 * �Q�[����o�^����B
**/
@SuppressWarnings("all")
abstract public class BasePreGameForm extends jp.rough_diamond.framework.web.struts.BaseForm implements Serializable {
    /**
     * �I�[�i�[��
    **/
    private  String   ownerName;

    /**
     * �v���C���[��
    **/
    private  String   playerName;

    /**
     * �`�[����
    **/
    private  String   teamName;

    /**
     * �I�[�i�[�����擾����
     * @return �I�[�i�[��
    **/
    public String getOwnerName() {
        return this.ownerName;
    }

    /**
     * �v���C���[�����擾����
     * @return �v���C���[��
    **/
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * �`�[�������擾����
     * @return �`�[����
    **/
    public String getTeamName() {
        return this.teamName;
    }


    /**
     * �I�[�i�[����ݒ肷��
     * @param ownerName �I�[�i�[��
    **/
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * �v���C���[����ݒ肷��
     * @param playerName �v���C���[��
    **/
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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
      buf.append("[�I�[�i�[��:");
      buf.append(ownerName + "]");
      buf.append("[�v���C���[��:");
      buf.append(playerName + "]");
      buf.append("[�`�[����:");
      buf.append(teamName + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
