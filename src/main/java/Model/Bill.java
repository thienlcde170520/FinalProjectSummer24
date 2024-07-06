package Model;

public class Bill {
    private String id;
    private String gamerId;
    private String gameId;
    private String buyTime;
    private Double buyPrice;
public Bill(String id, String gamerId, String gameId, String buyTime, Double buyPrice) {
        this.id = id;
        this.gamerId = gamerId;
        this.gameId = gameId;
        this.buyTime = buyTime;
        this.buyPrice = buyPrice;
    }
public Bill() {
      
    }


    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGamerId() {
        return gamerId;
    }

    public void setGamerId(String gamerId) {
        this.gamerId = gamerId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }
}
