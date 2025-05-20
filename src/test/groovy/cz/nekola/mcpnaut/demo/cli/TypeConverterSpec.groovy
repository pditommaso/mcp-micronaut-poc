package cz.nekola.mcpnaut.demo.cli

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification
import spock.lang.Unroll
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

@MicronautTest
class TypeConverterSpec extends Specification {

    def "convert simple types"() {
        given:
        def converter = new TypeConverter()

        expect:
        converter.jdkType2McpType(String.class) == [type: new JsonPrimitive('String')]
    }

    @Unroll
    def "convert integer types #type"() {
        given:
        def converter = new TypeConverter()

        expect:
        converter.jdkType2McpType(type) == [type: new JsonPrimitive('integer')]

        where:
        type << [int.class, Integer.class, Integer.TYPE, long.class, Long.class, Long.TYPE]
    }

    @Unroll
    def "convert floating types #type"() {
        given:
        def converter = new TypeConverter()

        expect:
        converter.jdkType2McpType(type) == [type: new JsonPrimitive('number')]

        where:
        type << [double.class, Double.class, Double.TYPE, float.class, Float.class, Float.TYPE]
    }

    @Unroll
    def "convert boolean types #type"() {
        given:
        def converter = new TypeConverter()

        expect:
        converter.jdkType2McpType(type) == [type: new JsonPrimitive('boolean')]

        where:
        type << [boolean.class, Boolean.class, Boolean.TYPE]
    }

    def "convert array types"() {
        given:
        def converter = new TypeConverter()

        expect:
        converter.jdkType2McpType(String[].class) == [
                type : new JsonPrimitive('array'),
                items: new JsonObject([type: new JsonPrimitive('String')])
        ]
    }
}

