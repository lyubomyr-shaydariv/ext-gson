package lsh.ext.gson.ext;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

import com.google.common.io.BaseEncoding;
import com.google.gson.TypeAdapter;
import lsh.ext.gson.domain.encoded.CharArrayTypeAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public final class CharArrayTypeAdapterTest {

	private static final char[] emptyArray = {};
	private static final String ENCODED_EMPTY_DATA = "";
	private static final String ENCODED_EMPTY_DATA_LITERAL = "\"\"";

	private static final char[] data = { 'f', 'o', 'o' };
	private static final String ENCODED_DATA_LITERAL = "\"Zm9v\"";

	private static final CharArrayTypeAdapter.IEncoder encoder = new CharArrayTypeAdapter.IEncoder() {
		private static final BaseEncoding encoding = BaseEncoding.base64();
		private static final CharsetEncoder charsetEncoder = StandardCharsets.UTF_8.newEncoder();
		private static final CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder();

		@Override
		public String encode(final char[] array) {
			try {
				final byte[] data = charsetEncoder.encode(CharBuffer.wrap(array))
						.array();
				return encoding.encode(data);
			} catch ( final IOException ex ) {
				throw new RuntimeException(ex);
			}
		}

		@Override
		public char[] decode(final String s) {
			try {
				return charsetDecoder.decode(ByteBuffer.wrap(encoding.decode(s)))
						.array();
			} catch ( final IOException ex ) {
				throw new RuntimeException(ex);
			}
		}
	};

	@Test
	public void testWriteEmpty() {
		final CharArrayTypeAdapter.IEncoder encoderSpy = Mockito.spy(encoder);
		final TypeAdapter<char[]> unit = CharArrayTypeAdapter.getInstance(encoderSpy);
		Assertions.assertEquals(ENCODED_EMPTY_DATA_LITERAL, unit.toJson(emptyArray));
		// make sure the encoder works even for empty data
		Mockito.verify(encoderSpy)
				.encode(AdditionalMatchers.aryEq(emptyArray));
		Mockito.verifyNoMoreInteractions(encoderSpy);
	}

	@Test
	public void testWriteNonEmpty() {
		final TypeAdapter<char[]> unit = CharArrayTypeAdapter.getInstance(encoder);
		Assertions.assertEquals(ENCODED_DATA_LITERAL, unit.toJson(data));
	}

	@Test
	public void testReadEmpty()
			throws IOException {
		final CharArrayTypeAdapter.IEncoder encoderSpy = Mockito.spy(encoder);
		final TypeAdapter<char[]> unit = CharArrayTypeAdapter.getInstance(encoderSpy);
		Assertions.assertArrayEquals(emptyArray, unit.fromJson(ENCODED_EMPTY_DATA_LITERAL));
		// make sure the decoder works even for empty data
		Mockito.verify(encoderSpy)
				.decode(ArgumentMatchers.eq(ENCODED_EMPTY_DATA));
		Mockito.verifyNoMoreInteractions(encoderSpy);
	}

	@Test
	public void testReadNonEmpty()
			throws IOException {
		final TypeAdapter<char[]> unit = CharArrayTypeAdapter.getInstance(encoder);
		Assertions.assertArrayEquals(data, unit.fromJson(ENCODED_DATA_LITERAL));
	}

}
