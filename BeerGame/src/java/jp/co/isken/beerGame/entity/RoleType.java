package jp.co.isken.beerGame.entity;

import java.util.HashSet;
import java.util.Set;

public enum RoleType {
	¬”„‚è,
	‰µ‚P,
	‰µ‚Q,
	ƒ[ƒJ[;
	
	public static Set<RoleType> getAll(){
		Set<RoleType> set = new HashSet<RoleType>();
		set.add(ƒ[ƒJ[);
		set.add(‰µ‚P);
		set.add(‰µ‚Q);
		set.add(¬”„‚è);
		return set;
	}
}
