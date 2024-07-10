/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static Common.SignUpServlet.generateRandomNumber;
import static Controller.JavaMongo.getConnectionLocal;
import Model.Report;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author LENOVO
 */
public class ReportDAO {
    
    public static List<Report> getAllReports() {
        List<Report> reports = new ArrayList<>();

        MongoClientSettings settings = getConnectionLocal();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("FPT");
            MongoCollection<Document> collection = database.getCollection("Reports");

            for (Document doc : collection.find()) {
                Report report = convertDocumentToReport(doc);
                reports.add(report);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }
      public static List<Report> searchReportsByProblemName(String problemName) {
        List<Report> reports = new ArrayList<>();

        MongoClientSettings settings = getConnectionLocal();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("FPT");
            MongoCollection<Document> collection = database.getCollection("Reports");

            Bson filter = Filters.and(
                Filters.regex("ProblemName", ".*" + problemName + ".*", "i"),
                Filters.eq("IsSearchable", true)
            );

            for (Document doc : collection.find(filter)) {
                Report report = convertDocumentToReport(doc);
                reports.add(report);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }

     public static void addReport(String description, String problemName, String userId) {
        MongoClientSettings settings = getConnectionLocal();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("FPT");
            MongoCollection<Document> collection = database.getCollection("Reports");
            
            String reportId;
            do{ reportId = "Report_" + generateRandomNumber();
            } while (isReportIdExist(reportId));
            String timestamp = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
            boolean isSearchable = false;
            String respond = null;
            String adminId = null;

            Document report = new Document()
                    .append("ReportId", reportId)
                    .append("Timestamp", timestamp)
                    .append("Description", description)
                    .append("ProblemName", problemName)
                    .append("UserID", userId)
                    .append("IsSearchable", isSearchable)
                    .append("Respond", respond)
                    .append("AdminID", adminId);

            collection.insertOne(report);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     private static boolean isReportIdExist (String reportId){
         List<Report> report = getAllReports();
         for (Report r : report){
             if(r.getReportId().equals(reportId))
                 return true;
         }
         return false;
     }
     
    public static void deleteReport(String reportId) {
        MongoClientSettings settings = getConnectionLocal();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("FPT");
            MongoCollection<Document> collection = database.getCollection("Reports");

            Bson filter = Filters.eq("ReportId", reportId);
            collection.deleteMany(filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  public static List<Report> getReportsWithSpecificAttributes() {
    List<Report> reports = new ArrayList<>();

    MongoClientSettings settings = getConnectionLocal();

    try (MongoClient mongoClient = MongoClients.create(settings)) {
        MongoDatabase database = mongoClient.getDatabase("FPT");
        MongoCollection<Document> collection = database.getCollection("Reports");

        // Define the filter
        Bson filter = Filters.and(
            Filters.or(
                Filters.exists("Respond", false),
                Filters.eq("Respond", null)
            ),
            Filters.or(
                Filters.exists("AdminID", false),
                Filters.eq("AdminID", null)
            )
        );

        // Find documents that match the filter
        for (Document doc : collection.find(filter)) {
            Report report = convertDocumentToReport(doc);
            reports.add(report);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return reports;
}
public static List<Report> getReportsByUserId(String userId) {
    List<Report> reports = new ArrayList<>();

    MongoClientSettings settings = getConnectionLocal();

    try (MongoClient mongoClient = MongoClients.create(settings)) {
        MongoDatabase database = mongoClient.getDatabase("FPT");
        MongoCollection<Document> collection = database.getCollection("Reports");

        // Define the filter to match the specific UserID
        Bson filter = Filters.eq("UserID", userId);

        // Find documents that match the filter
        for (Document doc : collection.find(filter)) {
            Report report = convertDocumentToReport(doc);
            reports.add(report);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return reports;
}


       public static void respondToReport(String reportId, String response, String adminId, boolean IsSearchable) {
        MongoClientSettings settings = getConnectionLocal();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("FPT");
            MongoCollection<Document> reportCollection = database.getCollection("Reports");

            Bson filter = Filters.eq("ReportId", reportId);
            Bson updates = Updates.combine(
                Updates.set("Respond", response),
                Updates.set("AdminID", adminId),
                    Updates.set("IsSearchable", IsSearchable)
            );

            reportCollection.updateOne(filter, updates);
        } catch (MongoException e) {
            e.printStackTrace();
        }
       }
    
 private static Report convertDocumentToReport(Document doc) {
    String reportId = doc.getString("ReportId");
    String timestamp = doc.getString("Timestamp");
    String description = doc.getString("Description");
    String problemName = doc.getString("ProblemName");
    String userId = doc.getString("UserID");
    boolean isSearchable = doc.getBoolean("IsSearchable");
    String respond = doc.getString("Respond");
    String adminId = doc.getString("AdminID");

    return new Report(reportId, timestamp, description, problemName, userId, isSearchable, respond, adminId);
}

}
