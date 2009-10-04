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
     * 新規ゲームFLG
    **/
    private  boolean   newGame;

    /**
     * オーナー名
    **/
    private  String   ownerName;

    /**
     * プレイヤー名
    **/
    private  String   playerName;

    /**
     * チーム名
    **/
    private  String   teamName;

    /**
     * 新規ゲームFLGを取得する
     * @return 新規ゲームFLG
    **/
    public boolean isNewGame() {
        return this.newGame;
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
     * チーム名を取得する
     * @return チーム名
    **/
    public String getTeamName() {
        return this.teamName;
    }


    /**
     * 新規ゲームFLGを設定する
     * @param newGame 新規ゲームFLG
    **/
    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
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
     * チーム名を設定する
     * @param teamName チーム名
    **/
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[新規ゲームFLG:");
      buf.append(newGame + "]");
      buf.append("[オーナー名:");
      buf.append(ownerName + "]");
      buf.append("[プレイヤー名:");
      buf.append(playerName + "]");
      buf.append("[チーム名:");
      buf.append(teamName + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
