/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg305_project;

import java.util.Date;

public class HtmlFile {
    Date expirationDate;
    String pdfUrl; 

    public HtmlFile() {
    }

    public HtmlFile(Date expirationDate, String pdfUrl) {
        this.expirationDate = expirationDate;
        this.pdfUrl = pdfUrl;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
    
    
}
