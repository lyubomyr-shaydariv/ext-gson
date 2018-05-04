package lsh.ext.gson.adapters.guava;

import java.lang.reflect.Type;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Supplier;
import com.google.common.collect.BiMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.ParameterizedTypes;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link BiMap} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see BiMapTypeAdapter
 * @since 0-SNAPSHOT
 */
public final class BiMapTypeAdapterFactory<V>
		extends AbstractTypeAdapterFactory<BiMap<String, V>> {

	private static final TypeAdapterFactory instance = new BiMapTypeAdapterFactory<>(null);

	@Nullable
	private final Supplier<? extends BiMap<String, V>> newBiMapFactory;

	private BiMapTypeAdapterFactory(@Nullable final Supplier<? extends BiMap<String, V>> newBiMapFactory) {
		this.newBiMapFactory = newBiMapFactory;
	}

	/**
	 * @return An instance of {@link BiMapTypeAdapterFactory}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static TypeAdapterFactory get() {
		return instance;
	}

	/**
	 * @return An instance of {@link BiMapTypeAdapterFactory} with a custom new {@link BiMap} factory.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapterFactory get(@Nullable final Supplier<? extends BiMap<String, V>> newBiMapFactory) {
		if ( newBiMapFactory == null ) {
			return instance;
		}
		return new BiMapTypeAdapterFactory<>(newBiMapFactory);
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		if ( !BiMap.class.isAssignableFrom(typeToken.getRawType()) ) {
			return false;
		}
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type keyType = typeArguments[0][0];
		return String.class.equals(keyType);
	}

	@Nonnull
	@Override
	protected TypeAdapter<BiMap<String, V>> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type valueType = typeArguments[1][0];
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		if ( newBiMapFactory == null ) {
			return BiMapTypeAdapter.get(valueTypeAdapter);
		}
		return BiMapTypeAdapter.get(valueTypeAdapter, newBiMapFactory);
	}

}
