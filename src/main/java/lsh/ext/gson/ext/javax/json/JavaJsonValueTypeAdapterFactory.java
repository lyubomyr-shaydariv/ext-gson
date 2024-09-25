package lsh.ext.gson.ext.javax.json;

import javax.json.JsonValue;
import javax.json.spi.JsonProvider;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class JavaJsonValueTypeAdapterFactory {

	public static ITypeAdapterFactory<JsonValue> defaultForJsonValue = forJsonValue(JavaJsonValueTypeAdapter.getInstance(JsonProvider.provider()));

	public static ITypeAdapterFactory<JsonValue> forJsonValue(final TypeAdapter<JsonValue> typeAdapter) {
		return ITypeAdapterFactory.forClassHierarchy(JsonValue.class, provider -> typeAdapter);
	}

}
