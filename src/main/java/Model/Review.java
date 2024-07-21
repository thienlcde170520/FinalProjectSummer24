/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LENOVO
 */
public class Review {
      private String idGamer;
    private String idGame;
    private Double rating;
    private String description;
public Review() {
    }
    public Review(String idGamer, String idGame, Double rating, String description) {
        this.idGamer = idGamer;
        this.idGame = idGame;
        this.rating = rating;
        this.description = description;
    }

    public String getIdGamer() {
        return idGamer;
    }

    public void setIdGamer(String idGamer) {
        this.idGamer = idGamer;
    }

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
