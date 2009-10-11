package jp.co.isken.beerGame.entity;

import jp.rough_diamond.commons.testing.DBInitializer;
import jp.rough_diamond.framework.service.ServiceLocator;

public class TransactionLoader extends DBInitializer {
	/**
	 * インスタンス
	 */
	public final static DBInitializer INSTANCE = ServiceLocator
			.getService(TransactionLoader.class);

	/**
	 * 初期処理
	 * 
	 * @throws Exception
	 */
	public static void init() throws Exception {
		BusinessMasterLoader.init();
		INSTANCE.load();
	}

	/**
	 * ロードするファイル名群
	 */
	final static String[] NAMES = new String[] {
		"jp/co/isken/beerGame/entity/ROLE.xls",
		"jp/co/isken/beerGame/entity/GAME.xls",
		"jp/co/isken/beerGame/entity/PLAYER.xls",
		"jp/co/isken/beerGame/entity/TRADE_TRANSACTION.xls",
		"jp/co/isken/beerGame/entity/CUSTMER_TRANSACTION.xls",
		"jp/co/isken/beerGame/entity/UNIT.xls",
	};

	public static String[] getNames() {
		return NAMES;
	}

	@Override
	protected String[] getResourceNames() {
		return getNames();
	}
}
