package lsh.ext.gson.internal;

import java.lang.reflect.Constructor;
import javax.annotation.Nullable;

import lombok.experimental.UtilityClass;

@UtilityClass
final class Constructors {

	@Nullable
	static <T> Constructor<T> getConstructorOrNull(final Class<T> klass, final Class<?>... parameterTypes) {
		try {
			return klass.getConstructor(parameterTypes);
		} catch ( final NoSuchMethodException ignored ) {
			return null;
		}
	}

}
