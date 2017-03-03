package lsh.ext.gson.adapters;

import java.lang.reflect.Type;
import javax.annotation.Nonnull;

import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractBoundTypeAdapterFactory;

import static lsh.ext.gson.ParameterizedTypes.resolveTypeArguments;
import static lsh.ext.gson.adapters.MultimapTypeAdapter.getMultimapTypeAdapter;

public final class MultimapTypeAdapterFactory
		extends AbstractBoundTypeAdapterFactory<Multimap<String, Object>> {

	private static final TypeAdapterFactory multimapTypeAdapterFactory = new MultimapTypeAdapterFactory();

	private MultimapTypeAdapterFactory() {
	}

	public static TypeAdapterFactory getMultimapTypeAdapterFactory() {
		return multimapTypeAdapterFactory;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return Multimap.class.isAssignableFrom(typeToken.getRawType());
	}

	@Nonnull
	@Override
	protected TypeAdapter<Multimap<String, Object>> createTypeAdapter(final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		final Type type = typeToken.getType();
		final Type[] typeArguments = resolveTypeArguments(type);
		final Type valueType = typeArguments[1];
		return getMultimapTypeAdapter(gson, valueType);
	}

}
