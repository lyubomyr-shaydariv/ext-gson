package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractRawClassHierarchyTypeAdapterFactory;
import lsh.ext.gson.IBuilder3;
import lsh.ext.gson.IFactory;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("Guava")
public final class TableTypeAdapter<R, C, V>
		extends TypeAdapter<Table<R, C, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final IFactory<? extends IBuilder3<? super R, ? super C, ? super V, ? extends Table<R, C, V>>> builderFactory;
	private final Function<? super R, String> encodeRowKey;
	private final Function<? super String, ? extends R> decodeRowKey;
	private final Function<? super C, String> encodeColumnKey;
	private final Function<? super String, ? extends C> decodeColumnKey;

	public static <V> TypeAdapter<Table<String, String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final IFactory<? extends IBuilder3<? super String, ? super String, ? super V, ? extends Table<String, String, V>>> builderFactory
	) {
		return new TableTypeAdapter<String, String, V>(valueTypeAdapter, builderFactory, Functions.identity(), Functions.identity(), Functions.identity(), Functions.identity())
				.nullSafe();
	}

	public static <R, C, V> TypeAdapter<Table<R, C, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final IFactory<? extends IBuilder3<? super R, ? super C, ? super V, ? extends Table<R, C, V>>> builderFactory,
			final Function<? super R, String> encodeRowKey,
			final Function<? super String, ? extends R> decodeRowKey,
			final Function<? super C, String> encodeColumnKey,
			final Function<? super String, ? extends C> decodeColumnKey
	) {
		return new TableTypeAdapter<R, C, V>(valueTypeAdapter, builderFactory, encodeRowKey, decodeRowKey, encodeColumnKey, decodeColumnKey)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final Table<R, C, V> table)
			throws IOException {
		out.beginObject();
		final Map<R, Map<C, V>> rowMap = table.rowMap();
		for ( final Map.Entry<R, Map<C, V>> rowEntry : rowMap.entrySet() ) {
			final R rowKey = rowEntry.getKey();
			out.name(encodeRowKey.apply(rowKey));
			out.beginObject();
			final Map<C, V> columnMap = rowEntry.getValue();
			for ( final Map.Entry<C, V> columnEntry : columnMap.entrySet() ) {
				final C columnKey = columnEntry.getKey();
				final V value = columnEntry.getValue();
				out.name(encodeColumnKey.apply(columnKey));
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
		final IBuilder3<? super R, ? super C, ? super V, ? extends Table<R, C, V>> builder = builderFactory.create();
		while ( in.hasNext() ) {
			final R rowKey = decodeRowKey.apply(in.nextName());
			in.beginObject();
			while ( in.hasNext() ) {
				final C columnKey = decodeColumnKey.apply(in.nextName());
				final V value = valueTypeAdapter.read(in);
				builder.accept(rowKey, columnKey, value);
			}
			in.endObject();
		}
		in.endObject();
		return builder.build();
	}

	public static final class Factory<R, C, V>
			extends AbstractRawClassHierarchyTypeAdapterFactory<Table<R, C, V>> {

		private static final ITypeAdapterFactory<?> instance = getInstance(Factory::defaultBuilderFactory, Functions.identity(), Functions.identity(), Functions.identity(), Functions.identity());

		private final IBuilder3.ILookup<? super R, ? super C, ? super V, ? extends Table<R, C, V>> builderLookup;
		private final Function<? super R, String> encodeRowKey;
		private final Function<? super String, ? extends R> decodeRowKey;
		private final Function<? super C, String> encodeColumnKey;
		private final Function<? super String, ? extends C> decodeColumnKey;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private Factory(
				final IBuilder3.ILookup<? super R, ? super C, ? super V, ? extends Table<R, C, V>> builderLookup,
				final Function<? super R, String> encodeRowKey,
				final Function<? super String, ? extends R> decodeRowKey,
				final Function<? super C, String> encodeColumnKey,
				final Function<? super String, ? extends C> decodeColumnKey
		) {
			super((Class) Table.class);
			this.builderLookup = builderLookup;
			this.encodeRowKey = encodeRowKey;
			this.decodeRowKey = decodeRowKey;
			this.encodeColumnKey = encodeColumnKey;
			this.decodeColumnKey = decodeColumnKey;
		}

		public static <R, C, V> ITypeAdapterFactory<Table<R, C, V>> getInstance() {
			@SuppressWarnings("unchecked")
			final ITypeAdapterFactory<Table<R, C, V>> castInstance = (ITypeAdapterFactory<Table<R, C, V>>) instance;
			return castInstance;
		}

		public static <R, C, V> ITypeAdapterFactory<Table<R, C, V>> getInstance(
				final IBuilder3.ILookup<? super R, ? super C, ? super V, ? extends Table<R, C, V>> builderLookup,
				final Function<? super R, String> encodeRowKey,
				final Function<? super String, ? extends R> decodeRowKey,
				final Function<? super C, String> encodeColumnKey,
				final Function<? super String, ? extends C> decodeColumnKey
		) {
			return new Factory<>(builderLookup, encodeRowKey, decodeRowKey, encodeColumnKey, decodeColumnKey);
		}

		// TODO handle all known implementations
		public static <V> IFactory<IBuilder3<String, String, V, Table<String, String, V>>> defaultBuilderFactory(final TypeToken<? super Table<String, String, V>> typeToken) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Class<? extends Table> rawType = (Class<? extends Table>) typeToken.getRawType();
			// TODO why is HashBasedTable not final?
			if ( HashBasedTable.class.isAssignableFrom(rawType) ) {
				return () -> builder(HashBasedTable.create());
			}
			if ( ImmutableTable.class.isAssignableFrom(rawType) ) {
				return Factory::immutableBuilder;
			}
			return () -> builder(HashBasedTable.create());
		}

		@Override
		@Nullable
		protected TypeAdapter<Table<R, C, V>> createTypeAdapter(final Gson gson, final TypeToken<? super Table<R, C, V>> typeToken) {
			@Nullable
			final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 2);
			assert valueType != null;
			@SuppressWarnings("unchecked")
			final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
			@SuppressWarnings("unchecked")
			final IBuilder3.ILookup<R, C, V, Table<R, C, V>> castBuilderLookup = (IBuilder3.ILookup<R, C, V, Table<R, C, V>>) builderLookup;
			return TableTypeAdapter.getInstance(valueTypeAdapter, castBuilderLookup.lookup(typeToken), encodeRowKey, decodeRowKey, encodeColumnKey, decodeColumnKey);
		}

		private static <V, T extends Table<String, String, V>> IBuilder3<String, String, V, T> builder(final T table) {
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

		private static <V> IBuilder3<String, String, V, Table<String, String, V>> immutableBuilder() {
			return new IBuilder3<>() {
				private final ImmutableTable.Builder<String, String, V> builder = ImmutableTable.builder();

				@Override
				public void accept(final String rowKey, final String columnKey, final V v) {
					builder.put(rowKey, columnKey, v);
				}

				@Override
				public Table<String, String, V> build() {
					return builder.build();
				}
			};
		}

	}

}
