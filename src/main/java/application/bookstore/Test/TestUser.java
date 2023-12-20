package application.bookstore.Test;
import application.bookstore.models.Author;
import application.bookstore.models.Role;
import application.bookstore.models.User;
import org.testng.annotations.Test;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.*;

public class TestUser {

    @Test
    void test_UserSavedInFile() throws IOException, ClassNotFoundException {
        User.getUsers().clear();
        User firstUser = new User("admin", "admin", Role.ADMIN);
        User secondUser = new User("adm", "1", Role.LIBRARIAN);
        firstUser.saveInFile();
        secondUser.saveInFile();
        boolean isSaved = firstUser.saveInFile();
        assertTrue(isSaved);
        assertSame(firstUser, User.getUsers().get(0));

        ObjectInputStream is = new ObjectInputStream(new FileInputStream("data/users.ser"));
        User firstUserFromFile = (User) is.readObject();
        User secondUserFromFile = (User)is.readObject();

        assertEquals(firstUser.toString(), firstUserFromFile.toString());
        assertEquals(secondUser.toString(), secondUserFromFile.toString());
    }


    @Test
    void test_UserNotSavedInFile() {
        User.getUsers().clear();
        User user = new User("", "pass", Role.MANAGER);
        boolean isSaved = user.saveInFile();
        assertFalse(isSaved);
        assertEquals(User.getUsers().size(), 0);
    }

    @Test
    void test_UserIsDeletedFromFile(){
        User.getUsers().clear();
        User user = new User("admin", "admin");
        user.saveInFile();
        boolean isDeleted = user.deleteFromFile();
        assertTrue(isDeleted);
        assertFalse(User.getUsers().contains(user));
    }

    @Test
    void test_IsValidMethod(){
        User.getUsers().clear();
        User user = new User("destiny", "hero");
        User user2 = new User("", "");
        assertTrue(user.isValid());
        assertFalse(user2.isValid());
    }

    @Test
    void test_valid_update() {
        User.getUsers().clear();
        User user = new User("doom", "lord");
        user.saveInFile();
        user.setUsername("Goldenstatewarriors");
        user.setPassword("p");
        user.updateInFile();
        assertTrue(user.isValid());
        assertSame(User.getUsers().size(), 1);
    }
//
//    @Test
//    void test_SaveDuplicateUser() throws FileNotFoundException {
//        User.getUsers().clear();
//        new PrintWriter("data/users.ser").close();
//        User user = new User("Iceman", "TheArchitects");
//        user.saveInFile();
//
//        User sameCredentialsUser = new User("Iceman", "TheArchitects");
//        sameCredentialsUser.saveInFile();
//
//        assertTrue(sameCredentialsUser.exists());
//        assertEquals(User.getUsers().size(), 1);
//
//    }

    @Test

    void test_Equals(){
        User user = new User("admin", "admin");
        User user2 = new User("admin", "admin");

        boolean sameCredentials = user.equals(user2);
        assertTrue(sameCredentials);
    }

    @Test
    void test_getIfExists(){
        User user = new User("admin", "admin");
        User user2 = new User("admin2", "admin2");
        User user3 = new User("random", "random");
        User user4 = new User("admin4", "admin4");
        user.saveInFile();
        user2.saveInFile();
        user3.saveInFile();

        assertTrue(User.getUsers().contains(User.getIfExists(user3)));
        assertTrue(User.getUsers().contains(User.getIfExists(user)));
        assertNull(User.getIfExists(user4));
    }

    @Test
    void test_SearchUserByUsername(){
        User user = new User("admin", "admin");
        User user2 = new User("steph", "curry");
        User user3 = new User("darth", "vader");
        user.saveInFile();
        user2.saveInFile();
        user3.saveInFile();

        int nrOfUsersFound = User.getList("rth").stream().map(User::getUsername).toList().size();
        assertSame(nrOfUsersFound, 1);

        assertEquals(User.getList("adMiN").stream().map(User::getUsername).toList(), Stream.of("admin").collect(Collectors.toList()));
        assertEquals(User.getList("sTe").stream().map(User::getUsername).toList(), Stream.of("steph").collect(Collectors.toList()));
        assertEquals(User.getList("Art").stream().map(User::getUsername).toList(), Stream.of("darth").collect(Collectors.toList()));
        assertEquals(User.getList("Anakin").stream().map(User::getUsername).toList(), List.of());

    }



}
