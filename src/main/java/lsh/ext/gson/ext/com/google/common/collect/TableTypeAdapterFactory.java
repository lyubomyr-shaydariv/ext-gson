package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.base.Converter;
import com.google.common.base.Supplier;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

/**
 * Represents a type adapter factory for {@link Table} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see Adapter
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TableTypeAdapterFactory<R, C, V>
		extends AbstractTypeAdapterFactory<Table<R, C, V>> {

	@Getter
	private static final TypeAdapterFactory instance = new TableTypeAdapterFactory<>(null, null, null);

	@Nullable
	private final Supplier<? extends Table<R, C, V>> newTableFactory;

	@Nullable
	private final Converter<R, String> rowKeyConverter;

	@Nullable
	private final Converter<C, String> columnKeyConverter;

	/**
	 * @param newTableFactory
	 * 		New table factory
	 * @param rowKeyConverter
	 * 		Row key converter
	 * @param columnKeyConverter
	 * 		Column key converter
	 * @param <R>
	 * 		Row type
	 * @param <C>
	 * 		Column type
	 * @param <V>
	 * 		Value type
	 *
	 * @return An instance of {@link TableTypeAdapterFactory} with a custom new {@link Table} factory.
	 */
	@SuppressWarnings("OverlyComplexBooleanExpression")
	public static <R, C, V> TypeAdapterFactory getInstance(@Nullable final Supplier<? extends Table<R, C, V>> newTableFactory,
			@Nullable final Converter<R, String> rowKeyConverter, @Nullable final Converter<C, String> columnKeyConverter) {
		if ( newTableFactory == null && rowKeyConverter == null && columnKeyConverter == null ) {
			return instance;
		}
		if ( rowKeyConverter == null && columnKeyConverter != null
				|| rowKeyConverter != null && columnKeyConverter == null ) {
			throw new NullPointerException("Row and column key converter must both be not null or both be null");
		}
		return new TableTypeAdapterFactory<>(newTableFactory, rowKeyConverter, columnKeyConverter);
	}

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return Table.class.isAssignableFrom(typeToken.getRawType());
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	protected TypeAdapter<Table<R, C, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type valueType = typeArguments[2][0];
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		final boolean lacksKeyConverters = rowKeyConverter == null && columnKeyConverter == null;
		if ( newTableFactory == null && lacksKeyConverters ) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final TypeAdapter<Table<R, C, V>> castTableTypeAdapter = (TypeAdapter) Adapter.getInstance(valueTypeAdapter);
			return castTableTypeAdapter;
		}
		if ( newTableFactory != null && lacksKeyConverters ) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Supplier<? extends Table<String, String, V>> castNewTableFactory = (Supplier) newTableFactory;
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final TypeAdapter<Table<R, C, V>> castTableTypeAdapter = (TypeAdapter) Adapter.getInstance(valueTypeAdapter, castNewTableFactory);
			return castTableTypeAdapter;
		}
		if ( newTableFactory == null && !lacksKeyConverters ) {
			return Adapter.getInstance(valueTypeAdapter, rowKeyConverter, columnKeyConverter);
		}
		return Adapter.getInstance(valueTypeAdapter, newTableFactory, rowKeyConverter, columnKeyConverter);
	}

	/**
	 * Represents a type adapter for {@link Table} from Google Guava.
	 *
	 * @author Lyubomyr Shaydariv
	 * @see TableTypeAdapterFactory
	 */
	public static final class Adapter<R, C, V>
			extends TypeAdapter<Table<R, C, V>> {

		private static final Supplier<? extends Table<?, ?, ?>> defaultNewTableFactory = HashBasedTable::create;
		private static final Converter<?, ?> defaultKeyConverter = Converter.identity();

		private final TypeAdapter<V> valueTypeAdapter;
		private final Supplier<? extends Table<R, C, V>> newTableFactory;
		private final Converter<R, String> rowKeyConverter;
		private final Converter<String, R> reverseRowKeyConverter;
		private final Converter<C, String> forwardColumnKeyConverter;
		private final Converter<String, C> columnKeyConverter;

		private Adapter(final TypeAdapter<V> valueTypeAdapter, final Supplier<? extends Table<R, C, V>> newTableFactory,
				final Converter<R, String> rowKeyConverter, final Converter<C, String> forwardColumnKeyConverter) {
			this.valueTypeAdapter = valueTypeAdapter;
			this.newTableFactory = newTableFactory;
			this.rowKeyConverter = rowKeyConverter;
			reverseRowKeyConverter = rowKeyConverter.reverse();
			this.forwardColumnKeyConverter = forwardColumnKeyConverter;
			columnKeyConverter = forwardColumnKeyConverter.reverse();
		}

		/**
		 * @param valueTypeAdapter
		 * 		Table value type adapter
		 * @param <V>
		 * 		Table value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <V> TypeAdapter<Table<String, String, V>> getInstance(final TypeAdapter<V> valueTypeAdapter) {
			@SuppressWarnings("unchecked")
			final Supplier<? extends Table<String, String, V>> newTableFactory = (Supplier<? extends Table<String, String, V>>) defaultNewTableFactory;
			@SuppressWarnings("unchecked")
			final Converter<String, String> rowKeyConverter = (Converter<String, String>) defaultKeyConverter;
			@SuppressWarnings("unchecked")
			final Converter<String, String> columnKeyConverter = (Converter<String, String>) defaultKeyConverter;
			return getInstance(valueTypeAdapter, newTableFactory, rowKeyConverter, columnKeyConverter);
		}

		/**
		 * @param valueTypeAdapter
		 * 		Table value type adapter
		 * @param newTableFactory
		 * 		A {@link Table} factory to create instance used while deserialization
		 * @param <V>
		 * 		Table value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <V> TypeAdapter<Table<String, String, V>> getInstance(final TypeAdapter<V> valueTypeAdapter,
				final Supplier<? extends Table<String, String, V>> newTableFactory) {
			@SuppressWarnings("unchecked")
			final Converter<String, String> rowKeyConverter = (Converter<String, String>) defaultKeyConverter;
			@SuppressWarnings("unchecked")
			final Converter<String, String> columnKeyConverter = (Converter<String, String>) defaultKeyConverter;
			return getInstance(valueTypeAdapter, newTableFactory, rowKeyConverter, columnKeyConverter);
		}

		/**
		 * @param valueTypeAdapter
		 * 		Table value type adapter
		 * @param rowKeyConverter
		 * 		A converter to convert row key to JSON object property names
		 * @param columnKeyConverter
		 * 		A converter to convert column key to JSON object property names
		 * @param <R>
		 * 		Table row type
		 * @param <C>
		 * 		Table column type
		 * @param <V>
		 * 		Table value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <R, C, V> TypeAdapter<Table<R, C, V>> getInstance(final TypeAdapter<V> valueTypeAdapter, final Converter<R, String> rowKeyConverter,
				final Converter<C, String> columnKeyConverter) {
			@SuppressWarnings("unchecked")
			final Supplier<? extends Table<R, C, V>> newTableFactory = (Supplier<? extends Table<R, C, V>>) defaultNewTableFactory;
			return getInstance(valueTypeAdapter, newTableFactory, rowKeyConverter, columnKeyConverter);
		}

		/**
		 * @param valueTypeAdapter
		 * 		Table value type adapter
		 * @param newTableFactory
		 * 		A {@link Table} factory to create instance used while deserialization
		 * @param rowKeyConverter
		 * 		A converter to convert row key to JSON object property names
		 * @param columnKeyConverter
		 * 		A converter to convert column key to JSON object property names
		 * @param <R>
		 * 		Table row type
		 * @param <C>
		 * 		Table column type
		 * @param <V>
		 * 		Table value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <R, C, V> TypeAdapter<Table<R, C, V>> getInstance(final TypeAdapter<V> valueTypeAdapter,
				final Supplier<? extends Table<R, C, V>> newTableFactory,
				final Converter<R, String> rowKeyConverter, final Converter<C, String> columnKeyConverter) {
			return new Adapter<>(valueTypeAdapter, newTableFactory, rowKeyConverter, columnKeyConverter);
		}

		@Override
		public void write(final JsonWriter out, final Table<R, C, V> table)
				throws IOException {
			out.beginObject();
			final Map<R, Map<C, V>> rowMap = table.rowMap();
			for ( final Map.Entry<R, Map<C, V>> rowEntry : rowMap.entrySet() ) {
				final String rowKey = rowKeyConverter.convert(rowEntry.getKey());
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
					final C columnKey = columnKeyConverter.convert(in.nextName());
					final V value = valueTypeAdapter.read(in);
					table.put(rowKey, columnKey, value);
				}
				in.endObject();
			}
			in.endObject();
			return table;
		}

	}

}
