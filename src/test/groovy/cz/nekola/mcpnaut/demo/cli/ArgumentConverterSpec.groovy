package cz.nekola.mcpnaut.demo.cli

import io.micronaut.core.type.Argument
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import spock.lang.Specification

@MicronautTest
class ArgumentConverterSpec extends Specification {

    def "convert primitive values"() {
        given:
        def converter = new ArgumentConverter()
        def request = new CallToolRequest(
                name: 'req',
                arguments: new JsonObject([("param1"): new JsonPrimitive(42)])
        )

        expect:
        converter.mcpValue2jvmValue(request, Argument.of(int.class, 'param1')) == 42
    }
}

