package jp.co.isken.beerGame.entity;

public enum TransactionType {
	���� {
		public String getQueue() {
			return "Deliver";
		}
		@Override
		public Role getParty(Role role) {
			return role.getUpper();
		}
	},
	�� {
		public String getQueue() {
			return "Order";
		}
		@Override
		public Role getParty(Role role) {
			return role.getDowner();
		}
	},
	�o�� {
		public String getQueue() {
			return "Deliver";
		}
		@Override
		public Role getParty(Role role) {
			return role;
		}
	},
	���� {
		public String getQueue() {
			return "Order";
		}
		@Override
		public Role getParty(Role role) {
			return role;
		}
	},
	�݌�{
		public String getQueue() {
			return "Stock";
		}
		@Override
		public Role getParty(Role role) {
			throw new RuntimeException("�݌Ƀg�����U�N�V��������㗬�Ɖ����͎擾�ł��܂���");
		}
	}; 

	public abstract String getQueue();
	/**
	 * ���b�Z�[�W�𑗎�M���郍�[����ԋp����
	 * ���M����ꍇ�́A������ԋp����
	 * ��M����ꍇ�́A�󒍂Ɠ��ׂ�����
	 * �󒍂̏ꍇ�A������ԋp����
	 * ���ׂ̏ꍇ�A�㗬��ԋp����
	 * �݌ɂ̏ꍇ�́A����s�̂��ߗ�O�𑗏o����
	 */
	public abstract Role getParty(Role role);
}
