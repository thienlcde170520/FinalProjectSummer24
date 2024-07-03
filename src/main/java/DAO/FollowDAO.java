/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static Controller.JavaMongo.getConnectionLocal;
import Model.Follow;
import Model.Game;
import Model.Gamers;
import Model.Publishers;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author LENOVO
 */
public class FollowDAO {
        public static boolean isFollow(Gamers gamer, Game game) {
        MongoClientSettings settings = getConnectionLocal();
        boolean isFollowed = false;

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
                MongoCollection<Document> followCollection = fpteamDB.getCollection("Follow");

                Bson filter = Filters.and(
                        Filters.eq("ID_Gamer", gamer.getId()),
                        Filters.eq("ID_Game", game.getId())
                );

                long count = followCollection.countDocuments(filter);
                isFollowed = count > 0;
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }

        return isFollowed;
    }
      public static ArrayList<Follow> getAllFollowsByGamerId(String id) {
        MongoClientSettings settings = getConnectionLocal();
        ArrayList<Follow> follows = new ArrayList<>();
        
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
                MongoCollection<Document> followCollection = fpteamDB.getCollection("Follow");

                for (Document doc : followCollection.find(Filters.eq("ID_Gamer", id))) {
                    Follow follow = new Follow(
                            doc.getString("ID_Gamer"),
                            doc.getString("ID_Game"),
                            doc.getDouble("Follow_price"),
                            doc.getString("Follow_time")
                    );
                    follows.add(follow);
                }
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }

        return follows;
    }
      public static void followGame(Game game, Gamers gamer) {
        MongoClientSettings settings = getConnectionLocal();

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedNow = now.format(formatter);

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
                MongoCollection<Document> followCollection = fpteamDB.getCollection("Follow");

                Document followDoc = new Document("ID_Gamer", gamer.getId())
                        .append("ID_Game", game.getId())
                        .append("Follow_price", game.getPrice())
                        .append("Follow_time", formattedNow); // Use the current time

                followCollection.insertOne(followDoc);
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }

    public static void unfollowGame(Game game, Gamers gamer) {
    MongoClientSettings settings = getConnectionLocal();

    try (MongoClient mongoClient = MongoClients.create(settings)) {
        try {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> followCollection = fpteamDB.getCollection("Follow");

            Bson filter = Filters.and(
                    Filters.eq("ID_Gamer", gamer.getId()),
                    Filters.eq("ID_Game", game.getId())
            );

            followCollection.deleteOne(filter);  // Remove the follow record
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}

}
