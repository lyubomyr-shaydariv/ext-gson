package lsh.ext.gson.ext.json;

import javax.json.JsonValue;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

public final class JsonValueTypeAdapterFactory
		extends AbstractTypeAdapterFactory<JsonValue> {

	private static final TypeAdapterFactory defaultInstance = new JsonValueTypeAdapterFactory();

	private JsonValueTypeAdapterFactory() {
	}

	public static TypeAdapterFactory getDefaultInstance() {
		return defaultInstance;
	}

	@Override
	protected boolean isSupported(final TypeToken<?> typeToken) {
		return JsonValue.class.isAssignableFrom(typeToken.getRawType());
	}

	@Override
	protected TypeAdapter<JsonValue> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return JsonValueTypeAdapter.getDefaultInstance();
	}

}
