package lsh.ext.gson.adapters.guava;

import java.io.IOException;
import java.util.Map;

import com.google.common.base.Supplier;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Represents a type adapter for {@link Table} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see TableTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
public final class TableTypeAdapter<V>
		extends TypeAdapter<Table<String, String, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final Supplier<? extends Table<String, String, V>> newTableFactory;

	private TableTypeAdapter(final TypeAdapter<V> valueTypeAdapter, final Supplier<? extends Table<String, String, V>> newTableFactory) {
		this.valueTypeAdapter = valueTypeAdapter;
		this.newTableFactory = newTableFactory;
	}

	/**
	 * @param valueTypeAdapter Table value type adapter
	 * @param <V>              Table value type parameter
	 *
	 * @return A {@link TableTypeAdapter} instance whose multimap factory is {@link HashBasedTable#create()}.
	 *
	 * @see #get(TypeAdapter, Supplier)
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapter<Table<String, String, V>> get(final TypeAdapter<V> valueTypeAdapter) {
		return get(valueTypeAdapter, (Supplier<? extends Table<String, String, V>>) HashBasedTable::create);
	}

	/**
	 * @param valueTypeAdapter Table value type adapter
	 * @param newTableFactory  A {@link Table} factory to create instance used while deserialization
	 * @param <V>              Table value type parameter
	 *
	 * @return A {@link TableTypeAdapter} instance.
	 *
	 * @see #get(TypeAdapter)
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapter<Table<String, String, V>> get(final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends Table<String, String, V>> newTableFactory) {
		return new TableTypeAdapter<>(valueTypeAdapter, newTableFactory)
				.nullSafe();
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final Table<String, String, V> table)
			throws IOException {
		out.beginObject();
		final Map<String, Map<String, V>> rowMap = table.rowMap();
		for ( final Map.Entry<String, Map<String, V>> rowEntry : rowMap.entrySet() ) {
			final String rowKey = rowEntry.getKey();
			out.name(rowKey);
			out.beginObject();
			final Map<String, V> columnMap = rowEntry.getValue();
			for ( final Map.Entry<String, V> columnEntry : columnMap.entrySet() ) {
				final String columnKey = columnEntry.getKey();
				final V value = columnEntry.getValue();
				out.name(columnKey);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}
		out.endObject();
	}

	@Override
	public Table<String, String, V> read(final JsonReader in)
			throws IOException {
		final Table<String, String, V> table = newTableFactory.get();
		in.beginObject();
		while ( in.hasNext() ) {
			final String rowKey = in.nextName();
			in.beginObject();
			while ( in.hasNext() ) {
				final String columnKey = in.nextName();
				final V value = valueTypeAdapter.read(in);
				table.put(rowKey, columnKey, value);
			}
			in.endObject();
		}
		in.endObject();
		return table;
	}

}
