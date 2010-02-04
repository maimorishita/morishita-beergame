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
     * 受注
    **/
    private  Long   acceptOrder;

    /**
     * ゲーム
    **/
    private  jp.co.isken.beerGame.entity.Game   game;

    /**
     * ゲームID
    **/
    private  Long   gameId;

    /**
     * ID
    **/
    private  long   id;

    /**
     * 入荷
    **/
    private  Long   inbound;

    /**
     * 新規ゲームFLG
    **/
    private  boolean   newGame;

    /**
     * 発注数
    **/
    private  String   order;

    /**
     * 出荷
    **/
    private  Long   outbound;

    /**
     * オーナー名
    **/
    private  String   ownerName;

    /**
     * プレイヤー名
    **/
    private  String   playerName;

    /**
     * 注残
    **/
    private  Long   remain;

    /**
     * ロール
    **/
    private  jp.co.isken.beerGame.entity.Role   role;

    /**
     * ロールID
    **/
    private  Long   roleId;

    /**
     * ロール名
    **/
    private  String   roleName;

    /**
     * 在庫
    **/
    private  Long   stock;

    /**
     * チーム名
    **/
    private  String   teamName;

    /**
     * 受注を取得する
     * @return 受注
    **/
    public Long getAcceptOrder() {
        return this.acceptOrder;
    }

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
     * IDを取得する
     * @return ID
    **/
    public long getId() {
        return this.id;
    }

    /**
     * 入荷を取得する
     * @return 入荷
    **/
    public Long getInbound() {
        return this.inbound;
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
     * 出荷を取得する
     * @return 出荷
    **/
    public Long getOutbound() {
        return this.outbound;
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
     * 注残を取得する
     * @return 注残
    **/
    public Long getRemain() {
        return this.remain;
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
     * ロール名を取得する
     * @return ロール名
    **/
    public String getRoleName() {
        return this.roleName;
    }

    /**
     * 在庫を取得する
     * @return 在庫
    **/
    public Long getStock() {
        return this.stock;
    }

    /**
     * チーム名を取得する
     * @return チーム名
    **/
    public String getTeamName() {
        return this.teamName;
    }


    /**
     * 受注を設定する
     * @param acceptOrder 受注
    **/
    public void setAcceptOrder(Long acceptOrder) {
        this.acceptOrder = acceptOrder;
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
     * IDを設定する
     * @param id ID
    **/
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 入荷を設定する
     * @param inbound 入荷
    **/
    public void setInbound(Long inbound) {
        this.inbound = inbound;
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
     * 出荷を設定する
     * @param outbound 出荷
    **/
    public void setOutbound(Long outbound) {
        this.outbound = outbound;
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
     * 注残を設定する
     * @param remain 注残
    **/
    public void setRemain(Long remain) {
        this.remain = remain;
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
     * ロール名を設定する
     * @param roleName ロール名
    **/
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 在庫を設定する
     * @param stock 在庫
    **/
    public void setStock(Long stock) {
        this.stock = stock;
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
      buf.append("[受注:");
      buf.append(acceptOrder + "]");
      buf.append("[ゲーム:");
      buf.append(game + "]");
      buf.append("[ゲームID:");
      buf.append(gameId + "]");
      buf.append("[ID:");
      buf.append(id + "]");
      buf.append("[入荷:");
      buf.append(inbound + "]");
      buf.append("[新規ゲームFLG:");
      buf.append(newGame + "]");
      buf.append("[発注数:");
      buf.append(order + "]");
      buf.append("[出荷:");
      buf.append(outbound + "]");
      buf.append("[オーナー名:");
      buf.append(ownerName + "]");
      buf.append("[プレイヤー名:");
      buf.append(playerName + "]");
      buf.append("[注残:");
      buf.append(remain + "]");
      buf.append("[ロール:");
      buf.append(role + "]");
      buf.append("[ロールID:");
      buf.append(roleId + "]");
      buf.append("[ロール名:");
      buf.append(roleName + "]");
      buf.append("[在庫:");
      buf.append(stock + "]");
      buf.append("[チーム名:");
      buf.append(teamName + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
