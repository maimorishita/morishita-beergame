//$Id: BeansBaseTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation.base;
import java.io.Serializable;

import java.util.*;

/**
 * 残高を参照する
**/
@SuppressWarnings("all")
abstract public class BaseViewStockForm extends jp.rough_diamond.framework.web.struts.BaseForm implements Serializable {
    /**
     * ゲームID
    **/
    private  Long   gameId;

    /**
     * チーム名
    **/
    private  String   teamName;

    /**
     * ゲームIDを取得する
     * @return ゲームID
    **/
    public Long getGameId() {
        return this.gameId;
    }

    /**
     * チーム名を取得する
     * @return チーム名
    **/
    public String getTeamName() {
        return this.teamName;
    }


    /**
     * ゲームIDを設定する
     * @param gameId ゲームID
    **/
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    /**
     * チーム名を設定する
     * @param teamName チーム名
    **/
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[ゲームID:");
      buf.append(gameId + "]");
      buf.append("[チーム名:");
      buf.append(teamName + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
