package jp.co.isken.beerGame.entity;

public enum TransactionType {
	���� {
		public String getQueue() {
			return "Deliver";
		}
	},
	�� {
		public String getQueue() {
			return "Order";
		}
	}, 
	�o�� {
		public String getQueue() {
			return "Deliver";
		}
	},
	���� {
		public String getQueue() {
			return "Order";
		}
	},
	;

	public abstract String getQueue();
}
