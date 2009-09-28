//$Id: BeansBaseTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation.base;
import java.io.Serializable;

import java.util.*;

/**
 * ゲームに登録する
**/
@SuppressWarnings("all")
abstract public class BasePlayerEntryForm extends jp.rough_diamond.framework.web.struts.BaseForm implements Serializable {
    /**
     * プレイヤー
    **/
    private  jp.co.isken.beerGame.entity.Player   player;

    /**
     * プレイヤーを取得する
     * @return プレイヤー
    **/
    public jp.co.isken.beerGame.entity.Player getPlayer() {
        return this.player;
    }


    /**
     * プレイヤーを設定する
     * @param player プレイヤー
    **/
    public void setPlayer(jp.co.isken.beerGame.entity.Player player) {
        this.player = player;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[プレイヤー:");
      buf.append(player + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
