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
     * ゲーム
    **/
    private  jp.co.isken.beerGame.entity.Game   game;

    /**
     * ゲームID
    **/
    private  Long   gameId;

    /**
     * 新規ゲームFLG
    **/
    private  boolean   newGame;

    /**
     * 発注数
    **/
    private  String   order;

    /**
     * オーナー名
    **/
    private  String   ownerName;

    /**
     * プレイヤー名
    **/
    private  String   playerName;

    /**
     * ロール
    **/
    private  jp.co.isken.beerGame.entity.Role   role;

    /**
     * ロール名
    **/
    private  String   roleName;

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
     * 新規ゲームFLGを取得する
     * @return 新規ゲームFLG
    **/
    public boolean isNewGame() {
        return this.newGame;
    }

    /**
     * 発注数を取得する
     * @return 発注数
    **/
    public String getOrder() {
        return this.order;
    }

    /**
     * オーナー名を取得する
     * @return オーナー名
    **/
    public String getOwnerName() {
        return this.ownerName;
    }

    /**
     * プレイヤー名を取得する
     * @return プレイヤー名
    **/
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * ロールを取得する
     * @return ロール
    **/
    public jp.co.isken.beerGame.entity.Role getRole() {
        return this.role;
    }

    /**
     * ロール名を取得する
     * @return ロール名
    **/
    public String getRoleName() {
        return this.roleName;
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
     * 新規ゲームFLGを設定する
     * @param newGame 新規ゲームFLG
    **/
    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }

    /**
     * 発注数を設定する
     * @param order 発注数
    **/
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * オーナー名を設定する
     * @param ownerName オーナー名
    **/
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * プレイヤー名を設定する
     * @param playerName プレイヤー名
    **/
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * ロールを設定する
     * @param role ロール
    **/
    public void setRole(jp.co.isken.beerGame.entity.Role role) {
        this.role = role;
    }

    /**
     * ロール名を設定する
     * @param roleName ロール名
    **/
    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
      buf.append("[新規ゲームFLG:");
      buf.append(newGame + "]");
      buf.append("[発注数:");
      buf.append(order + "]");
      buf.append("[オーナー名:");
      buf.append(ownerName + "]");
      buf.append("[プレイヤー名:");
      buf.append(playerName + "]");
      buf.append("[ロール:");
      buf.append(role + "]");
      buf.append("[ロール名:");
      buf.append(roleName + "]");
      buf.append("[チーム名:");
      buf.append(teamName + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
