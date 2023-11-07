package lsh.ext.gson;

import java.lang.reflect.Array;
import java.lang.reflect.Parameter;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

public final class VariadicArgumentsAggregator
		implements ArgumentsAggregator {

	@Override
	public Object aggregateArguments(final ArgumentsAccessor argumentsAccessor, final ParameterContext parameterContext) {
		final Parameter parameter = parameterContext.getParameter();
		if ( !parameter.isVarArgs() ) {
			throw new ArgumentsAggregationException("Not a variadic parameter: " + parameter + " #" + parameterContext.getIndex() + " in " + parameterContext.getDeclaringExecutable());
		}
		final Class<?> componentType = parameter.getType().componentType();
		if ( componentType.isPrimitive() ) {
			throw new ArgumentsAggregationException("Primitive array component type not yet supported: " + componentType);
		}
		return Stream.of(argumentsAccessor.toArray())
				.map(componentType::cast)
				.toArray(length -> (Object[]) Array.newInstance(componentType, length));
	}

}
