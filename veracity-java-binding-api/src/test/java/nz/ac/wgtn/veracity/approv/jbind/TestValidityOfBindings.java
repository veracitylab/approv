package nz.ac.wgtn.veracity.approv.jbind;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestValidityOfBindings {

    public static final String SCHEMA_NAME = "/bind-schema.json";
    private static JsonSchema SCHEMA = null;
    private static ObjectMapper MAPPER = new ObjectMapper();

    @BeforeAll
    public static void loadSchema () {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        InputStream is = Bindings.class.getResourceAsStream(SCHEMA_NAME);
        SCHEMA = factory.getSchema(is);
    }

    @ParameterizedTest
    @MethodSource("_bindings")
    public void testBindings (String binding) throws IOException {
        JsonNode node = MAPPER.readTree(Bindings.class.getResourceAsStream(binding));
        Set<ValidationMessage> errors = SCHEMA.validate(node);

        for (ValidationMessage error:errors) {
            System.out.println(error);
            System.out.println("\tcode: "+error.getCode());
            System.out.println("\tpath: "+error.getPath());
        }

        assertEquals(0,errors.size());
    }

    private static Stream<String> _bindings () {
        return Stream.of(Bindings.BINDING_DEFS);
    }

}
