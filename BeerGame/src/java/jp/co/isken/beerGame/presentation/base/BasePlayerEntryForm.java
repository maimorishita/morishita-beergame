//$Id: BeansBaseTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation.base;
import java.io.Serializable;

import java.util.*;

/**
 * �Q�[���ɓo�^����
**/
@SuppressWarnings("all")
abstract public class BasePlayerEntryForm extends jp.rough_diamond.framework.web.struts.BaseForm implements Serializable {
    /**
     * �v���C���[
    **/
    private  jp.co.isken.beerGame.entity.Player   player;

    /**
     * �v���C���[���擾����
     * @return �v���C���[
    **/
    public jp.co.isken.beerGame.entity.Player getPlayer() {
        return this.player;
    }


    /**
     * �v���C���[��ݒ肷��
     * @param player �v���C���[
    **/
    public void setPlayer(jp.co.isken.beerGame.entity.Player player) {
        this.player = player;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[�v���C���[:");
      buf.append(player + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
