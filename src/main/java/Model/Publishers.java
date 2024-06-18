/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class Publishers extends Users{

    private String bank_account;
    private int profit;
    private String description;
    private String AvatarLink;
    private int Money;
    private String RegistrationDate;

    public Publishers(String id, String name, String gmail, String password, String bank_account, int profit, String description, String AvatarLink, int Money, int role, String RegistrationDate) {
        super(id, name, gmail, password, role);
        this.bank_account = bank_account;
        this.profit = profit;
        this.description = description;
        this.AvatarLink = AvatarLink;
        this.Money = Money;
        this.RegistrationDate = RegistrationDate;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
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

    public int getMoney() {
        return Money;
    }

    public void setMoney(int Money) {
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
