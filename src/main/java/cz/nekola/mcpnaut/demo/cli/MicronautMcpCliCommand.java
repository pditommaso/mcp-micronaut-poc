package cz.nekola.mcpnaut.demo.cli;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.modelcontextprotocol.kotlin.sdk.server.StdioServerTransport;
import jakarta.inject.Inject;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.io.InputKt;
import kotlinx.io.OutputKt;
import kotlinx.io.Source;
import kotlinx.io.Sink;
import kotlinx.io.buffer.BufferingKt;
import picocli.CommandLine.Command;

/**
 * Entry point for the CLI application that starts the MCP server and listens on stdio.
 */
@Command(name = "micronaut-mcp-cli", description = "...", mixinStandardHelpOptions = true)
public class MicronautMcpCliCommand implements Runnable {

    @Inject
    ApplicationContext applicationContext;

    @Override
    public void run() {
        StdioServerTransport transport = new StdioServerTransport(
                BufferingKt.buffered(InputKt.asSource(System.in)),
                BufferingKt.buffered(OutputKt.asSink(System.out))
        );

        BuildersKt.runBlocking$default(null, (CoroutineScope scope) -> {
            ServerWrapper serverWrapper = applicationContext.getBean(ServerWrapper.class);
            serverWrapper.server.connect(transport);
            Job done = new Job();
            serverWrapper.server.onClose(() -> {
                done.complete(null);
            });
            done.join();
            System.out.println("Server closed");
            return null;
        }, 1, null);
    }

    public static void main(String[] args) {
        PicocliRunner.run(MicronautMcpCliCommand.class, args);
    }
}

