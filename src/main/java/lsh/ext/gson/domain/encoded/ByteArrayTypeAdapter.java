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
public final class ByteArrayTypeAdapter
		extends AbstractEncodingTypeAdapter<byte[], String> {

	private final IFunction1<? super byte[], String> encode;
	private final IFunction1<? super String, byte[]> decode;

	public static TypeAdapter<byte[]> getInstance(
			final IFunction1<? super byte[], String> encode,
			final IFunction1<? super String, byte[]> decode
	) {
		return new ByteArrayTypeAdapter(encode, decode)
				.nullSafe();
	}

	@Override
	protected String encode(final byte[] value) {
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
	protected byte[] decode(final String encodedValue) {
		return decode.apply(encodedValue);
	}

}
