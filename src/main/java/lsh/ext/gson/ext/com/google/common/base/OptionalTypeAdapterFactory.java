package lsh.ext.gson.ext.com.google.common.base;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractOptionalTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Optional} from Google Guava.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptionalTypeAdapterFactory<T>
		extends AbstractOptionalTypeAdapterFactory<Optional<T>, T> {

	@Getter
	private static final TypeAdapterFactory instance = new OptionalTypeAdapterFactory<>();

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return Optional.class.isAssignableFrom(typeToken.getRawType());
	}

	@Override
	protected TypeAdapter<Optional<T>> from(final TypeAdapter<T> valueTypeAdapter) {
		return OptionalTypeAdapterFactory.Adapter.getInstance(valueTypeAdapter);
	}

	/**
	 * Represents a type adapter for {@link Optional} from Google Guava.
	 */
	public static final class Adapter<T>
			extends AbstractOptionalTypeAdapterFactory.Adapter<Optional<T>, T> {

		private Adapter(final TypeAdapter<T> valueTypeAdapter) {
			super(valueTypeAdapter);
		}

		/**
		 * @param valueTypeAdapter
		 *        {@link Optional} backed value type adapter
		 * @param <T>
		 * 		Optional value type
		 *
		 * @return An {@link Adapter} instance
		 */
		public static <T> TypeAdapter<Optional<T>> getInstance(final TypeAdapter<T> valueTypeAdapter) {
			return new Adapter<>(valueTypeAdapter);
		}

		@Nullable
		@Override
		protected T fromOptional(@SuppressWarnings("Guava") final Optional<T> optional) {
			return optional.orNull();
		}

		@Override
		@SuppressWarnings("Guava")
		protected Optional<T> toOptional(@Nullable final T value) {
			return Optional.fromNullable(value);
		}

	}

}
