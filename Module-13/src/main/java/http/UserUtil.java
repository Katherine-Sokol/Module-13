package http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import http.userinfo.Address;
import http.userinfo.Geoposition;
import http.userinfo.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class UserUtil {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();
    private static final String USERS_URI = "https://jsonplaceholder.typicode.com/users";

    private UserUtil() {
        throw new IllegalStateException();
    }

    public static User createUser(User user) throws IOException, InterruptedException {
        String requestBody = GSON.toJson(user);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(USERS_URI))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static User updateUser(User user) throws IOException, InterruptedException {
        String requestBody = GSON.toJson(user);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/%d", USERS_URI, user.getId())))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static boolean deleteUser(int id) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/%d", USERS_URI, id)))
                .DELETE()
                .build();
        HttpResponse<String> response = CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() >= 200 && response.statusCode() < 300;
    }

    public static List<User> getAllUsers() throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(USERS_URI))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), new TypeToken<List<User>>() {
        }.getType());
    }

    public static User getUserById(int id) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/%d", USERS_URI, id)))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static List<User> getUsersByUsername(String username) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s?username=%s", USERS_URI, username)))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), new TypeToken<List<User>>() {
        }.getType());
    }

    public static User createDefaultUser() {
        User user = new User();
        user.setId(200);
        user.setName("Kate Sokol");
        user.setUsername("kate_sokol");
        user.setEmail("kate.sokol@email.com");
        user.setAddress(
                new Address("Kulas Light",
                        "Apt. 556",
                        "Gwenborough",
                        "92998-3874",
                        new Geoposition("-37.3159", "81.1496"))
        );
        user.setPhone("123456789");
        user.setWebsite("abc.org");
        user.setCompany(null);
        return user;
    }
}
