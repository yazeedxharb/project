/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg305_project;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
public class ClientHandler implements Runnable{
    /*
    this class will be used to create threads objects
    this is thread that will handle any client.
    the run method is the code that we will execute to handle the client.
    */
    Socket Client;

    public ClientHandler() {
    }

    public ClientHandler(Socket Client) {
        this.Client = Client;
    }

    public Socket getClient() {
        return Client;
    }

    public void setClient(Socket Client) {
        this.Client = Client;
    }
    
    
    @Override
    public void run() {
        
        // Specify the absolute path to your books directory here
        String booksDirectory = "C:\\Users\\starx\\Desktop\\CPIT305\\copresseed books\\";

  
            try (
                 BufferedReader in = new BufferedReader(new InputStreamReader(Client.getInputStream()));
                 OutputStream out = Client.getOutputStream()) {
                
                // Read the book name from the client
                String bookName = in.readLine();
               // Print the requested book name to the console
                System.out.println("Client requested: " + bookName);

                // Use the absolute path to locate the PDF file
                File pdfFile = new File(booksDirectory + bookName + ".pdf");
                if (pdfFile.exists()) {
                    // Create a buffer for reading and writing data
                    byte[] buffer = new byte[2097152]; //2MB buffer
                    // Open a FileInputStream for the file
                    try (FileInputStream fis = new FileInputStream(pdfFile)) {
                        int count;
                        // Read data from the file and write it to the client
                        while ((count = fis.read(buffer)) > 0) {
                            out.write(buffer, 0, count);
                        }
                    }
                } else {
                    out.write("Book not found".getBytes());
                }
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                
            }
            
            
     
        
        
        
        
    }
    
    
    
    
    public static synchronized boolean Commit(){
        /* this method will do the following
        commit chagnes to the local repo then push to git repo
        */
        

        //here I will make a process that executes the script 
        
        //here put the path to the script
        ProcessBuilder myProcessBuilder = new ProcessBuilder("cmd.exe", "/c", "C:\\Users\\zezox\\Documents\\GitHub\\305Project\\Git_Commit_push.bat");
        Map<String, String> environment = myProcessBuilder.environment();
        String path = environment.get("PATH");
        String gitPath = "C:\\Program Files\\Git\\bin";
        String updatedPath = gitPath + ";" + path;
        environment.put("PATH", updatedPath);
        myProcessBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        myProcessBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        try {
            myProcessBuilder.directory(new File("C:\\Users\\zezox\\Documents\\GitHub\\305Project"));
            Process process = myProcessBuilder.start();
             // Capture the output and error streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Print the output
            }
            
            int exitcode = process.waitFor();
            boolean result;
            result = (exitcode == 0);
            return result;
            //if true is returned the the process executed successfully
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.toString());
            //if we reach here this means that an exception have occured 
            //so we print the execption and return false to indicate that the operation failed.
            return false;
        }
        
        

        //here I will make a process that executes the script gg

    }
    public static synchronized boolean Pull(){
        /* this method will do the following
        pull the repo from github to ensure we have latest version
        */
        //here put the path to the script
        ProcessBuilder myProcessBuilder = new ProcessBuilder("cmd.exe", "/c", "C:\\Users\\96657\\Documents\\GitHub\\305Project\\Git_pull.bat");
        Map<String, String> environment = myProcessBuilder.environment();
        String path = environment.get("PATH");
        String gitPath = "C:\\Program Files\\Git\\bin";
        String updatedPath = gitPath + ";" + path;
        environment.put("PATH", updatedPath);
        myProcessBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        myProcessBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        try {
            myProcessBuilder.directory(new File("C:\\Users\\96657\\Documents\\GitHub\\305Project"));
            Process process = myProcessBuilder.start();
             // Capture the output and error streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Print the output
            }
            
            int exitcode = process.waitFor();
            boolean result;
            result = (exitcode == 0);
            return result;
            //if true is returned the the process executed successfully
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.toString());
            //if we reach here this means that an exception have occured 
            //so we print the execption and return false to indicate that the operation failed.
            return false;
        }
}
    public static String RentedBook(int BookNumber, int Months){
        File htmlFile = new File("C:\\Users\\zezox\\Documents\\GitHub\\305Project\\BestVersionWithdeletion.html");
        String BookURL = BookURLGetter(BookNumber);
        String Expirydate = ExpiryDateCreater(Months);
        String HtmlURl = "";
        try {
            Document doc = Jsoup.parse(htmlFile, "UTF-8");
            Element javascript = doc.selectFirst("script:containsData(pdfUrl)");
            
            
            /*
            here we when we used the jsoup.parse , we got a document object , that contains all the html script
            then from that document object , we want a specific thing , which is the java script portion
            thats why we uses a css selecter which is used to get certain elements , in this case we want 
            a script , and we want that script to contain 'pdfurl'
            doc.selectFirst("script:containsData(pdfUrl)") selects the first <script> element in the document 
            that contains the text "pdfUrl" within its content.
            */
            if (javascript!= null){
                // Get the JavaScript code as a string
                String scriptCode = javascript.html();
                // Use regular expressions to find and replace the PDF URL value
                String reg_url = "(const\\s+pdfUrl\\s*=\\s*').*(';)";
                String modifiedScriptCode_1 = scriptCode.replaceAll(reg_url,"$1" + BookURL + "$2");
                //now we replaced the url value
                String reg_pdf = "(const\\s+expirationDate\\s*=\\s*new\\s+Date\\(').*('\\);)";
                String modifiedScriptCode_2 = modifiedScriptCode_1.replaceAll(reg_pdf, "$1" + Expirydate + "$2");
                //now we replaced the expiry date value
                
                javascript.html(modifiedScriptCode_2);
                // Update the JavaScript code in the document  
            }
            //after we exit from the if statment , that means we have finished manipulation the doc object
            //now we just need to write it into a new html file
            //the file name should change depending on values from the database 
            String htmlFilename = "UserID" + "BookNumber";
            
            
            ChangeGitrepo(doc, htmlFilename);
            
            HtmlURl = "https://305project-git-main-aoighosts-projects.vercel.app/Webpages/" + htmlFilename + ".html";
            
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        return HtmlURl;
    }

    public static void writeHTMLFile(Document doc, String outputPath) {
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
                ) 
        {
            writer.write(doc.html());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String BookURLGetter(int BookNumber){
        String AllBooks = "https://305project-git-main-aoighosts-projects.vercel.app/OnlineLibrary/Books";
        //URL of a directory online containing all books
        String SpecificBook = AllBooks;
        SpecificBook += "/" + BookNumber + ".pdf";
        
        //here we made a url that will lead us to a specific book
        return SpecificBook;
    }
    
    public static String ExpiryDateCreater(int Months){
        //this method will return the expiry date 
        //based on how many Months does the client want to rent the book for.
        
        LocalDateTime currentDate = LocalDateTime.now();
        //this is the current date, I used LocalDateTime instead of date because it has Cool methods
        
        LocalDateTime expiryDateTime = currentDate.plusMonths(Months);
        //this is one of the cool methods , which allows us to add months to the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        //LocalDateTime CurrentdateTime 
       
        String Expirydate = expiryDateTime.format(formatter);
        Expirydate+= "Z";
        
        return Expirydate;
    }
    
    public synchronized static void ChangeGitrepo(Document doc, String Filename){
        Pull();
        writeHTMLFile(doc, "C:\\Users\\96657\\Documents\\GitHub\\305Project\\Webpages\\" + Filename + ".html");
        Commit();
    }
}
    
