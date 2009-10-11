package jp.co.isken.beerGame.entity.base;

import  java.io.Serializable;



/**
 * ���[����Hibernate�}�b�s���O�x�[�X�N���X
 * @hibernate.class
 *    table="ROLE"
 *    realClass="jp.co.isken.beerGame.entity.Role"
**/
public abstract class BaseRole  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * �f�t�H���g�R���X�g���N�^
    **/
    public BaseRole() {
    }

    /**
     * OID
    **/ 
    private Long id;
    public final static String ID = "id";
    /**
     * OID���擾����
     * @hibernate.id
     *    generator-class="assigned"
     *    column="ID"
     *    not-null="true"
     * @return OID
    **/
    public Long getId() {
        return id;
    }

    /**
     * OID��ݒ肷��
     * @param id  OID
    **/
    public void setId(Long id) {
        this.id = id;
        isLoaded = false;
    }

    public int hashCode() {
        if(getId() == null) {
            return super.hashCode();
        } else {
            return getId().hashCode();
        }
    }
    
    public boolean equals(Object o) {
        if(o instanceof BaseRole) {
            if(hashCode() == o.hashCode()) {
                BaseRole obj = (BaseRole)o;
                if(getId() == null) {
                    return super.equals(o);
                }
                return getId().equals(obj.getId());
            }
        }
        return false;
    }

    protected boolean isLoaded;
    @jp.rough_diamond.commons.service.annotation.PostLoad
    @jp.rough_diamond.commons.service.annotation.PostPersist
    public void setLoadingFlag() {
        isLoaded = true;
    }

    /**
     * �I�u�W�F�N�g���i��������
     * �i�������[���͈ȉ��̒ʂ�ł��B
     * <ul>
     *   <li>new��������̃I�u�W�F�N�g�̏ꍇ��insert</li>
     *   <li>load���ꂽ�I�u�W�F�N�g�̏ꍇ��update</li>
     *   <li>load���ꂽ�I�u�W�F�N�g�ł���L�[�������ւ����ꍇ��insert</li>
     *   <li>insert�����I�u�W�F�N�g���ēxsave�����ꍇ��update</li>
     *   <li>setLoadingFlag���\�b�h���Ăяo�����ꍇ�͋����I��update�i�񐄏��j</li>
     * </ul>
     * @throws VersionUnmuchException   �y�ϓI���b�L���O�G���[
     * @throws MessagesIncludingException ���ؗ�O
    **/
    public void save() throws jp.rough_diamond.framework.transaction.VersionUnmuchException, jp.rough_diamond.commons.resource.MessagesIncludingException {
        if(isLoaded) {
            update();
        } else {
            insert();
        }
    }

    /**
     * �I�u�W�F�N�g���i��������
     * @throws MessagesIncludingException ���ؗ�O
    **/
    protected void insert() throws jp.rough_diamond.commons.resource.MessagesIncludingException {
        jp.rough_diamond.commons.service.BasicService.getService().insert(this);
    }

    /**
     * �i�����I�u�W�F�N�g���X�V����
     * @throws MessagesIncludingException ���ؗ�O
     * @throws VersionUnmuchException   �y�ϓI���b�L���O�G���[
    **/
    protected void update() throws jp.rough_diamond.framework.transaction.VersionUnmuchException, jp.rough_diamond.commons.resource.MessagesIncludingException {
        jp.rough_diamond.commons.service.BasicService.getService().update(this);
    }
    /**
     * ���[����
    **/ 
    private String name;
    public final static String NAME = "name";

    /**
     * ���[�������擾����
     * @hibernate.property
     *    column="NAME"
     *    not-null="true"
     *    length="64"
     * @return ���[����
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=64, property="Role.name")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Role.name")
    public String getName() {
        return name;
    }

    /**
     * ���[������ݒ肷��
     * @param name  ���[����
    **/
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Cookie
    **/ 
    private String cookie;
    public final static String COOKIE = "cookie";

    /**
     * Cookie���擾����
     * @hibernate.property
     *    column="COOKIE"
     *    not-null="false"
     *    length="256"
     * @return Cookie
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=256, property="Role.cookie")
    public String getCookie() {
        return cookie;
    }

    /**
     * Cookie��ݒ肷��
     * @param cookie  Cookie
    **/
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
//ForeignProperties.vm start.

    
    private jp.co.isken.beerGame.entity.Player player;
    public final static String PLAYER = "player";

    /**
     * Get the associated Player object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "PLAYER_ID"
     *
     * @return the associated Player object
     */
    public jp.co.isken.beerGame.entity.Player getPlayer() {
        return this.player;
    }

    /**
     * Declares an association between this object and a Player object
     *
     * @param v Player
     */
    public void setPlayer(jp.co.isken.beerGame.entity.Player v) {
        this.player = v;
    }

    @jp.rough_diamond.commons.service.annotation.PostLoad
    public void loadPlayer() {
        jp.co.isken.beerGame.entity.Player player = getPlayer();
        if(player != null) {
            Long pk = player.getId();
            setPlayer(
                    jp.rough_diamond.commons.service.BasicService.getService().findByPK(jp.co.isken.beerGame.entity.Player.class, pk)
            );
        }
    }

//ForeignProperties.vm finish.
}
