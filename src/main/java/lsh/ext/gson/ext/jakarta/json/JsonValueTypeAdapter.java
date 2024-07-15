package lsh.ext.gson.ext.jakarta.json;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import jakarta.json.spi.JsonProvider;
import lombok.Getter;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.extshared.jakarta.json.x.javax.json.AbstractJsonValueTypeAdapter;

@SuppressWarnings("CPD-START")
public final class JsonValueTypeAdapter
		extends AbstractJsonValueTypeAdapter<JsonValue, JsonArray, JsonObject, JsonString, JsonNumber, JsonValue, JsonValue> {

	@Getter(onMethod_ = @SuppressFBWarnings("MS_EXPOSE_REP"))
	private static final TypeAdapter<JsonValue> instance = getInstance(JsonProvider.provider());

	private final JsonProvider jsonProvider;

	private JsonValueTypeAdapter(final JsonProvider jsonProvider) {
		super(JsonValue.NULL, JsonValue.TRUE, JsonValue.FALSE);
		this.jsonProvider = jsonProvider;
	}

	public static TypeAdapter<JsonValue> getInstance(final JsonProvider jsonProvider) {
		return new JsonValueTypeAdapter(jsonProvider)
				.nullSafe();
	}

	@Override
	protected ValueType getValueType(final JsonValue value) {
		switch ( value.getValueType() ) {
		case ARRAY:
			return ValueType.ARRAY;
		case OBJECT:
			return ValueType.OBJECT;
		case STRING:
			return ValueType.STRING;
		case NUMBER:
			return ValueType.NUMBER;
		case TRUE:
			return ValueType.TRUE;
		case FALSE:
			return ValueType.FALSE;
		case NULL:
			return ValueType.NULL;
		default:
			throw new AssertionError(value.getValueType());
		}
	}

	@Override
	protected String toStringFromJsonString(final JsonString value) {
		return value.getString();
	}

	@Override
	protected Number toNumberFromJsonNumber(final JsonNumber value) {
		return value.bigDecimalValue();
	}

	@Override
	protected JsonString toJsonStringFromString(final String value) {
		return jsonProvider.createValue(value);
	}

	@Override
	protected JsonNumber toJsonNumberFromString(final String value) {
		return jsonProvider.createValue(new BigDecimal(value));
	}

	@Override
	protected Iterable<? extends JsonValue> toJsonValueIterableFromJsonArray(final JsonArray value) {
		return value;
	}

	@Override
	protected Map<String, ? extends JsonValue> toJsonValueMapFromJsonObject(final JsonObject value) {
		return value;
	}

	@Override
	protected JsonArray toJsonArrayFromJsonValueList(final List<? extends JsonValue> value) {
		return jsonProvider.createArrayBuilder(value)
				.build();
	}

	@Override
	protected JsonObject toJsonObjectFromJsonValueMap(final Map<String, ? extends JsonValue> value) {
		return jsonProvider.createObjectBuilder(value)
				.build();
	}

	public static final class Factory
			extends AbstractFactory<JsonValue, JsonProvider> {

		private Factory(final JsonProvider jsonProvider) {
			super(JsonValue.class, jsonProvider);
		}

		public static ITypeAdapterFactory<JsonValue> getInstance(final JsonProvider jsonProvider) {
			return new Factory(jsonProvider);
		}

		@Override
		protected TypeAdapter<JsonValue> getJsonValueTypeAdapter() {
			return JsonValueTypeAdapter.getInstance();
		}

		@Override
		protected TypeAdapter<JsonValue> getJsonValueTypeAdapter(final JsonProvider jsonProvider) {
			return JsonValueTypeAdapter.getInstance(jsonProvider);
		}

	}

}
