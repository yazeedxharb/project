/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg305_project;


public class Book {
   private String name;
   private int BookNo;
   private String Book_Link;
   private Book []hypothetical_database;

    public Book(String name, int BookNo) {
        this.name = name;
        this.BookNo = BookNo;
    }

    public Book() {
        Book [] books = new Book[4];
        books[0]= new Book("Alice's Adventures in Wonderland author Lewis Caroll",5412);
        books[1]= new Book("Macbeth",5614);
        books[2]= new Book("Dirt-Dealers",5735);
        books[3]= new Book("Billionaire-Boss-Protector",5489);
        
        
        
        hypothetical_database = books;
        
    }

    public Book(String Book_Link) {
        this.Book_Link = Book_Link;
    }

    public Book[] getHypothetical_database() {
        return hypothetical_database;
    }

    public void setHypothetical_database(Book[] hypothetical_database) {
        this.hypothetical_database = hypothetical_database;
    }

   
   
   
   
   
   
   
   
   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBookNo() {
        return BookNo;
    }

    public void setBookNo(int BookNo) {
        this.BookNo = BookNo;
    }

    public String getBook_Link() {
        return Book_Link;
    }

    public void setBook_Link(String Book_Link) {
        this.Book_Link = Book_Link;
    }
   
   public void viewBooksInfo(){
       for (int i = 0; i < hypothetical_database.length; i++) {
           System.out.println("Book name: "+ hypothetical_database[i].getName());
           System.out.println("Book Number: "+ hypothetical_database[i].getBookNo()+"\n");
       }
   
   }
   
    
}
