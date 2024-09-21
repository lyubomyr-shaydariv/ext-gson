package lsh.ext.gson.domain.encoded;

import java.util.Base64;
import java.util.function.Function;

import com.google.gson.TypeAdapter;
import lombok.Getter;
import lombok.experimental.UtilityClass;
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
			final Function<? super byte[], String> encode,
			final Function<? super String, byte[]> decode
	) {
		return JsonStringTypeAdapter.getInstance(encode, decode);
	}

	public static TypeAdapter<char[]> forPrimitiveCharArray(
			final Function<? super char[], String> encode,
			final Function<? super String, char[]> decode
	) {
		return JsonStringTypeAdapter.getInstance(encode, decode);
	}

}
