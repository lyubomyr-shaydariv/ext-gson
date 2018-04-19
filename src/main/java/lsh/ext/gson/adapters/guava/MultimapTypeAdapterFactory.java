package lsh.ext.gson.adapters.guava;

import java.lang.reflect.Type;
import javax.annotation.Nonnull;

import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.ParameterizedTypes;
import lsh.ext.gson.adapters.AbstractBoundTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Multimap} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see MultimapTypeAdapter
 * @since 0-SNAPSHOT
 */
public final class MultimapTypeAdapterFactory<V>
		extends AbstractBoundTypeAdapterFactory<Multimap<String, V>> {

	private static final TypeAdapterFactory instance = new MultimapTypeAdapterFactory<>();

	private MultimapTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link MultimapTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory get() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		if ( !Multimap.class.isAssignableFrom(typeToken.getRawType()) ) {
			return false;
		}
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type keyType = typeArguments[0][0];
		return String.class.equals(keyType);
	}

	@Nonnull
	@Override
	protected TypeAdapter<Multimap<String, V>> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type valueType = typeArguments[1][0];
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		return MultimapTypeAdapter.get(valueTypeAdapter);
	}

}
