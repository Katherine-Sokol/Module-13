package http;

import http.userinfo.Comment;
import http.userinfo.Todo;
import http.userinfo.User;

import java.io.IOException;
import java.util.List;

public class HTTPTests {

    public static void main(String[] args) throws IOException, InterruptedException {
//        Task 1.1
        User user = UserUtil.createDefaultUser();
        User createdUser = UserUtil.createUser(user);
        System.out.println("Task 1.1 CreatedUser = " + createdUser);

//        Task 1.2
        User user2 = UserUtil.getUserById(5);
        user2.setUsername("newName");
        User updatedUser = UserUtil.updateUser(user2);
        System.out.println("Task 1.2 UpdatedUser = " + updatedUser);

//        Task 1.3
        boolean userIsDeleted = UserUtil.deleteUser(1);
        System.out.println("Task 1.3 UserIsDeleted = " + userIsDeleted);

//        Task 1.4
        List<User> users = UserUtil.getAllUsers();
        System.out.println("Task 1.4 AllUsers = " + users);

//        task 1.5
        User userById = UserUtil.getUserById(2);
        System.out.println("Task 1.5 GetUserById = " + userById);

//        task 1.6
        List<User> usersByUsername = UserUtil.getUsersByUsername("Kamren");
        System.out.println("Task 1.6 UsersByUsername = " + usersByUsername);

//        Task 2
        List<Comment> comments = PostUtil.getAndSaveCommentsToFile(userById);
        System.out.println("Task 2 CommentsYo save = " + comments);

//        Task 3
        List<Todo> todos = TodoUtil.getUncompletedTodos(userById);
        System.out.println("Task 3 UncompletedTodos = " + todos);
    }

}
