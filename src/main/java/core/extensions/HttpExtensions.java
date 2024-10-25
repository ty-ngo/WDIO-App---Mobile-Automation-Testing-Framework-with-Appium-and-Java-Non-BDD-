package core.extensions;

import okhttp3.Response;
import okhttp3.ResponseBody;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpExtensions {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Verify response schema against a JSON schema file
    public static void verifySchema(Response response, String pathFile) throws IOException {
        // Read the schema file
        String schemaContent = new String(Files.readAllBytes(Paths.get(pathFile)));
        JSONObject rawSchema = new JSONObject(new JSONTokener(schemaContent));
        Schema schema = SchemaLoader.load(rawSchema);

        // Get the response body
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            // Convert response body to JSON and validate
            String responseContent = responseBody.string();
            JSONObject jsonResponse = new JSONObject(responseContent);
            schema.validate(jsonResponse); // Throws exception if invalid
        } else {
            throw new IOException("Response body is null");
        }
    }

    // Convert response to a dynamic object (use Jackson for JSON handling)
    public static Object convertToDynamicObject(Response response) throws IOException {
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            String content = responseBody.string();
            return objectMapper.readValue(content, Object.class); // Convert to dynamic object (Map)
        } else {
            throw new IOException("Response body is null");
        }
    }

    // Convert response to a specific DTO class
    public static <T> T convertToDto(Response response, Class<T> dtoClass) throws IOException {
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            String content = responseBody.string();
            return objectMapper.readValue(content, dtoClass);
        } else {
            throw new IOException("Response body is null");
        }
    }

    // Verify status code
    public static void verifyStatusCodeOk(Response response) {
        if (response.code() != 200) {
            throw new AssertionError("Expected status code 200, but got " + response.code());
        }
    }

    public static void verifyStatusCodeCreated(Response response) {
        if (response.code() != 201) {
            throw new AssertionError("Expected status code 201, but got " + response.code());
        }
    }

    public static void verifyStatusCodeBadRequest(Response response) {
        if (response.code() != 400) {
            throw new AssertionError("Expected status code 400, but got " + response.code());
        }
    }

    public static void verifyStatusCodeUnauthorized(Response response) {
        if (response.code() != 401) {
            throw new AssertionError("Expected status code 401, but got " + response.code());
        }
    }

    public static void verifyStatusCodeForbidden(Response response) {
        if (response.code() != 403) {
            throw new AssertionError("Expected status code 403, but got " + response.code());
        }
    }

    public static void verifyStatusCodeInternalServerError(Response response) {
        if (response.code() != 500) {
            throw new AssertionError("Expected status code 500, but got " + response.code());
        }
    }

    public static void verifyStatusCodeNoContent(Response response) {
        if (response.code() != 204) {
            throw new AssertionError("Expected status code 204, but got " + response.code());
        }
    }
}
