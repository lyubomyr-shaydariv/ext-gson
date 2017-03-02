package lsh.ext.gson;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;

import static lsh.ext.gson.JsonObjectMergePredicates.alwaysReplaceLeft;

/**
 * Provides miscellaneous {@link TypeAdapter} utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class JsonElements {

	private JsonElements() {
	}

	/**
	 * Merges two {@link JsonObject} objects, left and right, into a new {@link JsonObject} object on common keys using the {@link JsonObjectMergePredicates#alwaysReplaceLeft()} strategy.
	 *
	 * @param left  Left
	 * @param right Right
	 *
	 * @return A new {@link JsonObject} containing a merged result of both {@link JsonObject} arguments.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static JsonObject mergeIntoNew(final JsonObject left, final JsonObject right) {
		return mergeIntoNew(left, right, alwaysReplaceLeft());
	}

	/**
	 * Merges two {@link JsonObject} objects, left and right, into a new {@link JsonObject} object on common keys.
	 *
	 * @param left      Left
	 * @param right     Right
	 * @param predicate Predicate
	 *
	 * @return A new {@link JsonObject} containing a merged result of both {@link JsonObject} arguments.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static JsonObject mergeIntoNew(final JsonObject left, final JsonObject right, final IJsonObjectMergePredicate predicate) {
		final JsonObject merged = new JsonObject();
		for ( final Entry<String, JsonElement> leftEntry : left.entrySet() ) {
			merged.add(leftEntry.getKey(), leftEntry.getValue());
		}
		mergeIntoLeft(merged, right, predicate);
		return merged;
	}

	/**
	 * Merges the right {@link JsonObject} into the left {@link JsonObject} on common keys using the {@link JsonObjectMergePredicates#alwaysReplaceLeft()} strategy.
	 *
	 * @param left  Left
	 * @param right Right
	 *
	 * @return The modified {@code left} parameter.
	 *
	 * @see #mergeIntoLeft(JsonObject, JsonObject, IJsonObjectMergePredicate)
	 * @since 0-SNAPSHOT
	 */
	public static JsonObject mergeIntoLeft(final JsonObject left, final JsonObject right) {
		return mergeIntoLeft(left, right, alwaysReplaceLeft());
	}

	/**
	 * Merges the right {@link JsonObject} into the left {@link JsonObject} on common keys.
	 *
	 * @param left      Left
	 * @param right     Right
	 * @param predicate Predicate
	 *
	 * @return The modified {@code left} parameter.
	 *
	 * @see #mergeIntoLeft(JsonObject, JsonObject)
	 * @since 0-SNAPSHOT
	 */
	public static JsonObject mergeIntoLeft(final JsonObject left, final JsonObject right, final IJsonObjectMergePredicate predicate) {
		for ( final Entry<String, JsonElement> rightEntry : right.entrySet() ) {
			final String key = rightEntry.getKey();
			final JsonElement leftValue = left.get(key);
			final JsonElement rightValue = rightEntry.getValue();
			if ( predicate.replace(key, left, leftValue, right, rightValue) ) {
				left.add(key, rightValue);
			}
		}
		return left;
	}

}
