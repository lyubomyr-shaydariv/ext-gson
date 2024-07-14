module lsh.ext.gson {

	exports lsh.ext.gson;
	exports lsh.ext.gson.ext.com.google.common.base;
	exports lsh.ext.gson.ext.com.google.common.collect;
	exports lsh.ext.gson.ext.com.jayway.jsonpath;
	exports lsh.ext.gson.ext.jakarta.json;
	exports lsh.ext.gson.ext.java.sql;
	exports lsh.ext.gson.ext.java.time;
	exports lsh.ext.gson.ext.java.util;
	exports lsh.ext.gson.ext.java.util.stream;
	exports lsh.ext.gson.ext.javax.json;
	exports lsh.ext.gson.ext.org.apache.commons.collections4;

	requires static com.github.spotbugs.annotations;
	requires static lombok;

	requires static com.google.common;
	requires static jakarta.json;
	requires static java.json;
	requires static java.sql;
	requires static json.path;
	requires static jsr305;
	requires static org.apache.commons.collections4;

	requires com.google.gson;

	opens lsh.ext.gson;
	opens lsh.ext.gson.ext.com.jayway.jsonpath;

}
