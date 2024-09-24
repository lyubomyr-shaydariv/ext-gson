package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.Table;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.IBuilder3;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class TableTypeAdapter<R, C, V>
		extends TypeAdapter<Table<R, C, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final Supplier<? extends IBuilder3<? super R, ? super C, ? super V, ? extends Table<R, C, V>>> builderFactory;
	private final Function<? super R, String> toRowName;
	private final Function<? super String, ? extends R> fromRowName;
	private final Function<? super C, String> toColumnName;
	private final Function<? super String, ? extends C> fromColumnName;

	static <V> TypeAdapter<Table<String, String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder3<? super String, ? super String, ? super V, ? extends Table<String, String, V>>> builderFactory
	) {
		return getInstance(valueTypeAdapter, builderFactory, Function.identity(), Function.identity(), Function.identity(), Function.identity());
	}

	static <R, C, V> TypeAdapter<Table<R, C, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder3<? super R, ? super C, ? super V, ? extends Table<R, C, V>>> builderFactory,
			final Function<? super R, String> toRowName,
			final Function<? super String, ? extends R> fromRowName,
			final Function<? super C, String> toColumnName,
			final Function<? super String, ? extends C> fromColumnName
	) {
		return new TableTypeAdapter<R, C, V>(valueTypeAdapter, builderFactory, toRowName, fromRowName, toColumnName, fromColumnName)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final Table<R, C, V> table)
			throws IOException {
		out.beginObject();
		final Map<R, Map<C, V>> rowMap = table.rowMap();
		for ( final Map.Entry<R, Map<C, V>> rowEntry : rowMap.entrySet() ) {
			final R rowKey = rowEntry.getKey();
			out.name(toRowName.apply(rowKey));
			out.beginObject();
			final Map<C, V> columnMap = rowEntry.getValue();
			for ( final Map.Entry<C, V> columnEntry : columnMap.entrySet() ) {
				final C columnKey = columnEntry.getKey();
				final V value = columnEntry.getValue();
				out.name(toColumnName.apply(columnKey));
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}
		out.endObject();
	}

	@Override
	public Table<R, C, V> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder3<? super R, ? super C, ? super V, ? extends Table<R, C, V>> builder = builderFactory.get();
		while ( in.hasNext() ) {
			final R rowKey = fromRowName.apply(in.nextName());
			in.beginObject();
			while ( in.hasNext() ) {
				final C columnKey = fromColumnName.apply(in.nextName());
				final V value = valueTypeAdapter.read(in);
				builder.accept(rowKey, columnKey, value);
			}
			in.endObject();
		}
		in.endObject();
		return builder.build();
	}

}
