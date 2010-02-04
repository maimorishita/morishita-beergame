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
     * ゲーム
    **/
    private  jp.co.isken.beerGame.entity.Game   game;

    /**
     * ゲームID
    **/
    private  Long   gameId;

    /**
     * ロール
    **/
    private  jp.co.isken.beerGame.entity.Role   role;

    /**
     * ロールID
    **/
    private  Long   roleId;

    /**
     * チーム名
    **/
    private  String   teamName;

    /**
     * ゲームを取得する
     * @return ゲーム
    **/
    public jp.co.isken.beerGame.entity.Game getGame() {
        return this.game;
    }

    /**
     * ゲームIDを取得する
     * @return ゲームID
    **/
    public Long getGameId() {
        return this.gameId;
    }

    /**
     * ロールを取得する
     * @return ロール
    **/
    public jp.co.isken.beerGame.entity.Role getRole() {
        return this.role;
    }

    /**
     * ロールIDを取得する
     * @return ロールID
    **/
    public Long getRoleId() {
        return this.roleId;
    }

    /**
     * チーム名を取得する
     * @return チーム名
    **/
    public String getTeamName() {
        return this.teamName;
    }


    /**
     * ゲームを設定する
     * @param game ゲーム
    **/
    public void setGame(jp.co.isken.beerGame.entity.Game game) {
        this.game = game;
    }

    /**
     * ゲームIDを設定する
     * @param gameId ゲームID
    **/
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    /**
     * ロールを設定する
     * @param role ロール
    **/
    public void setRole(jp.co.isken.beerGame.entity.Role role) {
        this.role = role;
    }

    /**
     * ロールIDを設定する
     * @param roleId ロールID
    **/
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
      buf.append("[ゲーム:");
      buf.append(game + "]");
      buf.append("[ゲームID:");
      buf.append(gameId + "]");
      buf.append("[ロール:");
      buf.append(role + "]");
      buf.append("[ロールID:");
      buf.append(roleId + "]");
      buf.append("[チーム名:");
      buf.append(teamName + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
