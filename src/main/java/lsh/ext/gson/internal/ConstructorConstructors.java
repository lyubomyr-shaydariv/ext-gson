package lsh.ext.gson.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
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

	static {
		gsonPre289Constructor = Constructors.getConstructorOrNull(ConstructorConstructor.class, Map.class);
		gsonPost290Constructor = Constructors.getConstructorOrNull(ConstructorConstructor.class, Map.class, boolean.class);
	}

	public static ConstructorConstructor create(final Map<Type, InstanceCreator<?>> instanceCreators) {
		return create(instanceCreators, true);
	}

	public static ConstructorConstructor create(final Map<Type, InstanceCreator<?>> instanceCreators, final boolean useJdkUnsafe) {
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
		throw new AssertionError("Cannot find a way to resolve the constructor for " + ConstructorConstructor.class);
	}

}
