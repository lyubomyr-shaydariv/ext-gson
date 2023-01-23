package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.stream.JsonToken;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public final class ValuedJsonToken<T> {

	private static final ValuedJsonToken<Void> arrayBegin = new ValuedJsonToken<>(JsonToken.BEGIN_ARRAY, null);
	private static final ValuedJsonToken<Void> arrayEnd = new ValuedJsonToken<>(JsonToken.END_ARRAY, null);
	private static final ValuedJsonToken<Void> objectBegin = new ValuedJsonToken<>(JsonToken.BEGIN_OBJECT, null);
	private static final ValuedJsonToken<Void> objectEnd = new ValuedJsonToken<>(JsonToken.END_OBJECT, null);
	private static final ValuedJsonToken<Void> documentEnd = new ValuedJsonToken<>(JsonToken.END_DOCUMENT, null);
	private static final ValuedJsonToken<Boolean> trueBoolean = new ValuedJsonToken<>(JsonToken.BOOLEAN, true);
	private static final ValuedJsonToken<Boolean> falseBoolean = new ValuedJsonToken<>(JsonToken.BOOLEAN, false);
	private static final ValuedJsonToken<String> nullStringValue = new ValuedJsonToken<>(JsonToken.STRING, null);
	private static final ValuedJsonToken<Boolean> nullBooleanValue = new ValuedJsonToken<>(JsonToken.BOOLEAN, null);
	private static final ValuedJsonToken<Number> nullNumberValue = new ValuedJsonToken<>(JsonToken.NUMBER, null);
	private static final ValuedJsonToken<Void> nullValue = new ValuedJsonToken<>(JsonToken.NULL, null);

	@Getter
	private final JsonToken token;

	@Nullable
	@Getter
	private final T value;

	public static ValuedJsonToken<Void> arrayBegin() {
		return arrayBegin;
	}

	public static ValuedJsonToken<Void> arrayEnd() {
		return arrayEnd;
	}

	public static ValuedJsonToken<Void> objectBegin() {
		return objectBegin;
	}

	public static ValuedJsonToken<Void> objectEnd() {
		return objectEnd;
	}

	public static ValuedJsonToken<Void> documentEnd() {
		return documentEnd;
	}

	public static ValuedJsonToken<String> name(final String name) {
		return new ValuedJsonToken<>(JsonToken.NAME, name);
	}

	public static ValuedJsonToken<String> value(@Nullable final String value) {
		if ( value == null ) {
			return nullStringValue;
		}
		return new ValuedJsonToken<>(JsonToken.STRING, value);
	}

	public static ValuedJsonToken<Boolean> value(@Nullable final Boolean value) {
		if ( value == null ) {
			return nullBooleanValue;
		}
		return value ? trueBoolean : falseBoolean;
	}

	public static ValuedJsonToken<Number> value(@Nullable final Number value) {
		if ( value == null ) {
			return nullNumberValue;
		}
		return new ValuedJsonToken<>(JsonToken.NUMBER, value);
	}

	public static ValuedJsonToken<Void> value() {
		return nullValue;
	}

}
