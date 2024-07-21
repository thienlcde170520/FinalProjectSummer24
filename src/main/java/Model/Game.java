package Model;

public class Game {
    private String id;
    private String name;
    private Double price;
    private String publishDay;
    private int numberOfBuyers;
    private String linkTrailer;
    private String avatarLink;
    private String gameLink;
    private String description;
    private String minimumCPU;
    private String minimumRAM;
    private String minimumGPU;
    private String maximumCPU;
    private String maximumRAM;
    private String maximumGPU;

   public Game(String id, String name, Double price, String publishDay, int numberOfBuyers, String linkTrailer,
            String avatarLink, String gameLink, String description, 
            String minimumCPU, String minimumRAM, String minimumGPU, 
            String maximumCPU, String maximumRAM, String maximumGPU) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.publishDay = publishDay;
    this.numberOfBuyers = numberOfBuyers;
    this.linkTrailer = linkTrailer;
    this.avatarLink = avatarLink;
    this.gameLink = gameLink;
    this.description = description;
    this.minimumCPU = minimumCPU;
    this.minimumRAM = minimumRAM;
    this.minimumGPU = minimumGPU;
    this.maximumCPU = maximumCPU;
    this.maximumRAM = maximumRAM;
    this.maximumGPU = maximumGPU;
}



    // Getters and Setters (omitted for brevity, but you should include them)
    // Remember to generate getters and setters for all fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPublishDay() {
        return publishDay;
    }

    public void setPublishDay(String publishDay) {
        this.publishDay = publishDay;
    }

    public int getNumberOfBuyers() {
        return numberOfBuyers;
    }

    public void setNumberOfBuyers(int numberOfBuyers) {
        this.numberOfBuyers = numberOfBuyers;
    }

    public String getLinkTrailer() {
        return linkTrailer;
    }

    public void setLinkTrailer(String linkTrailer) {
        this.linkTrailer = linkTrailer;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public String getGameLink() {
        return gameLink;
    }

    public void setGameLink(String gameLink) {
        this.gameLink = gameLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   
    public String getMinimumCPU() {
        return minimumCPU;
    }

    public void setMinimumCPU(String minimumCPU) {
        this.minimumCPU = minimumCPU;
    }

    public String getMinimumRAM() {
        return minimumRAM;
    }

    public void setMinimumRAM(String minimumRAM) {
        this.minimumRAM = minimumRAM;
    }

    public String getMinimumGPU() {
        return minimumGPU;
    }

    public void setMinimumGPU(String minimumGPU) {
        this.minimumGPU = minimumGPU;
    }

  

    public String getMaximumCPU() {
        return maximumCPU;
    }

    public void setMaximumCPU(String maximumCPU) {
        this.maximumCPU = maximumCPU;
    }

    public String getMaximumRAM() {
        return maximumRAM;
    }

    public void setMaximumRAM(String maximumRAM) {
        this.maximumRAM = maximumRAM;
    }

    public String getMaximumGPU() {
        return maximumGPU;
    }

    public void setMaximumGPU(String maximumGPU) {
        this.maximumGPU = maximumGPU;
    }

    @Override
    public String toString() {
        return "Game{" + "id=" + id + ", name=" + name + ", price=" + price + ", publishDay=" + publishDay + ", numberOfBuyers=" + numberOfBuyers + ", linkTrailer=" + linkTrailer + ", avatarLink=" + avatarLink + ", gameLink=" + gameLink + ", description=" + description + ", minimumCPU=" + minimumCPU + ", minimumRAM=" + minimumRAM + ", minimumGPU=" + minimumGPU + ", maximumCPU=" + maximumCPU + ", maximumRAM=" + maximumRAM + ", maximumGPU=" + maximumGPU + '}';
    }

  
}
