/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg305_project;

/**
 *
 * @author zezox
 */
import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Book books = new Book();
//        System.out.println(ClientHandler.RentedBook(1, 3));
        ClientHandler.Commit();
        
        
          try {
              
              DatabaseConnection db = new DatabaseConnection();
            System.out.println("Enter ID: ");
            Scanner user_input = new Scanner(System.in);
            int ID = user_input.nextInt();
            System.out.println("Enter Passowrd:");
            String password = user_input.next();
            if (Authentication(ID, password)) {
                try {
                    
                } catch (Exception e) {
                }
                System.out.println("What would you like to choose?, Buy an e-book(1), Rent e-book(2)");
                System.out.println("choose the number that's the next to the thing you want");
                
                try {
                    int number = user_input.nextInt();
                if (number == 1) {
                    System.out.println("the list of books that are available to buy below: " + "\n");
                    books.viewBooksInfo();

                    System.out.println("Enter the book number you want to buy: ");
                    int book_number = user_input.nextInt();
                   
                    Book book = findBook(books, book_number);
                    String book_name = book.getName();

                    String serverAddress = "localhost";
                    int port = 9324;

                    try (
                             Socket socket = new Socket(serverAddress, port);  
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  
                            BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
                            // Create a FileOutputStream to save the downloaded book to the spceific path written below
                              FileOutputStream fileOut = new FileOutputStream("C:\\Users\\starx\\Desktop\\CPIT305\\Downloaded books\\" + book_name + ".pdf")) {

                        String bookName = book_name;

                        //Send the book name to the server
                        out.println(bookName);

                        // Create a buffer for reading data
                        byte[] buffer = new byte[2097152];
                        int count;

                        // Read data from the server and write it to the file
                        while ((count = in.read(buffer)) > 0) {
                            fileOut.write(buffer, 0, count);
                            

                        }

                        System.out.println("Thanks for buying the book and the book downloaded successfully.");
                    } catch (IOException e) {
                        System.err.println("Error: " + e.getMessage());
                    }

                }

                    
                } catch (InputMismatchException e) {
                    System.out.println("you entered in the wrong format");
                }
                
            }

        } catch (InputMismatchException e) {

            System.out.println("you entered in the wrong format");
        }
        
    }
        
    
    private static boolean Authentication(int ID, String password) {
        return true;
    }

    private static Book findBook(Book books, int book_number) {
        for (int i = 0; i < books.getHypothetical_database().length; i++) {
            if (books.getHypothetical_database()[i].getBookNo() == book_number) {
                return books.getHypothetical_database()[i];
            }
        }

        return null;
    }

}