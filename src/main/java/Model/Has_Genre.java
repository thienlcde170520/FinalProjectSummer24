/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class Has_Genre {
    private Game game;
    private Genre genre;

    public Has_Genre(Game game, Genre genre) {
        this.game = game;
        this.genre = genre;
    }

    // Getters and setters
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Has_Genre{" +
                "game=" + game.getId() +
                ", genre=" + genre.getType() +
                '}';
    }
}
