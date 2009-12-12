package jp.co.isken.beerGame.entity;

import java.util.List;

import javax.jms.JMSException;

import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.service.BasicService;

public class QueueResetter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			QueueResetter resetter = new QueueResetter();
			resetter.disposeAllMessage();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * MQのメッセージを初期化します。
	 * @throws JMSException
	 */
	public void disposeAllMessage() throws JMSException {
		Extractor extractor = new Extractor(Role.class);
		extractor.add(Condition.in(Role.NAME, RoleType.小売り.name(), RoleType.卸１.name(),RoleType.卸２.name(),RoleType.メーカ.name()));
		List<Role> roles = BasicService.getService().findByExtractor(extractor);
		for (Role role : roles) {
			role.disposeAllMessage();
		}
	}
}
