package lsh.ext.gson.ext;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

import com.google.common.io.BaseEncoding;
import com.google.gson.TypeAdapter;
import lsh.ext.gson.IFunction1;
import lsh.ext.gson.domain.encoded.CharArrayTypeAdapter;
import lsh.ext.gson.test.MoreMockito;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public final class CharArrayTypeAdapterTest {

	private static final char[] emptyArray = {};
	private static final String ENCODED_EMPTY_DATA = "";
	private static final String ENCODED_EMPTY_DATA_LITERAL = "\"\"";

	private static final char[] data = { 'f', 'o', 'o' };
	private static final String ENCODED_DATA_LITERAL = "\"Zm9v\"";

	private static final BaseEncoding encoding = BaseEncoding.base64();
	private static final CharsetEncoder charsetEncoder = StandardCharsets.UTF_8.newEncoder();
	private static final CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder();

	@Test
	public void testWriteEmpty() {
		final IFunction1<? super char[], String> encodeSpy = Mockito.mock(AdditionalAnswers.delegatesTo((IFunction1<char[], String>) CharArrayTypeAdapterTest::encode));
		final TypeAdapter<char[]> unit = CharArrayTypeAdapter.getInstance(encodeSpy, Mockito.mock(MoreMockito.assertionError()));
		Assertions.assertEquals(ENCODED_EMPTY_DATA_LITERAL, unit.toJson(emptyArray));
		// make sure the encoder works even for empty data
		Mockito.verify(encodeSpy)
				.apply(AdditionalMatchers.aryEq(emptyArray));
		Mockito.verifyNoMoreInteractions(encodeSpy);
	}

	@Test
	public void testWriteNonEmpty() {
		final TypeAdapter<char[]> unit = CharArrayTypeAdapter.getInstance(CharArrayTypeAdapterTest::encode, Mockito.mock(MoreMockito.assertionError()));
		Assertions.assertEquals(ENCODED_DATA_LITERAL, unit.toJson(data));
	}

	@Test
	public void testReadEmpty()
			throws IOException {
		final IFunction1<? super String, char[]> decodeSpy = Mockito.mock(AdditionalAnswers.delegatesTo((IFunction1<String, char[]>) CharArrayTypeAdapterTest::decode));
		final TypeAdapter<char[]> unit = CharArrayTypeAdapter.getInstance(Mockito.mock(MoreMockito.assertionError()), decodeSpy);
		Assertions.assertArrayEquals(emptyArray, unit.fromJson(ENCODED_EMPTY_DATA_LITERAL));
		// make sure the decoder works even for empty data
		Mockito.verify(decodeSpy)
				.apply(ArgumentMatchers.eq(ENCODED_EMPTY_DATA));
		Mockito.verifyNoMoreInteractions(decodeSpy);
	}

	@Test
	public void testReadNonEmpty()
			throws IOException {
		final TypeAdapter<char[]> unit = CharArrayTypeAdapter.getInstance(Mockito.mock(MoreMockito.assertionError()), CharArrayTypeAdapterTest::decode);
		Assertions.assertArrayEquals(data, unit.fromJson(ENCODED_DATA_LITERAL));
	}

	private static String encode(final char[] array) {
		try {
			final byte[] data = charsetEncoder.encode(CharBuffer.wrap(array))
					.array();
			return encoding.encode(data);
		} catch ( final IOException ex ) {
			throw new RuntimeException(ex);
		}
	}

	private static char[] decode(final String s) {
		try {
			return charsetDecoder.decode(ByteBuffer.wrap(encoding.decode(s)))
					.array();
		} catch ( final IOException ex ) {
			throw new RuntimeException(ex);
		}
	}

}
