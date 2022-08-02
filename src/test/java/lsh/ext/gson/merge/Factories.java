package lsh.ext.gson.merge;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.experimental.UtilityClass;

@UtilityClass
final class Factories {

	static <K, V> Map<K, V> linkedHashMap(
			final K k1, final V v1
	) {
		final Map<K, V> map = new LinkedHashMap<>();
		map.put(k1, v1);
		return map;
	}

	static <K, V> Map<K, V> linkedHashMap(
			final K k1, final V v1,
			final K k2, final V v2
	) {
		final Map<K, V> map = linkedHashMap(k1, v1);
		map.put(k2, v2);
		return map;
	}

	static <K, V> Map<K, V> linkedHashMap(
			final K k1, final V v1,
			final K k2, final V v2,
			final K k3, final V v3
	) {
		final Map<K, V> map = linkedHashMap(k1, v1, k2, v2);
		map.put(k3, v3);
		return map;
	}

	static <K, V> Map<K, V> linkedHashMap(
			final K k1, final V v1,
			final K k2, final V v2,
			final K k3, final V v3,
			final K k4, final V v4
	) {
		final Map<K, V> map = linkedHashMap(k1, v1, k2, v2, k3, v3);
		map.put(k4, v4);
		return map;
	}

	static <K, V> Map<K, V> linkedHashMap(
			final K k1, final V v1,
			final K k2, final V v2,
			final K k3, final V v3,
			final K k4, final V v4,
			final K k5, final V v5
	) {
		final Map<K, V> map = linkedHashMap(k1, v1, k2, v2, k3, v3, k4, v4);
		map.put(k5, v5);
		return map;
	}

}
