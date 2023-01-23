package lsh.ext.gson.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ConstructorConstructors {

	@Nullable
	private static final Constructor<ConstructorConstructor> gsonPre289Constructor;

	@Nullable
	private static final Constructor<ConstructorConstructor> gsonPost290Constructor;

	@Nullable
	private static final Constructor<ConstructorConstructor> gsonPost291Constructor;

	static {
		gsonPre289Constructor = getConstructorConstructorConstructor(Map.class);
		gsonPost290Constructor = getConstructorConstructorConstructor(Map.class, boolean.class);
		gsonPost291Constructor = getConstructorConstructorConstructor(Map.class, boolean.class, List.class);
	}

	public static ConstructorConstructor create(final Map<Type, InstanceCreator<?>> instanceCreators) {
		return create(instanceCreators, true);
	}

	public static ConstructorConstructor create(final Map<Type, InstanceCreator<?>> instanceCreators, final boolean useJdkUnsafe) {
		if ( gsonPost291Constructor != null ) {
			try {
				return gsonPost291Constructor.newInstance(instanceCreators, useJdkUnsafe, Collections.emptyList());
			} catch ( final InstantiationException | IllegalAccessException | InvocationTargetException ex ) {
				throw new RuntimeException(ex);
			}
		}
		if ( gsonPost290Constructor != null ) {
			try {
				return gsonPost290Constructor.newInstance(instanceCreators, useJdkUnsafe);
			} catch ( final InstantiationException | IllegalAccessException | InvocationTargetException ex ) {
				throw new RuntimeException(ex);
			}
		}
		if ( gsonPre289Constructor != null ) {
			// fallback to the constructor prior to Gson 2.9.0
			try {
				return gsonPre289Constructor.newInstance(instanceCreators);
			} catch ( final InstantiationException | IllegalAccessException | InvocationTargetException ex ) {
				throw new RuntimeException(ex);
			}
		}
		throw new AssertionError("Cannot find a way to resolve the constructor for " + ConstructorConstructor.class + ". Candidates: " + Arrays.asList(ConstructorConstructor.class.getDeclaredConstructors()));
	}

	@Nullable
	private static Constructor<ConstructorConstructor> getConstructorConstructorConstructor(final Class<?>... parameterTypes) {
		try {
			return ConstructorConstructor.class.getConstructor(parameterTypes);
		} catch ( final NoSuchMethodException ignored ) {
			return null;
		}
	}

}
