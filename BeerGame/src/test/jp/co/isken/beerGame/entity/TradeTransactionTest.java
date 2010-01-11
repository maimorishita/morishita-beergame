package jp.co.isken.beerGame.entity;

import java.util.List;
import java.util.Map;

import jp.co.isken.beerGame.entity.TradeTransaction.TradeTransactionService;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class TradeTransactionTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void test“ü‰×—Ê‚ğŒvZ‚·‚é() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long result = TradeTransaction.calcAmount(10L, role, TransactionType.“ü‰×
				.name());
		assertEquals("“ü‰×”‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", 18, result.intValue());
	}

	public void testo‰×—Ê‚ğŒvZ‚·‚é() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long result = TradeTransaction.calcAmount(10L, role, TransactionType.o‰×
				.name());
		assertEquals("o‰×”‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", 26, result.intValue());
	}

	public void testİŒÉ—Ê‚ğZo‚·‚é() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltStock = TradeTransaction.calcAmountStock(10L, role);
		assertEquals("İŒÉ”‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", 4, rltStock.intValue());
	}

	public void testó’—Ê‚ğŒvZ‚·‚é() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltOrdered = TradeTransaction.calcAmount(10L, role,
				TransactionType.ó’.name());
		assertEquals("ó’”‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", 30, rltOrdered.intValue());
	}

	public void testó’c—Ê‚ğZo‚·‚é() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltStock = TradeTransaction.calcAmountRemain(4L, role);
		assertEquals("ó’”—Ê‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", 0, rltStock.intValue());
	}

	public void testİŒÉ—Ê‚ğƒŠƒXƒg‚Åæ“¾‚·‚é() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 2L);
		Map<Long, Long> rltStock = TradeTransaction.getStockList(10L, role);
		assertEquals("ƒŠƒXƒgo—Í‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", 10, rltStock.size());
		assertEquals("—İŒvİŒÉ‚ªŠÔˆá‚Á‚Ä‚¢‚Ü‚·B", 12, rltStock.get(1L).intValue());
		assertEquals("—İŒvİŒÉ‚ªŠÔˆá‚Á‚Ä‚¢‚Ü‚·B", 12, rltStock.get(2L).intValue());
		assertEquals("—İŒvİŒÉ‚ªŠÔˆá‚Á‚Ä‚¢‚Ü‚·B", 14, rltStock.get(3L).intValue());
		assertEquals("—İŒvİŒÉ‚ªŠÔˆá‚Á‚Ä‚¢‚Ü‚·B", 17, rltStock.get(4L).intValue());
		// 5T–ÚˆÈ~‚Íƒf[ƒ^‚ª‚È‚¢‚Ì‚ÅA‚ST–Ú‚Æ“¯‚¶‚É‚È‚Á‚Ä‚¢‚é
		assertEquals("—İŒvİŒÉ‚ªŠÔˆá‚Á‚Ä‚¢‚Ü‚·B", 17, rltStock.get(5L).intValue());
	}

	public void testƒ[ƒ‹‚ÆƒQ[ƒ€–¼‚ğˆø”‚É‚µ‚ÄİŒÉ‚ğæ“¾‚·‚é() throws Exception {
		Map<Long, Long> map = TradeTransaction.getStockAmount("NOAH",
				RoleType.‰µ‚Q.name());
		assertEquals("İŒÉ‚ğZo‚·‚éT‚Ì”‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 4, map.size());
		assertEquals("‚PT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 12, map.get(1L).intValue());
		assertEquals("‚QT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 12, map.get(2L).intValue());
		assertEquals("‚RT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 12, map.get(3L).intValue());
		assertEquals("‚ST–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 6, map.get(4L).intValue());
		map = TradeTransaction.getStockAmount("NOAH",
				RoleType.ƒ[ƒJ.name());
		assertEquals("İŒÉ‚ğZo‚·‚éT‚Ì”‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 4, map.size());
		assertEquals("‚PT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 12, map.get(1L).intValue());
		assertEquals("‚QT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 12, map.get(2L).intValue());
		assertEquals("‚RT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 10, map.get(3L).intValue());
		assertEquals("‚ST–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 0, map.get(4L).intValue());
//		map = TradeTransaction.getStockAmount("ƒOƒ‰ƒt‚ÌƒeƒXƒg—p",
//				RoleType.‰µ‚Q.name());
//		assertEquals("İŒÉ‚ğZo‚·‚éT‚Ì”‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 6, map.size());
//		assertEquals("‚PT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 29, map.get(1L).intValue());
//		assertEquals("‚QT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 29, map.get(2L).intValue());
//		assertEquals("‚RT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 24, map.get(3L).intValue());
//		assertEquals("‚ST–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 11, map.get(4L).intValue());
//		assertEquals("‚TT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", -4, map.get(5L).intValue());
//		assertEquals("‚UT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 9, map.get(6L).intValue());
	}

	public void test“ü‰×ó’o‰×”­’‚Ìƒgƒ‰ƒ“ƒUƒNƒVƒ‡ƒ“‚ğ‰i‘±‰»‚·‚é() throws Exception {
		// ‰Šúˆ—
		BasicService service = BasicService.getService();
		Game game = service.findByPK(Game.class, 8L);
		for (Role role : game.getRoles()) {
			if (role.isDisposable()) {
				role.disposeAllMessage();
			}
		}
		Role supplier1 = game.getRole(RoleType.‰µ‚P);
		supplier1.send(TransactionType.o‰×, "4");
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		// –{ˆ—
		Role wholeSaller = game.getRole(RoleType.¬”„‚è);
		TradeTransactionService.getService().addTransactions(wholeSaller, 2L);
		Extractor e = new Extractor(TradeTransaction.class);
		e.addOrder(Order.desc(new Property(TradeTransaction.ID)));
		List<TradeTransaction> transactions = service.findByExtractor(e);
		assertEquals("ƒgƒ‰ƒ“ƒUƒNƒVƒ‡ƒ“‚ÌŒ”‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", count + 4, transactions.size());

		assertEquals("ÅIT‚ğæ“¾‚Å‚«‚Ä‚¢‚Ü‚¹‚ñ", 1L, transactions.get(0).getWeek().longValue());
		assertEquals("³‚µ‚¢TradeTransaction‚ªæ“¾‚Å‚«‚Ä‚¢‚Ü‚¹‚ñ", TransactionType.”­’.name(), transactions.get(0).getTransactionType());
		assertEquals("æ“¾‚µ‚½”­’”‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 2L, transactions.get(0).getAmount().longValue());

		assertEquals("ÅIT‚ğæ“¾‚Å‚«‚Ä‚¢‚Ü‚¹‚ñ", 1L, transactions.get(1).getWeek().longValue());
		assertEquals("³‚µ‚¢TradeTransaction‚ªæ“¾‚Å‚«‚Ä‚¢‚Ü‚¹‚ñ", TransactionType.o‰×.name(), transactions.get(1).getTransactionType());
		assertEquals("æ“¾‚µ‚½o‰×”‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 4L, transactions.get(1).getAmount().longValue());

		assertEquals("ÅIT‚ğæ“¾‚Å‚«‚Ä‚¢‚Ü‚¹‚ñ", 1L, transactions.get(2).getWeek().longValue());
		assertEquals("³‚µ‚¢TradeTransaction‚ªæ“¾‚Å‚«‚Ä‚¢‚Ü‚¹‚ñ", TransactionType.ó’.name(), transactions.get(2).getTransactionType());
		assertEquals("æ“¾‚µ‚½ó’”‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 5L, transactions.get(2).getAmount().longValue());

		assertEquals("ÅIT‚ğæ“¾‚Å‚«‚Ä‚¢‚Ü‚¹‚ñ", 1L, transactions.get(3).getWeek().longValue());
		assertEquals("³‚µ‚¢TradeTransaction‚ªæ“¾‚Å‚«‚Ä‚¢‚Ü‚¹‚ñ", TransactionType.“ü‰×.name(), transactions.get(3).getTransactionType());
		assertEquals("æ“¾‚µ‚½“ü‰×”‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 4L, transactions.get(3).getAmount().longValue());
	}
}
