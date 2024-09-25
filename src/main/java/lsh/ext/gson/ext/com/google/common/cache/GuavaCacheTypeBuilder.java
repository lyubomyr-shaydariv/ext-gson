package lsh.ext.gson.ext.com.google.common.cache;

import com.google.common.cache.Cache;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.IBuilder2;

@UtilityClass
public final class GuavaCacheTypeBuilder {

	public static <K, V, C extends Cache<K, V>> IBuilder2<K, V, C> forCache(
			final C cache
	) {
		return new IBuilder2<>() {
			@Override
			public void accept(final K k, final V v) {
				cache.put(k, v);
			}

			@Override
			public C build() {
				return cache;
			}
		};
	}

}
