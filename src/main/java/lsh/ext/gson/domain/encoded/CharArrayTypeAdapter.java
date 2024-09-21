package lsh.ext.gson.domain.encoded;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractEncodingTypeAdapter;
import lsh.ext.gson.IFunction1;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CharArrayTypeAdapter
		extends AbstractEncodingTypeAdapter<char[], String> {

	private final IFunction1<? super char[], String> encode;
	private final IFunction1<? super String, char[]> decode;

	public static TypeAdapter<char[]> getInstance(
			final IFunction1<? super char[], String> encode,
			final IFunction1<? super String, char[]> decode
	) {
		return new CharArrayTypeAdapter(encode, decode)
				.nullSafe();
	}

	@Override
	protected String encode(final char[] value) {
		return encode.apply(value);
	}

	@Override
	protected void writeEncoded(final JsonWriter out, final String encodedValue)
			throws IOException {
		out.value(encodedValue);
	}

	@Override
	protected String readEncoded(final JsonReader in)
			throws IOException {
		return in.nextString();
	}

	@Override
	protected char[] decode(final String encodedValue) {
		return decode.apply(encodedValue);
	}

}
