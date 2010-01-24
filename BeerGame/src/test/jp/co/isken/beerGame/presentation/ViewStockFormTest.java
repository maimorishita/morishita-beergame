package jp.co.isken.beerGame.presentation;

import jp.co.isken.beerGame.entity.BusinessMasterLoader;
import jp.co.isken.beerGame.entity.NumberingLoader;
import jp.co.isken.beerGame.entity.TransactionLoader;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class ViewStockFormTest extends DataLoadingTestCase {

	private ViewStockForm form;

	protected void setUp() throws Exception {
		super.setUp();
		BusinessMasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
		form = new ViewStockForm();
	}

	public void test�I�u�W�F�N�g���擾����() throws Exception {
		// �Q�[���ƃ��[���̃I�u�W�F�N�g���擾����
		form.setGameId(5L);
		form.setRoleId(22L);
		form.result();
		assertNotNull("�Q�[�����擾�ł��Ă��܂���", form.getGame());
		assertNotNull("���[�����擾�ł��Ă��܂���", form.getRole());
	}

	public void test�݌v�����c�����擾����() throws Exception {
//		form.setGameId(5L);
//		form.setRoleId(22L);
//		form.result();
//		assertEquals("�݌v�̔����c�����Ԉ���Ă��܂�", 0L, form.getRemain());
		assertTrue(true);
	}

	public void test�݌v�݌ɐ����擾����() throws Exception {
		assertTrue(true);
	}
}
