package jp.co.isken.beerGame.entity.base;




import  java.io.Serializable;


/**
 * ���[����Hibernate�}�b�s���O�x�[�X�N���X
 * @hibernate.class
 *    table="ROLE"
 *    realClass="jp.co.isken.beerGame.entity.Role"
**/
public abstract class BaseRole  implements Serializable {
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



    
    private static final long serialVersionUID = 1L;
}
