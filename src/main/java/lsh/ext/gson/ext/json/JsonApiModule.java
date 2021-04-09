package lsh.ext.gson.ext.json;

import java.util.Arrays;
import java.util.Collections;

import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;

/**
 * <p>
 * Implements a Java JSON API module registering the following type adapter factories:
 * </p>
 *
 * <ul>
 * <li>{@link JsonValueTypeAdapterFactory}</li>
 * </ul>
 *
 * @author Lyubomyr Shaydariv
 * @version 0-SNAPSHOT
 */
public final class JsonApiModule
		extends AbstractModule {

	private static final IModule instance = build()
			.done();

	private JsonApiModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super("Java JSON API", typeAdapterFactories);
	}

	/**
	 * @return The default instance of the module with the default type adapter factories settings.
	 */
	public static IModule get() {
		return instance;
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static Builder build() {
		return new Builder();
	}

	/**
	 * A builder to configure a new module instance.
	 */
	public static final class Builder {

		private Builder() {
		}

		/**
		 * @return A new module instance.
		 */
		public IModule done() {
			final Iterable<? extends TypeAdapterFactory> typeAdapterFactories = Collections.unmodifiableList(Arrays.asList(
					JsonValueTypeAdapterFactory.get()
			));
			return new JsonApiModule(typeAdapterFactories);
		}

	}

}
