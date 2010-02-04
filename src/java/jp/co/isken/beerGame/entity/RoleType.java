package jp.co.isken.beerGame.entity;

import java.util.HashSet;
import java.util.Set;

public enum RoleType {
	sê {
		@Override
		RoleType getDowner() {
			throw new RuntimeException("sê‚Ì‰º—¬‚Í‘¶İ‚µ‚Ü‚¹‚ñ");
		}
		@Override
		RoleType getUpper() {
			return RoleType.¬”„‚è;
		}
		
	},
	¬”„‚è {
		@Override
		RoleType getUpper() {
			return RoleType.‰µ‚P;
		}

		@Override
		RoleType getDowner() {
			return RoleType.sê;
		}
	},
	‰µ‚P {
		@Override
		RoleType getUpper() {
			return RoleType.‰µ‚Q;
		}

		@Override
		RoleType getDowner() {
			return RoleType.¬”„‚è;
		}
	},
	‰µ‚Q {
		@Override
		RoleType getUpper() {
			return RoleType.ƒ[ƒJ;
		}

		@Override
		RoleType getDowner() {
			return RoleType.‰µ‚P;
		}
	},
	ƒ[ƒJ {
		@Override
		RoleType getUpper() {
			return RoleType.Hê;
		}

		@Override
		RoleType getDowner() {
			return RoleType.‰µ‚Q;
		}
	},
	Hê {
		@Override
		RoleType getDowner() {
			return RoleType.ƒ[ƒJ;
		}

		@Override
		RoleType getUpper() {
			throw new RuntimeException("Hê‚Ìã—¬‚Í‘¶İ‚µ‚Ü‚¹‚ñ");
		}
	},
	;

	public static RoleType getRoleTypeByName(String name) {
		return RoleType.valueOf(name);
	}

	public static Set<RoleType> getAll() {
		Set<RoleType> set = new HashSet<RoleType>();
		set.add(ƒ[ƒJ);
		set.add(‰µ‚P);
		set.add(‰µ‚Q);
		set.add(¬”„‚è);
		return set;
	}

	abstract RoleType getUpper();
	abstract RoleType getDowner();
}
