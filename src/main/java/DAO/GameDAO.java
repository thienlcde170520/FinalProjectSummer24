/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import static Controller.JavaMongo.getConnection;
import static Controller.JavaMongo.getConnectionLocal;
import Model.BankTransactions;
import Model.Bill;
import Model.Game;
import Model.Gamers;
import Model.Publishers;
import Model.Genre;
import Model.Review;
import Model.Users;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.bson.conversions.Bson;
/**
 *
 * @author LENOVO
 */
public class GameDAO {
        public static void addGame(Game game) {
              try (MongoClient mongoClientLocal = MongoClients.create(getConnectionLocal())) {
            MongoDatabase fpteamDBLocal = mongoClientLocal.getDatabase("FPT");
            MongoCollection<Document> gamesCollectionLocal = fpteamDBLocal.getCollection("Games");

            Document gameDoc = new Document()
                    .append("ID", game.getId())
                    .append("Name", game.getName())
                    .append("Price", game.getPrice())
                    .append("Publish_day", game.getPublishDay())
                    .append("Number_of_buyers", game.getNumberOfBuyers())
                    .append("LinkTrailer", game.getLinkTrailer())
                    .append("AvatarLink", game.getAvatarLink())
                    .append("GameLink", game.getGameLink())
                    .append("Description", game.getDescription())
                    .append("Minimum_CPU", game.getMinimumCPU())
                    .append("Minimum_RAM", game.getMinimumRAM())
                    .append("Minimum_GPU", game.getMinimumGPU())
                    .append("Maximum_CPU", game.getMaximumCPU())
                    .append("Maximum_RAM", game.getMaximumRAM())
                    .append("Maximum_GPU", game.getMaximumGPU());

            gamesCollectionLocal.insertOne(gameDoc);
            System.out.println("Game added successfully to MongoDB.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");
            Document gameDoc = new Document()
                    .append("ID", game.getId())
                    .append("Name", game.getName())
                    .append("Price", game.getPrice())
                    .append("Publish_day", game.getPublishDay())
                    .append("Number_of_buyers", game.getNumberOfBuyers())
                    .append("LinkTrailer", game.getLinkTrailer())
                    .append("AvatarLink", game.getAvatarLink())
                    .append("GameLink", game.getGameLink())
                    .append("Description", game.getDescription())
                    .append("Minimum_CPU", game.getMinimumCPU())
                    .append("Minimum_RAM", game.getMinimumRAM())
                    .append("Minimum_GPU", game.getMinimumGPU())
                    .append("Maximum_CPU", game.getMaximumCPU())
                    .append("Maximum_RAM", game.getMaximumRAM())
                    .append("Maximum_GPU", game.getMaximumGPU());

            gamesCollection.insertOne(gameDoc);
            System.out.println("Game added successfully to MongoDB.");
        } catch (Exception e) {
            e.printStackTrace();
        }
      
    }
          public static void updateGame(Game game) {
        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");

            // Create a filter to find the existing document by game ID
            Bson filter = Filters.eq("ID", game.getId());

            // Create a document with updated game information
            Document updateDoc = new Document()
                    .append("Name", game.getName())
                    .append("Price", game.getPrice())
                    .append("Publish_day", game.getPublishDay())
                    .append("Number_of_buyers", game.getNumberOfBuyers())
                    .append("LinkTrailer", game.getLinkTrailer())
                    .append("AvatarLink", game.getAvatarLink())
                    .append("GameLink", game.getGameLink())
                    .append("Description", game.getDescription())
                    .append("Minimum_CPU", game.getMinimumCPU())
                    .append("Minimum_RAM", game.getMinimumRAM())
                    .append("Minimum_GPU", game.getMinimumGPU())
                    .append("Maximum_CPU", game.getMaximumCPU())
                    .append("Maximum_RAM", game.getMaximumRAM())
                    .append("Maximum_GPU", game.getMaximumGPU());

            // Create an update operation
            UpdateOptions options = new UpdateOptions().upsert(true); // If document not found, create a new one
            UpdateResult updateResult = gamesCollection.updateOne(filter, new Document("$set", updateDoc), options);

            // Check if the update was successful
            if (updateResult.getModifiedCount() > 0) {
                System.out.println("Game updated successfully in MongoDB.");
            } else {
                System.out.println("No game found with ID: " + game.getId() + ". New game added.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         try (MongoClient mongoClientLocal = MongoClients.create(getConnectionLocal())) {
            MongoDatabase fpteamDBLocal = mongoClientLocal.getDatabase("FPT");
            MongoCollection<Document> gamesCollection = fpteamDBLocal.getCollection("Games");

            // Create a filter to find the existing document by game ID
            Bson filter = Filters.eq("ID", game.getId());

            // Create a document with updated game information
            Document updateDoc = new Document()
                    .append("Name", game.getName())
                    .append("Price", game.getPrice())
                    .append("Publish_day", game.getPublishDay())
                    .append("Number_of_buyers", game.getNumberOfBuyers())
                    .append("LinkTrailer", game.getLinkTrailer())
                    .append("AvatarLink", game.getAvatarLink())
                    .append("GameLink", game.getGameLink())
                    .append("Description", game.getDescription())
                    .append("Minimum_CPU", game.getMinimumCPU())
                    .append("Minimum_RAM", game.getMinimumRAM())
                    .append("Minimum_GPU", game.getMinimumGPU())
                    .append("Maximum_CPU", game.getMaximumCPU())
                    .append("Maximum_RAM", game.getMaximumRAM())
                    .append("Maximum_GPU", game.getMaximumGPU());

            // Create an update operation
            UpdateOptions options = new UpdateOptions().upsert(true); // If document not found, create a new one
            UpdateResult updateResult = gamesCollection.updateOne(filter, new Document("$set", updateDoc), options);

            // Check if the update was successful
            if (updateResult.getModifiedCount() > 0) {
                System.out.println("Game updated successfully in MongoDB.");
            } else {
                System.out.println("No game found with ID: " + game.getId() + ". New game added.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
          public static ArrayList<Game> getAllGames() {
        MongoClientSettings settings = getConnectionLocal();
        ArrayList<Game> gamesList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");
            MongoCursor<Document> cursor = gamesCollection.find().iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();

                // Parse price
                Double price = doc.getDouble("Price");

                // Parse number of buyers
                Integer numberOfBuyers = doc.getInteger("Number_of_buyers");

                // Parse nested fields for configuration
                Game game = new Game(
                        doc.getString("ID"),
                        doc.getString("Name"),
                        price,
                        doc.getString("Publish_day"),
                        numberOfBuyers,
                        doc.getString("LinkTrailer"),
                        doc.getString("AvatarLink"),
                        doc.getString("GameLink"),
                        doc.getString("Description"),
                        doc.getString("Minimum_CPU"),
                        doc.getString("Minimum_RAM"),
                        doc.getString("Minimum_GPU"),
                        doc.getString("Maximum_CPU"),
                        doc.getString("Maximum_RAM"),
                        doc.getString("Maximum_GPU")
                );
                gamesList.add(game);
            }
            cursor.close();
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return gamesList;
    }
           public static ArrayList<Game> getGamesByGamerId(String gamerId) {
        ArrayList<Game> games = new ArrayList<>();

        MongoClientSettings settings = getConnectionLocal();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Access the collections
            MongoCollection<Document> billCollection = fpteamDB.getCollection("Buy");
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");

            // Create a filter to find bills associated with the gamerId
            Bson filter = Filters.eq("ID_Gamer", gamerId);

            // Find all documents in Bill collection that match the filter
            FindIterable<Document> billDocs = billCollection.find(filter);

            for (Document billDoc : billDocs) {
                String gameId = billDoc.getString("ID_Game");

                // Retrieve the game details from Games collection based on gameId
                Game game = getGameByGameID(gameId);

                if (game != null) {
                    games.add(game);
                } else {
                    System.out.println("Game not found with ID: " + gameId);
                }
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return games;
    }
           
    public static Game getGameByGameID(String gameID) {
        MongoClientSettings settings = getConnectionLocal();
        Game game = null;
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Access the "Games" collection
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");

            // Create a filter to search for the game by ID
            Bson filter = Filters.eq("ID", gameID);

            // Find the first document that matches the filter
            Document gameDoc = gamesCollection.find(filter).first();

            if (gameDoc != null) {
                // Extract game attributes from the document
                String id = gameDoc.getString("ID");
                String name = gameDoc.getString("Name");
                double price = gameDoc.getDouble("Price");
                String publishDay = gameDoc.getString("Publish_day");
                int numberOfBuyers = gameDoc.getInteger("Number_of_buyers");
                String linkTrailer = gameDoc.getString("LinkTrailer");
                String avatarLink = gameDoc.getString("AvatarLink");
                String gameLink = gameDoc.getString("GameLink");
                String description = gameDoc.getString("Description");
                String minimumCPU = gameDoc.getString("Minimum_CPU");
                String minimumRAM = gameDoc.getString("Minimum_RAM");
                String minimumGPU = gameDoc.getString("Minimum_GPU");
                String maximumCPU = gameDoc.getString("Maximum_CPU");
                String maximumRAM = gameDoc.getString("Maximum_RAM");
                String maximumGPU = gameDoc.getString("Maximum_GPU");

                // Create a Game object
                game = new Game(id, name, price, publishDay, numberOfBuyers, linkTrailer, avatarLink, gameLink, description, minimumCPU, minimumRAM, minimumGPU, maximumCPU, maximumRAM, maximumGPU);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return game;
    }

    public static ArrayList<Game> getGamesByGameName(String gameName) {
        MongoClientSettings settings = getConnectionLocal();
        ArrayList<Game> games = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Access the "Games" collection
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");

            // Create a filter to search for the game by partial name match using regex
            Bson filter = Filters.regex("Name", ".*" + gameName + ".*", "i");

            // Find all documents that match the filter
            FindIterable<Document> gameDocs = gamesCollection.find(filter);

            for (Document gameDoc : gameDocs) {
                // Extract game attributes from the document
                String id = gameDoc.getString("ID");
                String name = gameDoc.getString("Name");
                double price = gameDoc.getDouble("Price");
                String publishDay = gameDoc.getString("Publish_day");
                int numberOfBuyers = gameDoc.getInteger("Number_of_buyers");
                String linkTrailer = gameDoc.getString("LinkTrailer");
                String avatarLink = gameDoc.getString("AvatarLink");
                String gameLink = gameDoc.getString("GameLink");
                String description = gameDoc.getString("Description");
                String minimumCPU = gameDoc.getString("Minimum_CPU");
                String minimumRAM = gameDoc.getString("Minimum_RAM");
                String minimumGPU = gameDoc.getString("Minimum_GPU");
                String maximumCPU = gameDoc.getString("Maximum_CPU");
                String maximumRAM = gameDoc.getString("Maximum_RAM");
                String maximumGPU = gameDoc.getString("Maximum_GPU");

                // Create a Game object and add it to the list
                Game game = new Game(id, name, price, publishDay, numberOfBuyers, linkTrailer, avatarLink, gameLink, description, minimumCPU, minimumRAM, minimumGPU, maximumCPU, maximumRAM, maximumGPU);
                games.add(game);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return games;
    }

    public static ArrayList<Game> getGamesByPublisherName(String publisherName) {
        MongoClientSettings settings = getConnectionLocal();
        ArrayList<Game> games = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Access the "Games" and "Publish" collections
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");
            MongoCollection<Document> publishCollection = fpteamDB.getCollection("Publish");
            MongoCollection<Document> gamePublishersCollection = fpteamDB.getCollection("GamePublishers");

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

                    // Create a filter to find the game document in the Games collection based on gameId
                    Bson gameFilter = Filters.eq("ID", gameId);

                    // Find the game document in the Games collection
                    Document gameDoc = gamesCollection.find(gameFilter).first();

                    if (gameDoc != null) {
                        // Extract game attributes from the document
                        String id = gameDoc.getString("ID");
                        String name = gameDoc.getString("Name");
                        double price = gameDoc.getDouble("Price");
                        String publishDay = gameDoc.getString("Publish_day");
                        int numberOfBuyers = gameDoc.getInteger("Number_of_buyers");
                        String linkTrailer = gameDoc.getString("LinkTrailer");
                        String avatarLink = gameDoc.getString("AvatarLink");
                        String gameLink = gameDoc.getString("GameLink");
                        String description = gameDoc.getString("Description");
                        String minimumCPU = gameDoc.getString("Minimum_CPU");
                        String minimumRAM = gameDoc.getString("Minimum_RAM");
                        String minimumGPU = gameDoc.getString("Minimum_GPU");
                        String maximumCPU = gameDoc.getString("Maximum_CPU");
                        String maximumRAM = gameDoc.getString("Maximum_RAM");
                        String maximumGPU = gameDoc.getString("Maximum_GPU");

                        // Create a Game object and add it to the list
                        Game game = new Game(id, name, price, publishDay, numberOfBuyers, linkTrailer, avatarLink, gameLink, description, minimumCPU, minimumRAM, minimumGPU, maximumCPU, maximumRAM, maximumGPU);
                        games.add(game);
                    }
                }
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return games;
    }

    public static ArrayList<Game> getGamesByGenres(String[] selectedGenres) {
        ArrayList<Game> games = new ArrayList<>();

        try {
            MongoClientSettings settings = getConnectionLocal();
            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Access the "Game_Has_Genre" collection
            MongoCollection<Document> gameGenresCollection = fpteamDB.getCollection("Game_Has_Genre");

            // Find games that have exactly the selected genres
            Bson filter = Filters.all("Type_of_genres", Arrays.asList(selectedGenres));
            FindIterable<Document> gameDocs = gameGenresCollection.find(filter);

            for (Document gameDoc : gameDocs) {
                String gameID = gameDoc.getString("ID_Game");
                Game game = getGameByGameID(gameID); // Implement this method to retrieve a Game by ID
                if (game != null) {
                    games.add(game);
                }
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return games;
    }
      public static ArrayList<Game> searchGames(String gameName, String gamePublisher, String year, String priceAmount, String priceCurrency, String[] selectedGenres) {
        ArrayList<Game> games = new ArrayList<>();

        // Retrieve games by game name
        if (gameName != null && !gameName.isEmpty()) {
            ArrayList<Game> gamesByGameName = getGamesByGameName(gameName);
            games.addAll(gamesByGameName);
        }

        // Retrieve games by publisher name
        if (gamePublisher != null && !gamePublisher.isEmpty()) {
            ArrayList<Game> gamesByPublisherName = getGamesByPublisherName(gamePublisher);
            games.addAll(gamesByPublisherName);
        }

        // Retrieve games by selected genres
        if (selectedGenres != null && selectedGenres.length > 0) {
            ArrayList<Game> gamesByGenres = getGamesByGenres(selectedGenres);
            games.addAll(gamesByGenres);
        }

        // Filter games by year of publication (Publish_day), price amount, and price currency
        ArrayList<Game> filteredGames = new ArrayList<>();
        for (Game game : games) {
            boolean matchYear = year == null || year.isEmpty() || matchYear(game.getPublishDay(), year); // Match by year

            boolean matchPrice = priceAmount == null || priceAmount.isEmpty() || matchPrice(game.getPrice(), priceAmount, priceCurrency); // Match by price range

            if (matchYear && matchPrice) {
                filteredGames.add(game);
            }
        }

        return filteredGames;
    }

// Helper method to match game publish year based on publishDay and year
    private static boolean matchYear(String publishDay, String year) {
        try {
            // Parse publishDay to LocalDate
            LocalDate publishDate = LocalDate.parse(publishDay, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            // Extract the year as string
            String gameYear = String.valueOf(publishDate.getYear());

            // Compare with the provided year
            return gameYear.equals(year);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

// Helper method to match game price based on priceAmount and priceCurrency
    private static boolean matchPrice(double gamePrice, String priceAmount, String priceCurrency) {
        double amount = Double.parseDouble(priceAmount);
        if ("Lower".equals(priceCurrency)) {
            return gamePrice <= amount;
        } else if ("Upper".equals(priceCurrency)) {
            return gamePrice >= amount;
        }
        return false;
    }

}
