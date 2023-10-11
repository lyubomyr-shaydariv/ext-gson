package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface IJsonObjectMergePredicate {

	IJsonObjectMergePredicate replace = (key, leftObject, leftValue, rightObject, rightValue) -> true;

	IJsonObjectMergePredicate retain = (key, leftObject, leftValue, rightObject, rightValue) -> false;

	boolean canReplace(String key, JsonObject leftObject, @Nullable JsonElement leftValue, JsonObject rightObject, @Nullable JsonElement rightValue);

}
