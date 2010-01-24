package jp.co.isken.beerGame.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.isken.beerGame.presentation.ViewStockForm;
import jp.rough_diamond.framework.web.struts.AllowRole;
import jp.rough_diamond.framework.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@AllowRole(isAllAccess = true)
public class ViewStockAction extends BaseAction {
	@Override
	public ActionForward unspecified(ActionMapping arg0, ActionForm arg1,
			HttpServletRequest arg2, HttpServletResponse arg3) throws Exception {
		return arg0.findForward("top");
	}
	
	public ActionForward init(ActionMapping arg0, ActionForm arg1,
			HttpServletRequest arg2, HttpServletResponse arg3) throws Exception {
		ViewStockForm form = (ViewStockForm) arg1;
		form.init();
		return arg0.findForward("top");
	}
	
	public ActionForward viewStock(ActionMapping arg0, ActionForm arg1,
			HttpServletRequest arg2, HttpServletResponse arg3) throws Exception {
		ViewStockForm form = (ViewStockForm) arg1;
		form.viewStock();
		return arg0.findForward("view");
	}
	
	public ActionForward result(ActionMapping arg0, ActionForm arg1,
			HttpServletRequest arg2, HttpServletResponse arg3) throws Exception {
		ViewStockForm form = (ViewStockForm) arg1;
		form.result();
		return arg0.findForward("result");
	}
}
