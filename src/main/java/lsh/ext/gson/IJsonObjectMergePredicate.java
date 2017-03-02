package lsh.ext.gson;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Predicate that specifies merging strategy for {@link JsonElements#mergeIntoLeft(JsonObject, JsonObject, IJsonObjectMergePredicate)}
 * and {@link JsonElements#mergeIntoNew(JsonObject, JsonObject, IJsonObjectMergePredicate)}
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
@FunctionalInterface
public interface IJsonObjectMergePredicate {

	/**
	 * Determines if the left object value can be replaced with the right object value.
	 *
	 * @param key         JSON object key
	 * @param leftObject  Left JSON object to be merged
	 * @param leftValue   Left JSON object value to be merge
	 * @param rightObject Right JSON object to be merged
	 * @param rightValue  Right JSON object value to be merge
	 *
	 * @return {@code true} if the left object value can be replaced with the right object value, otherwise {@code false}.
	 *
	 * @since 0-SNAPSHOT
	 */
	boolean replace(@Nonnull String key, @Nonnull JsonObject leftObject, @Nullable JsonElement leftValue, @Nonnull JsonObject rightObject,
			@Nullable JsonElement rightValue);

}
