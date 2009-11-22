package jp.co.isken.beerGame.entity;

import java.util.HashSet;
import java.util.Set;

public enum RoleType {
	小売り {
		@Override
		RoleType getUpper() {
			return RoleType.卸１;
		}

		@Override
		RoleType getDowner() {
			throw new RuntimeException("小売りの下流は存在しません");
		}
	},
	卸１ {
		@Override
		RoleType getUpper() {
			return RoleType.卸２;
		}

		@Override
		RoleType getDowner() {
			return RoleType.小売り;
		}
	},
	卸２ {
		@Override
		RoleType getUpper() {
			return RoleType.メーカ;
		}

		@Override
		RoleType getDowner() {
			return RoleType.卸１;
		}
	},
	メーカ {
		@Override
		RoleType getUpper() {
			throw new RuntimeException("メーカの上流は存在しません");
		}

		@Override
		RoleType getDowner() {
			return RoleType.卸２;
		}
	},
	;

	public static RoleType getRoleTypeByName(String name) {
		return RoleType.valueOf(name);
	}

	public static Set<RoleType> getAll() {
		Set<RoleType> set = new HashSet<RoleType>();
		set.add(メーカ);
		set.add(卸１);
		set.add(卸２);
		set.add(小売り);
		return set;
	}

	abstract RoleType getUpper();

	abstract RoleType getDowner();
}
