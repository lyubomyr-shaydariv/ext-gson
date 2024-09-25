package lsh.ext.gson.ext.com.google.common.base;

import com.google.common.base.Optional;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class OptionalTypeAdapterFactory {

	public static ITypeAdapterFactory<Optional<Object>> defaultForOptional = ITypeAdapterFactory.forClassHierarchy(Optional.class, provider -> OptionalTypeAdapter.getInstance(provider.getTypeAdapter(0)));

}
