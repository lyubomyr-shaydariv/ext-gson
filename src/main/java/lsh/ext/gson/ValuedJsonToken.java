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
@SuppressWarnings("checkstyle:MissingJavadocType")
public final class ValuedJsonToken<T> {

	/**
	 * Array begin token, {@code [}.
	 */
	public static final ValuedJsonToken<Void> arrayBegin = new ValuedJsonToken<>(JsonToken.BEGIN_ARRAY, null);

	/**
	 * Array end token, {@code ]}.
	 */
	public static final ValuedJsonToken<Void> arrayEnd = new ValuedJsonToken<>(JsonToken.END_ARRAY, null);

	/**
	 * Object begin token, <code>{</code>.
	 */
	public static final ValuedJsonToken<Void> objectBegin = new ValuedJsonToken<>(JsonToken.BEGIN_OBJECT, null);

	/**
	 * Object end token, <code>}</code>.
	 */
	public static final ValuedJsonToken<Void> objectEnd = new ValuedJsonToken<>(JsonToken.END_OBJECT, null);

	/**
	 * Document end.
	 */
	public static final ValuedJsonToken<Void> documentEnd = new ValuedJsonToken<>(JsonToken.END_DOCUMENT, null);

	/**
	 * True value literal, {@code true}.
	 */
	public static final ValuedJsonToken<Boolean> trueBoolean = new ValuedJsonToken<>(JsonToken.BOOLEAN, true);

	/**
	 * False value literal, {@code false}.
	 */
	public static final ValuedJsonToken<Boolean> falseBoolean = new ValuedJsonToken<>(JsonToken.BOOLEAN, false);

	/**
	 * Null string value literal, {@code null}.
	 */
	public static final ValuedJsonToken<String> nullStringValue = new ValuedJsonToken<>(JsonToken.STRING, null);

	/**
	 * Null boolean value literal, {@code null}.
	 */
	public static final ValuedJsonToken<Boolean> nullBooleanValue = new ValuedJsonToken<>(JsonToken.BOOLEAN, null);

	/**
	 * Null number value literal, {@code null}.
	 */
	public static final ValuedJsonToken<Number> nullNumberValue = new ValuedJsonToken<>(JsonToken.NUMBER, null);

	/**
	 * Null literal, {@code null}.
	 */
	public static final ValuedJsonToken<Void> nullValue = new ValuedJsonToken<>(JsonToken.NULL, null);

	@Getter
	private final JsonToken token;

	@Nullable
	@Getter
	private final T value;

	@SuppressWarnings("checkstyle:MissingJavadocMethod")
	public static ValuedJsonToken<String> name(final String name) {
		return new ValuedJsonToken<>(JsonToken.NAME, name);
	}

	@SuppressWarnings("checkstyle:MissingJavadocMethod")
	public static ValuedJsonToken<String> value(@Nullable final String value) {
		if ( value == null ) {
			return nullStringValue;
		}
		return new ValuedJsonToken<>(JsonToken.STRING, value);
	}

	@SuppressWarnings("checkstyle:MissingJavadocMethod")
	public static ValuedJsonToken<Boolean> value(@Nullable final Boolean value) {
		if ( value == null ) {
			return nullBooleanValue;
		}
		return value ? trueBoolean : falseBoolean;
	}

	@SuppressWarnings("checkstyle:MissingJavadocMethod")
	public static ValuedJsonToken<Number> value(@Nullable final Number value) {
		if ( value == null ) {
			return nullNumberValue;
		}
		return new ValuedJsonToken<>(JsonToken.NUMBER, value);
	}

}
