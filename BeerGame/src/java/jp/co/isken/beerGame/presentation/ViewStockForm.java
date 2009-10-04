//$Id: BeansTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation;

import java.util.List;
import java.util.Map;

import jp.co.isken.beerGame.entity.Game;
import jp.co.isken.beerGame.entity.Role;
import jp.co.isken.beerGame.entity.TradeTransaction;

/**
 * @see jp.co.isken.beerGame.presentation.BaseViewStockForm
**/
public class ViewStockForm extends jp.co.isken.beerGame.presentation.base.BaseViewStockForm {
    private static final long serialVersionUID = 1L;
    
    public void init() {
		this.setTeamName("");
	}
    
    public List<Game> getGameAll(){
    	return Game.getAll();
    }
    
    public List<Role> getRoles(){
    	return Game.getRoles(Game.getGameByName(this.getTeamName()));
    }
    
    public Map<Integer, Integer> getStockList(String gameName, String roleName){
    	TradeTransaction trdTran = new TradeTransaction();
    	return trdTran.getStockAmount(gameName, roleName);
    	    }
    
    public void viewStock(){
    	
    }
}
