package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.IInstanceFactory;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

/**
 * Represents a type adapter factory for {@link Table} from Google Guava.
 *
 * @param <R>
 * 		Row type
 * @param <C>
 * 		Column type
 * @param <V>
 * 		Value type
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TableTypeAdapterFactory<R, C, V>
		extends AbstractTypeAdapterFactory<Table<R, C, V>>
		implements ITypeAdapterFactory<Table<R, C, V>> {

	private final IInstanceFactory.IProvider<? extends Table<R, C, V>> newTableFactoryProvider;
	@SuppressWarnings("Guava")
	private final Function<? super R, String> rowKeyMapper;
	@SuppressWarnings("Guava")
	private final Function<? super String, ? extends R> rowKeyReverseMapper;
	@SuppressWarnings("Guava")
	private final Function<? super C, String> columnKeyMapper;
	@SuppressWarnings("Guava")
	private final Function<? super String, ? extends C> columnKeyReverseMapper;

	/**
	 * @param newTableFactoryProvider
	 * 		New table factory factory
	 * @param rowKeyMapper
	 * 		Row key mapper
	 * @param rowKeyReverseMapper
	 * 		Row key reverse mapper
	 * @param columnKeyMapper
	 * 		Column key mapper
	 * @param columnKeyReverseMapper
	 * 		Column key reverse mapper
	 * @param <R>
	 * 		Row type
	 * @param <C>
	 * 		Column type
	 * @param <V>
	 * 		Value type
	 *
	 * @return An instance of {@link TableTypeAdapterFactory} with a custom new {@link Table} factory.
	 */
	public static <R, C, V> ITypeAdapterFactory<Table<R, C, V>> getInstance(
			final IInstanceFactory.IProvider<? extends Table<R, C, V>> newTableFactoryProvider,
			@SuppressWarnings("Guava") final Function<? super R, String> rowKeyMapper,
			@SuppressWarnings("Guava") final Function<? super String, ? extends R> rowKeyReverseMapper,
			@SuppressWarnings("Guava") final Function<? super C, String> columnKeyMapper,
			@SuppressWarnings("Guava") final Function<? super String, ? extends C> columnKeyReverseMapper
	) {
		return new TableTypeAdapterFactory<>(newTableFactoryProvider, rowKeyMapper, rowKeyReverseMapper, columnKeyMapper, columnKeyReverseMapper);
	}

	@Override
	protected TypeAdapter<Table<R, C, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !Table.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@Nullable
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 2);
		assert valueType != null;
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		@SuppressWarnings("unchecked")
		final TypeToken<Table<R, C, V>> castTypeToken = (TypeToken<Table<R, C, V>>) typeToken;
		@SuppressWarnings("unchecked")
		final IInstanceFactory.IProvider<Table<R, C, V>> castNewTableFactoryProvider = (IInstanceFactory.IProvider<Table<R, C, V>>) newTableFactoryProvider;
		return Adapter.getInstance(valueTypeAdapter, castNewTableFactoryProvider.provide(castTypeToken), rowKeyMapper, rowKeyReverseMapper, columnKeyMapper, columnKeyReverseMapper);
	}

	/**
	 * Represents a type adapter for {@link Table} from Google Guava.
	 *
	 * @param <R>
	 * 		Row type
	 * @param <C>
	 * 		Column type
	 * @param <V>
	 * 		Value type
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<R, C, V>
			extends TypeAdapter<Table<R, C, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		private final IInstanceFactory<? extends Table<R, C, V>> newTableFactory;
		@SuppressWarnings("Guava")
		private final Function<? super R, String> rowKeyMapper;
		@SuppressWarnings("Guava")
		private final Function<? super String, ? extends R> rowKeyReverseMapper;
		@SuppressWarnings("Guava")
		private final Function<? super C, String> columnKeyMapper;
		@SuppressWarnings("Guava")
		private final Function<? super String, ? extends C> columnKeyReverseMapper;

		/**
		 * @param valueTypeAdapter
		 * 		Table value type adapter
		 * @param newTableFactory
		 * 		A {@link Table} factory to create instance used while deserialization
		 * @param rowKeyMapper
		 * 		A mapper to map row key to JSON object property names
		 * @param rowKeyReverseMapper
		 * 		A mapper to map row key to JSON object property names in reverse
		 * @param columnKeyMapper
		 * 		A mapper to map column key to JSON object property names
		 * @param columnKeyReverseMapper
		 * 		A mapper to map column key to JSON object property names in reverse
		 * @param <R>
		 * 		Table row type
		 * @param <C>
		 * 		Table column type
		 * @param <V>
		 * 		Table value type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <R, C, V> TypeAdapter<Table<R, C, V>> getInstance(
				final TypeAdapter<V> valueTypeAdapter,
				final IInstanceFactory<? extends Table<R, C, V>> newTableFactory,
				@SuppressWarnings("Guava") final Function<? super R, String> rowKeyMapper,
				@SuppressWarnings("Guava") final Function<? super String, ? extends R> rowKeyReverseMapper,
				@SuppressWarnings("Guava") final Function<? super C, String> columnKeyMapper,
				@SuppressWarnings("Guava") final Function<? super String, ? extends C> columnKeyReverseMapper
		) {
			return new Adapter<>(valueTypeAdapter, newTableFactory, rowKeyMapper, rowKeyReverseMapper, columnKeyMapper, columnKeyReverseMapper)
					.nullSafe();
		}

		@Override
		public void write(final JsonWriter out, final Table<R, C, V> table)
				throws IOException {
			out.beginObject();
			final Map<R, Map<C, V>> rowMap = table.rowMap();
			for ( final Map.Entry<R, Map<C, V>> rowEntry : rowMap.entrySet() ) {
				final String rowKey = rowKeyMapper.apply(rowEntry.getKey());
				out.name(rowKey);
				out.beginObject();
				final Map<C, V> columnMap = rowEntry.getValue();
				for ( final Map.Entry<C, V> columnEntry : columnMap.entrySet() ) {
					final String columnKey = columnKeyMapper.apply(columnEntry.getKey());
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
			final Table<R, C, V> table = newTableFactory.createInstance();
			in.beginObject();
			while ( in.hasNext() ) {
				final R rowKey = rowKeyReverseMapper.apply(in.nextName());
				in.beginObject();
				while ( in.hasNext() ) {
					final C columnKey = columnKeyReverseMapper.apply(in.nextName());
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
