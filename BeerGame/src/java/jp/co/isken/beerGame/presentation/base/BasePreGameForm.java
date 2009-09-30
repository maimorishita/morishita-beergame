//$Id: BeansBaseTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation.base;
import java.io.Serializable;

import java.util.*;

/**
 * ゲームを登録する。
**/
@SuppressWarnings("all")
abstract public class BasePreGameForm extends jp.rough_diamond.framework.web.struts.BaseForm implements Serializable {
    /**
     * オーナー名
    **/
    private  String   ownerName;

    /**
     * チーム名
    **/
    private  String   teamName;

    /**
     * オーナー名を取得する
     * @return オーナー名
    **/
    public String getOwnerName() {
        return this.ownerName;
    }

    /**
     * チーム名を取得する
     * @return チーム名
    **/
    public String getTeamName() {
        return this.teamName;
    }


    /**
     * オーナー名を設定する
     * @param ownerName オーナー名
    **/
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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
      buf.append("[オーナー名:");
      buf.append(ownerName + "]");
      buf.append("[チーム名:");
      buf.append(teamName + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
