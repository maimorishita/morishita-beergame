package jp.co.isken.beerGame.entity.base;




import  java.io.Serializable;


/**
 * 取引記録のHibernateマッピングベースクラス
 * @hibernate.class
 *    table="TRADE_TRANSACTION"
 *    realClass="jp.co.isken.beerGame.entity.TradeTransaction"
**/
public abstract class BaseTradeTransaction  implements Serializable {
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
     * 取引種別
    **/ 
    private String transactionId;
    public final static String TRANSACTION_ID = "transactionId";

    /**
     * 取引種別を取得する
     * @hibernate.property
     *    column="TRANSACTION_ID"
     *    not-null="true"
     *    length="64"
     * @return 取引種別
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=64, property="TradeTransaction.transactionId")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="TradeTransaction.transactionId")
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * 取引種別を設定する
     * @param transactionId  取引種別
    **/
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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


    /**
     * ロールID
    **/ 
    private Long roleId;
    public final static String ROLE_ID = "roleId";

    /**
     * ロールIDを取得する
     * @hibernate.property
     *    column="ROLE_ID"
     *    not-null="true"
     * @return ロールID
    **/
    public Long getRoleId() {
        return roleId;
    }

    /**
     * ロールIDを設定する
     * @param roleId  ロールID
    **/
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }



    
    private static final long serialVersionUID = 1L;
}
