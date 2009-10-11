package jp.co.isken.beerGame.entity.base;

import  java.io.Serializable;



/**
 * ロールのHibernateマッピングベースクラス
 * @hibernate.class
 *    table="ROLE"
 *    realClass="jp.co.isken.beerGame.entity.Role"
**/
public abstract class BaseRole  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * デフォルトコンストラクタ
    **/
    public BaseRole() {
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
        if(isLoaded) {
            update();
        } else {
            insert();
        }
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
     * ロール名
    **/ 
    private String name;
    public final static String NAME = "name";

    /**
     * ロール名を取得する
     * @hibernate.property
     *    column="NAME"
     *    not-null="true"
     *    length="64"
     * @return ロール名
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=64, property="Role.name")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Role.name")
    public String getName() {
        return name;
    }

    /**
     * ロール名を設定する
     * @param name  ロール名
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
     * Cookieを取得する
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
     * Cookieを設定する
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
