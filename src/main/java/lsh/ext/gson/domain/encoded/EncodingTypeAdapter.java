package lsh.ext.gson.domain.encoded;

import java.util.Base64;

import com.google.gson.TypeAdapter;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.IFunction1;
import lsh.ext.gson.JsonStringTypeAdapter;

@UtilityClass
public final class EncodingTypeAdapter {

	@Getter
	private static final TypeAdapter<byte[]> defaultForPrimitiveByteArrayAsBase64 = forPrimitiveByteArray(
			Base64.getEncoder()::encodeToString,
			Base64.getDecoder()::decode
	);

	@Getter
	private static final TypeAdapter<byte[]> defaultForPrimitiveByteArrayAsUrl = forPrimitiveByteArray(
			Base64.getUrlEncoder()::encodeToString,
			Base64.getUrlDecoder()::decode
	);

	public static TypeAdapter<byte[]> forPrimitiveByteArray(
			final IFunction1<? super byte[], String> encode,
			final IFunction1<? super String, byte[]> decode
	) {
		return JsonStringTypeAdapter.getInstance(encode, decode);
	}

	public static TypeAdapter<char[]> forPrimitiveCharArray(
			final IFunction1<? super char[], String> encode,
			final IFunction1<? super String, char[]> decode
	) {
		return JsonStringTypeAdapter.getInstance(encode, decode);
	}

}
