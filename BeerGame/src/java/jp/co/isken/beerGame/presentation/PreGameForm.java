//$Id: BeansTemplate.vm,v 1.1 2005/10/27 15:43:53 yamane Exp $
package jp.co.isken.beerGame.presentation;

import java.util.List;

import jp.co.isken.beerGame.entity.Player;
import jp.rough_diamond.commons.resource.Message;
import jp.rough_diamond.commons.resource.Messages;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.framework.transaction.VersionUnmuchException;


/**
 * @see jp.co.isken.beerGame.presentation.BasePreGameForm
 **/
public class PreGameForm extends
		jp.co.isken.beerGame.presentation.base.BasePreGameForm {
	private static final long serialVersionUID = 1L;

	public void init(){
		this.setOwnerName("");
		this.setTeamName("");
	}
	
	public boolean addGame() {
				
		List<Player> players = Player.createPlayers(this.getTeamName(), this.getOwnerName());		
		if(players.size() == 0 ){
			Messages msgs = new Messages();
			msgs.add("", new Message("errors.teamName.duplicate"));
			this.setMessage(msgs);
			return false;
		}
		try {
			//�i����{
			for(Player p : players){
				p.save();
			}
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
}