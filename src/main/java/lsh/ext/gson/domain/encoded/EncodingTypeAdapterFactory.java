package lsh.ext.gson.domain.encoded;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.IFunction1;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class EncodingTypeAdapterFactory {

	public static ITypeAdapterFactory<byte[]> forPrimitiveByteArray(
			final IFunction1<? super byte[], String> encode,
			final IFunction1<? super String, byte[]> decode
	) {
		return forPrimitiveByteArray(ByteArrayTypeAdapter.getInstance(encode, decode));
	}

	public static ITypeAdapterFactory<byte[]> forPrimitiveByteArray(final TypeAdapter<byte[]> typeAdapter) {
		return ITypeAdapterFactory.forClass(byte[].class, () -> typeAdapter);
	}

	public static ITypeAdapterFactory<char[]> forPrimitiveCharArray(
			final IFunction1<? super char[], String> encode,
			final IFunction1<? super String, char[]> decode
	) {
		return forPrimitiveCharArray(CharArrayTypeAdapter.getInstance(encode, decode));
	}

	public static ITypeAdapterFactory<char[]> forPrimitiveCharArray(final TypeAdapter<char[]> typeAdapter) {
		return ITypeAdapterFactory.forClass(char[].class, () -> typeAdapter);
	}

}
