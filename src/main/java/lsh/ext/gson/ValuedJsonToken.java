package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.stream.JsonToken;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class ValuedJsonToken<T> {

	private static final ValuedJsonToken<Void> arrayBegin = new ValuedJsonToken<>(JsonToken.BEGIN_ARRAY, null);
	private static final ValuedJsonToken<Void> arrayEnd = new ValuedJsonToken<>(JsonToken.END_ARRAY, null);
	private static final ValuedJsonToken<Void> objectBegin = new ValuedJsonToken<>(JsonToken.BEGIN_OBJECT, null);
	private static final ValuedJsonToken<Void> objectEnd = new ValuedJsonToken<>(JsonToken.END_OBJECT, null);
	private static final ValuedJsonToken<Void> documentEnd = new ValuedJsonToken<>(JsonToken.END_DOCUMENT, null);
	private static final ValuedJsonToken<Boolean> trueBoolean = new ValuedJsonToken<>(JsonToken.BOOLEAN, true);
	private static final ValuedJsonToken<Boolean> falseBoolean = new ValuedJsonToken<>(JsonToken.BOOLEAN, false);
	private static final ValuedJsonToken<Void> nullValue = new ValuedJsonToken<>(JsonToken.NULL, null);

	private final JsonToken token;

	@Nullable
	private final T value;

	private ValuedJsonToken(final JsonToken token, @Nullable final T value) {
		this.token = token;
		this.value = value;
	}

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

	public static ValuedJsonToken<String> value(final String s) {
		return new ValuedJsonToken<>(JsonToken.STRING, s);
	}

	public static ValuedJsonToken<Boolean> value(final boolean b) {
		return b ? trueBoolean : falseBoolean;
	}

	public static ValuedJsonToken<Number> value(final Number n) {
		return new ValuedJsonToken<>(JsonToken.NUMBER, n);
	}

	public static ValuedJsonToken<Void> value() {
		return nullValue;
	}

	public JsonToken getToken() {
		return token;
	}

	@Nullable
	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		if ( value == null ) {
			return token.toString();
		}
		return token + "=" + value;
	}

}
