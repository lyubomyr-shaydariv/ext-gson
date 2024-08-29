package lsh.ext.gson.ext;

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
public final class ByteArrayTypeAdapter
		extends AbstractEncodingTypeAdapter<byte[], String> {

	public interface IEncoder {

		String encode(byte[] array);

		byte[] decode(String s);

	}

	private final IEncoder encoder;

	public static TypeAdapter<byte[]> getInstance(final IEncoder encoder) {
		return new ByteArrayTypeAdapter(encoder)
				.nullSafe();
	}

	@Override
	protected String encode(final byte[] value) {
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
	protected byte[] decode(final String encodedValue) {
		return encoder.decode(encodedValue);
	}

	public static final class Factory
			extends AbstractRawClassTypeAdapterFactory<byte[]> {

		private final TypeAdapter<byte[]> typeAdapter;

		private Factory(final TypeAdapter<byte[]> typeAdapter) {
			super(byte[].class);
			this.typeAdapter = typeAdapter;
		}

		@Override
		protected TypeAdapter<byte[]> createTypeAdapter(final Gson gson, final TypeToken<? super byte[]> typeToken) {
			return typeAdapter;
		}

	}

}
