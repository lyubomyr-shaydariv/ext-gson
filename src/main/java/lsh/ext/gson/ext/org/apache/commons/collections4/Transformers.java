package lsh.ext.gson.ext.org.apache.commons.collections4;

import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.Transformer;

@UtilityClass
final class Transformers {

	private static Transformer<?, ?> identity = input -> input;

	static <I, O> Transformer<I, O> identity() {
		@SuppressWarnings("unchecked")
		final Transformer<I, O> castIdentity = (Transformer<I, O>) identity;
		return castIdentity;
	}

}
