package lsh.ext.gson.ext.java.util.regex;

import java.util.regex.Pattern;

import com.google.gson.TypeAdapter;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class RegexTypeAdapterFactory {

	@Getter
	private static ITypeAdapterFactory<Pattern> defaultForEdStylePattern = forEdStylePattern(RegexTypeAdapter.getDefaultForEdStylePattern());

	public static ITypeAdapterFactory<Pattern> forEdStylePattern(final TypeAdapter<Pattern> typeAdapter) {
		return ITypeAdapterFactory.forClass(Pattern.class, () -> typeAdapter);
	}

	@Getter
	private static ITypeAdapterFactory<Pattern> defaultForSimplePattern = forSimplePattern(RegexTypeAdapter.getDefaultForSimplePattern());

	public static ITypeAdapterFactory<Pattern> forSimplePattern(final TypeAdapter<Pattern> typeAdapter) {
		return ITypeAdapterFactory.forClass(Pattern.class, () -> typeAdapter);
	}

}
