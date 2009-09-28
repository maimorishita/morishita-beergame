package jp.co.isken.beerGame.entity.base;




import  java.io.Serializable;


/**
 * ����L�^��Hibernate�}�b�s���O�x�[�X�N���X
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
     * ������
    **/ 
    private String transactionId;
    public final static String TRANSACTION_ID = "transactionId";

    /**
     * �����ʂ��擾����
     * @hibernate.property
     *    column="TRANSACTION_ID"
     *    not-null="true"
     *    length="64"
     * @return ������
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=64, property="TradeTransaction.transactionId")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="TradeTransaction.transactionId")
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * �����ʂ�ݒ肷��
     * @param transactionId  ������
    **/
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


    /**
     * �T
    **/ 
    private Long week;
    public final static String WEEK = "week";

    /**
     * �T���擾����
     * @hibernate.property
     *    column="WEEK"
     *    not-null="true"
     *    length="20"
     * @return �T
    **/
    public Long getWeek() {
        return week;
    }

    /**
     * �T��ݒ肷��
     * @param week  �T
    **/
    public void setWeek(Long week) {
        this.week = week;
    }


    /**
     * ��
    **/ 
    private Long amount;
    public final static String AMOUNT = "amount";

    /**
     * �����擾����
     * @hibernate.property
     *    column="AMOUNT"
     *    not-null="true"
     *    length="20"
     * @return ��
    **/
    public Long getAmount() {
        return amount;
    }

    /**
     * ����ݒ肷��
     * @param amount  ��
    **/
    public void setAmount(Long amount) {
        this.amount = amount;
    }


    /**
     * ���[��ID
    **/ 
    private Long roleId;
    public final static String ROLE_ID = "roleId";

    /**
     * ���[��ID���擾����
     * @hibernate.property
     *    column="ROLE_ID"
     *    not-null="true"
     * @return ���[��ID
    **/
    public Long getRoleId() {
        return roleId;
    }

    /**
     * ���[��ID��ݒ肷��
     * @param roleId  ���[��ID
    **/
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }



    
    private static final long serialVersionUID = 1L;
}
