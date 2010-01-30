package jp.co.isken.beerGame.entity.base;

import  java.io.Serializable;



/**
 * �v���C���[��Hibernate�}�b�s���O�x�[�X�N���X
 * @hibernate.class
 *    table="PLAYER"
 *    realClass="jp.co.isken.beerGame.entity.Player"
**/
public abstract class BasePlayer  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * �f�t�H���g�R���X�g���N�^
    **/
    public BasePlayer() {
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
        if(o instanceof BasePlayer) {
            if(hashCode() == o.hashCode()) {
                BasePlayer obj = (BasePlayer)o;
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
        if(isThisObjectAnUpdateObject()) {
            update();
        } else {
            insert();
        }
    }

    /**
     * ���̃I�u�W�F�N�g���i����������@��ԋp����B
     * �i�����������s���A�{���\�b�h��true��ԋp���ꂽ�ꍇ�͍X�V(UPDATE)�Afalse�̏ꍇ�͓o�^(INSERT)���ĐU�镑��
     * @return true�̏ꍇ�͍X�V�Afalse�̏ꍇ�͓o�^�Ƃ��ĐU�镑��
    **/
    protected boolean isThisObjectAnUpdateObject() {
        return isLoaded;
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
     * �I�u�W�F�N�g�̉i���\�������؂���
     * @return ���،��ʁBmsgs.hasError()==false����������ꍇ�͌��ؐ����Ƃ݂Ȃ�
    */
    public jp.rough_diamond.commons.resource.Messages validateObject() {
        if(isThisObjectAnUpdateObject()) {
            return validateObject(jp.rough_diamond.commons.service.WhenVerifier.UPDATE);
        } else {
            return validateObject(jp.rough_diamond.commons.service.WhenVerifier.INSERT);
        }
    }

    /**
     * �I�u�W�F�N�g�̉i���\�������؂���
     * @return ���،��ʁBmsgs.hasError()==false����������ꍇ�͌��ؐ����Ƃ݂Ȃ�
    */
    protected jp.rough_diamond.commons.resource.Messages validateObject(jp.rough_diamond.commons.service.WhenVerifier when) {
        return jp.rough_diamond.commons.service.BasicService.getService().validate(this, when);
    }

    /**
     * �v���C���[��
    **/ 
    private String name;
    public final static String NAME = "name";

    /**
     * �v���C���[�����擾����
     * @hibernate.property
     *    column="NAME"
     *    not-null="true"
     *    length="64"
     * @return �v���C���[��
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=64, property="Player.name")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Player.name")
    public String getName() {
        return name;
    }

    /**
     * �v���C���[����ݒ肷��
     * @param name  �v���C���[��
    **/
    public void setName(String name) {
        this.name = name;
    }
    /**
     * �I�[�i�[�̏ꍇ��T�B����N
    **/ 
    private Boolean isOwner = Boolean.FALSE;
    public final static String IS_OWNER = "isOwnerInDB";

    /**
     * �I�[�i�[�̏ꍇ��T�B����N���擾����
     * @hibernate.property
     *    column="IS_OWNER"
     *    not-null="true"
     * @return �I�[�i�[�̏ꍇ��T�B����N
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=1, property="Player.isOwner")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Player.isOwner")
    public String getIsOwnerInDB() {
        return (isOwner) ? "Y" : "N";
    }

    /**
     * �I�[�i�[�̏ꍇ��T�B����N���擾����
     * @return �I�[�i�[�̏ꍇ��T�B����N
    **/
    public Boolean isIsOwner() {
        return isOwner;
    }

    /**
     * �I�[�i�[�̏ꍇ��T�B����N���擾����
     * @return �I�[�i�[�̏ꍇ��T�B����N
    **/
    public Boolean getIsOwner() {
        return isOwner;
    }


    /**
     * �I�[�i�[�̏ꍇ��T�B����N��ݒ肷��
     * @param isOwner  �I�[�i�[�̏ꍇ��T�B����N
    **/
    public void setIsOwnerInDB(String isOwner) {
        this.isOwner = ("Y".equalsIgnoreCase(isOwner));
    }

    /**
     * �I�[�i�[�̏ꍇ��T�B����N��ݒ肷��
     * @param isOwner  �I�[�i�[�̏ꍇ��T�B����N
    **/
    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
    }
//ForeignProperties.vm start.

    
    private jp.co.isken.beerGame.entity.Game game;
    public final static String GAME = "game";

    /**
     * Get the associated Game object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "GAME_ID"
     *
     * @return the associated Game object
     */
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Player.gameId")
    public jp.co.isken.beerGame.entity.Game getGame() {
        return this.game;
    }

    /**
     * Declares an association between this object and a Game object
     *
     * @param v Game
     */
    public void setGame(jp.co.isken.beerGame.entity.Game v) {
        this.game = v;
    }

    @jp.rough_diamond.commons.service.annotation.PostLoad
    public void loadGame() {
        jp.co.isken.beerGame.entity.Game game = getGame();
        if(game != null) {
            Long pk = game.getId();
            setGame(
                    jp.rough_diamond.commons.service.BasicService.getService().findByPK(jp.co.isken.beerGame.entity.Game.class, pk)
            );
        }
    }

//ForeignProperties.vm finish.
}
