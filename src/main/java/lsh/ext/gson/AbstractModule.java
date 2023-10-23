package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Implements an abstract module with modules found in linear manner (from first to last).
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractModule
		implements IModule {

	private final Iterable<? extends TypeAdapterFactory> typeAdapterFactories;

	@Override
	@Nullable
	public final <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		for ( final TypeAdapterFactory typeAdapterFactory : typeAdapterFactories ) {
			@Nullable
			final TypeAdapter<T> typeAdapter = typeAdapterFactory.create(gson, typeToken);
			if ( typeAdapter != null ) {
				return typeAdapter;
			}
		}
		return null;
	}

	@Nullable
	protected static String toStringOrNull(@Nullable final Object o) {
		if ( o == null ) {
			return null;
		}
		return o.toString();
	}

	@Nullable
	protected static <T> T parseToNullOrFail(@Nullable final String s) {
		if ( s == null ) {
			return null;
		}
		throw new UnsupportedOperationException("Cannot parse: " + s);
	}

}
