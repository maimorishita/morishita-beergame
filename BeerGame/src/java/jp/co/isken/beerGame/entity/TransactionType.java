package jp.co.isken.beerGame.entity;

public enum TransactionType {
	入荷 {
		public String getQueue() {
			return "Deliver";
		}
		@Override
		public Role getParty(Role role) {
			return role.getUpper();
		}
	},
	受注 {
		public String getQueue() {
			return "Order";
		}
		@Override
		public Role getParty(Role role) {
			return role.getDowner();
		}
	},
	出荷 {
		public String getQueue() {
			return "Deliver";
		}
		@Override
		public Role getParty(Role role) {
			return role;
		}
	},
	発注 {
		public String getQueue() {
			return "Order";
		}
		@Override
		public Role getParty(Role role) {
			return role;
		}
	},
	在庫{
		public String getQueue() {
			return "Stock";
		}
		@Override
		public Role getParty(Role role) {
			throw new RuntimeException("在庫トランザクションから上流と下流は取得できません");
		}
	}; 

	public abstract String getQueue();
	/**
	 * メッセージを送受信するロールを返却する
	 * 送信する場合は、自分を返却する
	 * 受信する場合は、受注と入荷がある
	 * 受注の場合、下流を返却する
	 * 入荷の場合、上流を返却する
	 * 在庫の場合は、判定不可のため例外を送出する
	 */
	public abstract Role getParty(Role role);
}
