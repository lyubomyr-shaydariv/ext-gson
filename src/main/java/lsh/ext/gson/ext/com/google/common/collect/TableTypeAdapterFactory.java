package lsh.ext.gson.ext.com.google.common.collect;

import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.common.base.Converter;
import com.google.common.base.Supplier;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.ParameterizedTypes;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Table} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see TableTypeAdapter
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TableTypeAdapterFactory<R, C, V>
		extends AbstractTypeAdapterFactory<Table<R, C, V>> {

	private static final TypeAdapterFactory instance = new TableTypeAdapterFactory<>(null, null, null);

	@Nullable
	private final Supplier<? extends Table<R, C, V>> newTableFactory;

	@Nullable
	private final Converter<R, String> rowKeyConverter;

	@Nullable
	private final Converter<C, String> columnKeyConverter;

	/**
	 * @return An instance of {@link TableTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

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
	protected boolean isSupported(final TypeToken<?> typeToken) {
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
			final TypeAdapter<Table<R, C, V>> castTableTypeAdapter = (TypeAdapter) TableTypeAdapter.getInstance(valueTypeAdapter);
			return castTableTypeAdapter;
		}
		if ( newTableFactory != null && lacksKeyConverters ) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Supplier<? extends Table<String, String, V>> castNewTableFactory = (Supplier) newTableFactory;
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final TypeAdapter<Table<R, C, V>> castTableTypeAdapter = (TypeAdapter) TableTypeAdapter.getInstance(valueTypeAdapter, castNewTableFactory);
			return castTableTypeAdapter;
		}
		if ( newTableFactory == null && !lacksKeyConverters ) {
			return TableTypeAdapter.getInstance(valueTypeAdapter, rowKeyConverter, columnKeyConverter);
		}
		return TableTypeAdapter.getInstance(valueTypeAdapter, newTableFactory, rowKeyConverter, columnKeyConverter);
	}

}
