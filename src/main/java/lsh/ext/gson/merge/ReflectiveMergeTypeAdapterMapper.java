package lsh.ext.gson.merge;

import java.util.Collections;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.InstanceCreator;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * TODO
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class ReflectiveMergeTypeAdapterMapper
		implements IMergeTypeAdapterMapper {

	private ReflectiveMergeTypeAdapterMapper() {
	}

	/**
	 * TODO
	 *
	 * @return TODO
	 */
	public static IMergeTypeAdapterMapper get() {
		return new ReflectiveMergeTypeAdapterMapper();
	}

	@Nullable
	@Override
	@SuppressWarnings("UnnecessarilyQualifiedInnerClassAccess")
	public <T> TypeAdapter<T> map(@Nonnull final TypeAdapter<?> typeAdapter, final Object instance, @Nonnull final Gson gson,
			@Nonnull final TypeToken<T> typeToken) {
		if ( typeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter ) {
			final InstanceCreator<?> o = (InstanceCreator<Object>) type -> instance;
			final ConstructorConstructor constructorConstructor = new ConstructorConstructor(Collections.singletonMap(typeToken.getType(), o));
			final FieldNamingStrategy fieldNamingPolicy = gson.fieldNamingStrategy();
			final Excluder excluder = gson.excluder();
			final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(constructorConstructor);
			final TypeAdapterFactory typeAdapterFactory = new ReflectiveTypeAdapterFactory(constructorConstructor, fieldNamingPolicy, excluder, jsonAdapterFactory);
			return typeAdapterFactory.create(gson, typeToken);
		}
		return null;
	}

}
