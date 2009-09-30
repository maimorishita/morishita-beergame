//$Id: BeansBaseTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation.base;
import java.io.Serializable;

import java.util.*;

/**
 * プレイヤーを登録する
**/
@SuppressWarnings("all")
abstract public class BasePlayerEntryForm extends jp.rough_diamond.framework.web.struts.BaseForm implements Serializable {
    /**
     * プレイヤー名
    **/
    private  String   playerName;

    /**
     * プレイヤー名を取得する
     * @return プレイヤー名
    **/
    public String getPlayerName() {
        return this.playerName;
    }


    /**
     * プレイヤー名を設定する
     * @param playerName プレイヤー名
    **/
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[プレイヤー名:");
      buf.append(playerName + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
