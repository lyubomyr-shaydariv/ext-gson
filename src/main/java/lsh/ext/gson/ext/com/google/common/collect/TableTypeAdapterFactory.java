package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.Nullable;

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

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TableTypeAdapterFactory<V>
		extends AbstractTypeAdapterFactory<Table<String, String, V>>
		implements ITypeAdapterFactory<Table<String, String, V>> {

	private final IInstanceFactory.IProvider<? extends Table<String, String, V>> newTableFactoryProvider;

	public static <V> ITypeAdapterFactory<Table<String, String, V>> getInstance(
			final IInstanceFactory.IProvider<? extends Table<String, String, V>> newTableFactoryProvider
	) {
		return new TableTypeAdapterFactory<>(newTableFactoryProvider);
	}

	@Override
	protected TypeAdapter<Table<String, String, V>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !Table.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@Nullable
		final Type valueType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 2);
		assert valueType != null;
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		@SuppressWarnings("unchecked")
		final TypeToken<Table<String, String, V>> castTypeToken = (TypeToken<Table<String, String, V>>) typeToken;
		@SuppressWarnings("unchecked")
		final IInstanceFactory.IProvider<Table<String, String, V>> castNewTableFactoryProvider = (IInstanceFactory.IProvider<Table<String, String, V>>) newTableFactoryProvider;
		return Adapter.getInstance(valueTypeAdapter, castNewTableFactoryProvider.provide(castTypeToken));
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<V>
			extends TypeAdapter<Table<String, String, V>> {

		private final TypeAdapter<V> valueTypeAdapter;
		private final IInstanceFactory<? extends Table<String, String, V>> newTableFactory;

		public static <V> TypeAdapter<Table<String, String, V>> getInstance(
				final TypeAdapter<V> valueTypeAdapter,
				final IInstanceFactory<? extends Table<String, String, V>> newTableFactory
		) {
			return new Adapter<>(valueTypeAdapter, newTableFactory)
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
			final Table<String, String, V> table = newTableFactory.createInstance();
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

}
