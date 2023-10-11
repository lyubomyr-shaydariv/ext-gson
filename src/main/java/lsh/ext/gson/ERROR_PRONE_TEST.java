package lsh.ext.gson;

import java.util.HashSet;
import java.util.Set;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ERROR_PRONE_TEST {

	public static void main(final String... args) {
		final Set<Short> s = new HashSet<>();
		for ( short i = 0; i < 100; i++ ) {
			s.add(i);
			s.remove(i - 1);
		}
		System.out.println(s.size());
	}

}
