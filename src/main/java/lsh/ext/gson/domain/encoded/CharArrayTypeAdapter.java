package lsh.ext.gson.domain.encoded;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractEncodingTypeAdapter;
import lsh.ext.gson.AbstractRawClassTypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CharArrayTypeAdapter
		extends AbstractEncodingTypeAdapter<char[], String> {

	public interface IEncoder {

		String encode(char[] array);

		char[] decode(String s);

	}

	private final IEncoder encoder;

	public static TypeAdapter<char[]> getInstance(final IEncoder encoder) {
		return new CharArrayTypeAdapter(encoder)
				.nullSafe();
	}

	@Override
	protected String encode(final char[] value) {
		return encoder.encode(value);
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
		return encoder.decode(encodedValue);
	}

	public static final class Factory
			extends AbstractRawClassTypeAdapterFactory<char[]> {

		private final TypeAdapter<char[]> typeAdapter;

		private Factory(final TypeAdapter<char[]> typeAdapter) {
			super(char[].class);
			this.typeAdapter = typeAdapter;
		}

		@Override
		protected TypeAdapter<char[]> createTypeAdapter(final Gson gson, final TypeToken<? super char[]> typeToken) {
			return typeAdapter;
		}

	}

}