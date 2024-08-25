package lsh.ext.gson.ext;

import java.io.IOException;

import com.google.common.io.BaseEncoding;
import com.google.gson.TypeAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public final class ByteArrayTypeAdapterTest {

	private static final byte[] emptyArray = {};
	private static final String ENCODED_EMPTY_DATA = "";
	private static final String ENCODED_EMPTY_DATA_LITERAL = "\"\"";

	private static final byte[] data = { 'f', 'o', 'o' };
	private static final String ENCODED_DATA_LITERAL = "\"Zm9v\"";

	private static final ByteArrayTypeAdapter.IEncoder encoder = new ByteArrayTypeAdapter.IEncoder() {
		private static final BaseEncoding baseEncoding = BaseEncoding.base64();

		@Override
		public String encode(final byte[] array) {
			return baseEncoding.encode(array);
		}

		@Override
		public byte[] decode(final String s) {
			return baseEncoding.decode(s);
		}
	};

	@Test
	public void testWriteEmpty() {
		final ByteArrayTypeAdapter.IEncoder encoderSpy = Mockito.spy(encoder);
		final TypeAdapter<byte[]> unit = ByteArrayTypeAdapter.getInstance(encoderSpy);
		Assertions.assertEquals(ENCODED_EMPTY_DATA_LITERAL, unit.toJson(emptyArray));
		// make sure the encoder works even for empty data
		Mockito.verify(encoderSpy)
				.encode(AdditionalMatchers.aryEq(emptyArray));
		Mockito.verifyNoMoreInteractions(encoderSpy);
	}

	@Test
	public void testWriteNonEmpty() {
		final TypeAdapter<byte[]> unit = ByteArrayTypeAdapter.getInstance(encoder);
		Assertions.assertEquals(ENCODED_DATA_LITERAL, unit.toJson(data));
	}

	@Test
	public void testReadEmpty()
			throws IOException {
		final ByteArrayTypeAdapter.IEncoder encoderSpy = Mockito.spy(encoder);
		final TypeAdapter<byte[]> unit = ByteArrayTypeAdapter.getInstance(encoderSpy);
		Assertions.assertArrayEquals(emptyArray, unit.fromJson(ENCODED_EMPTY_DATA_LITERAL));
		// make sure the decoder works even for empty data
		Mockito.verify(encoderSpy)
				.decode(ArgumentMatchers.eq(ENCODED_EMPTY_DATA));
		Mockito.verifyNoMoreInteractions(encoderSpy);
	}

	@Test
	public void testReadNonEmpty()
			throws IOException {
		final TypeAdapter<byte[]> unit = ByteArrayTypeAdapter.getInstance(encoder);
		Assertions.assertArrayEquals(data, unit.fromJson(ENCODED_DATA_LITERAL));
	}

}
