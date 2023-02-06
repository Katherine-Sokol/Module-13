package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import http.userinfo.Todo;
import http.userinfo.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TodoUtil {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private TodoUtil() {
        throw new IllegalStateException();
    }

    public static List <Todo> getUncompletedTodos (User user) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + user.getId() + "/todos"))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        List<Todo> allTodos =  GSON.fromJson(response.body(), new TypeToken<List<Todo>>() {
        }.getType());
        List <Todo> uncompletedTodos = new ArrayList<>();
        for (Todo allTodo : allTodos) {
            if (!allTodo.isCompleted()) {
                uncompletedTodos.add(allTodo);
            }
        }
        return uncompletedTodos;
    }
}
