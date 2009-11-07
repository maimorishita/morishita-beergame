package jp.co.isken.beerGame.entity;

public enum TransactionType {
	“ü‰× {
		public String getQueue() {
			return "Deliver";
		}
	},
	ó’ {
		public String getQueue() {
			return "Order";
		}
	}, 
	o‰× {
		public String getQueue() {
			return "Deliver";
		}
	},
	”­’ {
		public String getQueue() {
			return "Order";
		}
	}, 
	
	İŒÉ{
		public String getQueue() {
			return "Stock";
		}
	}; 

	public abstract String getQueue();
}
