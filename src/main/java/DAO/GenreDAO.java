/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static Controller.JavaMongo.getConnection;
import static Controller.JavaMongo.getConnectionLocal;
import Model.Genre;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author LENOVO
 */
public class GenreDAO {
 

public static void exclusiveGenreToGame(String gameId, String genreType) {
    try (MongoClient mongoClient = MongoClients.create(getConnectionLocal())) {
        MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
        MongoCollection<Document> gameGenresCollection = fpteamDB.getCollection("Game_Has_Genre");

        // Define the filter to find the document with the specified gameId and genreType
        Document filter = new Document()
                .append("ID_Game", gameId)
                .append("Type_of_genres", genreType);

        // Delete the document matching the filter
     gameGenresCollection.deleteMany(filter);

      
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public static void addGenreToGame(String gameId, String genreType) {
        try (MongoClient mongoClient = MongoClients.create(getConnectionLocal())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> gameGenresCollection = fpteamDB.getCollection("Game_Has_Genre");

            Document gameGenreDoc = new Document()
                    .append("ID_Game", gameId)
                    .append("Type_of_genres", genreType);

            gameGenresCollection.insertOne(gameGenreDoc);
            System.out.println("Genre '" + genreType + "' added to game ID: " + gameId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    public static ArrayList<Genre> getExcludeGenresByGameId(String gameID) {
        ArrayList<Genre> allGenres = getAllGenres();
        ArrayList<String> gameGenres = getGenreTypesByGameID(gameID);
        ArrayList<Genre> excludeGenres = new ArrayList<>();

        // Iterate through all genres and add those not associated with the game
        for (Genre genre : allGenres) {
            if (!gameGenres.contains(genre.getType())) {
                excludeGenres.add(genre);
            }
        }

        return excludeGenres;
    }

    public static ArrayList<Genre> getAllGenres() {
        MongoClientSettings settings = getConnectionLocal();
        ArrayList<Genre> genresList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> Collection = fpteamDB.getCollection("Genres");

            MongoCursor<Document> cursor = Collection.find().iterator();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Genre genre = new Genre(
                        doc.getString("Type_of_Genre"),
                        doc.getString("Description")
                );
                genresList.add(genre);
            }
            cursor.close();
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return genresList;
    }
    public static Genre getGenreByType(String genreType) {
    MongoClientSettings settings = getConnectionLocal();
    Genre genre = null;

    try (MongoClient mongoClient = MongoClients.create(settings)) {
        MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
        MongoCollection<Document> collection = fpteamDB.getCollection("Genres");

        // Create a filter to find the genre by its type
        Bson filter = Filters.eq("Type_of_Genre", genreType);
        Document doc = collection.find(filter).first();

        // Check if the document is found
        if (doc != null) {
            genre = new Genre(
                doc.getString("Type_of_Genre"),
                doc.getString("Description")
            );
        }
    } catch (MongoException e) {
        e.printStackTrace();
    }

    return genre;
}


    private static ArrayList<String> getGenreTypesByGameID(String gameID) {
        MongoClientSettings settings = getConnectionLocal();
        ArrayList<String> genreTypes = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Access the "GameGenres" collection (assuming this collection maps games to genres)
            MongoCollection<Document> gameGenresCollection = fpteamDB.getCollection("Game_Has_Genre");

            // Create a filter to search for the game by ID_Game
            Bson filter = Filters.eq("ID_Game", gameID);

            // Find all documents that match the filter
            for (Document gameGenreDoc : gameGenresCollection.find(filter)) {
                if (gameGenreDoc != null) {
                    // Extract the Type_of_genre attribute from the document
                    String genreType = gameGenreDoc.getString("Type_of_genres");
                    if (genreType != null) {
                        genreTypes.add(genreType);
                    }
                }
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return genreTypes;
    }

    // Method to get genre details by genre type
    private static Genre getGenreDetailsByType(String genreType) {
        MongoClientSettings settings = getConnectionLocal();
        Genre genre = null;
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Access the "Genres" collection
            MongoCollection<Document> genresCollection = fpteamDB.getCollection("Genres");

            // Create a filter to search for the genre by Type_of_Genre
            Bson filter = Filters.eq("Type_of_Genre", genreType);

            // Find the first document that matches the filter
            Document genreDoc = genresCollection.find(filter).first();

            if (genreDoc != null) {
                // Extract genre attributes from the document
                String type = genreDoc.getString("Type_of_Genre");
                String description = genreDoc.getString("Description");

                // Create a Genre object
                genre = new Genre(type, description);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return genre;
    }

    // Method to get genres by game ID
    public static ArrayList<Genre> getGenresByGameID(String gameID) {
        ArrayList<Genre> genres = new ArrayList<>();
        ArrayList<String> genreTypes = getGenreTypesByGameID(gameID);
        for (String genreType : genreTypes) {
            Genre genre = getGenreDetailsByType(genreType);
            if (genre != null) {
                genres.add(genre);
            }
        }
        return genres;
    }

}
