package jp.co.isken.beerGame.entity;

import java.util.HashSet;
import java.util.Set;

public enum RoleType {
	������,
	���P,
	���Q,
	���[�J�[;
	
	public static Set<RoleType> getAll(){
		Set<RoleType> set = new HashSet<RoleType>();
		set.add(���[�J�[);
		set.add(���P);
		set.add(���Q);
		set.add(������);
		return set;
	}
}
