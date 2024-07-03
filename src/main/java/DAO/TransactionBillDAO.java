/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static Controller.JavaMongo.getConnection;
import static Controller.JavaMongo.getConnectionLocal;
import Model.BankTransactions;
import Model.Bill;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author LENOVO
 */
public class TransactionBillDAO {
     public static Bill getBillByGameIDAndGamerID(String gameId, String gamerId) {
        MongoClientSettings settings = getConnectionLocal();
        Bill bill = null;

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Access the "Buy" collection
            MongoCollection<Document> buyCollection = fpteamDB.getCollection("Buy");

            // Create a filter to search for the bill by gameId and gamerId
            Bson filter = Filters.and(
                    Filters.eq("ID_Game", gameId),
                    Filters.eq("ID_Gamer", gamerId)
            );

            // Find the first document that matches the filter
            Document billDoc = buyCollection.find(filter).first();

            if (billDoc != null) {
                // Extract bill attributes from the document
                String id = billDoc.getString("ID_Bill");
                String gamerID = billDoc.getString("ID_Gamer");
                String gameID = billDoc.getString("ID_Game");
                String buyTime = billDoc.getString("Buy_time");
                Double buyPrice = billDoc.getDouble("Buy_price");

                // Create a Bill object
                bill = new Bill();
                bill.setId(id);
                bill.setGamerId(gamerID);
                bill.setGameId(gameID);
                bill.setBuyTime(buyTime);
                bill.setBuyPrice(buyPrice);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return bill;
    }
     public static List<Bill> getBillsByGameID(String gameId) {
    MongoClientSettings settings = getConnectionLocal();
    List<Bill> billsList = new ArrayList<>();

    try (MongoClient mongoClient = MongoClients.create(settings)) {
        MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

        // Access the "Buy" collection
        MongoCollection<Document> buyCollection = fpteamDB.getCollection("Buy");

        // Create a filter to search for all bills by gamerId
        Bson filter = Filters.eq("ID_Game", gameId);

        // Find all documents that match the filter
        try (MongoCursor<Document> cursor = buyCollection.find(filter).iterator()) {
            while (cursor.hasNext()) {
                Document billDoc = cursor.next();
                
                // Extract bill attributes from the document
                String id = billDoc.getString("ID_Bill");
                String gamerID = billDoc.getString("ID_Gamer");
                String buyTime = billDoc.getString("Buy_time");
                Double buyPrice = billDoc.getDouble("Buy_price");

                // Create a Bill object
                Bill bill = new Bill();
                bill.setId(id);
                bill.setGamerId(gamerID);  // Set gamerId for the Bill
                bill.setGameId(gameId);
                bill.setBuyTime(buyTime);
                bill.setBuyPrice(buyPrice);

                // Add Bill to the list
                billsList.add(bill);
            }
        }
    } catch (MongoException e) {
        e.printStackTrace();
    }

    return billsList;
}

     public static Double getProfitByGameId(String gameId) {
    MongoClientSettings settings = getConnectionLocal();
    double totalProfit = 0.0;

    try (MongoClient mongoClient = MongoClients.create(settings)) {
        MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

        // Access the "Buy" collection
        MongoCollection<Document> buyCollection = fpteamDB.getCollection("Buy");

        // Create a filter to search for the bills by gameId
        Bson filter = Filters.eq("ID_Game", gameId);

        // Use aggregation to calculate the total profit
        List<Bson> pipeline = Arrays.asList(
            Aggregates.match(filter),
            Aggregates.group(null, Accumulators.sum("totalProfit", "$Buy_price"))
        );

        // Execute the aggregation pipeline
        Document result = buyCollection.aggregate(pipeline).first();

        if (result != null && result.containsKey("totalProfit")) {
            totalProfit = result.getDouble("totalProfit");
        }
    } catch (MongoException e) {
        e.printStackTrace();
    }

    return totalProfit;
}


    public static boolean isRefundable(Bill bill) {
        if (bill == null) {
            return false;
        }

        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Parse the buy time from the bill
        LocalDateTime buyTime = LocalDateTime.parse(bill.getBuyTime(), formatter);
        LocalDateTime currentTime = LocalDateTime.now();

        // Calculate the difference in hours
        long hoursBetween = ChronoUnit.HOURS.between(buyTime, currentTime);

        return hoursBetween <= 2;
    }

    public static void refundPurchase(String billId, String gamerId, String gameId, Double refundPrice) {
        MongoClientSettings settings = getConnection();
              MongoClientSettings settingsLocal = getConnectionLocal();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Access the collections
            MongoCollection<Document> buyCollection = fpteamDB.getCollection("Buy");
            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");
       

            // Retrieve the gamer document
            Document gamerDoc = gamersCollection.find(Filters.eq("ID", gamerId)).first();
            if (gamerDoc == null) {
                System.out.println("Gamer not found with ID: " + gamerId);
                return;
            }

            // Retrieve the game document
            Document gameDoc = gamesCollection.find(Filters.eq("ID", gameId)).first();
            if (gameDoc == null) {
                System.out.println("Game not found with ID: " + gameId);
                return;
            }

            // Calculate the new balance for the gamer after refund
            Double currentMoney = gamerDoc.getDouble("Money");
            Double newBalance = currentMoney + refundPrice;

            // Update the Money field in the Gamers collection
            Bson updateBalance = Updates.set("Money", newBalance);
            gamersCollection.updateOne(Filters.eq("ID", gamerId), updateBalance);
            System.out.println("Gamer balance updated: " + newBalance);
         
           
                // Decrement the number of buyers for the game in the Games collection
                int currentBuyers = gameDoc.getInteger("Number_of_buyers");
                int newBuyers = currentBuyers - 1;
                Bson updateBuyers = Updates.set("Number_of_buyers", newBuyers);
                gamesCollection.updateOne(Filters.eq("ID", gameId), updateBuyers);
                System.out.println("Number of buyers updated for game " + gameId + ": " + newBuyers);

                // Remove the purchase document from the Buy collection
                Bson deleteFilter = Filters.eq("ID_Bill", billId);
                DeleteResult result = buyCollection.deleteOne(deleteFilter);
                if (result.getDeletedCount() > 0) {
                    System.out.println("Purchase refunded: " + billId);
                } else {
                    System.out.println("No purchase found with ID_Bill: " + billId);
                }
            
        } catch (MongoException e) {
            e.printStackTrace();
        }
         try (MongoClient mongoClientLocal = MongoClients.create(settingsLocal)) {
            MongoDatabase fpteamDBLocal = mongoClientLocal.getDatabase("FPT");

            // Access the collections
            MongoCollection<Document> buyCollection = fpteamDBLocal.getCollection("Buy");
            MongoCollection<Document> gamersCollection = fpteamDBLocal.getCollection("Gamers");
            MongoCollection<Document> gamesCollection = fpteamDBLocal.getCollection("Games");
            MongoCollection<Document> publishersCollection = fpteamDBLocal.getCollection("GamePublishers");

            // Retrieve the gamer document
            Document gamerDoc = gamersCollection.find(Filters.eq("ID", gamerId)).first();
            if (gamerDoc == null) {
                System.out.println("Gamer not found with ID: " + gamerId);
                return;
            }

            // Retrieve the game document
            Document gameDoc = gamesCollection.find(Filters.eq("ID", gameId)).first();
            if (gameDoc == null) {
                System.out.println("Game not found with ID: " + gameId);
                return;
            }

            // Calculate the new balance for the gamer after refund
            Double currentMoney = gamerDoc.getDouble("Money");
            Double newBalance = currentMoney + refundPrice;

            // Update the Money field in the Gamers collection
            Bson updateBalance = Updates.set("Money", newBalance);
            gamersCollection.updateOne(Filters.eq("ID", gamerId), updateBalance);
            System.out.println("Gamer balance updated: " + newBalance);

            // Calculate the profit to be reverted (90% of the refund price)
            Double profitReversion = (Double) (1.0 * refundPrice);

            // Update the profit for the publisher
            String publisherId = PublisherDAO.getPublisherByGameId(gameId).getId(); // Assuming this method gets publisher ID
            Document publisherDoc = publishersCollection.find(Filters.eq("ID", publisherId)).first();
            if (publisherDoc != null) {
                Double currentProfit = publisherDoc.getDouble("Profit");
                Double newProfit = currentProfit - profitReversion;

                // Update the Profit field in the Publishers collection
                Bson updateProfit = Updates.set("Profit", newProfit);
                publishersCollection.updateOne(Filters.eq("ID", publisherId), updateProfit);
                System.out.println("Profit updated for publisher " + publisherId + ": " + newProfit);

                // Decrement the number of buyers for the game in the Games collection
                int currentBuyers = gameDoc.getInteger("Number_of_buyers");
                int newBuyers = currentBuyers - 1;
                Bson updateBuyers = Updates.set("Number_of_buyers", newBuyers);
                gamesCollection.updateOne(Filters.eq("ID", gameId), updateBuyers);
                System.out.println("Number of buyers updated for game " + gameId + ": " + newBuyers);

                // Remove the purchase document from the Buy collection
                Bson deleteFilter = Filters.eq("ID_Bill", billId);
                DeleteResult result = buyCollection.deleteOne(deleteFilter);
                if (result.getDeletedCount() > 0) {
                    System.out.println("Purchase refunded: " + billId);
                } else {
                    System.out.println("No purchase found with ID_Bill: " + billId);
                }
            } else {
                System.out.println("Publisher not found with ID: " + publisherId);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public static void addPurchase(String billId, String gamerId, String gameId, String buyTime, Double buyPrice) {
        MongoClientSettings settings = getConnection();
            MongoClientSettings settingsLocal = getConnectionLocal();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Access the collections
            MongoCollection<Document> buyCollection = fpteamDB.getCollection("Buy");
            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");
           
            // Retrieve the current balance of the gamer
            Document gamerDoc = gamersCollection.find(Filters.eq("ID", gamerId)).first();
            if (gamerDoc == null) {
                System.out.println("Gamer not found with ID: " + gamerId);
                return;
            }

            // Check if the game exists in the Games collection
            Document gameDoc = gamesCollection.find(Filters.eq("ID", gameId)).first();
            if (gameDoc == null) {
                System.out.println("Game not found with ID: " + gameId);
                return;
            }

            Double currentMoney = gamerDoc.getDouble("Money");
            if (currentMoney >= buyPrice) {
                // Calculate the new balance for the gamer after purchase
                Double newBalance = currentMoney - buyPrice;

                // Update the Money field in the Gamers collection
                Bson updateBalance = Updates.set("Money", newBalance);
                gamersCollection.updateOne(Filters.eq("ID", gamerId), updateBalance);
                System.out.println("Gamer balance updated: " + newBalance);

                

                // Update the profit for the publisher
               
                    // Increment the number of buyers for the game in the Games collection
                    int currentBuyers = gameDoc.getInteger("Number_of_buyers");
                    int newBuyers = currentBuyers + 1;
                    Bson updateBuyers = Updates.set("Number_of_buyers", newBuyers);
                    gamesCollection.updateOne(Filters.eq("ID", gameId), updateBuyers);
                    System.out.println("Number of buyers updated for game " + gameId + ": " + newBuyers);

                    // Create the document to be inserted in the Buy collection
                    Document buyDoc = new Document("ID_Bill", billId)
                            .append("ID_Gamer", gamerId)
                            .append("ID_Game", gameId)
                            .append("Buy_time", buyTime)
                            .append("Buy_price", buyPrice);

                    // Insert the document into the Buy collection
                    buyCollection.insertOne(buyDoc);
                    System.out.println("Purchase added: " + buyDoc.toJson());
                
            } else {
                System.out.println("Insufficient funds for gamer ID: " + gamerId);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
        try (MongoClient mongoClientLocal = MongoClients.create(settingsLocal)) {
            MongoDatabase fpteamDBLocal = mongoClientLocal.getDatabase("FPT");

            // Access the collections
            MongoCollection<Document> buyCollection = fpteamDBLocal.getCollection("Buy");
            MongoCollection<Document> gamersCollection = fpteamDBLocal.getCollection("Gamers");
            MongoCollection<Document> gamesCollection = fpteamDBLocal.getCollection("Games");
            MongoCollection<Document> publishersCollection = fpteamDBLocal.getCollection("GamePublishers");

            // Retrieve the current balance of the gamer
            Document gamerDoc = gamersCollection.find(Filters.eq("ID", gamerId)).first();
            if (gamerDoc == null) {
                System.out.println("Gamer not found with ID: " + gamerId);
                return;
            }

            // Check if the game exists in the Games collection
            Document gameDoc = gamesCollection.find(Filters.eq("ID", gameId)).first();
            if (gameDoc == null) {
                System.out.println("Game not found with ID: " + gameId);
                return;
            }

            Double currentMoney = gamerDoc.getDouble("Money");
            if (currentMoney >= buyPrice) {
                // Calculate the new balance for the gamer after purchase
                Double newBalance = currentMoney - buyPrice;

                // Update the Money field in the Gamers collection
                Bson updateBalance = Updates.set("Money", newBalance);
                gamersCollection.updateOne(Filters.eq("ID", gamerId), updateBalance);
                System.out.println("Gamer balance updated: " + newBalance);

                // Calculate the profit as 90% of the buy price
                Double profitAmount = (Double) (1.0 * buyPrice);

                // Update the profit for the publisher
                String publisherId = PublisherDAO.getPublisherByGameId(gameId).getId(); // Assuming this method gets publisher ID
                Document publisherDoc = publishersCollection.find(Filters.eq("ID", publisherId)).first();
                if (publisherDoc != null) {
                    Double currentProfit = publisherDoc.getDouble("Profit");
                    Double newProfit = currentProfit + profitAmount;

                    // Update the Profit field in the Publishers collection
                    Bson updateProfit = Updates.set("Profit", newProfit);
                    publishersCollection.updateOne(Filters.eq("ID", publisherId), updateProfit);
                    System.out.println("Profit updated for publisher " + publisherId + ": " + newProfit);

                    // Increment the number of buyers for the game in the Games collection
                    int currentBuyers = gameDoc.getInteger("Number_of_buyers");
                    int newBuyers = currentBuyers + 1;
                    Bson updateBuyers = Updates.set("Number_of_buyers", newBuyers);
                    gamesCollection.updateOne(Filters.eq("ID", gameId), updateBuyers);
                    System.out.println("Number of buyers updated for game " + gameId + ": " + newBuyers);

                    // Create the document to be inserted in the Buy collection
                    Document buyDoc = new Document("ID_Bill", billId)
                            .append("ID_Gamer", gamerId)
                            .append("ID_Game", gameId)
                            .append("Buy_time", buyTime)
                            .append("Buy_price", buyPrice);

                    // Insert the document into the Buy collection
                    buyCollection.insertOne(buyDoc);
                    System.out.println("Purchase added: " + buyDoc.toJson());
                } else {
                    System.out.println("Publisher not found with ID: " + publisherId);
                }
            } else {
                System.out.println("Insufficient funds for gamer ID: " + gamerId);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
       public static void insertTransaction(String partnerCode, String orderId, String requestId, String amount,
            String orderInfo, String orderType, String transId, String payType, String signature, String payerId) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
  
            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");
            MongoCollection<Document> publishersCollection = fpteamDB.getCollection("GamePublishers");
         
            // Retrieve the corresponding gamer from the Gamers collection
            Bson filter = Filters.eq("ID", payerId);
            Document gamerDoc = gamersCollection.find(filter).first();
            Document pubDoc = publishersCollection.find(filter).first();
            if (gamerDoc != null) {
                // Update the money field in the Gamers collection
                Double currentMoney = gamerDoc.getDouble("Money");
                int transactionAmount = Integer.parseInt(amount);
                Double updatedMoney = currentMoney + transactionAmount;

                Bson updateOperation = Updates.set("Money", updatedMoney);
                gamersCollection.updateOne(filter, updateOperation);

                System.out.println("Gamer's money updated successfully.");
            } else {
                System.out.println("Gamer not found.");
            }

            if (pubDoc != null) {
                Double currentMoney = pubDoc.getDouble("Money");
                int transactionAmount = Integer.parseInt(amount);
                Double updatedMoney = currentMoney + transactionAmount;

                Bson updateOperation = Updates.set("Money", updatedMoney);
                publishersCollection.updateOne(filter, updateOperation);

                System.out.println("Phublisher's money updated successfully.");
            }

        } catch (Exception e) {
            throw new Exception("Error inserting transaction into MongoDB: " + e.getMessage());
        }
        try (MongoClient mongoClientLocal = MongoClients.create(getConnectionLocal())) {
            MongoDatabase fpteamDBLocal = mongoClientLocal.getDatabase("FPT");
            MongoCollection<Document> transactionsCollection = fpteamDBLocal.getCollection("BankTransactions");
            MongoCollection<Document> gamersCollection = fpteamDBLocal.getCollection("Gamers");
            MongoCollection<Document> publishersCollection = fpteamDBLocal.getCollection("GamePublishers");
            Document transactionDoc = new Document()
                    .append("partnerCode", partnerCode)
                    .append("orderId", orderId)
                    .append("requestId", requestId)
                    .append("amount", amount)
                    .append("orderInfo", orderInfo)
                    .append("orderType", orderType)
                    .append("transId", transId)
                    .append("payType", payType)
                    .append("signature", signature)
                    .append("payerId", payerId)
                    .append("createdAt", new Date());

            transactionsCollection.insertOne(transactionDoc);
            System.out.println("Transaction inserted successfully into MongoDB.");
            // Retrieve the corresponding gamer from the Gamers collection
            Bson filter = Filters.eq("ID", payerId);
            Document gamerDoc = gamersCollection.find(filter).first();
            Document pubDoc = publishersCollection.find(filter).first();
            if (gamerDoc != null) {
                // Update the money field in the Gamers collection
                Double currentMoney = gamerDoc.getDouble("Money");
                int transactionAmount = Integer.parseInt(amount);
                Double updatedMoney = currentMoney + transactionAmount;

                Bson updateOperation = Updates.set("Money", updatedMoney);
                gamersCollection.updateOne(filter, updateOperation);

                System.out.println("Gamer's money updated successfully.");
            } else {
                System.out.println("Gamer not found.");
            }

            if (pubDoc != null) {
                Double currentMoney = pubDoc.getDouble("Money");
                int transactionAmount = Integer.parseInt(amount);
                Double updatedMoney = currentMoney + transactionAmount;

                Bson updateOperation = Updates.set("Money", updatedMoney);
                publishersCollection.updateOne(filter, updateOperation);

                System.out.println("Phublisher's money updated successfully.");
            }

        } catch (Exception e) {
            throw new Exception("Error inserting transaction into MongoDB: " + e.getMessage());
        }
    }

    public static ArrayList<BankTransactions> getTransactionHistoryByPayerId(String payerId) {
        ArrayList<BankTransactions> transactionsList = new ArrayList<>();

        MongoClientSettings settings = getConnectionLocal();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> transactionsCollection = fpteamDB.getCollection("BankTransactions");

            BasicDBObject query = new BasicDBObject("payerId", payerId);

            MongoCursor<Document> cursor = transactionsCollection.find(query).iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                BankTransactions transaction = new BankTransactions(
                        doc.getString("partnerCode"),
                        doc.getString("orderId"),
                        doc.getString("requestId"),
                        doc.getString("amount"),
                        doc.getString("orderInfo"),
                        doc.getString("orderType"),
                        doc.getString("transId"),
                        doc.getString("payType"),
                        doc.getString("signature"),
                        doc.getString("payerId"),
                        doc.getDate("createdAt").toInstant()
                );
                transactionsList.add(transaction);
            }
            cursor.close();
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return transactionsList;
    }

}
