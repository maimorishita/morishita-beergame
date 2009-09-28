//$Id: BeansBaseTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation.base;
import java.io.Serializable;

import java.util.*;

/**
 * �Q�[���̏���
**/
@SuppressWarnings("all")
abstract public class BaseJoinGameForm extends jp.rough_diamond.framework.web.struts.BaseForm implements Serializable {
    /**
     * ���O
    **/
    private  String   name;

    /**
     * �X�g���[��
    **/
    private transient java.io.InputStream   stream;

    /**
     * ���O���擾����
     * @return ���O
    **/
    public String getName() {
        return this.name;
    }

    /**
     * �X�g���[�����擾����
     * @return �X�g���[��
    **/
    public java.io.InputStream getStream() {
        return this.stream;
    }


    /**
     * ���O��ݒ肷��
     * @param name ���O
    **/
    public void setName(String name) {
        this.name = name;
    }

    /**
     * �X�g���[����ݒ肷��
     * @param stream �X�g���[��
    **/
    public void setStream(java.io.InputStream stream) {
        this.stream = stream;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[���O:");
      buf.append(name + "]");
      buf.append("[�X�g���[��:");
      buf.append(stream + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
