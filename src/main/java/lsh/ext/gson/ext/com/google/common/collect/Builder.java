package lsh.ext.gson.ext.com.google.common.collect;

import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.IBuilder3;

@UtilityClass
public final class Builder {

	public static <K, V, M extends Multimap<K, V>> IBuilder2<K, V, M> forMultimap(final M multimap) {
		return new IBuilder2<>() {
			@Override
			public void accept(final K k, final V v) {
				multimap.put(k, v);
			}

			@Override
			public M build() {
				return multimap;
			}
		};
	}

	public static <V, T extends Table<String, String, V>> IBuilder3<String, String, V, T> forTable(final T table) {
		return new IBuilder3<>() {
			@Override
			public void accept(final String rowKey, final String columnKey, final V v) {
				table.put(rowKey, columnKey, v);
			}

			@Override
			public T build() {
				return table;
			}
		};
	}

}
