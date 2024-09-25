package lsh.ext.gson.ext.com.google.common.cache;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.cache.Cache;
import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.Container2TypeAdapter;

@UtilityClass
public final class GuavaCacheTypeAdapter {

	public static <V, C extends Cache<String, V>> TypeAdapter<C> forCache(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends C> getCache
	) {
		return forCache(valueTypeAdapter, Function.identity(), Function.identity(), getCache);
	}

	@SuppressWarnings("RedundantTypeArguments")
	public static <K, V, C extends Cache<K, V>> TypeAdapter<C> forCache(
			final TypeAdapter<V> valueTypeAdapter,
			final Function<? super K, String> toName,
			final Function<? super String, ? extends K> fromName,
			final Supplier<? extends C> getCache
	) {
		return Container2TypeAdapter.<K, V, C, Map.Entry<K, V>>getInstance(
				valueTypeAdapter,
				cache -> cache.asMap()
						.entrySet()
						.iterator(),
				Map.Entry::getKey,
				Map.Entry::getValue,
				toName,
				fromName,
				() -> GuavaCacheTypeBuilder.forCache(getCache.get())
		);
	}

}
