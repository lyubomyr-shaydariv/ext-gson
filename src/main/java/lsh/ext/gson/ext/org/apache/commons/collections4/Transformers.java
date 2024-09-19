package lsh.ext.gson.ext.org.apache.commons.collections4;

import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.Transformer;

@UtilityClass
final class Transformers {

	private static final Transformer<?, ?> identity = t -> t;

	static <T> Transformer<T, T> identity() {
		@SuppressWarnings("unchecked")
		final Transformer<T, T> castIdentity = (Transformer<T, T>) identity;
		return castIdentity;
	}

}
