package cz.nekola.mcpnaut.demo.cli;

import jakarta.inject.Singleton;
import java.util.Arrays;

@Singleton
public class MultiTypeToolKotlin {

    @Tool(name = "singleParamTool_int", description = "Test tool with single int param")
    public String singleParamTool_int(@ToolArg(description = "int arg description") int param1) {
        return "MultiTypeToolKotlin_int answer " + param1;
    }

    @Tool(name = "singleParamTool_Integer", description = "Test tool with single Integer param")
    public String singleParamTool_Integer(@ToolArg(description = "Integer arg description") Integer param1) {
        return "MultiTypeToolKotlin_Integer answer " + param1;
    }

    @Tool(name = "singleParamTool_bool", description = "Test tool with single bool param")
    public String singleParamTool_bool(@ToolArg(description = "Boolean arg description") Boolean param1) {
        return "MultiTypeToolKotlin_Bool answer " + param1;
    }

    @Tool(name = "singleParamTool_long", description = "Test tool with single long param")
    public String singleParamTool_long(@ToolArg(description = "Long arg description") Long param1) {
        return "MultiTypeToolKotlin_Long answer " + param1;
    }

    @Tool(name = "singleParamTool_float", description = "Test tool with single float param")
    public String singleParamTool_float(@ToolArg(description = "Float arg description") Float param1) {
        return "MultiTypeToolKotlin_Float answer " + param1;
    }

    @Tool(name = "singleParamTool_double", description = "Test tool with single double param")
    public String singleParamTool_double(@ToolArg(description = "Double arg description") Double param1) {
        return "MultiTypeToolKotlin_Double answer " + param1;
    }

    @Tool(name = "singleParamTool_string", description = "Test tool with single string param")
    public String singleParamTool_string(@ToolArg(description = "String arg description") String param1) {
        return "MultiTypeToolKotlin_String answer " + param1;
    }

    @Tool(name = "singleParamTool_array_of_strings", description = "Test tool with single array of strings param")
    public String singleParamTool_array_of_strings(@ToolArg(description = "Array of Strings arg description") String[] param1) {
        return "MultiTypeToolKotlin_Array_of_Strings answer " + String.join(", ", param1);
    }

    @Tool(name = "singleParamTool_array_of_ints", description = "Test tool with single array of ints param")
    public String singleParamTool_array_of_ints(@ToolArg(description = "Array of Ints arg description") Integer[] param1) {
        return "MultiTypeToolKotlin_Array_of_Ints answer " + Arrays.toString(param1);
    }

    @Tool(name = "singleParamTool_array_of_array_of_ints", description = "Test tool with single array of array of ints param")
    public String singleParamTool_array_of_arrays_of_ints(@ToolArg(description = "Array of Ints arg description") Integer[][] param1) {
        StringBuilder sb = new StringBuilder();
        sb.append("MultiTypeToolKotlin_Array_of_Array_of_Ints answer ");
        sb.append(Arrays.deepToString(param1));
        return sb.toString();
    }
}

