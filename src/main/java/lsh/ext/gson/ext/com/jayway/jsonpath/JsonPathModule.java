package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.util.Collections;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;

/**
 * Implements a Jayway JsonPath module registering the following type adapter factories:
 *
 * <ul>
 * <li>{@link JsonPathTypeAdapterFactory}</li>
 * </ul>
 *
 * @author Lyubomyr Shaydariv
 */
public final class JsonPathModule
		extends AbstractModule {

	private static final IModule instance = build()
			.build();

	private JsonPathModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super("Jayway JsonPath", typeAdapterFactories);
	}

	/**
	 * @return The default instance of the module with the default type adapter factories settings.
	 */
	public static IModule getInstance() {
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
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Builder {

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			final Iterable<? extends TypeAdapterFactory> typeAdapterFactories = Collections.unmodifiableList(Collections.singletonList(
					JsonPathTypeAdapterFactory.getInstance()
			));
			return new JsonPathModule(typeAdapterFactories);
		}

	}

}
