package Model;

public class Gamers extends Users{
    
    private int Money;
    private String AvatarLink;
    
    
    
    public Gamers(String id, String name, String gmail, String password, int role, Integer Money, String AvatarLink) {
        super(id, name, gmail, password, role);
        this.Money = (Money != null) ? Money : 0;
        this.AvatarLink = (AvatarLink != null && !AvatarLink.isEmpty()) ? AvatarLink : "https://i.pinimg.com/736x/bc/43/98/bc439871417621836a0eeea768d60944.jpg";
    }

    // Hàm khởi tạo mặc định, thiết lập giá trị mặc định
    public Gamers() {
        this.Money = 0;
        this.AvatarLink = "https://i.pinimg.com/736x/bc/43/98/bc439871417621836a0eeea768d60944.jpg";
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int Money) {
        this.Money = Money;
    }

    public String getAvatarLink() {
        return AvatarLink;
    }

    public void setAvatarLink(String AvatarLink) {
        this.AvatarLink = AvatarLink;
    }

    @Override
    public String toString() {
        return "Gamers{"+ super.toString() + "Money=" + Money + ", AvatarLink=" + AvatarLink + '}';
    }
    
    
}
