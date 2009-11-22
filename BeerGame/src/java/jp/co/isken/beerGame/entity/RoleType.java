package jp.co.isken.beerGame.entity;

import java.util.HashSet;
import java.util.Set;

public enum RoleType {
	������ {
		@Override
		RoleType getUpper() {
			return RoleType.���P;
		}

		@Override
		RoleType getDowner() {
			throw new RuntimeException("������̉����͑��݂��܂���");
		}
	},
	���P {
		@Override
		RoleType getUpper() {
			return RoleType.���Q;
		}

		@Override
		RoleType getDowner() {
			return RoleType.������;
		}
	},
	���Q {
		@Override
		RoleType getUpper() {
			return RoleType.���[�J;
		}

		@Override
		RoleType getDowner() {
			return RoleType.���P;
		}
	},
	���[�J {
		@Override
		RoleType getUpper() {
			throw new RuntimeException("���[�J�̏㗬�͑��݂��܂���");
		}

		@Override
		RoleType getDowner() {
			return RoleType.���Q;
		}
	},
	;

	public static RoleType getRoleTypeByName(String name) {
		return RoleType.valueOf(name);
	}

	public static Set<RoleType> getAll() {
		Set<RoleType> set = new HashSet<RoleType>();
		set.add(���[�J);
		set.add(���P);
		set.add(���Q);
		set.add(������);
		return set;
	}

	abstract RoleType getUpper();

	abstract RoleType getDowner();
}
