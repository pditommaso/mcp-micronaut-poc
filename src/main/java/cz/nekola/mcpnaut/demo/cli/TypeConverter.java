package cz.nekola.mcpnaut.demo.cli;

import jakarta.inject.Singleton;
import kotlinx.serialization.json.JsonElement;
import kotlinx.serialization.json.JsonObject;
import kotlinx.serialization.json.JsonPrimitive;

import java.util.Map;

@Singleton
public class TypeConverter {

    public Map<String, JsonElement> jdkType2McpType(Class<?> type) {
        if (type == String.class) {
            return Map.of("type", new JsonPrimitive("String"));
        }
        if (type == int.class || type == Integer.class || type == Integer.TYPE
                || type == long.class || type == Long.class || type == Long.TYPE) {
            return Map.of("type", new JsonPrimitive("integer"));
        }
        if (type == double.class || type == Double.class || type == Double.TYPE
                || type == float.class || type == Float.class || type == Float.TYPE) {
            return Map.of("type", new JsonPrimitive("number"));
        }
        if (type == boolean.class || type == Boolean.class || type == Boolean.TYPE) {
            return Map.of("type", new JsonPrimitive("boolean"));
        }

        if (type.isArray()) {
            return Map.of(
                    "type", new JsonPrimitive("array"),
                    "items", new JsonObject(jdkType2McpType(type.getComponentType()))
            );
        }

        throw new IllegalArgumentException(type + " is not supported");
    }
}

