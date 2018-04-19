package lsh.ext.gson.adapters.jsonapi;

import java.io.IOException;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import lsh.ext.gson.Numbers;

/**
 * <p>Represents a type adapter for {@code javax.json} JSON values. During the {@link #read(JsonReader)} execution, numeric values are parsed as {@code long}
 * or {@code double} values using the {@link Numbers#tryParseDouble(String)} method.</p>
 *
 * @author Lyubomyr Shaydariv
 * @see JsonValue
 * @see JsonNumber
 * @see JsonString
 * @see JsonObject
 * @see JsonArray
 * @since 0-SNAPSHOT
 */
public final class JsonValueTypeAdapter
		extends TypeAdapter<JsonValue> {

	private static final TypeAdapter<JsonValue> instance = new JsonValueTypeAdapter();

	private JsonValueTypeAdapter() {
	}

	/**
	 * @return An instance of {@link JsonValueTypeAdapter}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static TypeAdapter<JsonValue> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final JsonValue jsonValue)
			throws IOException {
		final ValueType valueType = jsonValue.getValueType();
		switch ( valueType ) {
		case ARRAY:
			JsonArrayTypeAdapter.instance.write(out, (JsonArray) jsonValue);
			break;
		case OBJECT:
			JsonObjectTypeAdapter.instance.write(out, (JsonObject) jsonValue);
			break;
		case STRING:
			JsonStringTypeAdapter.instance.write(out, (JsonString) jsonValue);
			break;
		case NUMBER:
			JsonNumberTypeAdapter.instance.write(out, (JsonNumber) jsonValue);
			break;
		case TRUE:
			JsonBooleanTypeAdapter.instance.write(out, jsonValue);
			break;
		case FALSE:
			JsonBooleanTypeAdapter.instance.write(out, jsonValue);
			break;
		case NULL:
			JsonNullTypeAdapter.instance.write(out, jsonValue);
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
			return JsonArrayTypeAdapter.instance.read(in);
		case END_ARRAY:
			throw new AssertionError("Must never happen due to delegation to the array type adapter");
		case BEGIN_OBJECT:
			return JsonObjectTypeAdapter.instance.read(in);
		case END_OBJECT:
			throw new AssertionError("Must never happen due to delegation to the object type adapter");
		case NAME:
			throw new AssertionError("Must never happen");
		case STRING:
			return JsonStringTypeAdapter.instance.read(in);
		case NUMBER:
			return JsonNumberTypeAdapter.instance.read(in);
		case BOOLEAN:
			return JsonBooleanTypeAdapter.instance.read(in);
		case NULL:
			return JsonNullTypeAdapter.instance.read(in);
		case END_DOCUMENT:
			throw new AssertionError("Must never happen");
		default:
			throw new AssertionError(jsonToken);
		}
	}

	private static final class JsonNullTypeAdapter
			extends TypeAdapter<JsonValue> {

		private static final TypeAdapter<JsonValue> instance = new JsonNullTypeAdapter().nullSafe();

		@Override
		@SuppressWarnings("resource")
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

	private static final class JsonBooleanTypeAdapter
			extends TypeAdapter<JsonValue> {

		private static final TypeAdapter<JsonValue> instance = new JsonBooleanTypeAdapter().nullSafe();

		@Override
		@SuppressWarnings("resource")
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
				throw new IllegalArgumentException("Not a boolean: " + valueType);
			default:
				throw new AssertionError(jsonBoolean.getValueType());
			}
		}

		@Override
		public JsonValue read(final JsonReader in)
				throws IOException {
			return in.nextBoolean() ? JsonValue.TRUE : JsonValue.FALSE;
		}

	}

	private static final class JsonNumberTypeAdapter
			extends TypeAdapter<JsonNumber> {

		private static final TypeAdapter<JsonNumber> instance = new JsonNumberTypeAdapter().nullSafe();

		@Override
		@SuppressWarnings("resource")
		public void write(final JsonWriter out, final JsonNumber jsonNumber)
				throws IOException {
			if ( jsonNumber.isIntegral() ) {
				out.value(jsonNumber.longValue());
			} else {
				out.value(jsonNumber.doubleValue());
			}
		}

		@Override
		public JsonNumber read(final JsonReader in)
				throws IOException {
			final String rawValue = in.nextString();
			@Nullable
			final Number number = Numbers.tryParseLongOrDouble(rawValue);
			if ( number == null ) {
				throw new MalformedJsonException("Cannot parse " + rawValue);
			}
			// TODO is there a good way to instantiate a JsonNumber instance?
			final JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
			if ( number instanceof Long ) {
				jsonArrayBuilder.add(number.longValue());
			} else if ( number instanceof Double ) {
				jsonArrayBuilder.add(number.doubleValue());
			} else {
				throw new AssertionError("Unexpected: " + number.getClass());
			}
			return (JsonNumber) jsonArrayBuilder
					.build()
					.get(0);
		}

	}

	private static final class JsonStringTypeAdapter
			extends TypeAdapter<JsonString> {

		private static final TypeAdapter<JsonString> instance = new JsonStringTypeAdapter().nullSafe();

		@Override
		@SuppressWarnings("resource")
		public void write(final JsonWriter out, final JsonString jsonString)
				throws IOException {
			out.value(jsonString.getString());
		}

		@Override
		public JsonString read(final JsonReader in)
				throws IOException {
			// TODO is there a good way to instantiate a JsonString instance?
			return (JsonString) Json.createArrayBuilder()
					.add(in.nextString())
					.build()
					.get(0);
		}

	}

	private static final class JsonObjectTypeAdapter
			extends TypeAdapter<JsonObject> {

		private static final TypeAdapter<JsonObject> instance = new JsonObjectTypeAdapter().nullSafe();

		@Override
		@SuppressWarnings("resource")
		public void write(final JsonWriter out, final JsonObject jsonObject)
				throws IOException {
			out.beginObject();
			for ( final Entry<String, JsonValue> e : jsonObject.entrySet() ) {
				out.name(e.getKey());
				JsonValueTypeAdapter.instance.write(out, e.getValue());
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
					jsonObjectBuilder.add(key, JsonValueTypeAdapter.instance.read(in));
					break;
				case END_ARRAY:
					throw new AssertionError("Must never happen due to delegation to the array type adapter");
				case BEGIN_OBJECT:
					jsonObjectBuilder.add(key, JsonValueTypeAdapter.instance.read(in));
					break;
				case END_OBJECT:
					throw new AssertionError("Must never happen due to delegation to the object type adapter");
				case NAME:
					throw new AssertionError("Must never happen");
				case STRING:
					jsonObjectBuilder.add(key, in.nextString());
					break;
				case NUMBER:
					final String rawValue = in.nextString();
					@Nullable
					final Number number = Numbers.tryParseLongOrDouble(rawValue);
					if ( number == null ) {
						throw new MalformedJsonException("Cannot parse " + rawValue);
					}
					if ( number instanceof Long ) {
						jsonObjectBuilder.add(key, number.longValue());
					} else if ( number instanceof Double ) {
						jsonObjectBuilder.add(key, number.doubleValue());
					} else {
						throw new AssertionError("Unexpected: " + number.getClass());
					}
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

	private static final class JsonArrayTypeAdapter
			extends TypeAdapter<JsonArray> {

		private static final TypeAdapter<JsonArray> instance = new JsonArrayTypeAdapter().nullSafe();

		@Override
		@SuppressWarnings("resource")
		public void write(final JsonWriter out, final JsonArray jsonArray)
				throws IOException {
			out.beginArray();
			for ( final JsonValue jsonValue : jsonArray ) {
				JsonValueTypeAdapter.instance.write(out, jsonValue);
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
					jsonArrayBuilder.add(JsonValueTypeAdapter.instance.read(in));
					break;
				case END_ARRAY:
					throw new AssertionError("Must never happen due to delegation to the array type adapter");
				case BEGIN_OBJECT:
					jsonArrayBuilder.add(JsonValueTypeAdapter.instance.read(in));
					break;
				case END_OBJECT:
					throw new AssertionError("Must never happen due to delegation to the object type adapter");
				case NAME:
					throw new AssertionError("Must never happen");
				case STRING:
					jsonArrayBuilder.add(in.nextString());
					break;
				case NUMBER:
					final String rawValue = in.nextString();
					@Nullable
					final Number number = Numbers.tryParseLongOrDouble(rawValue);
					if ( number == null ) {
						throw new MalformedJsonException("Cannot parse " + rawValue);
					}
					if ( number instanceof Long ) {
						jsonArrayBuilder.add(number.longValue());
					} else if ( number instanceof Double ) {
						jsonArrayBuilder.add(number.doubleValue());
					} else {
						throw new AssertionError("Unexpected: " + number.getClass());
					}
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
