package lsh.ext.gson.adapters;

import java.lang.reflect.Type;
import javax.annotation.Nonnull;

import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractBoundTypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

/**
 * Represents a type adapter factory for {@link Multimap} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see MultimapTypeAdapter
 * @since 0-SNAPSHOT
 */
public final class MultimapTypeAdapterFactory
		extends AbstractBoundTypeAdapterFactory<Multimap<String, Object>> {

	private static final TypeAdapterFactory multimapTypeAdapterFactory = new MultimapTypeAdapterFactory();

	private MultimapTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link MultimapTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory get() {
		return multimapTypeAdapterFactory;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		final boolean isAssignable = Multimap.class.isAssignableFrom(typeToken.getRawType());
		if ( !isAssignable ) {
			return false;
		}
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type keyType = typeArguments[0][0];
		return String.class.equals(keyType);
	}

	@Nonnull
	@Override
	protected TypeAdapter<Multimap<String, Object>> createTypeAdapter(final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type valueType = typeArguments[1][0];
		return MultimapTypeAdapter.get(gson, valueType);
	}

}
