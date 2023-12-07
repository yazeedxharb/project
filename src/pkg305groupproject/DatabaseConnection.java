/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg305groupproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;
/**
 *
 * @author zezox
 */
public class DatabaseConnection {

    private String username = "yaz";
    private String password = "123";
    private static final String DB_URL = "jdbc:derby://localhost:1527/library";

    public DatabaseConnection() {
        initialize();
    }

    private void initialize() {
        try (Connection conn = DriverManager.getConnection(DB_URL, username, password); Statement stmt = conn.createStatement()) {
            String sql = "";
//String dro = "DROP TABLE " + "book";
//stmt.execute(dro);
//             sql = "CREATE TABLE author ("
//                    + "author_id INTEGER PRIMARY KEY,"
//                    + "name VARCHAR(255) NOT NULL,"
//                    + "bio VARCHAR(255) NOT NULL )";
//            stmt.execute(sql);

//            sql = "CREATE TABLE book ("
//                    + "book_id INTEGER PRIMARY KEY,"
//                    + "title VARCHAR(255) NOT NULL,"
//                    + "author_name VARCHAR(255) NOT NULL,"
//                    + "author_id INTEGER,"
//                    + "FOREIGN KEY (author_id) REFERENCES author(author_id),"
//                    + "isbn VARCHAR(255) NOT NULL,"
//                    + "publication_year DATE NOT NULL,"
//                    + "years DATE NOT NULL )";
//            stmt.execute(sql);
//            sql = "CREATE TABLE Member ("
//                    + "member_id INTEGER PRIMARY KEY,"
//                    + "name VARCHAR(255) NOT NULL,"
//                    + "email VARCHAR(255) NOT NULL"
//                    + ")";
//            stmt.execute(sql);
//            sql = "CREATE TABLE loan ("
//                    + "loan_id INTEGER PRIMARY KEY,"
//                    + "member_id INTEGER NOT NULL,"
//                    + "FOREIGN KEY (member_id) REFERENCES Member(member_id),"
//                    + "book_id INTEGER NOT NULL,"
//                    + "FOREIGN KEY (book_id) REFERENCES book(book_id),"
//                    + "loan_Date DATE NOT NULL,"
//                    + "return_Date DATE NOT NULL"
//                    + ")";
//            stmt.execute(sql);
//            sql = "CREATE TABLE users ("
//                    + "username varchar(225) PRIMARY KEY,"
//                    + "password varchar(225) NOT NULL)";
//            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addAuthor() {
        int id;
        String name;
        String bio;
        Scanner writer = new Scanner(System.in);
        String sql = "INSERT INTO author(author_id,name, bio) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, username, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.println("Enter you id; ");
            id = writer.nextInt();
            pstmt.setInt(1, id);
            System.out.println("Enter your name: ");
            name = writer.next();
            pstmt.setString(2, name);
            System.out.println("Enter your bio: ");
            bio = writer.next();
            pstmt.setString(3, bio);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addBook() throws ParseException {
        int bookId;
        String title;
        String authorName;
        int aouthorId;
        String isbn;
        Date publicationYear = null;
        Date Year = null;
        Scanner writer = new Scanner(System.in);
        String sql = "INSERT INTO book(book_id, title, author_name, author_id ,isbn, publication_year, years) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, username, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.println("Enter the Publication year of book: ");
            String dobString = writer.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = dateFormat.parse(dobString);

            System.out.println("Enter the year of book: ");
            String dobtring = writer.nextLine();
            SimpleDateFormat ateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date ob = dateFormat.parse(dobString);
//--------------------------------------
            System.out.println("Enter the id book: ");
            bookId = writer.nextInt();
            pstmt.setInt(1, bookId);
            //--------------------------------------
            System.out.println("Enter the title of book: ");
            title = writer.next();
            pstmt.setString(2, title);
            //--------------------------------------
            System.out.println("Enter the author name: ");
            authorName = writer.next();
            pstmt.setString(3, authorName);
            //--------------------------------------
            System.out.println("Enter the author id: ");
            aouthorId = writer.nextInt();
            pstmt.setInt(4, aouthorId);
            //--------------------------------------
            System.out.println("Enter the isbn: ");
            isbn = writer.next();
            pstmt.setString(5, isbn);
            //--------------------------------------
            System.out.println("Enter the Publication year of book: ");
//            String dobString = writer.nextLine();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            Date dob = dateFormat.parse(dobString);
            pstmt.setDate(6, new java.sql.Date(dob.getTime()));
            //--------------------------------------
//            System.out.println("Enter the year of book: ");
//            dobString = writer.nextLine();
//            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            dob = dateFormat.parse(dobString);
            pstmt.setDate(7, new java.sql.Date(ob.getTime()));
            //--------------------------------------
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getAllBooks() {
        String sql = "SELECT * FROM author";

        try (Connection conn = DriverManager.getConnection(DB_URL, username, password); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("author_id") + "\t"
                        + rs.getString("name") + "\t"
                        + rs.getString("bio") + "\t"
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean login(String usernam, String passwor) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, username, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usernam);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getString("password").equals(passwor)) {
                return true;
            } else {
                System.out.println("Wrong username or password!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void signUp(String usernam, String passwor) {
        String sql = "INSERT INTO users(username, password) VALUES(?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, username, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usernam);
            pstmt.setString(2, passwor);
            pstmt.executeUpdate();
            System.out.println("User registered successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

}
