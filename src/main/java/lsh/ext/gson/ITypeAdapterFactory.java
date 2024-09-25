package lsh.ext.gson;

import java.lang.reflect.Type;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

@SuppressWarnings("unused")
public interface ITypeAdapterFactory<K>
		extends TypeAdapterFactory {

	@Override
	@Nullable
	<T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken);

	interface ITypeResolver<K> {

		TypeToken<K> getTypeToken();

		TypeAdapter<K> getTypeAdapter(Class<K> klass);

		<T> TypeAdapter<T> getTypeAdapter(int index);

		<T> TypeAdapter<T> getTypeAdapterForClass(Class<T> klass);

	}

	@SuppressWarnings("NewClassNamingConvention")
	static <K extends RAW_K, RAW_K> ITypeAdapterFactory<K> forClass(final Class<RAW_K> klass, final Function<? super ITypeResolver<K>, ? extends TypeAdapter<K>> createTypeAdapter) {
		return new ITypeAdapterFactory<>() {
			@Nullable
			@Override
			public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
				if ( typeToken.getRawType() != klass ) {
					return null;
				}
				@SuppressWarnings("unchecked")
				final TypeToken<K> castTypeToken = (TypeToken<K>) typeToken;
				final ITypeResolver<K> typeResolver = new ITypeResolver<K>() {
					private final Type type = castTypeToken.getType();

					@Override
					public TypeToken<K> getTypeToken() {
						return castTypeToken;
					}

					@Override
					public TypeAdapter<K> getTypeAdapter(final Class<K> klass) {
						return gson.getAdapter(klass);
					}

					@Override
					public <INNER_T> TypeAdapter<INNER_T> getTypeAdapter(final int index) {
						final Type typeArgument = ParameterizedTypes.getTypeArgumentOrObjectClass(castTypeToken.getType(), index);
						@SuppressWarnings("unchecked")
						final TypeToken<INNER_T> o = (TypeToken<INNER_T>) TypeToken.get(typeArgument);
						return gson.getAdapter(o);
					}

					@Override
					public <T> TypeAdapter<T> getTypeAdapterForClass(final Class<T> klass) {
						return gson.getAdapter(klass);
					}
				};
				@SuppressWarnings("unchecked")
				final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) createTypeAdapter.apply(typeResolver);
				return castTypeAdapter;
			}
		};
	}

	@SuppressWarnings("NewClassNamingConvention")
	static <K extends RAW_K, RAW_K> ITypeAdapterFactory<K> forClassHierarchy(final Class<? extends RAW_K> klass, final Function<? super ITypeResolver<K>, ? extends TypeAdapter<K>> createTypeAdapter) {
		return new ITypeAdapterFactory<>() {
			@Nullable
			@Override
			public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
				if ( !klass.isAssignableFrom(typeToken.getRawType()) ) {
					return null;
				}
				@SuppressWarnings("unchecked")
				final TypeToken<K> castTypeToken = (TypeToken<K>) typeToken;
				final ITypeResolver<K> typeResolver = new ITypeResolver<K>() {
					private final Type type = castTypeToken.getType();

					@Override
					public TypeToken<K> getTypeToken() {
						return castTypeToken;
					}

					@Override
					public TypeAdapter<K> getTypeAdapter(final Class<K> klass) {
						return gson.getAdapter(klass);
					}

					@Override
					public <INNER_T> TypeAdapter<INNER_T> getTypeAdapter(final int index) {
						final Type typeArgument = ParameterizedTypes.getTypeArgumentOrObjectClass(castTypeToken.getType(), index);
						@SuppressWarnings("unchecked")
						final TypeToken<INNER_T> o = (TypeToken<INNER_T>) TypeToken.get(typeArgument);
						return gson.getAdapter(o);
					}

					@Override
					public <T> TypeAdapter<T> getTypeAdapterForClass(final Class<T> klass) {
						return gson.getAdapter(klass);
					}
				};
				@SuppressWarnings("unchecked")
				final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) createTypeAdapter.apply(typeResolver);
				return castTypeAdapter;
			}
		};
	}

}
