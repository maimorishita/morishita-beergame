package jp.co.isken.beerGame.entity;

import java.util.ArrayList;
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
		Role role = BasicService.getService().findByPK(Role.class, 3L);
		assertEquals("最終週でない週が取得されました。", 3L, role.getLastWeek("発注").longValue());
	}

	public void test現在週を取得するテスト() throws Exception {
		BasicService service = BasicService.getService();
		Role role = service.findByPK(Role.class, 1L);
		assertEquals("現在週でない週が取得されました。", 4L, role.getCurrentWeek("発注").longValue());

//		role = service.findByPK(Role.class, 6L);
//		// 一週目のため、取引記録がない場合
//		assertEquals("現在週でない週が取得されました。", 1L, role.getCurrentWeek("発注").longValue());
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

	// TODO 2009/11/29 imai タイムアウトしないと応答がなくなるため、コメントアウト
//	public void testキューのメッセージが正しく消されているか確認する() throws Exception {
//		BasicService service = BasicService.getService();
//		Role supplier1 = service.findByPK(Role.class, 2L);
//		supplier1.send(TransactionType.発注, "hogehoge");
//		supplier1.send(TransactionType.発注, "hohogege");
//		Role supplier2 = service.findByPK(Role.class, 3L);
//		supplier2.disposeAllMessage();
//		String ret = supplier2.receive(TransactionType.受注);
//		assertNull("メッセージが破棄されていません。", ret);
//	}

	// TODO 2009/11/29 imai タイムアウトしないと応答がなくなるため、コメントアウト
//	public void test異なるゲーム間でメッセージを送受信できないこと() throws Exception {
//		BasicService service = BasicService.getService();
//		Role supplier2 = service.findByPK(Role.class, 8L);
//		supplier2.disposeAllMessage();
//		// ゲーム3の卸1が発注を送信する
//		Role supplier1 = service.findByPK(Role.class, 2L);
//		supplier1.send(TransactionType.発注, "Hoge");
//		// ゲーム4の卸2が受注を受信する
//		supplier2 = service.findByPK(Role.class, 8L);
//		String ret = supplier2.receive(TransactionType.受注);
//		assertNull("メッセージが取得できています", ret);
//	}

	public void test発注の取引記録を登録する() throws Exception {
		BasicService service = BasicService.getService();
		// 発注の取引記録を登録する
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		Role role = BasicService.getService().findByPK(Role.class, 3L);
		role.order(2L);
		assertEquals("取得件数が誤っています。", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		assertEquals("数に誤りがあります", 2L, tradeTransaction.getAmount().longValue());
		assertEquals("ロールが間違ってます。", 3L, tradeTransaction.getRole().getId().longValue());
		assertEquals("週が間違ってます。", 4L, tradeTransaction.getWeek().longValue());
		assertEquals("取引種別が間違ってます。", TransactionType.発注.name(), tradeTransaction.getTransactionType());
	}

	public void test受注の取引記録を登録する() throws Exception {
		BasicService service = BasicService.getService();
		// 初期処理
		Role supplier2 = BasicService.getService().findByPK(Role.class, 3L);
		supplier2.disposeAllMessage();
		// 本処理
		Role supplier1 = service.findByPK(Role.class, 2L);
		supplier1.order(8L);
		// Role8Lの第三週から発注数を受け取る
		// 受注の取引記録を登録する
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		supplier2 = BasicService.getService().findByPK(Role.class, 3L);
		supplier2.acceptOrder();
		assertEquals("取得件数が誤っています。", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		assertEquals("数に誤りがあります", 8L, tradeTransaction.getAmount().longValue());
		assertEquals("ロールが間違ってます。", 3L, tradeTransaction.getRole().getId().longValue());
		assertEquals("週が間違ってます。", 5L, tradeTransaction.getWeek().longValue());
		assertEquals("取引種別が間違ってます。", TransactionType.受注.name(), tradeTransaction.getTransactionType());
	}
	
	public void test発注数を取得する() throws Exception {
		BasicService service = BasicService.getService();
		// 初期処理
		Role supplier2 = BasicService.getService().findByPK(Role.class, 3L);
		supplier2.disposeAllMessage();
		// 本処理
		Role supplier1 = service.findByPK(Role.class, 2L);
		supplier1.order(8L);
		supplier2 = BasicService.getService().findByPK(Role.class, 3L);
		assertEquals("発注数が誤っています。", 8L , supplier2.getOrderAmount().longValue());
	}

	public void test入荷の取引記録を登録する() throws Exception {
		BasicService service = BasicService.getService();
		// メーカーから卸2への入荷を想定する
		// 初期処理
		Role supplier2 = BasicService.getService().findByPK(Role.class, 9L);
		supplier2.disposeAllMessage();
		// 本処理
		// メーカーが受注記録をもとに出荷する
		Role maker = BasicService.getService().findByPK(Role.class, 10L);
		maker.outbound();
		// メーカーからの出荷データを受けて入荷の取引記録を登録する
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		supplier2 = BasicService.getService().findByPK(Role.class, 9L);
		supplier2.inbound();
		assertEquals("取得件数が誤っています。", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		// 入荷のトランザクションが正しく作られたか？
		assertEquals("数に誤りがあります", 4L, tradeTransaction.getAmount().longValue());
		assertEquals("ロールが間違ってます。", 9L, tradeTransaction.getRole().getId().longValue());
		assertEquals("週が間違ってます。", 1L, tradeTransaction.getWeek().longValue());
		assertEquals("取引種別が間違ってます。", TransactionType.入荷.name(), tradeTransaction.getTransactionType());
	}

	public void test出荷の取引記録を登録する() throws Exception {
		BasicService service = BasicService.getService();
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		// メーカーが受注記録をもとに出荷する
		Role maker = BasicService.getService().findByPK(Role.class, 10L);
		maker.outbound();
		// トランザクションの数が1つ増えているか？
		assertEquals("取得件数が誤っています。", count + 1, service.getCountByExtractor(new Extractor(TradeTransaction.class)));
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> list = service.findByExtractor(extractor);
		TradeTransaction tradeTransaction = list.get(0);
		// 出荷のトランザクションが正しく作られたか？
		assertEquals("数に誤りがあります", 4L, tradeTransaction.getAmount().longValue());
		assertEquals("ロールが間違ってます。", 10L, tradeTransaction.getRole().getId().longValue());
		assertEquals("週が間違ってます。", 1L, tradeTransaction.getWeek().longValue());
		assertEquals("取引種別が間違ってます。", TransactionType.出荷.name(), tradeTransaction.getTransactionType());
	}

	public void test上流のロールを取得する() throws Exception {
		BasicService service = BasicService.getService();
		Role market = service.findByPK(Role.class, 5L);
		assertEquals("ロールが市場ではありません", RoleType.市場.name(), market.getName());
		Role wholeSeller = market.getUpper();
		assertEquals("取得したロールのIDに誤りがあります", 1L, wholeSeller.getId().longValue());
		assertEquals("市場の上流である小売りを取得できていません", RoleType.小売り.name(), wholeSeller.getName());
		Role supplier1 = wholeSeller.getUpper();
		assertEquals("取得したロールのIDに誤りがあります", 2L, supplier1.getId().longValue());
		assertEquals("小売りの上流である卸１を取得できていません", RoleType.卸１.name(), supplier1.getName());
		Role supplier2 = supplier1.getUpper();
		assertEquals("取得したロールのIDに誤りがあります", 3L, supplier2.getId().longValue());
		assertEquals("卸１の上流である卸２を取得できていません", RoleType.卸２.name(), supplier2.getName());
		Role maker = supplier2.getUpper();
		assertEquals("取得したロールのIDに誤りがあります", 4L, maker.getId().longValue());
		assertEquals("卸２の上流であるメーカを取得できていません", RoleType.メーカ.name(), maker.getName());
		Role factory = maker.getUpper();
		assertEquals("取得したロールのIDに誤りがあります", 6L, factory.getId().longValue());
		assertEquals("メーカの上流である工場を取得できていません", RoleType.工場.name(), factory.getName());
		try {
			factory.getUpper();
			fail("工場の上流が取得できています");
		} catch (RuntimeException e) {
			assertTrue(true);
		}
	}

	public void test下流のロールを取得する() throws Exception {
		BasicService service = BasicService.getService();
		Role factory = service.findByPK(Role.class, 6L);
		assertEquals("ロールが工場ではありません", RoleType.工場.name(), factory.getName());
		Role maker = factory.getDowner();
		assertEquals("取得したロールのIDに誤りがあります", 4L, maker.getId().longValue());
		assertEquals("メーカの下流である卸２を取得できていません", RoleType.メーカ.name(), maker.getName());
		Role supplier2 = maker.getDowner();
		assertEquals("取得したロールのIDに誤りがあります", 3L, supplier2.getId().longValue());
		assertEquals("メーカの下流である卸２を取得できていません", RoleType.卸２.name(), supplier2.getName());
		Role supplier1 = supplier2.getDowner();
		assertEquals("取得したロールのIDに誤りがあります", 2L, supplier1.getId().longValue());
		assertEquals("卸２の下流である卸１を取得できていません", RoleType.卸１.name(), supplier1.getName());
		Role wholeSeller = supplier1.getDowner();
		assertEquals("取得したロールのIDに誤りがあります", 1L, wholeSeller.getId().longValue());
		assertEquals("卸１の下流である小売りを取得できていません", RoleType.小売り.name(), wholeSeller.getName());
		Role market = wholeSeller.getDowner();
		assertEquals("取得したロールのIDに誤りがあります", 5L, market.getId().longValue());
		assertEquals("小売りの下流である市場を取得できていません", RoleType.市場.name(), market.getName());
		try {
			market.getDowner();
			fail("市場の下流が取得できています");
		} catch (RuntimeException e) {
			assertTrue(true);
		}
	}

	//TODO 2009/11/29 imai&yoshioka 一時的なスタブだよーん☆
	public void test市場から小売りへの発注を固定値で返却する() throws Exception {
		BasicService service =  BasicService.getService();
		Role wholeSeller = service.findByPK(Role.class, 1L);
		assertEquals("市場からの発注値が誤っています。", 4L, wholeSeller.getOrderAmount().longValue());
	}
	
	//TODO 2009/11/29 imai&yoshioka 一時的なスタブだよーん☆
	public void testメーカからの発注数分を工場が出荷する() throws Exception {
		BasicService service =  BasicService.getService();
		Role maker = service.findByPK(Role.class, 4L);
		maker.disposeAllMessage();
		maker.order(5L);		
		assertEquals("工場からの出荷値が誤っています。", 5L, maker.getInboundAmount().longValue());
	}
	
	public void test各トランザクションが取得できる() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 3L);
		TradeTransaction tradeTransaction = role.getTransaction(TransactionType.入荷);
		assertEquals("正しい数量が取得出来ていません。",14L,tradeTransaction.getAmount().longValue());
		assertEquals("正しい週が取得出来ていません。",4L,tradeTransaction.getWeek().longValue());
		
		tradeTransaction = role.getTransaction(TransactionType.受注);
		assertEquals("正しい数量が取得出来ていません。",20L,tradeTransaction.getAmount().longValue());
		assertEquals("正しい週が取得出来ていません。",4L,tradeTransaction.getWeek().longValue());
		
		tradeTransaction = role.getTransaction(TransactionType.出荷);
		assertEquals("正しい数量が取得出来ていません。",20L,tradeTransaction.getAmount().longValue());
		assertEquals("正しい週が取得出来ていません。",4L,tradeTransaction.getWeek().longValue());
	}
	
	public void testゲームNOAHのロールのキューをすべて削除する() throws Exception {
		BasicService service =  BasicService.getService();
		List<Role> roles = new ArrayList<Role>();
		roles.add(service.findByPK(Role.class, 1L));
		roles.add(service.findByPK(Role.class, 2L));
		roles.add(service.findByPK(Role.class, 3L));
		roles.add(service.findByPK(Role.class, 4L));
		for (Role role : roles) {
			role.disposeAllMessage();
		}
	}
	
	public void test市場と工場以外のロールを取得する(){
		BasicService service =  BasicService.getService();
		Game game = service.findByPK(Game.class, 1L);
		List<Role> roles = Role.getRoles(game);
		assertEquals(4, roles.size());
		assertEquals("小売り", roles.get(0).getName());
		assertEquals("卸１", roles.get(1).getName());
		assertEquals("卸２", roles.get(2).getName());
		assertEquals("メーカ", roles.get(3).getName());	
	}
}
