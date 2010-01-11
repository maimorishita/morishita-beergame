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

	public void test�g�������s������() throws Exception {
		// ���p�X�y�[�X
		assertEquals("���p�X�y�[�X����������Ă��܂���", "", FormUtil.trim(" "));
		assertEquals("���p�X�y�[�X����������Ă��܂���", "a", FormUtil.trim(" a"));
		assertEquals("���p�X�y�[�X����������Ă��܂���", "a", FormUtil.trim("a "));
		assertEquals("���p�X�y�[�X����������Ă��܂���", "a", FormUtil.trim(" a "));
		assertEquals("���p�X�y�[�X����������Ă��܂���", "a a", FormUtil.trim(" a a "));
		assertEquals("���p�X�y�[�X����������Ă��܂���", "a a", FormUtil.trim("  a a  "));
		// �S�p�X�y�[�X
		assertEquals("�S�p�X�y�[�X����������Ă��܂���", "", FormUtil.trim("�@"));
		assertEquals("�S�p�X�y�[�X����������Ă��܂���", "a", FormUtil.trim("�@a"));
		assertEquals("�S�p�X�y�[�X����������Ă��܂���", "a", FormUtil.trim("a�@"));
		assertEquals("�S�p�X�y�[�X����������Ă��܂���", "a", FormUtil.trim("�@a�@"));
		assertEquals("�S�p�X�y�[�X����������Ă��܂���", "a�@a", FormUtil.trim("�@a�@a�@"));
		assertEquals("�S�p�X�y�[�X����������Ă��܂���", "a�@a", FormUtil.trim("�@�@a�@a�@�@"));
		// ���p�X�y�[�X�ƑS�p�X�y�[�X
		assertEquals("���p�X�y�[�X�ƑS�p�X�y�[�X����������Ă��܂���", "a �@a", FormUtil.trim(" �@ �@a �@a �@ �@ "));
		// null
		assertNull("null���ԋp����Ă��܂���", FormUtil.trim(null));
	}
	
	public void testNull�ƃu�����N�𔻒肷�邱��() {
		assertTrue("null�ł͂Ȃ��ƌ����Ă��܂�", FormUtil.isNullOrEmpty(null));
		assertTrue("�u�����N�ł͂Ȃ��ƌ����Ă��܂�", FormUtil.isNullOrEmpty(""));
		assertFalse("Null�܂��̓u�����N���ƌ����Ă��܂�", FormUtil.isNullOrEmpty("a"));
		assertFalse("Null�܂��̓u�����N���ƌ����Ă��܂�", FormUtil.isNullOrEmpty(" "));
		assertFalse("Null�܂��̓u�����N���ƌ����Ă��܂�", FormUtil.isNullOrEmpty("�@"));
	}
}
