package lsh.ext.gson.ext.com.google.common.collect;

import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.common.base.Converter;
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
 */
public final class BiMapTypeAdapterFactory<K, V>
		extends AbstractTypeAdapterFactory<BiMap<K, V>> {

	private static final TypeAdapterFactory instance = new BiMapTypeAdapterFactory<>(null, null);

	@Nullable
	private final Supplier<? extends BiMap<K, V>> newBiMapFactory;

	@Nullable
	private final Converter<K, String> keyConverter;

	private BiMapTypeAdapterFactory(@Nullable final Supplier<? extends BiMap<K, V>> newBiMapFactory, final Converter<K, String> keyConverter) {
		this.newBiMapFactory = newBiMapFactory;
		this.keyConverter = keyConverter;
	}

	/**
	 * @return An instance of {@link BiMapTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	/**
	 * @param newBiMapFactory New bidirectional map factory
	 * @param keyConverter    Key converter
	 * @param <K>             Key type
	 * @param <V>             Value type
	 *
	 * @return An instance of {@link BiMapTypeAdapterFactory} with a custom new {@link BiMap} factory.
	 */
	public static <K, V> TypeAdapterFactory getInstance(@Nullable final Supplier<? extends BiMap<K, V>> newBiMapFactory,
			@Nullable final Converter<K, String> keyConverter) {
		if ( newBiMapFactory == null && keyConverter == null ) {
			return instance;
		}
		return new BiMapTypeAdapterFactory<>(newBiMapFactory, keyConverter);
	}

	@Override
	protected boolean isSupported(final TypeToken<?> typeToken) {
		return BiMap.class.isAssignableFrom(typeToken.getRawType());
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	protected TypeAdapter<BiMap<K, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type valueType = typeArguments[1][0];
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		if ( newBiMapFactory == null && keyConverter == null ) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final TypeAdapter<BiMap<K, V>> castBiMapTypeAdapter = (TypeAdapter) BiMapTypeAdapter.getInstance(valueTypeAdapter);
			return castBiMapTypeAdapter;
		}
		if ( newBiMapFactory != null && keyConverter == null ) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Supplier<? extends BiMap<String, V>> castNewBiMapFactory = (Supplier) newBiMapFactory;
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final TypeAdapter<BiMap<K, V>> castBiMapTypeAdapter = (TypeAdapter) BiMapTypeAdapter.getInstance(valueTypeAdapter, castNewBiMapFactory);
			return castBiMapTypeAdapter;
		}
		if ( newBiMapFactory == null && keyConverter != null ) {
			return BiMapTypeAdapter.getInstance(valueTypeAdapter, keyConverter);
		}
		return BiMapTypeAdapter.getInstance(valueTypeAdapter, newBiMapFactory, keyConverter);
	}

}
