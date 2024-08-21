package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IBuilder3;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("Guava")
public final class TableTypeAdapter<V>
		extends TypeAdapter<Table<String, String, V>> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final Supplier<? extends IBuilder3<? super String, ? super String, ? super V, ? extends Table<String, String, V>>> tableBuilderFactory;

	public static <V> TypeAdapter<Table<String, String, V>> getInstance(
			final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends IBuilder3<? super String, ? super String, ? super V, ? extends Table<String, String, V>>> tableBuilderFactory
	) {
		return new TableTypeAdapter<>(valueTypeAdapter, tableBuilderFactory)
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
		final IBuilder3<? super String, ? super String, ? super V, ? extends Table<String, String, V>> builder = tableBuilderFactory.get();
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

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<V>
			extends AbstractTypeAdapterFactory<Table<String, String, V>> {

		private final IBuilder3.IFactory<? super String, ? super String, ? super V, ? extends Table<String, String, V>> builderFactory;

		public static <V> ITypeAdapterFactory<Table<String, String, V>> getInstance(
				final IBuilder3.IFactory<? super String, ? super String, ? super V, ? extends Table<String, String, V>> builderFactory
		) {
			return new Factory<>(builderFactory);
		}

		public static <V> ITypeAdapterFactory<Table<String, String, V>> getDefaultBuilderInstance(
				final IBuilder0.IFactory<? extends Table<String, String, V>> builderFactory
		) {
			return getInstance(typeToken -> defaultBuilder(typeToken, builderFactory));
		}

		public static <V> IBuilder3<String, String, V, Table<String, String, V>> defaultBuilder(
				final TypeToken<? super Table<String, String, V>> typeToken,
				final IBuilder0.IFactory<? extends Table<String, String, V>> builderFactory
		) {
			@SuppressWarnings("unchecked")
			final Class<? extends Table<?, ?, ?>> rawType = (Class<? extends Table<?, ?, ?>>) typeToken.getRawType();
			if ( ImmutableTable.class.isAssignableFrom(rawType) ) {
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
			@SuppressWarnings("LawOfDemeter")
			final Table<String, String, V> table = builderFactory.create(typeToken)
					.build();
			return new IBuilder3<>() {
				@Override
				public void accept(final String rowKey, final String columnKey, final V v) {
					table.put(rowKey, columnKey, v);
				}

				@Override
				public Table<String, String, V> build() {
					return table;
				}
			};
		}

		@Override
		@Nullable
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
			final IBuilder3.IFactory<String, String, V, Table<String, String, V>> castTableBuilderFactory = (IBuilder3.IFactory<String, String, V, Table<String, String, V>>) builderFactory;
			return TableTypeAdapter.getInstance(valueTypeAdapter, () -> castTableBuilderFactory.create(castTypeToken));
		}

	}

}
