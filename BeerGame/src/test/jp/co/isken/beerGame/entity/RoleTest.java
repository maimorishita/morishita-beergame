package jp.co.isken.beerGame.entity;

import java.util.List;

import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class RoleTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void test最終週を取得する() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 7L);
		int ret = role.getWeek();
		assertEquals("テスト失敗だよ☆", 3, ret);
	}

	// TODO 2009/10/24 imai なぜかメッセージが残ってしまうので、コメントアウト。だれか修正して。
//	public void testMQを送受信する() throws Exception {
//		Role role = BasicService.getService().findByPK(Role.class, 3L);
//		while(true) {
//			String ret = role.receive(SendType.受注);
//			if (ret == null) {
//				break;
//			} else {
//				System.out.println("残留物： " + ret);
//			}
//		}
//
//		BasicService service = BasicService.getService();
//		// 卸1から発注を送信する
//		Role supplier1 = service.findByPK(Role.class, 2L);
//		supplier1.send(SendType.発注, "Hoge");
//		// 卸2から受注を受信する
//		Role supplier2 = service.findByPK(Role.class, 3L);
//		String ret = supplier2.receive(SendType.受注);
//		assertEquals("メッセージに誤りがあります", "Hoge", ret);
//	}
	
	public void test異なるゲーム間でメッセージを送受信できないこと() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 3L);
		while(true) {
			String ret = role.receive(TransactionType.受注);
			if (ret == null) {
				break;
			} else {
				System.out.println("残留物： " + ret);
			}
		}

		// TODO ゲーム + ロール + 取引種別で一意のキューにすること
		BasicService service = BasicService.getService();
		// 卸1から発注を送信する
		Role supplier1 = service.findByPK(Role.class, 2L);
		supplier1.send(TransactionType.発注, "Hoge");
		// 卸2から受注を受信する
		Role supplier2 = service.findByPK(Role.class, 1L);
		String ret = supplier2.receive(TransactionType.受注);
		assertNull("メッセージが取得できています", ret);
	}

	public void test発注の取引記録を登録する() throws Exception {
		BasicService service = BasicService.getService();
		// 発注の取引記録を登録する
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		Role role = BasicService.getService().findByPK(Role.class, 2L);
		role.order(2L);
		assertEquals("取得件数が誤っています。", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		assertEquals("数に誤りがあります", 2L, tradeTransaction.getAmount().longValue());
		assertEquals("ロールが間違ってます。", 2L, tradeTransaction.getRole().getId().longValue());
		assertEquals("週が間違ってます。", 1L, tradeTransaction.getWeek().longValue());
		assertEquals("取引種別が間違ってます。", "発注", tradeTransaction.getTransactionType());
	}
}
