package jp.co.isken.beerGame.entity.base;




import  java.io.Serializable;


/**
 * プレイヤーのHibernateマッピングベースクラス
 * @hibernate.class
 *    table="PLAYER"
 *    realClass="jp.co.isken.beerGame.entity.Player"
**/
public abstract class BasePlayer  implements Serializable {
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

    private boolean isLoaded;
    @jp.rough_diamond.commons.service.annotation.PostLoad
    @jp.rough_diamond.commons.service.annotation.PostPersist
    public void setLoadingFlag() {
        isLoaded = true;
    }

    public void save() throws jp.rough_diamond.framework.transaction.VersionUnmuchException, jp.rough_diamond.commons.resource.MessagesIncludingException {
        if(isLoaded) {
            jp.rough_diamond.commons.service.BasicService.getService().update(this);
        } else {
            jp.rough_diamond.commons.service.BasicService.getService().insert(this);
        }
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
     *    not-null="false"
     *    length="64"
     * @return プレイヤー名
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=64, property="Player.name")
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
     * チーム名
    **/ 
    private String teamName;
    public final static String TEAM_NAME = "teamName";

    /**
     * チーム名を取得する
     * @hibernate.property
     *    column="TEAM_NAME"
     *    not-null="true"
     *    length="64"
     * @return チーム名
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=64, property="Player.teamName")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Player.teamName")
    public String getTeamName() {
        return teamName;
    }

    /**
     * チーム名を設定する
     * @param teamName  チーム名
    **/
    public void setTeamName(String teamName) {
        this.teamName = teamName;
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
     *    not-null="false"
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



    
    private jp.co.isken.beerGame.entity.Role role;
    public final static String ROLE = "role";

    /**
     * Get the associated Role object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "ROLE_ID"
     *
     * @return the associated Role object
     */
    public jp.co.isken.beerGame.entity.Role getRole() {
        return this.role;
    }

    /**
     * Declares an association between this object and a Role object
     *
     * @param v Role
     */
    public void setRole(jp.co.isken.beerGame.entity.Role v) {
        this.role = v;
    }

    @jp.rough_diamond.commons.service.annotation.PostLoad
    public void loadRole() {
        jp.co.isken.beerGame.entity.Role role = getRole();
        if(role != null) {
            Long pk = role.getId();
            setRole(
                    jp.rough_diamond.commons.service.BasicService.getService().findByPK(jp.co.isken.beerGame.entity.Role.class, pk)
            );
        }
    }

    private static final long serialVersionUID = 1L;
}
