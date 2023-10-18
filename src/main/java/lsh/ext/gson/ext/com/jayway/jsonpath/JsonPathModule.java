package lsh.ext.gson.ext.com.jayway.jsonpath;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IModule;
import lsh.ext.gson.UnmodifiableIterable;

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

	@Getter
	private static final IModule instance = builder()
			.build();

	private JsonPathModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true, prefix = "with")
	public static final class Builder {

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new JsonPathModule(UnmodifiableIterable.copyOf(
					JsonPathTypeAdapterFactory.getInstance()
			));
		}

	}

}
