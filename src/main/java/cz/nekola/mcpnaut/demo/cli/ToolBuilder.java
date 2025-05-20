package cz.nekola.mcpnaut.demo.cli;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.processor.ExecutableMethodProcessor;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.ExecutableMethod;
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest;
import io.modelcontextprotocol.kotlin.sdk.CallToolResult;
import io.modelcontextprotocol.kotlin.sdk.TextContent;
import io.modelcontextprotocol.kotlin.sdk.Tool;
import jakarta.inject.Singleton;
import kotlinx.serialization.json.JsonElement;
import kotlinx.serialization.json.JsonObject;
import kotlinx.serialization.json.JsonPrimitive;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Processes {@link Tool} annotated beans and registers them in the MCP server.
 */
@Singleton
public class ToolBuilder implements ExecutableMethodProcessor<Tool> {

    private final ServerWrapper serverWrapper;
    private final ApplicationContext applicationContext;
    private final TypeConverter typeConverter;
    private final ArgumentConverter argumentConverter;

    public ToolBuilder(ServerWrapper serverWrapper,
                       ApplicationContext applicationContext,
                       TypeConverter typeConverter,
                       ArgumentConverter argumentConverter) {
        this.serverWrapper = serverWrapper;
        this.applicationContext = applicationContext;
        this.typeConverter = typeConverter;
        this.argumentConverter = argumentConverter;
    }

    @Override
    public void process(BeanDefinition<?> beanDefinition, ExecutableMethod<?, ?> method) {
        io.micronaut.core.annotation.AnnotationValue<Tool> annotation =
                method.getAnnotationMetadata().getAnnotation(Tool.class);

        Map<String, JsonElement> properties = new LinkedHashMap<>();
        Arrays.stream(method.getArguments()).forEach(arg -> {
            String description = arg.getAnnotation(ToolArg.class)
                    .stringValue("description").get();
            Map<String, JsonElement> typeInfo = typeConverter.jdkType2McpType(arg.getType());

            Map<String, JsonElement> value = new LinkedHashMap<>();
            value.put("description", new JsonPrimitive(description));
            value.putAll(typeInfo);

            properties.put(arg.getName(), new JsonObject(value));
        });

        Tool.Input inputSchema = new Tool.Input(new JsonObject(properties));

        serverWrapper.addTool(
                new Tool(annotation.stringValue("name").get(),
                        annotation.stringValue("description").get(),
                        inputSchema),
                call -> {
                    Object[] args = Arrays.stream(method.getArguments())
                            .map(p -> argumentConverter.mcpValue2jvmValue(call, p))
                            .toArray();
                    Object bean = applicationContext.getBean(beanDefinition);
                    Object beanResult = method.getTargetMethod().invoke(bean, args);
                    return new CallToolResult(java.util.List.of(new TextContent(beanResult.toString())));
                }
        );
    }
}

