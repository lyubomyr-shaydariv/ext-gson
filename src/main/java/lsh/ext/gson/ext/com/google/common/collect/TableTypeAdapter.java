package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.Nullable;

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
import lsh.ext.gson.AbstractHierarchyTypeAdapterFactory;
import lsh.ext.gson.IBuilder3;
import lsh.ext.gson.IFactory;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("Guava")
public final class TableTypeAdapter<V>
		extends TypeAdapter<Table<String, String, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final IFactory<? extends IBuilder3<? super String, ? super String, ? super V, ? extends Table<String, String, V>>> builderFactory;

	public static <V> TypeAdapter<Table<String, String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final IFactory<? extends IBuilder3<? super String, ? super String, ? super V, ? extends Table<String, String, V>>> builderFactory
	) {
		return new TableTypeAdapter<>(valueTypeAdapter, builderFactory)
				.nullSafe();
	}

	@Override
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
		in.beginObject();
		final IBuilder3<? super String, ? super String, ? super V, ? extends Table<String, String, V>> builder = builderFactory.create();
		while ( in.hasNext() ) {
			final String rowKey = in.nextName();
			in.beginObject();
			while ( in.hasNext() ) {
				final String columnKey = in.nextName();
				final V value = valueTypeAdapter.read(in);
				builder.accept(rowKey, columnKey, value);
			}
			in.endObject();
		}
		in.endObject();
		return builder.build();
	}

	public static final class Factory<V>
			extends AbstractHierarchyTypeAdapterFactory<Table<String, String, V>> {

		private static final ITypeAdapterFactory<?> instance = new Factory<>(Factory::defaultBuilderFactory);

		private final IBuilder3.ILookup<? super String, ? super String, ? super V, ? extends Table<String, String, V>> builderLookup;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private Factory(final IBuilder3.ILookup<? super String, ? super String, ? super V, ? extends Table<String, String, V>> builderLookup) {
			super((Class) Table.class);
			this.builderLookup = builderLookup;
		}

		public static <V> ITypeAdapterFactory<Table<String, String, V>> getInstance() {
			@SuppressWarnings("unchecked")
			final ITypeAdapterFactory<Table<String, String, V>> castInstance = (ITypeAdapterFactory<Table<String, String, V>>) instance;
			return castInstance;
		}

		public static <V> ITypeAdapterFactory<Table<String, String, V>> getInstance(
				final IBuilder3.ILookup<? super String, ? super String, ? super V, ? extends Table<String, String, V>> builderLookup
		) {
			return new Factory<>(builderLookup);
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
		protected TypeAdapter<Table<String, String, V>> createTypeAdapter(final Gson gson, final TypeToken<? super Table<String, String, V>> typeToken) {
			@Nullable
			final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 2);
			assert valueType != null;
			@SuppressWarnings("unchecked")
			final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
			@SuppressWarnings("unchecked")
			final IBuilder3.ILookup<String, String, V, Table<String, String, V>> castBuilderLookup = (IBuilder3.ILookup<String, String, V, Table<String, String, V>>) builderLookup;
			return TableTypeAdapter.getInstance(valueTypeAdapter, castBuilderLookup.lookup(typeToken));
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
