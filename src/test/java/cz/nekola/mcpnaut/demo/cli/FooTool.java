package cz.nekola.mcpnaut.demo.cli;

import jakarta.inject.Singleton;

@Singleton
public class FooTool {

    @Tool(name = "footoolik", description = "Toolik that Foos a lot")
    public String toolik() {
        return "toolik";
    }

    @Tool(name = "footoolai2", description = "Toolik2 that Foos a lot")
    public String toolika2(@ToolArg(description = "Arg 1 desc") int arg11,
                           @ToolArg(description = "Arg 2 desc") String arg22) {
        return "toolik " + arg11 + " " + arg22;
    }

    @Tool(name = "footoolai3", description = "Toolik3 that Foos a lot")
    public String toolika3(@ToolArg(description = "Arg 1 desc") int arg1) {
        return "toolik " + arg1;
    }
}

