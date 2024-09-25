package lsh.ext.gson.ext.com.google.common.cache;

import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class GuavaCacheTypeAdapterFactory {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final Class<? extends Cache<String, String>> stringStringToStringCacheClass = (Class) Cache.class;

	public static final ITypeAdapterFactory<Cache<String, String>> defaultForCache = forCache(() -> CacheBuilder.newBuilder()
			.build()
	);

	public static ITypeAdapterFactory<Cache<String, String>> forCache(
			final Supplier<? extends Cache<String, String>> getCache
	) {
		return forCache(stringStringToStringCacheClass, Function.identity(), Function.identity(), getCache);
	}

	public static <K, V, C extends Cache<K, V>> ITypeAdapterFactory<C> forCache(
			final Class<? extends C> klass,
			final Function<? super K, String> toName,
			final Function<? super String, ? extends K> fromName,
			final Supplier<? extends C> getCache
	) {
		return ITypeAdapterFactory.forClassHierarchy(
				klass,
				typeResolver -> GuavaCacheTypeAdapter.forCache(
						typeResolver.getTypeAdapter(1),
						toName,
						fromName,
						getCache
				)
		);
	}

}
