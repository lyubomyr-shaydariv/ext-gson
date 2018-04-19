package lsh.ext.gson.adapters.java8;

import java.util.Optional;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractOptionalTypeAdapterFactoryTest;

public final class OptionalTypeAdapterFactoryTest
		extends AbstractOptionalTypeAdapterFactoryTest<Optional<String>, String> {

	private static final TypeToken<String> stringTypeToken = TypeToken.get(String.class);

	private static final TypeToken<Optional<String>> optionalStringTypeToken = new TypeToken<Optional<String>>() {
	};

	private static final TypeToken<? extends TypeAdapter<Optional<String>>> optionalStringTypeAdapterToken = new TypeToken<OptionalTypeAdapter<String>>() {
	};

	@Nonnull
	@Override
	protected TypeToken<String> getValueTypeToken() {
		return stringTypeToken;
	}

	@Nonnull
	@Override
	protected TypeToken<Optional<String>> getOptionalTypeToken() {
		return optionalStringTypeToken;
	}

	@Override
	protected TypeToken<? extends TypeAdapter<Optional<String>>> getOptionalTypeAdapterTypeToken() {
		return optionalStringTypeAdapterToken;
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return OptionalTypeAdapterFactory.get();
	}

}
