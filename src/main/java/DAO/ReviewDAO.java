/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static Controller.JavaMongo.getConnection;
import static Controller.JavaMongo.getConnectionLocal;
import Model.Game;
import Model.Gamers;

import Model.Publishers;
import Model.Review;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author LENOVO
 */
public class ReviewDAO {
      public static ArrayList<Review> getReviewByGame(Game game) {
        ArrayList<Review> reviews = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(getConnectionLocal())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> collection = fpteamDB.getCollection("Reviews");

            MongoCursor<Document> cursor = collection.find(new Document("ID_Game", game.getId())).iterator();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Review review = new Review(
                        doc.getString("ID_Gamer"),
                        doc.getString("ID_Game"),
                        doc.getDouble("Rating"),
                        doc.getString("Description")
                );
                reviews.add(review);
            }
            cursor.close();
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return reviews;
    }
  public static ArrayList<Review> getReviewsByPublisherName(String publisherName) {
        MongoClientSettings settings = getConnectionLocal();
        ArrayList<Review> reviews = new ArrayList<>();
        
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Access the necessary collections
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");
            MongoCollection<Document> publishCollection = fpteamDB.getCollection("Publish");
            MongoCollection<Document> gamePublishersCollection = fpteamDB.getCollection("GamePublishers");
            MongoCollection<Document> reviewsCollection = fpteamDB.getCollection("Reviews");

            // Find the publisher document based on the provided publisher name
            Document publisherDoc = gamePublishersCollection.find(Filters.regex("Name", ".*" + publisherName + ".*", "i")).first();

            if (publisherDoc != null) {
                String publisherId = publisherDoc.getString("ID");

                // Create a filter to search for game documents with the matching publisher ID in the Publish collection
                Bson filter = Filters.eq("ID_Game_Publisher", publisherId);

                // Find all documents in the Publish collection that match the filter
                FindIterable<Document> publishDocs = publishCollection.find(filter);

                for (Document publishDoc : publishDocs) {
                    String gameId = publishDoc.getString("ID_Game");

                    // Create a filter to find the review documents in the Reviews collection based on gameId
                    Bson reviewFilter = Filters.eq("ID_Game", gameId);

                    // Find all reviews associated with the gameId
                    FindIterable<Document> reviewDocs = reviewsCollection.find(reviewFilter);

                    for (Document reviewDoc : reviewDocs) {
                        Review review = new Review(
                                reviewDoc.getString("ID_Gamer"),
                                reviewDoc.getString("ID_Game"),
                                reviewDoc.getDouble("Rating"),
                                reviewDoc.getString("Description")
                        );
                        reviews.add(review);
                    }
                }
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return reviews;
    }

      
    public static void deleteReview(String gamerId, String gameId) {
        try (MongoClient mongoClient = MongoClients.create(getConnectionLocal())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> collection = fpteamDB.getCollection("Reviews");

            // Create the filter to find the review to delete
            Bson filter = Filters.and(
                    Filters.eq("ID_Gamer", gamerId),
                    Filters.eq("ID_Game", gameId)
            );

            // Print debug information
            System.out.println("Attempting to delete review with gamerId: " + gamerId + ", gameId: " + gameId);

            // Delete the review document from the Reviews collection
            DeleteResult result = collection.deleteMany(filter);

            // Check if deletion was successful
            if (result.getDeletedCount() > 0) {
                System.out.println("Review deleted successfully for gamerId: " + gamerId + ", gameId: " + gameId);
            } else {
                System.out.println("Review not found or already deleted for gamerId: " + gamerId + ", gameId: " + gameId);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public static void addReview(String gamerId, String gameId, double rating, String description) {
        try (MongoClient mongoClient = MongoClients.create(getConnectionLocal())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> collection = fpteamDB.getCollection("Reviews");

            // Create a new review document
            Document reviewDoc = new Document("ID_Gamer", gamerId)
                    .append("ID_Game", gameId)
                    .append("Rating", rating)
                    .append("Description", description);

            // Insert the review document into the Reviews collection
            collection.insertOne(reviewDoc);

            System.out.println("Review added successfully: " + reviewDoc.toJson());
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    
    public static double getAverageRatingByGame(Game game) {
        double averageRating = 0.0;

        try (MongoClient mongoClient = MongoClients.create(getConnectionLocal())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> collection = fpteamDB.getCollection("Reviews");

            List<Document> pipeline = Arrays.asList(
                    new Document("$match", new Document("ID_Game", game.getId())),
                    new Document("$group", new Document("_id", "$ID_Game")
                            .append("averageRating", new Document("$avg", "$Rating")))
            );

            AggregateIterable<Document> result = collection.aggregate(pipeline);
            if (result.first() != null) {
                averageRating = result.first().getDouble("averageRating");
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return averageRating;
    }
}
