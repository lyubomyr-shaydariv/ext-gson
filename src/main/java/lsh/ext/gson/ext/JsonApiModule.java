package lsh.ext.gson.ext;

import java.util.Collections;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;
import lsh.ext.gson.ext.javax.json.JsonValueTypeAdapterFactory;

/**
 * Implements a Java JSON API module registering the following type adapter factories:
 *
 * <ul>
 * <li>{@link JsonValueTypeAdapterFactory}</li>
 * </ul>
 *
 * @author Lyubomyr Shaydariv
 */
public final class JsonApiModule
		extends AbstractModule {

	@Getter
	private static final IModule instance = build()
			.build();

	private JsonApiModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
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

		private static final Iterable<? extends TypeAdapterFactory> defaultTypeAdapterFactories = Collections.unmodifiableList(Collections.singletonList(
						JsonValueTypeAdapterFactory.getInstance()
				));

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new JsonApiModule(defaultTypeAdapterFactories);
		}

	}

}
