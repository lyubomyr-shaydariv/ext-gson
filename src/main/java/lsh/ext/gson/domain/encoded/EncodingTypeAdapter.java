package lsh.ext.gson.domain.encoded;

import java.util.Base64;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.LiteralStringTypeAdapter;

@UtilityClass
public final class EncodingTypeAdapter {

	public static final TypeAdapter<byte[]> base64ForPrimitiveByteArray = LiteralStringTypeAdapter.getInstance(
			Base64.getEncoder()::encodeToString,
			Base64.getDecoder()::decode
	);

	public static final TypeAdapter<byte[]> urlForPrimitiveByteArray = LiteralStringTypeAdapter.getInstance(
			Base64.getUrlEncoder()::encodeToString,
			Base64.getUrlDecoder()::decode
	);

}
