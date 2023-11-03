package lsh.ext.gson;

import java.util.Map;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MapBuilder<K, V, M extends Map<K, V>>
		implements IBuilder2<K, V, M> {

	private final M map;

	public static <K, V, M extends Map<K, V>> IBuilder2<K, V, M> getInstance(final M map) {
		return new MapBuilder<>(map);
	}

	@Override
	public void modify(final K k, final V v) {
		map.put(k, v);
	}

	@Override
	public M build() {
		return map;
	}

}
