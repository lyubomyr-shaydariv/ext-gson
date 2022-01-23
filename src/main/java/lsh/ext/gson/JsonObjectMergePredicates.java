package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Provides some default merge predicates for {@link IJsonObjectMergePredicate}.
 */
public final class JsonObjectMergePredicates {

	private JsonObjectMergePredicates() {
	}

	/**
	 * @return A predicate that always allows to replace the left value.
	 */
	public static IJsonObjectMergePredicate alwaysReplaceLeft() {
		return DefaultJsonObjectMergePredicate.ALWAYS_REPLACE_LEFT;
	}

	/**
	 * @return A predicate that never allows to replace the left value leaving the left object as is.
	 */
	public static IJsonObjectMergePredicate neverReplaceLeft() {
		return DefaultJsonObjectMergePredicate.NEVER_REPLACE_LEFT;
	}

	private enum DefaultJsonObjectMergePredicate
			implements IJsonObjectMergePredicate {

		ALWAYS_REPLACE_LEFT {
			@Override
			public boolean replace(final String key, final JsonObject leftObject, @Nullable final JsonElement leftValue,
					final JsonObject rightObject, @Nullable final JsonElement rightValue) {
				return true;
			}
		},

		NEVER_REPLACE_LEFT {
			@Override
			public boolean replace(final String key, final JsonObject leftObject, @Nullable final JsonElement leftValue,
					final JsonObject rightObject, @Nullable final JsonElement rightValue) {
				return false;
			}
		}

	}

}
