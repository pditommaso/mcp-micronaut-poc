package cz.nekola.mcpnaut.demo.cli;

import io.modelcontextprotocol.kotlin.sdk.CallToolRequest;
import io.modelcontextprotocol.kotlin.sdk.CallToolResult;
import io.modelcontextprotocol.kotlin.sdk.Implementation;
import io.modelcontextprotocol.kotlin.sdk.ServerCapabilities;
import io.modelcontextprotocol.kotlin.sdk.Tool;
import io.modelcontextprotocol.kotlin.sdk.server.Server;
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions;
import jakarta.inject.Singleton;

/**
 * Simple wrapper around the MCP server used in tests and the CLI command.
 */
@Singleton
public class ServerWrapper {

    final Server server = new Server(
            new Implementation("mcp-kotlin test server", "0.1.0"),
            new ServerOptions(
                    new ServerCapabilities(
                            new ServerCapabilities.Prompts(true),
                            new ServerCapabilities.Resources(true, true),
                            new ServerCapabilities.Tools(true)
                    )
            )
    );

    public void addTool(Tool tool,
                        kotlin.jvm.functions.Function2<CallToolRequest, ? super kotlin.coroutines.Continuation<? super CallToolResult>, ? extends Object> handler) {
        server.addTool(tool.getName(), tool.getDescription(), tool.getInputSchema(), handler);
    }
}

