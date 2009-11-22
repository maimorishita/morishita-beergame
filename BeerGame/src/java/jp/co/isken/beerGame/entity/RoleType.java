package jp.co.isken.beerGame.entity;

import java.util.HashSet;
import java.util.Set;

public enum RoleType {
	¬”„‚è {
		@Override
		RoleType getUpper() {
			return RoleType.‰µ‚P;
		}

		@Override
		RoleType getDowner() {
			throw new RuntimeException("¬”„‚è‚Ì‰º—¬‚Í‘¶İ‚µ‚Ü‚¹‚ñ");
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
			throw new RuntimeException("ƒ[ƒJ‚Ìã—¬‚Í‘¶İ‚µ‚Ü‚¹‚ñ");
		}

		@Override
		RoleType getDowner() {
			return RoleType.‰µ‚Q;
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
