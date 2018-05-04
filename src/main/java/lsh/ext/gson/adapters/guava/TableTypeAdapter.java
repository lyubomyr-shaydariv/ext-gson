package lsh.ext.gson.adapters.guava;

import java.io.IOException;
import java.util.Map;

import com.google.common.base.Converter;
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
public final class TableTypeAdapter<R, C, V>
		extends TypeAdapter<Table<R, C, V>> {

	private static final Supplier<? extends Table<?, ?, ?>> defaultNewTableFactory = HashBasedTable::create;
	private static final Converter<?, ?> defaultKeyConverter = Converter.identity();

	private final TypeAdapter<V> valueTypeAdapter;
	private final Supplier<? extends Table<R, C, V>> newTableFactory;
	private final Converter<R, String> forwardRowKeyConverter;
	private final Converter<String, R> reverseRowKeyConverter;
	private final Converter<C, String> forwardColumnKeyConverter;
	private final Converter<String, C> reverseColumnKeyConverter;

	private TableTypeAdapter(final TypeAdapter<V> valueTypeAdapter, final Supplier<? extends Table<R, C, V>> newTableFactory,
			final Converter<R, String> forwardRowKeyConverter, final Converter<C, String> forwardColumnKeyConverter) {
		this.valueTypeAdapter = valueTypeAdapter;
		this.newTableFactory = newTableFactory;
		this.forwardRowKeyConverter = forwardRowKeyConverter;
		reverseRowKeyConverter = forwardRowKeyConverter.reverse();
		this.forwardColumnKeyConverter = forwardColumnKeyConverter;
		reverseColumnKeyConverter = forwardColumnKeyConverter.reverse();
	}

	/**
	 * @param valueTypeAdapter Table value type adapter
	 * @param <V>              Table value type parameter
	 *
	 * @return A {@link TableTypeAdapter} instance.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapter<Table<String, String, V>> get(final TypeAdapter<V> valueTypeAdapter) {
		@SuppressWarnings("unchecked")
		final Supplier<? extends Table<String, String, V>> newTableFactory = (Supplier<? extends Table<String, String, V>>) defaultNewTableFactory;
		@SuppressWarnings("unchecked")
		final Converter<String, String> rowKeyConverter = (Converter<String, String>) defaultKeyConverter;
		@SuppressWarnings("unchecked")
		final Converter<String, String> columnKeyConverter = (Converter<String, String>) defaultKeyConverter;
		return get(valueTypeAdapter, newTableFactory, rowKeyConverter, columnKeyConverter);
	}

	/**
	 * @param valueTypeAdapter Table value type adapter
	 * @param newTableFactory  A {@link Table} factory to create instance used while deserialization
	 * @param <V>              Table value type parameter
	 *
	 * @return A {@link TableTypeAdapter} instance.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapter<Table<String, String, V>> get(final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends Table<String, String, V>> newTableFactory) {
		@SuppressWarnings("unchecked")
		final Converter<String, String> rowKeyConverter = (Converter<String, String>) defaultKeyConverter;
		@SuppressWarnings("unchecked")
		final Converter<String, String> columnKeyConverter = (Converter<String, String>) defaultKeyConverter;
		return get(valueTypeAdapter, newTableFactory, rowKeyConverter, columnKeyConverter);
	}

	/**
	 * @param valueTypeAdapter   Table value type adapter
	 * @param rowKeyConverter    A converter to convert row key to JSON object property names
	 * @param columnKeyConverter A converter to convert column key to JSON object property names
	 * @param <V>                Table value type parameter
	 *
	 * @return A {@link TableTypeAdapter} instance.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <R, C, V> TypeAdapter<Table<R, C, V>> get(final TypeAdapter<V> valueTypeAdapter, final Converter<R, String> rowKeyConverter,
			final Converter<C, String> columnKeyConverter) {
		@SuppressWarnings("unchecked")
		final Supplier<? extends Table<R, C, V>> newTableFactory = (Supplier<? extends Table<R, C, V>>) defaultNewTableFactory;
		return get(valueTypeAdapter, newTableFactory, rowKeyConverter, columnKeyConverter);
	}

	/**
	 * @param valueTypeAdapter   Table value type adapter
	 * @param newTableFactory    A {@link Table} factory to create instance used while deserialization
	 * @param rowKeyConverter    A converter to convert row key to JSON object property names
	 * @param columnKeyConverter A converter to convert column key to JSON object property names
	 * @param <V>                Table value type parameter
	 *
	 * @return A {@link TableTypeAdapter} instance.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <R, C, V> TypeAdapter<Table<R, C, V>> get(final TypeAdapter<V> valueTypeAdapter, final Supplier<? extends Table<R, C, V>> newTableFactory,
			final Converter<R, String> rowKeyConverter, final Converter<C, String> columnKeyConverter) {
		return new TableTypeAdapter<>(valueTypeAdapter, newTableFactory, rowKeyConverter, columnKeyConverter)
				.nullSafe();
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final Table<R, C, V> table)
			throws IOException {
		out.beginObject();
		final Map<R, Map<C, V>> rowMap = table.rowMap();
		for ( final Map.Entry<R, Map<C, V>> rowEntry : rowMap.entrySet() ) {
			final String rowKey = forwardRowKeyConverter.convert(rowEntry.getKey());
			out.name(rowKey);
			out.beginObject();
			final Map<C, V> columnMap = rowEntry.getValue();
			for ( final Map.Entry<C, V> columnEntry : columnMap.entrySet() ) {
				final String columnKey = forwardColumnKeyConverter.convert(columnEntry.getKey());
				final V value = columnEntry.getValue();
				out.name(columnKey);
				valueTypeAdapter.write(out, value);
			}
			out.endObject();
		}
		out.endObject();
	}

	@Override
	public Table<R, C, V> read(final JsonReader in)
			throws IOException {
		final Table<R, C, V> table = newTableFactory.get();
		in.beginObject();
		while ( in.hasNext() ) {
			final R rowKey = reverseRowKeyConverter.convert(in.nextName());
			in.beginObject();
			while ( in.hasNext() ) {
				final C columnKey = reverseColumnKeyConverter.convert(in.nextName());
				final V value = valueTypeAdapter.read(in);
				table.put(rowKey, columnKey, value);
			}
			in.endObject();
		}
		in.endObject();
		return table;
	}

}
