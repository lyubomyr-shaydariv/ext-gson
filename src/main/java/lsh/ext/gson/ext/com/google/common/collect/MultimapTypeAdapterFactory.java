package lsh.ext.gson.ext.com.google.common.collect;

import java.lang.reflect.Type;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Converter;
import com.google.common.base.Supplier;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.ParameterizedTypes;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Multimap} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see MultimapTypeAdapter
 * @since 0-SNAPSHOT
 */
public final class MultimapTypeAdapterFactory<K, V>
		extends AbstractTypeAdapterFactory<Multimap<K, V>> {

	private static final TypeAdapterFactory defaultInstance = new MultimapTypeAdapterFactory<>(null, null);

	@Nullable
	private final Supplier<? extends Multimap<K, V>> newMultimapFactory;

	@Nullable
	private final Converter<K, String> keyConverter;

	private MultimapTypeAdapterFactory(@Nullable final Supplier<? extends Multimap<K, V>> newMultimapFactory,
			@Nullable final Converter<K, String> keyConverter) {
		this.newMultimapFactory = newMultimapFactory;
		this.keyConverter = keyConverter;
	}

	/**
	 * @return An instance of {@link MultimapTypeAdapterFactory}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static TypeAdapterFactory getDefaultInstance() {
		return defaultInstance;
	}

	/**
	 * @param newMultimapFactory Multimap factory
	 * @param keyConverter       Key converter
	 * @param <K>                Key type
	 * @param <V>                Vqlue type
	 *
	 * @return An instance of {@link MultimapTypeAdapterFactory} with a custom new {@link Multimap} factory.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <K, V> TypeAdapterFactory get(@Nullable final Supplier<? extends Multimap<K, V>> newMultimapFactory,
			@Nullable final Converter<K, String> keyConverter) {
		if ( newMultimapFactory == null && keyConverter == null ) {
			return defaultInstance;
		}
		return new MultimapTypeAdapterFactory<>(newMultimapFactory, keyConverter);
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return Multimap.class.isAssignableFrom(typeToken.getRawType());
	}

	@Nonnull
	@Override
	@SuppressWarnings("ConstantConditions")
	protected TypeAdapter<Multimap<K, V>> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type valueType = typeArguments[1][0];
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		if ( newMultimapFactory == null && keyConverter == null ) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final TypeAdapter<Multimap<K, V>> castMultimapTypeAdapter = (TypeAdapter) MultimapTypeAdapter.get(valueTypeAdapter);
			return castMultimapTypeAdapter;
		}
		if ( newMultimapFactory != null && keyConverter == null ) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Supplier<? extends Multimap<String, V>> castNewMultimapFactory = (Supplier) newMultimapFactory;
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final TypeAdapter<Multimap<K, V>> castMultimapTypeAdapter = (TypeAdapter) MultimapTypeAdapter.get(valueTypeAdapter, castNewMultimapFactory);
			return castMultimapTypeAdapter;
		}
		if ( newMultimapFactory == null && keyConverter != null ) {
			return MultimapTypeAdapter.get(valueTypeAdapter, keyConverter);
		}
		return MultimapTypeAdapter.get(valueTypeAdapter, newMultimapFactory, keyConverter);
	}

}
