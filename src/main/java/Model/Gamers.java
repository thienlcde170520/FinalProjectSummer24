package Model;

import java.util.Random;

public class Gamers extends Users{
    
    private Double Money;
    private String AvatarLink;
    private String RegistrationDate;
    private String DOB;
    

    
    public Gamers(String id, String name, String gmail, String password, int role, Double Money, String AvatarLink, String RegistrationDate, String DOB) {

        super(id, name, gmail, password, role);
        //if Money = null => this.Money = 0;
        this.Money = (Money != null) ? Money : 0;
        this.AvatarLink = (AvatarLink != null && !AvatarLink.isEmpty()) ? AvatarLink : "https://i.pinimg.com/736x/bc/43/98/bc439871417621836a0eeea768d60944.jpg";
        this.RegistrationDate = RegistrationDate;
        this.DOB = (DOB != null) ? DOB : "";
    }

    // Hàm khởi tạo mặc định, thiết lập giá trị mặc định
    public Gamers() {
        this.Money = 0.0;
        this.AvatarLink = "https://i.pinimg.com/736x/bc/43/98/bc439871417621836a0eeea768d60944.jpg";
        this.DOB = "";
    }
    

    public Double getMoney() {
        return Money;
    }

    public void setMoney(Double Money) {
        this.Money = Money;
    }

    public String getAvatarLink() {
        return AvatarLink;
    }

    public void setAvatarLink(String AvatarLink) {
        this.AvatarLink = AvatarLink;
    }

    public String getRegistrationDate() {
        return RegistrationDate;
    }

    public void setRegistrationDate(String RegistrationDate) {
        this.RegistrationDate = RegistrationDate;
    }
  public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }



    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
    
    

    @Override
    public String toString() {
        return "Gamers{" + super.toString()+ "Money=" + Money + ", AvatarLink=" + AvatarLink + ", RegistrationDate=" + RegistrationDate + ", Date of Birth=" + DOB + '}';
    }
    
    
    
}
