package lsh.ext.gson.ext.java.util.regex;

import java.util.regex.Pattern;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class RegexTypeAdapterFactory {

	public static final ITypeAdapterFactory<Pattern> edStyleForPattern = forPattern(RegexTypeAdapter.edStyleForPattern);

	public static final ITypeAdapterFactory<Pattern> simpleForPattern = forPattern(RegexTypeAdapter.simpleForPattern);

	public static ITypeAdapterFactory<Pattern> forPattern(final TypeAdapter<Pattern> typeAdapter) {
		return ITypeAdapterFactory.forClass(Pattern.class, typeAdapter);
	}

}
