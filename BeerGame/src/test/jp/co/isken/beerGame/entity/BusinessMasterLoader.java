package jp.co.isken.beerGame.entity;

import jp.rough_diamond.commons.testing.DBInitializer;
import jp.rough_diamond.framework.service.ServiceLocator;

public class BusinessMasterLoader extends DBInitializer {
	/**
	 * インスタンス
	 */
	public final static DBInitializer INSTANCE = ServiceLocator
			.getService(BusinessMasterLoader.class);

	/**
	 * 初期処理
	 * 
	 * @throws Exception
	 */
	public static void init() throws Exception {
		MasterLoader.init();
		INSTANCE.load();
	}

	/**
	 * ロードするファイル名群
	 */
	final static String[] NAMES = new String[] {
			
			};

	public static String[] getNames() {
		return NAMES;
	}

	@Override
	protected String[] getResourceNames() {
		return getNames();
	}
}
