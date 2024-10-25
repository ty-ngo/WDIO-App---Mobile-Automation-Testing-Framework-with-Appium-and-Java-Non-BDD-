package core.APIClient;

import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class APIClient {

    private final OkHttpClient client;
    private final Request.Builder requestBuilder;
    private RequestBody requestBody;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public APIClient() {
        this.client = new OkHttpClient();
        this.requestBuilder = new Request.Builder();
    }

    public APIClient(String baseUrl) {
        this.client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        this.requestBuilder = new Request.Builder().url(baseUrl);
    }

    public APIClient setBasicAuthentication(String username, String password) {
        String credential = Credentials.basic(username, password);
        this.requestBuilder.addHeader("Authorization", credential);
        return this;
    }

    public APIClient setBearerTokenAuthentication(String token) {
        this.requestBuilder.addHeader("Authorization", "Bearer " + token);
        return this;
    }

    public APIClient addDefaultHeaders(Map<String, String> headers) {
        headers.forEach((key, value) -> requestBuilder.addHeader(key, value));
        return this;
    }

    public APIClient addHeader(String name, String value) {
        this.requestBuilder.addHeader(name, value);
        return this;
    }

    public APIClient setRequestBody(Object body) throws IOException {
        String json = objectMapper.writeValueAsString(body);
        this.requestBody = RequestBody.create(json, MediaType.parse("application/json"));
        return this;
    }

    public APIClient setRequestBody(Map<String, String> formParams) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formParams.forEach(formBodyBuilder::add);
        this.requestBody = formBodyBuilder.build();
        return this;
    }

    public Response executeGet() throws IOException {
        Request request = requestBuilder.get().build();
        return client.newCall(request).execute();
    }

    public Response executePost() throws IOException {
        Request request = requestBuilder.post(requestBody).build();
        return client.newCall(request).execute();
    }

    public Response executePut() throws IOException {
        Request request = requestBuilder.put(requestBody).build();
        return client.newCall(request).execute();
    }

    public Response executeDelete() throws IOException {
        Request request = requestBuilder.delete().build();
        return client.newCall(request).execute();
    }

    public <T> T executeGet(Class<T> responseType) throws IOException {
        try (Response response = executeGet()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String bodyString = responseBody.string();
                return objectMapper.readValue(bodyString, responseType);
            } else {
                throw new IOException("Response body is null");
            }
        }
    }

    public <T> T executePost(Class<T> responseType) throws IOException {
        try (Response response = executePost()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String bodyString = responseBody.string();
                return objectMapper.readValue(bodyString, responseType);
            } else {
                throw new IOException("Response body is null");
            }
        }
    }

    public <T> T executePut(Class<T> responseType) throws IOException {
        try (Response response = executePut()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String bodyString = responseBody.string();
                return objectMapper.readValue(bodyString, responseType);
            } else {
                throw new IOException("Response body is null");
            }
        }
    }

    public <T> T executeDelete(Class<T> responseType) throws IOException {
        try (Response response = executeDelete()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String bodyString = responseBody.string();
                return objectMapper.readValue(bodyString, responseType);
            } else {
                throw new IOException("Response body is null");
            }
        }
    }

}
