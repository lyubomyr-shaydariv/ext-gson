package lsh.ext.gson;

import java.util.Collections;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Types {

	@SuppressWarnings("RedundantUnmodifiable")
	public static final Class<?> jdkUnmodifiableListClass = Collections.unmodifiableList(Collections.emptyList())
			.getClass();

	@SuppressWarnings("RedundantUnmodifiable")
	public static final Class<?> jdkUnmodifiableMapClass = Collections.unmodifiableMap(Collections.emptyMap())
			.getClass();

}
