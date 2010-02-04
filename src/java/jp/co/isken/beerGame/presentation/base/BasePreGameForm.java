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
     * ��
    **/
    private  Long   acceptOrder;

    /**
     * �Q�[��
    **/
    private  jp.co.isken.beerGame.entity.Game   game;

    /**
     * �Q�[��ID
    **/
    private  Long   gameId;

    /**
     * ID
    **/
    private  long   id;

    /**
     * ����
    **/
    private  Long   inbound;

    /**
     * �V�K�Q�[��FLG
    **/
    private  boolean   newGame;

    /**
     * ������
    **/
    private  String   order;

    /**
     * �o��
    **/
    private  Long   outbound;

    /**
     * �I�[�i�[��
    **/
    private  String   ownerName;

    /**
     * �v���C���[��
    **/
    private  String   playerName;

    /**
     * ���c
    **/
    private  Long   remain;

    /**
     * ���[��
    **/
    private  jp.co.isken.beerGame.entity.Role   role;

    /**
     * ���[��ID
    **/
    private  Long   roleId;

    /**
     * ���[����
    **/
    private  String   roleName;

    /**
     * �݌�
    **/
    private  Long   stock;

    /**
     * �`�[����
    **/
    private  String   teamName;

    /**
     * �󒍂��擾����
     * @return ��
    **/
    public Long getAcceptOrder() {
        return this.acceptOrder;
    }

    /**
     * �Q�[�����擾����
     * @return �Q�[��
    **/
    public jp.co.isken.beerGame.entity.Game getGame() {
        return this.game;
    }

    /**
     * �Q�[��ID���擾����
     * @return �Q�[��ID
    **/
    public Long getGameId() {
        return this.gameId;
    }

    /**
     * ID���擾����
     * @return ID
    **/
    public long getId() {
        return this.id;
    }

    /**
     * ���ׂ��擾����
     * @return ����
    **/
    public Long getInbound() {
        return this.inbound;
    }

    /**
     * �V�K�Q�[��FLG���擾����
     * @return �V�K�Q�[��FLG
    **/
    public boolean isNewGame() {
        return this.newGame;
    }

    /**
     * ���������擾����
     * @return ������
    **/
    public String getOrder() {
        return this.order;
    }

    /**
     * �o�ׂ��擾����
     * @return �o��
    **/
    public Long getOutbound() {
        return this.outbound;
    }

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
     * ���c���擾����
     * @return ���c
    **/
    public Long getRemain() {
        return this.remain;
    }

    /**
     * ���[�����擾����
     * @return ���[��
    **/
    public jp.co.isken.beerGame.entity.Role getRole() {
        return this.role;
    }

    /**
     * ���[��ID���擾����
     * @return ���[��ID
    **/
    public Long getRoleId() {
        return this.roleId;
    }

    /**
     * ���[�������擾����
     * @return ���[����
    **/
    public String getRoleName() {
        return this.roleName;
    }

    /**
     * �݌ɂ��擾����
     * @return �݌�
    **/
    public Long getStock() {
        return this.stock;
    }

    /**
     * �`�[�������擾����
     * @return �`�[����
    **/
    public String getTeamName() {
        return this.teamName;
    }


    /**
     * �󒍂�ݒ肷��
     * @param acceptOrder ��
    **/
    public void setAcceptOrder(Long acceptOrder) {
        this.acceptOrder = acceptOrder;
    }

    /**
     * �Q�[����ݒ肷��
     * @param game �Q�[��
    **/
    public void setGame(jp.co.isken.beerGame.entity.Game game) {
        this.game = game;
    }

    /**
     * �Q�[��ID��ݒ肷��
     * @param gameId �Q�[��ID
    **/
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    /**
     * ID��ݒ肷��
     * @param id ID
    **/
    public void setId(long id) {
        this.id = id;
    }

    /**
     * ���ׂ�ݒ肷��
     * @param inbound ����
    **/
    public void setInbound(Long inbound) {
        this.inbound = inbound;
    }

    /**
     * �V�K�Q�[��FLG��ݒ肷��
     * @param newGame �V�K�Q�[��FLG
    **/
    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }

    /**
     * ��������ݒ肷��
     * @param order ������
    **/
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * �o�ׂ�ݒ肷��
     * @param outbound �o��
    **/
    public void setOutbound(Long outbound) {
        this.outbound = outbound;
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
     * ���c��ݒ肷��
     * @param remain ���c
    **/
    public void setRemain(Long remain) {
        this.remain = remain;
    }

    /**
     * ���[����ݒ肷��
     * @param role ���[��
    **/
    public void setRole(jp.co.isken.beerGame.entity.Role role) {
        this.role = role;
    }

    /**
     * ���[��ID��ݒ肷��
     * @param roleId ���[��ID
    **/
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * ���[������ݒ肷��
     * @param roleName ���[����
    **/
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * �݌ɂ�ݒ肷��
     * @param stock �݌�
    **/
    public void setStock(Long stock) {
        this.stock = stock;
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
      buf.append("[��:");
      buf.append(acceptOrder + "]");
      buf.append("[�Q�[��:");
      buf.append(game + "]");
      buf.append("[�Q�[��ID:");
      buf.append(gameId + "]");
      buf.append("[ID:");
      buf.append(id + "]");
      buf.append("[����:");
      buf.append(inbound + "]");
      buf.append("[�V�K�Q�[��FLG:");
      buf.append(newGame + "]");
      buf.append("[������:");
      buf.append(order + "]");
      buf.append("[�o��:");
      buf.append(outbound + "]");
      buf.append("[�I�[�i�[��:");
      buf.append(ownerName + "]");
      buf.append("[�v���C���[��:");
      buf.append(playerName + "]");
      buf.append("[���c:");
      buf.append(remain + "]");
      buf.append("[���[��:");
      buf.append(role + "]");
      buf.append("[���[��ID:");
      buf.append(roleId + "]");
      buf.append("[���[����:");
      buf.append(roleName + "]");
      buf.append("[�݌�:");
      buf.append(stock + "]");
      buf.append("[�`�[����:");
      buf.append(teamName + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
