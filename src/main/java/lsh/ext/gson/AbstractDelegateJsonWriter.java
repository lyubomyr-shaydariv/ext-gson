package lsh.ext.gson;

import java.io.IOException;
import java.io.Writer;
import javax.annotation.Nonnull;
import javax.annotation.WillCloseWhenClosed;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.stream.JsonWriter;

@SuppressWarnings({ "AbstractClassExtendsConcreteClass", "AbstractClassWithOnlyOneDirectInheritor", "ClassWithoutNoArgConstructor" })
public abstract class AbstractDelegateJsonWriter
		extends JsonWriter {

	private final JsonWriter out;

	@SuppressWarnings({ "WeakerAccess" })
	protected AbstractDelegateJsonWriter(@WillCloseWhenClosed final JsonWriter out) {
		super(neverWriter);
		this.out = out;
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public boolean isLenient() {
		return out.isLenient();
	}

	@Override
	@CanIgnoreReturnValue
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonWriter beginArray()
			throws IOException {
		return out.beginArray();
	}

	@Override
	@CanIgnoreReturnValue
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonWriter endArray()
			throws IOException {
		return out.endArray();
	}

	@Override
	@CanIgnoreReturnValue
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonWriter beginObject()
			throws IOException {
		return out.beginObject();
	}

	@Override
	@CanIgnoreReturnValue
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonWriter endObject()
			throws IOException {
		return out.endObject();
	}

	@Override
	@CanIgnoreReturnValue
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonWriter name(final String name)
			throws IOException {
		return out.name(name);
	}

	@Override
	@CanIgnoreReturnValue
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonWriter value(final String value)
			throws IOException {
		return out.value(value);
	}

	@Override
	@CanIgnoreReturnValue
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonWriter value(final boolean value)
			throws IOException {
		return out.value(value);
	}

	@Override
	@CanIgnoreReturnValue
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonWriter value(final Boolean value)
			throws IOException {
		return out.value(value);
	}

	@Override
	@CanIgnoreReturnValue
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonWriter value(final float value)
			throws IOException {
		return out.value(value);
	}

	@Override
	@CanIgnoreReturnValue
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonWriter value(final double value)
			throws IOException {
		return out.value(value);
	}

	@Override
	@CanIgnoreReturnValue
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonWriter value(final long value)
			throws IOException {
		return out.value(value);
	}

	@Override
	@CanIgnoreReturnValue
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonWriter value(final Number value)
			throws IOException {
		return out.value(value);
	}

	@Override
	@CanIgnoreReturnValue
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonWriter nullValue()
			throws IOException {
		return out.nullValue();
	}

	@Override
	@CanIgnoreReturnValue
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonWriter jsonValue(final String value)
			throws IOException {
		return out.jsonValue(value);
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod" })
	public void flush()
			throws IOException {
		out.flush();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod" })
	public void close()
			throws IOException {
		out.close();
	}

	private static final Writer neverWriter = new Writer() {
		// @formatter:off
		@Override public void write(@Nonnull final char[] buffer, final int offset, final int length) { throw new AssertionError(); }
		@Override public void flush() { throw new AssertionError(); }
		@Override public void close() { throw new AssertionError(); }
		// @formatter:on
	};

}
