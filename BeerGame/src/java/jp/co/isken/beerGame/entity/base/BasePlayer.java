package jp.co.isken.beerGame.entity.base;

import  java.io.Serializable;



/**
 * プレイヤーのHibernateマッピングベースクラス
 * @hibernate.class
 *    table="PLAYER"
 *    realClass="jp.co.isken.beerGame.entity.Player"
**/
public abstract class BasePlayer  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * デフォルトコンストラクタ
    **/
    public BasePlayer() {
    }

    /**
     * OID
    **/ 
    private Long id;
    public final static String ID = "id";
    /**
     * OIDを取得する
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
     * OIDを設定する
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
     * オブジェクトを永続化する
     * 永続化ルールは以下の通りです。
     * <ul>
     *   <li>newした直後のオブジェクトの場合はinsert</li>
     *   <li>loadされたオブジェクトの場合はupdate</li>
     *   <li>loadされたオブジェクトでも主キーを差し替えた場合はinsert</li>
     *   <li>insertしたオブジェクトを再度saveした場合はupdate</li>
     *   <li>setLoadingFlagメソッドを呼び出した場合は強制的にupdate（非推奨）</li>
     * </ul>
     * @throws VersionUnmuchException   楽観的ロッキングエラー
     * @throws MessagesIncludingException 検証例外
    **/
    public void save() throws jp.rough_diamond.framework.transaction.VersionUnmuchException, jp.rough_diamond.commons.resource.MessagesIncludingException {
        if(isThisObjectAnUpdateObject()) {
            update();
        } else {
            insert();
        }
    }

    /**
     * このオブジェクトを永続化する方法を返却する。
     * 永続化処理実行時、本メソッドがtrueを返却された場合は更新(UPDATE)、falseの場合は登録(INSERT)して振る舞う
     * @return trueの場合は更新、falseの場合は登録として振る舞う
    **/
    protected boolean isThisObjectAnUpdateObject() {
        return isLoaded;
    }

    /**
     * オブジェクトを永続化する
     * @throws MessagesIncludingException 検証例外
    **/
    protected void insert() throws jp.rough_diamond.commons.resource.MessagesIncludingException {
        jp.rough_diamond.commons.service.BasicService.getService().insert(this);
    }

    /**
     * 永続化オブジェクトを更新する
     * @throws MessagesIncludingException 検証例外
     * @throws VersionUnmuchException   楽観的ロッキングエラー
    **/
    protected void update() throws jp.rough_diamond.framework.transaction.VersionUnmuchException, jp.rough_diamond.commons.resource.MessagesIncludingException {
        jp.rough_diamond.commons.service.BasicService.getService().update(this);
    }

    /**
     * オブジェクトの永続可能性を検証する
     * @return 検証結果。msgs.hasError()==falseが成立する場合は検証成功とみなす
    */
    public jp.rough_diamond.commons.resource.Messages validateObject() {
        if(isThisObjectAnUpdateObject()) {
            return validateObject(jp.rough_diamond.commons.service.WhenVerifier.UPDATE);
        } else {
            return validateObject(jp.rough_diamond.commons.service.WhenVerifier.INSERT);
        }
    }

    /**
     * オブジェクトの永続可能性を検証する
     * @return 検証結果。msgs.hasError()==falseが成立する場合は検証成功とみなす
    */
    protected jp.rough_diamond.commons.resource.Messages validateObject(jp.rough_diamond.commons.service.WhenVerifier when) {
        return jp.rough_diamond.commons.service.BasicService.getService().validate(this, when);
    }

    /**
     * プレイヤー名
    **/ 
    private String name;
    public final static String NAME = "name";

    /**
     * プレイヤー名を取得する
     * @hibernate.property
     *    column="NAME"
     *    not-null="true"
     *    length="64"
     * @return プレイヤー名
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=64, property="Player.name")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Player.name")
    public String getName() {
        return name;
    }

    /**
     * プレイヤー名を設定する
     * @param name  プレイヤー名
    **/
    public void setName(String name) {
        this.name = name;
    }
    /**
     * オーナーの場合はT。他はN
    **/ 
    private Boolean isOwner = Boolean.FALSE;
    public final static String IS_OWNER = "isOwnerInDB";

    /**
     * オーナーの場合はT。他はNを取得する
     * @hibernate.property
     *    column="IS_OWNER"
     *    not-null="true"
     * @return オーナーの場合はT。他はN
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=1, property="Player.isOwner")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Player.isOwner")
    public String getIsOwnerInDB() {
        return (isOwner) ? "Y" : "N";
    }

    /**
     * オーナーの場合はT。他はNを取得する
     * @return オーナーの場合はT。他はN
    **/
    public Boolean isIsOwner() {
        return isOwner;
    }

    /**
     * オーナーの場合はT。他はNを取得する
     * @return オーナーの場合はT。他はN
    **/
    public Boolean getIsOwner() {
        return isOwner;
    }


    /**
     * オーナーの場合はT。他はNを設定する
     * @param isOwner  オーナーの場合はT。他はN
    **/
    public void setIsOwnerInDB(String isOwner) {
        this.isOwner = ("Y".equalsIgnoreCase(isOwner));
    }

    /**
     * オーナーの場合はT。他はNを設定する
     * @param isOwner  オーナーの場合はT。他はN
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
