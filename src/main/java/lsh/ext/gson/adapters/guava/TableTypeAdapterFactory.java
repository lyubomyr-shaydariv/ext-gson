package lsh.ext.gson.adapters.guava;

import java.lang.reflect.Type;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Supplier;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.ParameterizedTypes;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Table} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see TableTypeAdapter
 * @since 0-SNAPSHOT
 */
public final class TableTypeAdapterFactory<V>
		extends AbstractTypeAdapterFactory<Table<String, String, V>> {

	private static final TypeAdapterFactory instance = new TableTypeAdapterFactory<>(null);

	@Nullable
	private final Supplier<? extends Table<String, String, V>> newTableFactory;

	private TableTypeAdapterFactory(@Nullable final Supplier<? extends Table<String, String, V>> newTableFactory) {
		this.newTableFactory = newTableFactory;
	}

	/**
	 * @return An instance of {@link TableTypeAdapterFactory}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static TypeAdapterFactory get() {
		return instance;
	}

	/**
	 * @return An instance of {@link TableTypeAdapterFactory} with a custom new {@link Table} factory.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapterFactory get(@Nullable final Supplier<? extends Table<String, String, V>> newTableFactory) {
		if ( newTableFactory == null ) {
			return instance;
		}
		return new TableTypeAdapterFactory<>(newTableFactory);
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		if ( !Table.class.isAssignableFrom(typeToken.getRawType()) ) {
			return false;
		}
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type rowKeyType = typeArguments[0][0];
		final Type columnKeyType = typeArguments[1][0];
		return String.class.equals(rowKeyType) && String.class.equals(columnKeyType);
	}

	@Nonnull
	@Override
	protected TypeAdapter<Table<String, String, V>> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type valueType = typeArguments[2][0];
		@SuppressWarnings("unchecked")
		final TypeAdapter<V> valueTypeAdapter = (TypeAdapter<V>) gson.getAdapter(TypeToken.get(valueType));
		if ( newTableFactory == null ) {
			return TableTypeAdapter.get(valueTypeAdapter);
		}
		return TableTypeAdapter.get(valueTypeAdapter, newTableFactory);
	}

}
