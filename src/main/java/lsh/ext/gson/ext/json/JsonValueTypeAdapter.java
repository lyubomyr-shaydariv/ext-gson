package lsh.ext.gson.ext.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.json.spi.JsonProvider;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a type adapter for {@code javax.json} JSON values.
 *
 * @author Lyubomyr Shaydariv
 * @see JsonValue
 * @see JsonNumber
 * @see JsonString
 * @see JsonObject
 * @see JsonArray
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonValueTypeAdapter
		extends TypeAdapter<JsonValue> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapter<JsonValue> instance = getInstance(JsonProvider.provider());

	private final TypeAdapter<JsonValue> jsonNullTypeAdapter = JsonNullTypeAdapter.instance;
	private final TypeAdapter<JsonValue> jsonBooleanTypeAdapter = JsonBooleanTypeAdapter.instance;
	private final TypeAdapter<JsonNumber> jsonNumberTypeAdapter;
	private final TypeAdapter<JsonString> jsonStringTypeAdapter;
	private final TypeAdapter<JsonObject> jsonObjectTypeAdapter = new JsonObjectTypeAdapter().nullSafe();
	private final TypeAdapter<JsonArray> jsonArrayTypeAdapter = new JsonArrayTypeAdapter().nullSafe();

	/**
	 * @param jsonProvider
	 * 		JSON provider
	 *
	 * @return An instance of {@link JsonValueTypeAdapter}.
	 */
	public static TypeAdapter<JsonValue> getInstance(final JsonProvider jsonProvider) {
		return new JsonValueTypeAdapter(JsonNumberTypeAdapter.getInstance(jsonProvider), JsonStringTypeAdapter.getInstance(jsonProvider));
	}

	@Override
	public void write(final JsonWriter out, final JsonValue jsonValue)
			throws IOException {
		final ValueType valueType = jsonValue.getValueType();
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
	public JsonValue read(final JsonReader in)
			throws IOException {
		final JsonToken jsonToken = in.peek();
		switch ( jsonToken ) {
		case BEGIN_ARRAY:
			return jsonArrayTypeAdapter.read(in);
		case END_ARRAY:
			throw new AssertionError("Must never happen due to delegation to the array type adapter");
		case BEGIN_OBJECT:
			return jsonObjectTypeAdapter.read(in);
		case END_OBJECT:
			throw new AssertionError("Must never happen due to delegation to the object type adapter");
		case NAME:
			throw new AssertionError("Must never happen");
		case STRING:
			return jsonStringTypeAdapter.read(in);
		case NUMBER:
			return jsonNumberTypeAdapter.read(in);
		case BOOLEAN:
			return jsonBooleanTypeAdapter.read(in);
		case NULL:
			return jsonNullTypeAdapter.read(in);
		case END_DOCUMENT:
			throw new AssertionError("Must never happen");
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

		private static final TypeAdapter<JsonValue> instance = new JsonBooleanTypeAdapter()
				.nullSafe();

		@Override
		public void write(final JsonWriter out, final JsonValue jsonBoolean)
				throws IllegalArgumentException, IOException {
			final ValueType valueType = jsonBoolean.getValueType();
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

		private static TypeAdapter<JsonNumber> getInstance(final JsonProvider jsonProvider) {
			return new JsonNumberTypeAdapter(jsonProvider)
					.nullSafe();
		}

		@Override
		public void write(final JsonWriter out, final JsonNumber jsonNumber)
				throws IOException {
			try {
				out.value(jsonNumber.intValueExact());
			} catch ( final ArithmeticException exIntExact ) {
				try {
					out.value(jsonNumber.longValueExact());
				} catch ( final ArithmeticException exLongExact ) {
					out.value(jsonNumber.bigDecimalValue());
				}
			}
		}

		@Override
		public JsonNumber read(final JsonReader in)
				throws IOException {
			final String rawValue = in.nextString();
			final Number number = new BigDecimal(rawValue);
			return jsonProvider.createValue((BigDecimal) number);
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class JsonStringTypeAdapter
			extends TypeAdapter<JsonString> {

		private final JsonProvider jsonProvider;

		private static TypeAdapter<JsonString> getInstance(final JsonProvider jsonProvider) {
			return new JsonStringTypeAdapter(jsonProvider)
					.nullSafe();
		}

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
		public JsonObject read(final JsonReader in)
				throws IOException {
			final JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
			in.beginObject();
			while ( in.hasNext() ) {
				final String key = in.nextName();
				final JsonToken token = in.peek();
				switch ( token ) {
				case BEGIN_ARRAY:
					jsonObjectBuilder.add(key, JsonValueTypeAdapter.this.read(in));
					break;
				case END_ARRAY:
					throw new AssertionError("Must never happen due to delegation to the array type adapter");
				case BEGIN_OBJECT:
					jsonObjectBuilder.add(key, JsonValueTypeAdapter.this.read(in));
					break;
				case END_OBJECT:
					throw new AssertionError("Must never happen due to delegation to the object type adapter");
				case NAME:
					throw new AssertionError("Must never happen");
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
		public JsonArray read(final JsonReader in)
				throws IOException {
			final JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
			in.beginArray();
			while ( in.hasNext() ) {
				final JsonToken token = in.peek();
				switch ( token ) {
				case BEGIN_ARRAY:
					jsonArrayBuilder.add(JsonValueTypeAdapter.this.read(in));
					break;
				case END_ARRAY:
					throw new AssertionError("Must never happen due to delegation to the array type adapter");
				case BEGIN_OBJECT:
					jsonArrayBuilder.add(JsonValueTypeAdapter.this.read(in));
					break;
				case END_OBJECT:
					throw new AssertionError("Must never happen due to delegation to the object type adapter");
				case NAME:
					throw new AssertionError("Must never happen");
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
				default:
					throw new AssertionError(token);
				}
			}
			in.endArray();
			return jsonArrayBuilder.build();
		}

	}

}
