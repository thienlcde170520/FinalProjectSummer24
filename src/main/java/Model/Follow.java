/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class Follow {
    private String idGamer;
    private String idGame;
    private double followPrice;
    private String followTime;

    public Follow(String idGamer, String idGame, double followPrice, String followTime) {
        this.idGamer = idGamer;
        this.idGame = idGame;
        this.followPrice = followPrice;
        this.followTime = followTime;
    }

    // Getters and setters
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

    public double getFollowPrice() {
        return followPrice;
    }

    public void setFollowPrice(double followPrice) {
        this.followPrice = followPrice;
    }

    public String getFollowTime() {
        return followTime;
    }

    public void setFollowTime(String followTime) {
        this.followTime = followTime;
    }
}
