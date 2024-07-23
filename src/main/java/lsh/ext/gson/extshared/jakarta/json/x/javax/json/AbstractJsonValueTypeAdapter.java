package lsh.ext.gson.extshared.jakarta.json.x.javax.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;

// TODO currently Numbers are only supported
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuppressWarnings("NewClassNamingConvention")
public abstract class AbstractJsonValueTypeAdapter<
		JsonValue,
		JsonArray extends JsonValue,
		JsonObject extends JsonValue,
		JsonString extends JsonValue,
		JsonNumber extends JsonValue,
		JsonBoolean extends JsonValue,
		JsonNull extends JsonValue
		>
		extends TypeAdapter<JsonValue> {

	protected enum ValueType {

		ARRAY,
		OBJECT,
		STRING,
		NUMBER,
		TRUE,
		FALSE,
		NULL

	}

	private final JsonNull nullValue;
	private final JsonBoolean trueValue;
	private final JsonBoolean falseValue;

	protected abstract ValueType getValueType(JsonValue value);

	protected abstract String toStringFromJsonString(JsonString value);

	protected abstract Number toNumberFromJsonNumber(JsonNumber value);

	protected abstract JsonString toJsonStringFromString(String value);

	protected abstract JsonNumber toJsonNumberFromString(String value);

	protected abstract Iterable<? extends JsonValue> toJsonValueIterableFromJsonArray(JsonArray value);

	protected abstract Map<String, ? extends JsonValue> toJsonValueMapFromJsonObject(JsonObject value);

	protected abstract JsonArray toJsonArrayFromJsonValueList(List<? extends JsonValue> value);

	protected abstract JsonObject toJsonObjectFromJsonValueMap(Map<String, ? extends JsonValue> value);

	@Override
	@SuppressWarnings("checkstyle:CyclomaticComplexity")
	public final void write(final JsonWriter out, final JsonValue jsonValue)
			throws IOException {
		final ValueType valueType = getValueType(jsonValue);
		switch ( valueType ) {
		case ARRAY:
			@SuppressWarnings("unchecked")
			final JsonArray jsonArray = (JsonArray) jsonValue;
			out.beginArray();
			for ( final JsonValue e : toJsonValueIterableFromJsonArray(jsonArray) ) {
				write(out, e);
			}
			out.endArray();
			break;
		case OBJECT:
			@SuppressWarnings("unchecked")
			final JsonObject jsonObject = (JsonObject) jsonValue;
			out.beginObject();
			for ( final Map.Entry<String, ? extends JsonValue> e : toJsonValueMapFromJsonObject(jsonObject).entrySet() ) {
				out.name(e.getKey());
				write(out, e.getValue());
			}
			out.endObject();
			break;
		case STRING:
			@SuppressWarnings("unchecked")
			final JsonString jsonString = (JsonString) jsonValue;
			out.value(toStringFromJsonString(jsonString));
			break;
		case NUMBER:
			@SuppressWarnings("unchecked")
			final JsonNumber jsonNumber = (JsonNumber) jsonValue;
			out.value(toNumberFromJsonNumber(jsonNumber));
			break;
		case TRUE:
			out.value(true);
			break;
		case FALSE:
			out.value(false);
			break;
		case NULL:
			out.nullValue();
			break;
		default:
			throw new AssertionError(valueType);
		}
	}

	@Override
	@SuppressWarnings({ "NestedSwitchStatement", "checkstyle:CyclomaticComplexity", "checkstyle:JavaNCSS" })
	public final JsonValue read(final JsonReader in)
			throws IOException {
		final JsonToken jsonToken = in.peek();
		switch ( jsonToken ) {
		case BEGIN_ARRAY: {
			in.beginArray();
			final List<JsonValue> jsonValueArray = new ArrayList<>();
			while ( in.hasNext() ) {
				final JsonToken token = in.peek();
				switch ( token ) {
				case BEGIN_ARRAY:
				case BEGIN_OBJECT:
				case STRING:
				case NUMBER:
				case BOOLEAN:
				case NULL:
					jsonValueArray.add(read(in));
					break;
				case END_DOCUMENT:
					// do nothing
					break;
				case END_ARRAY:
				case END_OBJECT:
				case NAME:
				default:
					throw new AssertionError(token);
				}
			}
			in.endArray();
			return toJsonArrayFromJsonValueList(jsonValueArray);
		}
		case BEGIN_OBJECT: {
			in.beginObject();
			final Map<String, JsonValue> jsonValueMap = new LinkedHashMap<>();
			while ( in.hasNext() ) {
				final String key = in.nextName();
				final JsonToken token = in.peek();
				switch ( token ) {
				case BEGIN_ARRAY:
				case BEGIN_OBJECT:
				case NUMBER:
				case STRING:
				case BOOLEAN:
				case NULL:
					jsonValueMap.put(key, read(in));
					break;
				case END_DOCUMENT:
					// do nothing
					break;
				case END_ARRAY:
				case END_OBJECT:
				case NAME:
				default:
					throw new AssertionError(token);
				}
			}
			in.endObject();
			return toJsonObjectFromJsonValueMap(jsonValueMap);
		}
		case STRING:
			return toJsonStringFromString(in.nextString());
		case NUMBER:
			return toJsonNumberFromString(in.nextString());
		case BOOLEAN:
			if ( !in.nextBoolean() ) {
				return falseValue;
			}
			return trueValue;
		case NULL:
			in.nextNull();
			return nullValue;
		case END_ARRAY:
		case END_DOCUMENT:
		case END_OBJECT:
		case NAME:
		default:
			throw new AssertionError(jsonToken);
		}
	}

	@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
	protected abstract static class AbstractFactory<JsonValue, JsonProvider>
			extends AbstractTypeAdapterFactory<JsonValue> {

		private final Class<? extends JsonValue> jsonValueClass;

		@Nullable
		private final JsonProvider jsonProvider;

		protected abstract TypeAdapter<JsonValue> getJsonValueTypeAdapter();

		protected abstract TypeAdapter<JsonValue> getJsonValueTypeAdapter(JsonProvider jsonProvider);

		@Override
		@Nullable
		protected final TypeAdapter<JsonValue> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( !jsonValueClass.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			if ( jsonProvider == null ) {
				return getJsonValueTypeAdapter();
			}
			return getJsonValueTypeAdapter(jsonProvider);
		}

	}

}
