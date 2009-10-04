//$Id: BeansBaseTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation.base;
import java.io.Serializable;

/**
 * NullForm
**/
@SuppressWarnings("all")
abstract public class BaseNullForm extends jp.rough_diamond.framework.web.struts.BaseForm implements Serializable {

    public String toString() {
      StringBuffer buf = new StringBuffer();
      return buf.toString();
    }

    private static final long serialVersionUID = 1L;
}
