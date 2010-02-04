package jp.co.isken.beerGame.presentation;

import jp.co.isken.beerGame.entity.BusinessMasterLoader;
import jp.co.isken.beerGame.entity.MasterLoader;
import jp.co.isken.beerGame.entity.NumberingLoader;
import jp.co.isken.beerGame.entity.TransactionLoader;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class FormUtilTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		MasterLoader.init();
		BusinessMasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void testトリムを行うこと() throws Exception {
		// 半角スペース
		assertEquals("半角スペースが除去されていません", "", FormUtil.trim(" "));
		assertEquals("半角スペースが除去されていません", "a", FormUtil.trim(" a"));
		assertEquals("半角スペースが除去されていません", "a", FormUtil.trim("a "));
		assertEquals("半角スペースが除去されていません", "a", FormUtil.trim(" a "));
		assertEquals("半角スペースが除去されていません", "a a", FormUtil.trim(" a a "));
		assertEquals("半角スペースが除去されていません", "a a", FormUtil.trim("  a a  "));
		// 全角スペース
		assertEquals("全角スペースが除去されていません", "", FormUtil.trim("　"));
		assertEquals("全角スペースが除去されていません", "a", FormUtil.trim("　a"));
		assertEquals("全角スペースが除去されていません", "a", FormUtil.trim("a　"));
		assertEquals("全角スペースが除去されていません", "a", FormUtil.trim("　a　"));
		assertEquals("全角スペースが除去されていません", "a　a", FormUtil.trim("　a　a　"));
		assertEquals("全角スペースが除去されていません", "a　a", FormUtil.trim("　　a　a　　"));
		// 半角スペースと全角スペース
		assertEquals("半角スペースと全角スペースが除去されていません", "a 　a", FormUtil.trim(" 　 　a 　a 　 　 "));
		// null
		assertNull("nullが返却されていません", FormUtil.trim(null));
	}
	
	public void testNullとブランクを判定すること() {
		assertTrue("nullではないと言われています", FormUtil.isNullOrEmpty(null));
		assertTrue("ブランクではないと言われています", FormUtil.isNullOrEmpty(""));
		assertFalse("Nullまたはブランクだと言われています", FormUtil.isNullOrEmpty("a"));
		assertFalse("Nullまたはブランクだと言われています", FormUtil.isNullOrEmpty(" "));
		assertFalse("Nullまたはブランクだと言われています", FormUtil.isNullOrEmpty("　"));
	}
}
