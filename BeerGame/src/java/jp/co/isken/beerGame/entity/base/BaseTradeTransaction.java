package jp.co.isken.beerGame.entity.base;

import  java.io.Serializable;



/**
 * 取引記録のHibernateマッピングベースクラス
 * @hibernate.class
 *    table="TRADE_TRANSACTION"
 *    realClass="jp.co.isken.beerGame.entity.TradeTransaction"
**/
public abstract class BaseTradeTransaction  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * デフォルトコンストラクタ
    **/
    public BaseTradeTransaction() {
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
        if(o instanceof BaseTradeTransaction) {
            if(hashCode() == o.hashCode()) {
                BaseTradeTransaction obj = (BaseTradeTransaction)o;
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
     * 取引種別
    **/ 
    private String transactionType;
    public final static String TRANSACTION_TYPE = "transactionType";

    /**
     * 取引種別を取得する
     * @hibernate.property
     *    column="TRANSACTION_TYPE"
     *    not-null="true"
     *    length="64"
     * @return 取引種別
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=64, property="TradeTransaction.transactionType")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="TradeTransaction.transactionType")
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * 取引種別を設定する
     * @param transactionType  取引種別
    **/
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    /**
     * 週
    **/ 
    private Long week;
    public final static String WEEK = "week";

    /**
     * 週を取得する
     * @hibernate.property
     *    column="WEEK"
     *    not-null="true"
     *    length="20"
     * @return 週
    **/
    public Long getWeek() {
        return week;
    }

    /**
     * 週を設定する
     * @param week  週
    **/
    public void setWeek(Long week) {
        this.week = week;
    }
    /**
     * 数
    **/ 
    private Long amount;
    public final static String AMOUNT = "amount";

    /**
     * 数を取得する
     * @hibernate.property
     *    column="AMOUNT"
     *    not-null="true"
     *    length="20"
     * @return 数
    **/
    public Long getAmount() {
        return amount;
    }

    /**
     * 数を設定する
     * @param amount  数
    **/
    public void setAmount(Long amount) {
        this.amount = amount;
    }
//ForeignProperties.vm start.

    
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
    @jp.rough_diamond.commons.service.annotation.NotNull(property="TradeTransaction.roleId")
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

//ForeignProperties.vm finish.
}
