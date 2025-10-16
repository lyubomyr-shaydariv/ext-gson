package lsh.ext.gson.ext.jakarta.json;

import com.google.gson.TypeAdapter;
import jakarta.json.JsonValue;
import jakarta.json.spi.JsonProvider;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class JakartaJsonTypeAdapterFactory {

	public static final ITypeAdapterFactory<JsonValue> defaultForJsonValue = forJsonValue(JakartaJsonValueTypeAdapter.getInstance(JsonProvider.provider()));

	public static ITypeAdapterFactory<JsonValue> forJsonValue(final TypeAdapter<JsonValue> typeAdapter) {
		return ITypeAdapterFactory.forClassHierarchy(JsonValue.class, typeAdapter);
	}

}
