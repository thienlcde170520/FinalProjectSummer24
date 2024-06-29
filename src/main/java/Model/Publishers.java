/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;
import java.util.Random;

/**
 *
 * @author ASUS
 */
public class Publishers extends Users{

    private String bank_account;
    private Double profit;
    private String description;
    private String AvatarLink;
    private Double Money;
    private String RegistrationDate;

    public Publishers(String id, String name, String gmail, String password, String bank_account, Double profit, String description, String AvatarLink, Double Money, int role, String RegistrationDate) {
        super(id, name, gmail, password, role);
        this.bank_account = bank_account;
        this.profit = profit;
        this.description = description;
        this.Money = (Money != null) ? Money : 0;
        this.AvatarLink = (AvatarLink != null && !AvatarLink.isEmpty()) ? AvatarLink : "https://i.pinimg.com/736x/bc/43/98/bc439871417621836a0eeea768d60944.jpg";
    
        this.RegistrationDate = RegistrationDate;
    }
    
    public Publishers(){
        //super(generateRandomId(), "", "", "", 0);
        this.bank_account = " ";
        this.profit = 0.0;
        this.description = " ";
      
        this.Money = 0.0;
        this.AvatarLink = "https://i.pinimg.com/736x/bc/43/98/bc439871417621836a0eeea768d60944.jpg";

    }
    /*
    private static String generateRandomId() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000); // Số ngẫu nhiên từ 0 đến 9999
        return "pub_" + randomNumber;
    }
    */
    
    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarLink() {
        return AvatarLink;
    }

    public void setAvatarLink(String AvatarLink) {
        this.AvatarLink = AvatarLink;
    }

    public Double getMoney() {
        return Money;
    }

    public void setMoney(Double Money) {
        this.Money = Money;
    }

    public String getRegistrationDate() {
        return RegistrationDate;
    }

    public void setRegistrationDate(String RegistrationDate) {
        this.RegistrationDate = RegistrationDate;
    }

    @Override
    public String toString() {
        return "Publishers{" + super.toString() + "bank_account=" + bank_account + ", profit=" + profit + ", description=" + description + ", AvatarLink=" + AvatarLink + ", Money=" + Money + ", RegistrationDate=" + RegistrationDate + '}';
    }
    
    
    
    
}
