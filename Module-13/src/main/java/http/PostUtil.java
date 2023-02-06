package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import http.userinfo.Comment;
import http.userinfo.Post;
import http.userinfo.User;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PostUtil {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private PostUtil() {
        throw new IllegalStateException();
    }

    public static List<Comment> getAndSaveCommentsToFile(User user) throws IOException, InterruptedException {
        PostUtil postUtil = new PostUtil();
        List <Post> allPosts =  postUtil.getAllPostsOfCertainUser(user);
        Post lastPost = postUtil.getLastPostOfCertainUser(allPosts);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/" + lastPost.getId() + "/comments"))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        List <Comment> allComments = GSON.fromJson(response.body(), new TypeToken<List<Comment>>() {
        }.getType());
        try(
                FileWriter fileWriter = new FileWriter(String.format("user-%d-post-%d-comments.json", user.getId(), lastPost.getId()))
        ){
            GSON.toJson(allComments, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allComments;
    }

    private List<Post> getAllPostsOfCertainUser(User user) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + user.getId() + "/posts"))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), new TypeToken<List<Post>>() {
        }.getType());
    }

    private Post getLastPostOfCertainUser(List<Post> posts) {
        int maxPostId = 0;
        Post lastPost = new Post();
        for (Post post : posts) {
            if (post.getId() > maxPostId) {
                maxPostId = post.getId();
                lastPost = post;
            }
        }
        return lastPost;
    }
}
