//$Id: BeansTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation;

import java.util.List;

import jp.co.isken.beerGame.entity.Player;
import jp.rough_diamond.commons.resource.Message;
import jp.rough_diamond.commons.resource.Messages;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.framework.transaction.VersionUnmuchException;

/**
 * @see jp.co.isken.beerGame.presentation.BasePlayerEntryForm
**/
public class PlayerEntryForm extends jp.co.isken.beerGame.presentation.base.BasePlayerEntryForm {
    private static final long serialVersionUID = 1L;
    
	/**
	 * プレイヤーを登録する。
	 * 
	 * @return boolean
	 */
	public boolean EntryPlayer() {
		try {
			this.getPlayer().save();
		} catch (VersionUnmuchException e) {
			Messages msgs = new Messages();
			msgs.add("", new Message(e.getMessage()));
			this.setMessage(msgs);
			return false;
		} catch (MessagesIncludingException e) {
			Messages msgs = new Messages();
			msgs.add("", new Message(e.getMessage()));
			this.setMessage(msgs);
			return false;
		}
		return true;
	}

	public void init() {
		// TODO Auto-generated method stub
	}
	
	@SuppressWarnings("static-access")
	public List<Player> getTeamList() {
		return this.getPlayer().getTeamList();		
	}
}
