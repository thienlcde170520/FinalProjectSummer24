/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import static Controller.JavaMongo.getConnection;
import static Controller.JavaMongo.getConnectionLocal;
import static DAO.TransactionBillDAO.getBillsByGameID;
import Model.Bill;
import Model.Follow;
import Model.Game;
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
import com.mongodb.client.model.Accumulators;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.lookup;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Aggregates.unwind;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.bson.conversions.Bson;
import com.mongodb.client.model.Sorts;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.ascending;
import java.time.ZoneId;
import java.util.Date;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.time.DayOfWeek;
import org.bson.Document;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author LENOVO
 */
public class GameDAO {
   public static Game getGameByReview(Review review) {
        Game game = null;

        try (MongoClient mongoClient = MongoClients.create(getConnectionLocal())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");

            // Create a filter to find the game document based on the ID_Game from the review
            Bson gameFilter = Filters.eq("ID", review.getIdGame());

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

                // Create a Game object
                game = new Game(id, name, price, publishDay, numberOfBuyers, linkTrailer, avatarLink, gameLink, description, minimumCPU, minimumRAM, minimumGPU, maximumCPU, maximumRAM, maximumGPU);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return game;
    }
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
            public static Game getGameByFollow(Follow follow) {
        MongoClientSettings settings = getConnectionLocal();
        Game game = null;

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
                MongoCollection<Document> gameCollection = fpteamDB.getCollection("Games");

                Document doc = gameCollection.find(Filters.eq("ID", follow.getIdGame())).first();

                if (doc != null) {
                    game = new Game(
                            doc.getString("ID"),
                            doc.getString("Name"),
                            doc.getDouble("Price"),
                            doc.getString("Publish_day"),
                            doc.getInteger("Number_of_buyers"),
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
                }
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }

        return game;
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

        // Check if the selectedGenres array is valid
        if (selectedGenres != null && selectedGenres.length > 0) {
            // Create a list from the selected genres
            List<String> genreList = Arrays.asList(selectedGenres);


            // Find games that have any of the selected genres
            Bson filter = Filters.in("Type_of_genres", genreList);
            FindIterable<Document> gameDocs = gameGenresCollection.find(filter);

            // Create a Set to store unique game IDs to avoid adding the same game multiple times
            Set<String> gameIDs = new HashSet<>();
            for (Document gameDoc : gameDocs) {
                String gameID = gameDoc.getString("ID_Game");
                if (!gameIDs.contains(gameID)) {
                    gameIDs.add(gameID);
                    Game game = getGameByGameID(gameID); // Implement this method to retrieve a Game by ID
                    if (game != null) {
                        games.add(game);
                    }
                }
            }
        } else {
            System.out.println("No genres provided for filtering.");
        }
    } catch (MongoException e) {
        e.printStackTrace();
    }

    return games;
}

public static ArrayList<Game> searchGames(String gameName, String gamePublisher, String year, String priceAmount, String priceCurrency, String[] selectedGenres) {
    ArrayList<Game> filteredGames = new ArrayList<>();
    ArrayList<Game> gamesByPublisherName = new ArrayList<>();
    ArrayList<Game> gamesByGameName = new ArrayList<>();
    ArrayList<Game> gamesByGenres = new ArrayList<>();

    // Retrieve games by game name if provided


        gamesByGameName = getGamesByGameName(gameName);
        System.out.println("Games by Game Name: " + gamesByGameName); // Debugging statement
    

    // Retrieve games by publisher name if provided
   
        gamesByPublisherName = getGamesByPublisherName(gamePublisher);
        System.out.println("Games by Publisher Name: " + gamesByPublisherName); // Debugging statement



    // Combine the games by game name and publisher name
 
        // Find common games by ID from both lists
        for (Game gameP : gamesByPublisherName) {
            for (Game gameN : gamesByGameName) {
                if (gameP.getId() != null && gameN.getId() != null && gameP.getId().equals(gameN.getId())) {
                    filteredGames.add(gameN);
                }
            }
        }

    // If no games are found after the initial filtering, return an empty list
    if (filteredGames.isEmpty()) {
        System.out.println("No games found after initial filters.");
        return filteredGames;
    }

    System.out.println("Games after Publisher Filter: " + filteredGames); // Debugging statement

    // Apply genre filter if provided
      if (selectedGenres != null && selectedGenres.length > 0 && !Arrays.stream(selectedGenres).allMatch(String::isEmpty)) {
        gamesByGenres = getGamesByGenres(selectedGenres);
        System.out.println("Games by Genres: " + gamesByGenres); // Debugging statement   
        ArrayList<Game> tempFilteredGames = new ArrayList<>();
        for (Game gameG : gamesByGenres) {
            for (Game gameN : filteredGames) {
                if (gameG.getId() != null && gameN.getId() != null && gameG.getId().equals(gameN.getId())) {
                    tempFilteredGames.add(gameN);
                }
            }
        }
        filteredGames = tempFilteredGames;
    }

    System.out.println("Games after Genre Filter: " + filteredGames); // Debugging statement
    
    // Apply year filter if provided
    if (year != null && !year.isEmpty()) {
        filteredGames.removeIf(game -> !matchYear(game.getPublishDay(), year));
        System.out.println("Games after Year Filter: " + filteredGames); // Debugging statement
    }

    // Apply price filter if provided
    if (priceAmount != null && !priceAmount.isEmpty()) {
        filteredGames.removeIf(game -> !matchPrice(game.getPrice(), priceAmount, priceCurrency));
        System.out.println("Games after Price Filter: " + filteredGames); // Debugging statement
    }

    return filteredGames;
}

// Helper method to match game publish year based on publishDay and year
    public static boolean matchYear(String publishDay, String year) {
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
    public static List<Game> getMostPopularGamesByPeriod(String period) {
        // Map to store game ID and the number of buyers in the specified period
        Map<String, Integer> gameBuyersCount = new HashMap<>();
        // Map to store game ID and the list of bills for that game
        Map<String, List<Bill>> gameBillsMap = new HashMap<>();

        // Fetch all games
        List<Game> games = getAllGames();

        // Initialize lists for storing bills
        List<Bill> allBills = new ArrayList<>();

        // Fetch bills for all games and populate the map
        for (Game g : games) {
            List<Bill> bills = TransactionBillDAO.getBillsByGameID(g.getId());  // Fetch bills for each game
            gameBillsMap.put(g.getId(), bills);  // Store bills in the map
            allBills.addAll(bills);  // Add all bills to a single list for filtering
        }

        // Get today's date in the required format (assuming "yyyy-MM-dd" format for simplicity)
        LocalDate today = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate;

        // Determine the start and end dates based on the period
         switch (period.toLowerCase()) {
        case "day":
            startDate = today;
            endDate = today;
            break;
        case "week":
            startDate = today.with(DayOfWeek.MONDAY);  // Start of the week
            endDate = today.with(DayOfWeek.SUNDAY);  // End of the week
            break;
        case "month":
            startDate = today.with(TemporalAdjusters.firstDayOfMonth());  // Start of the month
            endDate = today.with(TemporalAdjusters.lastDayOfMonth());  // End of the month
            break;
        case "year":
            startDate = today.with(TemporalAdjusters.firstDayOfYear());  // Start of the year
            endDate = today.with(TemporalAdjusters.lastDayOfYear());  // End of the year
            break;
        default:
            throw new IllegalArgumentException("Invalid period specified. Valid periods are: day, week, month, year.");
    }
       // Convert dates to strings in the required format

      

        // Filter bills based on the period
        for (Bill bill : allBills) {
            String buyDate = bill.getBuyTime().split(" ")[0];
            if (buyDate.compareTo(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) >= 0 &&
                buyDate.compareTo(endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) <= 0) {
                gameBuyersCount.put(bill.getGameId(), gameBuyersCount.getOrDefault(bill.getGameId(), 0) + 1);
            }
        }

        // Sort games based on the number of buyers in the specified period
        games.sort((g1, g2) -> {
            Integer buyersCountG1 = gameBuyersCount.getOrDefault(g1.getId(), 0);
            Integer buyersCountG2 = gameBuyersCount.getOrDefault(g2.getId(), 0);
            return Integer.compare(buyersCountG2, buyersCountG1);
        });

        // Optional: Print out each game's list of bills for debugging
        for (Map.Entry<String, List<Bill>> entry : gameBillsMap.entrySet()) {
            String gameId = entry.getKey();
            List<Bill> bills = entry.getValue();
            System.out.println("Game ID: " + gameId);
            for (Bill bill : bills) {
                System.out.println("  " + bill.getBuyTime() + " - " + bill.getBuyPrice());
            }
        }

        // Return the sorted list of games
        return games;
    }

 public static List<Game> getMostProfitableGamesByPeriod(String period) {
        // Map to store game ID and the total profit in the specified period
        Map<String, Double> gameProfitMap = new HashMap<>();
        // Map to store game ID and the list of bills for that game
        Map<String, List<Bill>> gameBillsMap = new HashMap<>();

        // Fetch all games
        List<Game> games = getAllGames();

        // Initialize lists for storing bills
        List<Bill> allBills = new ArrayList<>();

        // Fetch bills for all games and populate the map
        for (Game g : games) {
            List<Bill> bills = TransactionBillDAO.getBillsByGameID(g.getId());  // Fetch bills for each game
            gameBillsMap.put(g.getId(), bills);  // Store bills in the map
            allBills.addAll(bills);  // Add all bills to a single list for filtering
        }

        // Get today's date in the required format (assuming "yyyy-MM-dd" format for simplicity)
        LocalDate today = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate;

        // Determine the start and end dates based on the period
         switch (period.toLowerCase()) {
        case "day":
            startDate = today;
            endDate = today;
            break;
        case "week":
            startDate = today.with(DayOfWeek.MONDAY);  // Start of the week
            endDate = today.with(DayOfWeek.SUNDAY);  // End of the week
            break;
        case "month":
            startDate = today.with(TemporalAdjusters.firstDayOfMonth());  // Start of the month
            endDate = today.with(TemporalAdjusters.lastDayOfMonth());  // End of the month
            break;
        case "year":
            startDate = today.with(TemporalAdjusters.firstDayOfYear());  // Start of the year
            endDate = today.with(TemporalAdjusters.lastDayOfYear());  // End of the year
            break;
        default:
            throw new IllegalArgumentException("Invalid period specified. Valid periods are: day, week, month, year.");
    }

        // Convert dates to strings in the required format
      

        // Filter bills based on the period and calculate the total profit
        for (Bill bill : allBills) {
            String buyDate = bill.getBuyTime().split(" ")[0];
            if (buyDate.compareTo(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) >= 0 &&
                buyDate.compareTo(endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) <= 0) {
                gameProfitMap.put(bill.getGameId(), gameProfitMap.getOrDefault(bill.getGameId(), 0.0) + bill.getBuyPrice());
            }
        }

        // Sort games based on the total profit in the specified period
        games.sort((g1, g2) -> {
            Double profitG1 = gameProfitMap.getOrDefault(g1.getId(), 0.0);
            Double profitG2 = gameProfitMap.getOrDefault(g2.getId(), 0.0);
            return Double.compare(profitG2, profitG1);
        });

        // Optional: Print out each game's list of bills for debugging
        for (Map.Entry<String, List<Bill>> entry : gameBillsMap.entrySet()) {
            String gameId = entry.getKey();
            List<Bill> bills = entry.getValue();
            System.out.println("Game ID: " + gameId);
            for (Bill bill : bills) {
                System.out.println("  " + bill.getBuyTime() + " - " + bill.getBuyPrice());
            }
        }

        // Return the sorted list of games
        return games;
    }
   public static Bson createPeriodFilter(String period) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(30);  // Default to a 30-day period

        switch (period.toLowerCase()) {
            case "day":
                startDate = today.minusDays(1);
                break;
            case "week":
                startDate = today.minusWeeks(1);
                break;
            case "month":
                startDate = today.minusMonths(1);
                break;
            case "year":
                startDate = today.minusYears(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid time period: " + period);
        }

        Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Adjust the filter to match Buy date
        return Filters.and(Filters.gte("Buy_time", start), Filters.lt("Buy_time", end));
    }
}
