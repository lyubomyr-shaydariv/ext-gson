package lsh.ext.gson.domain.encoded;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class EncodingTypeAdapterFactory {

	public static ITypeAdapterFactory<byte[]> forPrimitiveByteArray(final ByteArrayTypeAdapter.IEncoder encoder) {
		return forPrimitiveByteArray(ByteArrayTypeAdapter.getInstance(encoder));
	}

	public static ITypeAdapterFactory<byte[]> forPrimitiveByteArray(final TypeAdapter<byte[]> typeAdapter) {
		return ITypeAdapterFactory.forClass(byte[].class, () -> typeAdapter);
	}

	public static ITypeAdapterFactory<char[]> forPrimitiveCharArray(final CharArrayTypeAdapter.IEncoder encoder) {
		return forPrimitiveCharArray(CharArrayTypeAdapter.getInstance(encoder));
	}

	public static ITypeAdapterFactory<char[]> forPrimitiveCharArray(final TypeAdapter<char[]> typeAdapter) {
		return ITypeAdapterFactory.forClass(char[].class, () -> typeAdapter);
	}

}
