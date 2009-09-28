package jp.co.isken.beerGame.entity;

import jp.rough_diamond.commons.testing.DBInitializer;
import jp.rough_diamond.framework.service.ServiceLocator;

public class NumberingLoader extends DBInitializer {
	/**
	 * �C���X�^���X
	 */
	public final static DBInitializer INSTANCE = ServiceLocator
			.getService(NumberingLoader.class);

	/**
	 * ��������
	 * 
	 * @throws Exception
	 */
	public static void init() throws Exception {
		Resetter.reset();
		INSTANCE.load();
	}

	/**
	 * ���[�h����t�@�C�����Q
	 */
	final static String[] NAMES = new String[] { "jp/co/isken/beerGame/entity/NUMBERING.xls", };

	public static String[] getNames() {
		return NAMES;
	}

	@Override
	protected String[] getResourceNames() {
		return getNames();
	}
}
