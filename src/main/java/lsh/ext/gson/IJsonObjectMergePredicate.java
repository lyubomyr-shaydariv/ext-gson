package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Predicate that specifies merging strategy for {@link JsonObjects#mergeIntoLeft(JsonObject, JsonObject, IJsonObjectMergePredicate)} and {@link
 * JsonObjects#mergeIntoNew(JsonObject, JsonObject, IJsonObjectMergePredicate)}
 *
 * @author Lyubomyr Shaydariv
 */
public interface IJsonObjectMergePredicate {

	/**
	 * Determines if the left object value can be replaced with the right object value.
	 *
	 * @param key
	 * 		JSON object key
	 * @param leftObject
	 * 		Left JSON object to be merged
	 * @param leftValue
	 * 		Left JSON object value to be merge
	 * @param rightObject
	 * 		Right JSON object to be merged
	 * @param rightValue
	 * 		Right JSON object value to be merge
	 *
	 * @return {@code true} if the left object value can be replaced with the right object value, otherwise {@code false}.
	 */
	boolean replace(String key, JsonObject leftObject, @Nullable JsonElement leftValue, JsonObject rightObject,
			@Nullable JsonElement rightValue);

}
