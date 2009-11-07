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
		assertEquals("テスト失敗だよ☆", 4L, role.getWeek("発注").longValue());
	}

	public void test現在週を取得するテスト() throws Exception {
		BasicService service = BasicService.getService();
		Role role = service.findByPK(Role.class, 9L);
		assertEquals("現在週でない週が取得されました。", 4L, role.getCurrentWeek("発注").longValue());

		role = service.findByPK(Role.class, 6L);
		// 一週目のため、取引記録がない場合
		assertEquals("現在週でない週が取得されました。", 1L, role.getCurrentWeek("発注").longValue());
	}

	public void testMQを送受信する() throws Exception {
		BasicService service = BasicService.getService();
		Role role = service.findByPK(Role.class, 3L);
		role.disposeAllMessage();
		// 卸1から発注を送信する
		Role supplier1 = service.findByPK(Role.class, 2L);
		supplier1.send(TransactionType.発注, "Hoge");
		// 卸2から受注を受信する
		Role supplier2 = service.findByPK(Role.class, 3L);
		String ret = supplier2.receive(TransactionType.受注);
		assertEquals("メッセージに誤りがあります", "Hoge", ret);
	}
	
	// TODO 2009/11/07 ogasawara,yoshioka テストが重くなるので，一旦コメントアウトします
//	public void testキューのメッセージが正しく消されているか確認する() throws Exception {
//		BasicService service = BasicService.getService();
//		Role supplier1 = service.findByPK(Role.class, 2L);
//		supplier1.send(TransactionType.発注, "hogehoge");
//		supplier1.send(TransactionType.発注, "hohogege");
//		supplier1.disposeAllMessage();
//		Role supplier2 = service.findByPK(Role.class, 3L);
//		String ret = supplier2.receive(TransactionType.受注);
//		assertNull("メッセージが破棄されていません。", ret);		
//	}
	
	// TODO 2009/11/07 imai 上のテストを直したら、こっちが動かない
	// Queueが違うはずだから取れないはずなのに、取れてしまう
//	public void test異なるゲーム間でメッセージを送受信できないこと() throws Exception {
//		BasicService service = BasicService.getService();
//		Role role = service.findByPK(Role.class, 1L);
//		role.disposeAllMessage();
//		// TODO ゲーム + ロール + 取引種別で一意のキューにすること
//		// 卸1から発注を送信する
//		Role supplier1 = service.findByPK(Role.class, 2L);
//		supplier1.send(TransactionType.発注, "Hoge");
//		// 卸2から受注を受信する
//		Role supplier2 = service.findByPK(Role.class, 1L);
//		String ret = supplier2.receive(TransactionType.受注);
//		assertNull("メッセージが取得できています", ret);
//	}

	public void test発注の取引記録を登録する() throws Exception {
		BasicService service = BasicService.getService();
		// 発注の取引記録を登録する
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		Role role = BasicService.getService().findByPK(Role.class, 9L);
		role.order(2L);
		assertEquals("取得件数が誤っています。", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		assertEquals("数に誤りがあります", 2L, tradeTransaction.getAmount().longValue());
		assertEquals("ロールが間違ってます。", 9L, tradeTransaction.getRole().getId().longValue());
		assertEquals("週が間違ってます。", 4L, tradeTransaction.getWeek().longValue());
		assertEquals("取引種別が間違ってます。", TransactionType.発注.name(), tradeTransaction.getTransactionType());
	}

	public void test受注の取引記録を登録する() throws Exception {
		BasicService service = BasicService.getService();
		// Role8Lの第三週から発注数を受け取る
		// 受注の取引記録を登録する
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		Role role = BasicService.getService().findByPK(Role.class, 9L);
		role.acceptOrder();
		assertEquals("取得件数が誤っています。", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		assertEquals("数に誤りがあります", 8L, tradeTransaction.getAmount().longValue());
		assertEquals("ロールが間違ってます。", 9L, tradeTransaction.getRole().getId().longValue());
		assertEquals("週が間違ってます。", 4L, tradeTransaction.getWeek().longValue());
		assertEquals("取引種別が間違ってます。", TransactionType.受注.name(), tradeTransaction.getTransactionType());
	}
	
	public void test発注数を取得する() throws Exception {
		// TODO MQで取得するが、MQ未実装の為DBから引っ張ってます。
		Role role = BasicService.getService().findByPK(Role.class, 9L);
		assertEquals("発注数が誤っています。", 8L , role.getOrderCount().longValue());
	}

	public void test入荷の取引記録を登録する() throws Exception {
		BasicService service = BasicService.getService();
		// Role10Lの第三週から出荷数を受け取る
		// 入荷の取引記録を登録する
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		Role role = BasicService.getService().findByPK(Role.class, 9L);
		role.inbound();
		assertEquals("取得件数が誤っています。", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		assertEquals("数に誤りがあります", 10L, tradeTransaction.getAmount().longValue());
		assertEquals("ロールが間違ってます。", 9L, tradeTransaction.getRole().getId().longValue());
		assertEquals("週が間違ってます。", 4L, tradeTransaction.getWeek().longValue());
		assertEquals("取引種別が間違ってます。", TransactionType.入荷.name(), tradeTransaction.getTransactionType());
	}

	public void test出荷の取引記録を登録する() throws Exception {
		BasicService service = BasicService.getService();
		// Role10Lの第三週から出荷数を受け取る 10L
		// 現在在庫を計算する 25L
		// Role8Lの発注数を受け取る 5L
		// 出荷の取引記録を登録する 5L
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		Role role = BasicService.getService().findByPK(Role.class, 9L);
		role.outbound();
		assertEquals("取得件数が誤っています。", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		assertEquals("数に誤りがあります", 5L, tradeTransaction.getAmount().longValue());
		assertEquals("ロールが間違ってます。", 9L, tradeTransaction.getRole().getId().longValue());
		assertEquals("週が間違ってます。", 4L, tradeTransaction.getWeek().longValue());
		assertEquals("取引種別が間違ってます。", TransactionType.出荷.name(), tradeTransaction.getTransactionType());
	}
}
