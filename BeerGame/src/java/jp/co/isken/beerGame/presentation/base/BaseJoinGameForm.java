//$Id: BeansBaseTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation.base;
import java.io.Serializable;

import java.util.*;

/**
 * ゲームの準備
**/
@SuppressWarnings("all")
abstract public class BaseJoinGameForm extends jp.rough_diamond.framework.web.struts.BaseForm implements Serializable {
    /**
     * 名前
    **/
    private  String   name;

    /**
     * ストリーム
    **/
    private transient java.io.InputStream   stream;

    /**
     * 名前を取得する
     * @return 名前
    **/
    public String getName() {
        return this.name;
    }

    /**
     * ストリームを取得する
     * @return ストリーム
    **/
    public java.io.InputStream getStream() {
        return this.stream;
    }


    /**
     * 名前を設定する
     * @param name 名前
    **/
    public void setName(String name) {
        this.name = name;
    }

    /**
     * ストリームを設定する
     * @param stream ストリーム
    **/
    public void setStream(java.io.InputStream stream) {
        this.stream = stream;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[名前:");
      buf.append(name + "]");
      buf.append("[ストリーム:");
      buf.append(stream + "]");
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
