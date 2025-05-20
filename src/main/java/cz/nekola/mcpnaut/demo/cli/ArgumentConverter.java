package cz.nekola.mcpnaut.demo.cli;

import io.micronaut.core.type.Argument;
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest;
import jakarta.inject.Singleton;
import kotlinx.serialization.json.JsonArray;
import kotlinx.serialization.json.JsonElement;
import kotlinx.serialization.json.JsonPrimitive;

import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.IntStream;

@Singleton
public class ArgumentConverter {

    public Object mcpValue2jvmValue(CallToolRequest call, Argument<?> parameter) {
        JsonElement element = call.getArguments().get(parameter.getName());
        return convertType(parameter.getType(), element);
    }

    private Object convertType(Class<?> type, JsonElement element) {
        if (type == int.class || type == Integer.class || type == Integer.TYPE) {
            return element.getJsonPrimitive().getIntOrNull();
        }
        if (type == long.class || type == Long.class || type == Long.TYPE) {
            return element.getJsonPrimitive().getLongOrNull();
        }
        if (type == boolean.class || type == Boolean.class || type == Boolean.TYPE) {
            return element.getJsonPrimitive().getBooleanOrNull();
        }
        if (type == float.class || type == Float.class || type == Float.TYPE) {
            return element.getJsonPrimitive().getFloatOrNull();
        }
        if (type == double.class || type == Double.class || type == Double.TYPE) {
            return element.getJsonPrimitive().getDoubleOrNull();
        }
        if (type == String.class) {
            return element.getJsonPrimitive().getContentOrNull();
        }

        if (type.isArray()) {
            return convertArray(type, element);
        }

        throw new IllegalArgumentException("Unsupported type " + type);
    }

    private Object convertArray(Class<?> type, JsonElement element) {
        Class<?> componentType = type.getComponentType();

        if (componentType.isPrimitive()) {
            JsonArray array = element.getJsonArray();
            if (componentType == int.class) {
                int[] target = new int[array.size()];
                IntStream.range(0, array.size()).forEach(i -> target[i] = array.get(i).getJsonPrimitive().getInt());
                return target;
            }
            if (componentType == long.class) {
                long[] target = new long[array.size()];
                IntStream.range(0, array.size()).forEach(i -> target[i] = array.get(i).getJsonPrimitive().getLong());
                return target;
            }
            if (componentType == double.class) {
                double[] target = new double[array.size()];
                IntStream.range(0, array.size()).forEach(i -> target[i] = array.get(i).getJsonPrimitive().getDouble());
                return target;
            }
            if (componentType == float.class) {
                float[] target = new float[array.size()];
                IntStream.range(0, array.size()).forEach(i -> target[i] = array.get(i).getJsonPrimitive().getFloat());
                return target;
            }
            if (componentType == boolean.class) {
                boolean[] target = new boolean[array.size()];
                IntStream.range(0, array.size()).forEach(i -> target[i] = array.get(i).getJsonPrimitive().getBoolean());
                return target;
            }
            throw new IllegalArgumentException("Unsupported componentType: " + componentType);
        } else if (componentType.isRecord()) {
            throw new UnsupportedOperationException();
        } else {
            List<Object> elements = element.getJsonArray().stream()
                    .map(it -> convertType(componentType, it))
                    .toList();
            Object targetArray = Array.newInstance(componentType, elements.size());
            for (int i = 0; i < elements.size(); i++) {
                Array.set(targetArray, i, elements.get(i));
            }
            return targetArray;
        }
    }
}

