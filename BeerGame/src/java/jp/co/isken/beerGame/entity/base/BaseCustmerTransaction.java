package jp.co.isken.beerGame.entity.base;

import java.io.Serializable;



/**
 * 顧客取引のHibernateマッピングベースクラス
 * @hibernate.joined-subclass-gg
 *    realClass="jp.co.isken.beerGame.entity.CustmerTransaction"
 *    table="CUSTMER_TRANSACTION"
 * @hibernate.joined-subclass-key
 *    column="TRADE_TRANSACTION_ID"
**/
public abstract class BaseCustmerTransaction extends jp.co.isken.beerGame.entity.TradeTransaction implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * デフォルトコンストラクタ
    **/
    public BaseCustmerTransaction() {
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
        if(o instanceof BaseCustmerTransaction) {
            if(hashCode() == o.hashCode()) {
                BaseCustmerTransaction obj = (BaseCustmerTransaction)o;
                if(getId() == null) {
                    return super.equals(o);
                }
                return getId().equals(obj.getId());
            }
        }
        return false;
    }
//ForeignProperties.vm start.

    
//ForeignProperties.vm finish.
}
