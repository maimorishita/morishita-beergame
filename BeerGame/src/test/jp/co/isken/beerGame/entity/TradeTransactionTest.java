package jp.co.isken.beerGame.entity;

import java.util.List;
import java.util.Map;

import jp.co.isken.beerGame.entity.TradeTransaction.TradeTransactionService;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.resource.Messages;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.service.WhenVerifier;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class TradeTransactionTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void test“ü‰×—Ê‚Ì—İÏ‚ğŒvZ‚·‚é() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long result = TradeTransaction.calcAmount(10L, role, TransactionType.“ü‰×.name());
		assertEquals("“ü‰×”‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", 42, result.intValue());
	}

	public void testó’—Ê‚Ì—İÏ‚ğŒvZ‚·‚é() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltOrdered = TradeTransaction.calcAmount(10L, role, TransactionType.ó’.name());
		assertEquals("ó’”‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", 24, rltOrdered.intValue());
	}

	public void testo‰×—Ê‚Ì—İÏ‚ğŒvZ‚·‚é() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long result = TradeTransaction.calcAmount(10L, role, TransactionType.o‰×.name());
		assertEquals("o‰×”‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", 24, result.intValue());
	}

	public void testİŒÉ—Ê‚ğZo‚·‚é() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 1L);
		Long rltStock = TradeTransaction.calcAmountStock(10L, role);
		assertEquals("İŒÉ”‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", 30, rltStock.intValue());
	}

	public void testó’c—Ê‚ğZo‚·‚é() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 3L);
		Long rltStock = TradeTransaction.calcAmountRemain(5L, role);
		assertEquals("ó’”—Ê‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", -6, rltStock.intValue());
	}

	public void testİŒÉ—Ê‚ğƒŠƒXƒg‚Åæ“¾‚·‚é() throws Exception {
		Role role = BasicService.getService().findByPK(Role.class, 2L);
		Map<Long, Long> rltStock = TradeTransaction.getStockList(5L, role);
		assertEquals("ƒŠƒXƒgo—Í‚ªŒë‚Á‚Ä‚¢‚Ü‚·B", 5, rltStock.size());
		assertEquals("—İŒvİŒÉ‚ªŠÔˆá‚Á‚Ä‚¢‚Ü‚·B", 12, rltStock.get(1L).intValue());
		assertEquals("—İŒvİŒÉ‚ªŠÔˆá‚Á‚Ä‚¢‚Ü‚·B", 14, rltStock.get(2L).intValue());
		assertEquals("—İŒvİŒÉ‚ªŠÔˆá‚Á‚Ä‚¢‚Ü‚·B", 8, rltStock.get(3L).intValue());
		assertEquals("—İŒvİŒÉ‚ªŠÔˆá‚Á‚Ä‚¢‚Ü‚·B", 2, rltStock.get(4L).intValue());
		// 5T–ÚˆÈ~‚Íƒf[ƒ^‚ª‚È‚¢‚Ì‚ÅA‚ST–Ú‚Æ“¯‚¶‚É‚È‚Á‚Ä‚¢‚é
		assertEquals("—İŒvİŒÉ‚ªŠÔˆá‚Á‚Ä‚¢‚Ü‚·B", -4, rltStock.get(5L).intValue());
	}

	public void testƒ[ƒ‹‚ÆƒQ[ƒ€–¼‚ğˆø”‚É‚µ‚ÄİŒÉ‚ğæ“¾‚·‚é() throws Exception {
		Map<Long, Long> map = TradeTransaction.getStockAmount("NOAH",RoleType.‰µ‚Q.name());
		assertEquals("İŒÉ‚ğZo‚·‚éT‚Ì”‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 5, map.size());
		assertEquals("‚PT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 12, map.get(1L).intValue());
		assertEquals("‚QT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 12, map.get(2L).intValue());
		assertEquals("‚RT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 6, map.get(3L).intValue());
		assertEquals("‚ST–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 0, map.get(4L).intValue());
		assertEquals("‚TT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", -6, map.get(5L).intValue());
		
		map = TradeTransaction.getStockAmount("NOAH", RoleType.ƒ[ƒJ.name());
		assertEquals("İŒÉ‚ğZo‚·‚éT‚Ì”‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 5, map.size());
		assertEquals("‚PT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 12, map.get(1L).intValue());
		assertEquals("‚QT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 14, map.get(2L).intValue());
		assertEquals("‚RT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 16, map.get(3L).intValue());
		assertEquals("‚ST–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 18, map.get(4L).intValue());
		assertEquals("‚TT–Ú‚ÌİŒÉ‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 20, map.get(5L).intValue());
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
		Role wholeSeller = game.getRole(RoleType.¬”„‚è);
		wholeSeller.getUpper().send(TransactionType.o‰×, "4");
		long count = service.getCountByExtractor(new Extractor(TradeTransaction.class));
		// –{ˆ—
		TradeTransactionService.getService().addTransactions(wholeSeller, 2L);
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
		assertEquals("æ“¾‚µ‚½ó’”‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 4L, transactions.get(2).getAmount().longValue());

		assertEquals("ÅIT‚ğæ“¾‚Å‚«‚Ä‚¢‚Ü‚¹‚ñ", 1L, transactions.get(3).getWeek().longValue());
		assertEquals("³‚µ‚¢TradeTransaction‚ªæ“¾‚Å‚«‚Ä‚¢‚Ü‚¹‚ñ", TransactionType.“ü‰×.name(), transactions.get(3).getTransactionType());
		assertEquals("æ“¾‚µ‚½“ü‰×”‚ÉŒë‚è‚ª‚ ‚è‚Ü‚·", 4L, transactions.get(3).getAmount().longValue());
	}
	
	public void test‰i‘±‰»‚ÌŒŸØ‚ğs‚¤() throws Exception {
		BasicService service = BasicService.getService();
		TradeTransaction transaction = new TradeTransaction();
		transaction.setAmount(-1L);
		Game game = service.findByPK(Game.class, 1L);
		transaction.setRole(game.getRole(RoleType.¬”„‚è));
		transaction.setTransactionType(TransactionType.“ü‰×.name());
		transaction.setWeek(1L);
		Messages msgs = service.validate(transaction, WhenVerifier.INSERT);
		assertTrue("Amount‚Éƒ}ƒCƒiƒX’l‚ğ“ü—Í‚Å‚«‚Ä‚¢‚Ü‚·", msgs.hasError());
	}
}
