package jp.co.isken.beerGame.entity;

import javax.jms.JMSException;

import jp.rough_diamond.commons.service.BasicService;

public class QueueInitialize {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			QueueInitialize initialize = new QueueInitialize();
			initialize.init();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void init() throws JMSException {
		// ��ʏI������p�̃J�X�N���X
		Role wholeSeller = BasicService.getService().findByPK(Role.class, 22L);
		wholeSeller.getUpper().send(TransactionType.�o��, "10");
	}
}
