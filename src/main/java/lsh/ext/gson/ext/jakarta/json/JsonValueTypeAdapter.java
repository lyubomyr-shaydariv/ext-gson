package lsh.ext.gson.ext.jakarta.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import jakarta.json.spi.JsonProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonValueTypeAdapter
		extends TypeAdapter<JsonValue> {

	@Getter(onMethod_ = @SuppressFBWarnings("MS_EXPOSE_REP"))
	private static final TypeAdapter<JsonValue> instance = getInstance(JsonProvider.provider());

	private final TypeAdapter<JsonValue> jsonNullTypeAdapter = JsonNullTypeAdapter.instance;
	private final TypeAdapter<JsonValue> jsonBooleanTypeAdapter = JsonBooleanTypeAdapter.instance;
	private final TypeAdapter<JsonNumber> jsonNumberTypeAdapter;
	private final TypeAdapter<JsonString> jsonStringTypeAdapter;
	private final TypeAdapter<JsonObject> jsonObjectTypeAdapter = new JsonObjectTypeAdapter();
	private final TypeAdapter<JsonArray> jsonArrayTypeAdapter = new JsonArrayTypeAdapter();

	public static TypeAdapter<JsonValue> getInstance(final JsonProvider jsonProvider) {
		return new JsonValueTypeAdapter(new JsonNumberTypeAdapter(jsonProvider), new JsonStringTypeAdapter(jsonProvider))
				.nullSafe();
	}

	@Override
	@SuppressWarnings("checkstyle:CyclomaticComplexity")
	public void write(final JsonWriter out, final JsonValue jsonValue)
			throws IOException {
		final JsonValue.ValueType valueType = jsonValue.getValueType();
		switch ( valueType ) {
		case ARRAY:
			jsonArrayTypeAdapter.write(out, (JsonArray) jsonValue);
			break;
		case OBJECT:
			jsonObjectTypeAdapter.write(out, (JsonObject) jsonValue);
			break;
		case STRING:
			jsonStringTypeAdapter.write(out, (JsonString) jsonValue);
			break;
		case NUMBER:
			jsonNumberTypeAdapter.write(out, (JsonNumber) jsonValue);
			break;
		case TRUE:
		case FALSE:
			jsonBooleanTypeAdapter.write(out, jsonValue);
			break;
		case NULL:
			jsonNullTypeAdapter.write(out, jsonValue);
			break;
		default:
			throw new AssertionError(valueType);
		}
	}

	@Override
	@SuppressWarnings("checkstyle:CyclomaticComplexity")
	public JsonValue read(final JsonReader in)
			throws IOException {
		final JsonToken jsonToken = in.peek();
		switch ( jsonToken ) {
		case BEGIN_ARRAY:
			return jsonArrayTypeAdapter.read(in);
		case BEGIN_OBJECT:
			return jsonObjectTypeAdapter.read(in);
		case STRING:
			return jsonStringTypeAdapter.read(in);
		case NUMBER:
			return jsonNumberTypeAdapter.read(in);
		case BOOLEAN:
			return jsonBooleanTypeAdapter.read(in);
		case NULL:
			return jsonNullTypeAdapter.read(in);
		case END_ARRAY:
		case END_DOCUMENT:
		case END_OBJECT:
		case NAME:
			throw new AssertionError();
		default:
			throw new AssertionError(jsonToken);
		}
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class JsonNullTypeAdapter
			extends TypeAdapter<JsonValue> {

		private static final TypeAdapter<JsonValue> instance = new JsonNullTypeAdapter();

		@Override
		public void write(final JsonWriter out, final JsonValue jsonNull)
				throws IOException {
			out.nullValue();
		}

		@Override
		public JsonValue read(final JsonReader in)
				throws IOException {
			in.nextNull();
			return JsonValue.NULL;
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class JsonBooleanTypeAdapter
			extends TypeAdapter<JsonValue> {

		private static final TypeAdapter<JsonValue> instance = new JsonBooleanTypeAdapter();

		@Override
		public void write(final JsonWriter out, final JsonValue jsonBoolean)
				throws IOException {
			final JsonValue.ValueType valueType = jsonBoolean.getValueType();
			switch ( valueType ) {
			case TRUE:
				out.value(true);
				break;
			case FALSE:
				out.value(false);
				break;
			case ARRAY:
			case OBJECT:
			case STRING:
			case NUMBER:
			case NULL:
			default:
				throw new AssertionError(valueType);
			}
		}

		@Override
		public JsonValue read(final JsonReader in)
				throws IOException {
			return in.nextBoolean() ? JsonValue.TRUE : JsonValue.FALSE;
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class JsonNumberTypeAdapter
			extends TypeAdapter<JsonNumber> {

		private final JsonProvider jsonProvider;

		@Override
		public void write(final JsonWriter out, final JsonNumber jsonNumber)
				throws IOException {
			out.value(jsonNumber.numberValue());
		}

		@Override
		public JsonNumber read(final JsonReader in)
				throws IOException {
			return jsonProvider.createValue(new BigDecimal(in.nextString()));
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class JsonStringTypeAdapter
			extends TypeAdapter<JsonString> {

		private final JsonProvider jsonProvider;

		@Override
		public void write(final JsonWriter out, final JsonString jsonString)
				throws IOException {
			out.value(jsonString.getString());
		}

		@Override
		public JsonString read(final JsonReader in)
				throws IOException {
			return jsonProvider.createValue(in.nextString());
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private final class JsonObjectTypeAdapter
			extends TypeAdapter<JsonObject> {

		@Override
		public void write(final JsonWriter out, final JsonObject jsonObject)
				throws IOException {
			out.beginObject();
			for ( final Map.Entry<String, JsonValue> e : jsonObject.entrySet() ) {
				out.name(e.getKey());
				JsonValueTypeAdapter.this.write(out, e.getValue());
			}
			out.endObject();
		}

		@Override
		@SuppressWarnings("checkstyle:CyclomaticComplexity")
		public JsonObject read(final JsonReader in)
				throws IOException {
			final JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
			in.beginObject();
			while ( in.hasNext() ) {
				final String key = in.nextName();
				final JsonToken token = in.peek();
				switch ( token ) {
				case BEGIN_ARRAY:
				case BEGIN_OBJECT:
					jsonObjectBuilder.add(key, JsonValueTypeAdapter.this.read(in));
					break;
				case STRING:
					jsonObjectBuilder.add(key, in.nextString());
					break;
				case NUMBER:
					jsonObjectBuilder.add(key, new BigDecimal(in.nextString()));
					break;
				case BOOLEAN:
					jsonObjectBuilder.add(key, in.nextBoolean());
					break;
				case NULL:
					in.nextNull();
					jsonObjectBuilder.addNull(key);
					break;
				case END_DOCUMENT:
					// do nothing
					break;
				case END_ARRAY:
				case END_OBJECT:
				case NAME:
					throw new AssertionError();
				default:
					throw new AssertionError(token);
				}
			}
			in.endObject();
			return jsonObjectBuilder.build();
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private final class JsonArrayTypeAdapter
			extends TypeAdapter<JsonArray> {

		@Override
		public void write(final JsonWriter out, final JsonArray jsonArray)
				throws IOException {
			out.beginArray();
			for ( final JsonValue jsonValue : jsonArray ) {
				JsonValueTypeAdapter.this.write(out, jsonValue);
			}
			out.endArray();
		}

		@Override
		@SuppressWarnings("checkstyle:CyclomaticComplexity")
		public JsonArray read(final JsonReader in)
				throws IOException {
			final JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
			in.beginArray();
			while ( in.hasNext() ) {
				final JsonToken token = in.peek();
				switch ( token ) {
				case BEGIN_ARRAY:
				case BEGIN_OBJECT:
					jsonArrayBuilder.add(JsonValueTypeAdapter.this.read(in));
					break;
				case STRING:
					jsonArrayBuilder.add(in.nextString());
					break;
				case NUMBER:
					jsonArrayBuilder.add(new BigDecimal(in.nextString()));
					break;
				case BOOLEAN:
					jsonArrayBuilder.add(in.nextBoolean());
					break;
				case NULL:
					in.nextNull();
					jsonArrayBuilder.addNull();
					break;
				case END_DOCUMENT:
					// do nothing
					break;
				case END_ARRAY:
				case END_OBJECT:
				case NAME:
					throw new AssertionError();
				default:
					throw new AssertionError(token);
				}
			}
			in.endArray();
			return jsonArrayBuilder.build();
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory
			extends AbstractTypeAdapterFactory<JsonValue> {

		@Nullable
		private final JsonProvider jsonProvider;

		public static ITypeAdapterFactory<JsonValue> getInstance(final JsonProvider jsonProvider) {
			return new Factory(jsonProvider);
		}

		@Override
		@Nullable
		protected TypeAdapter<JsonValue> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( !JsonValue.class.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			if ( jsonProvider == null ) {
				return JsonValueTypeAdapter.getInstance();
			}
			return JsonValueTypeAdapter.getInstance(jsonProvider);
		}

	}

}
