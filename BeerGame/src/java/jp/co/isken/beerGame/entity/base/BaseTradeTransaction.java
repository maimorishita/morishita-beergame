package jp.co.isken.beerGame.entity.base;

import  java.io.Serializable;



/**
 * ����L�^��Hibernate�}�b�s���O�x�[�X�N���X
 * @hibernate.class
 *    table="TRADE_TRANSACTION"
 *    realClass="jp.co.isken.beerGame.entity.TradeTransaction"
**/
public abstract class BaseTradeTransaction  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * �f�t�H���g�R���X�g���N�^
    **/
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
     * ������
    **/ 
    private String transactionType;
    public final static String TRANSACTION_TYPE = "transactionType";

    /**
     * �����ʂ��擾����
     * @hibernate.property
     *    column="TRANSACTION_TYPE"
     *    not-null="true"
     *    length="64"
     * @return ������
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=64, property="TradeTransaction.transactionType")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="TradeTransaction.transactionType")
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * �����ʂ�ݒ肷��
     * @param transactionType  ������
    **/
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
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
