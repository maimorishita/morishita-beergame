package jp.co.isken.beerGame.entity;

public enum TransactionType {
	入荷 {
		public String getQueue() {
			return "Deliver";
		}
	},
	受注 {
		public String getQueue() {
			return "Order";
		}
	}, 
	出荷 {
		public String getQueue() {
			return "Deliver";
		}
	},
	発注 {
		public String getQueue() {
			return "Order";
		}
	},
	;

	public abstract String getQueue();
}
