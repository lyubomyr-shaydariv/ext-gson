package lsh.ext.gson.ext;

import java.io.IOException;

import com.google.common.io.BaseEncoding;
import com.google.gson.TypeAdapter;
import lsh.ext.gson.IFunction1;
import lsh.ext.gson.domain.encoded.EncodingTypeAdapter;
import lsh.ext.gson.test.MoreMockito;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public final class ByteArrayTypeAdapterTest {

	private static final byte[] emptyArray = {};
	private static final String ENCODED_EMPTY_DATA = "";
	private static final String ENCODED_EMPTY_DATA_LITERAL = "\"\"";

	private static final byte[] data = { 'f', 'o', 'o' };
	private static final String ENCODED_DATA_LITERAL = "\"Zm9v\"";

	private static final BaseEncoding encoding = BaseEncoding.base64();

	@Test
	public void testWriteEmpty() {
		final IFunction1<? super byte[], String> encodeSpy = Mockito.mock(AdditionalAnswers.delegatesTo((IFunction1<byte[], String>) encoding::encode));
		final TypeAdapter<byte[]> unit = EncodingTypeAdapter.forPrimitiveByteArray(encodeSpy, Mockito.mock(MoreMockito.assertionError()));
		Assertions.assertEquals(ENCODED_EMPTY_DATA_LITERAL, unit.toJson(emptyArray));
		// make sure the encoder works even for empty data
		Mockito.verify(encodeSpy)
				.apply(AdditionalMatchers.aryEq(emptyArray));
		Mockito.verifyNoMoreInteractions(encodeSpy);
	}

	@Test
	public void testWriteNonEmpty() {
		final TypeAdapter<byte[]> unit = EncodingTypeAdapter.forPrimitiveByteArray(encoding::encode, encoding::decode);
		Assertions.assertEquals(ENCODED_DATA_LITERAL, unit.toJson(data));
	}

	@Test
	public void testReadEmpty()
			throws IOException {
		final IFunction1<? super String, byte[]> decodeSpy = Mockito.mock(AdditionalAnswers.delegatesTo((IFunction1<String, byte[]>) encoding::decode));
		final TypeAdapter<byte[]> unit = EncodingTypeAdapter.forPrimitiveByteArray(Mockito.mock(MoreMockito.assertionError()), decodeSpy);
		Assertions.assertArrayEquals(emptyArray, unit.fromJson(ENCODED_EMPTY_DATA_LITERAL));
		// make sure the decoder works even for empty data
		Mockito.verify(decodeSpy)
				.apply(ArgumentMatchers.eq(ENCODED_EMPTY_DATA));
		Mockito.verifyNoMoreInteractions(decodeSpy);
	}

	@Test
	public void testReadNonEmpty()
			throws IOException {
		final TypeAdapter<byte[]> unit = EncodingTypeAdapter.forPrimitiveByteArray(encoding::encode, encoding::decode);
		Assertions.assertArrayEquals(data, unit.fromJson(ENCODED_DATA_LITERAL));
	}

}
