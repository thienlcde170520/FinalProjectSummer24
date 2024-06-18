package Controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class DriveQuickstart {
    static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String REFRESH_TOKEN = "1//04myG4wQjNOrnCgYIARAAGAQSNwF-L9IrDk5LkFPxAv3hA-qxThxiDxlU78N_5L2wW_5AujS4mRXag1X4S7gj1gApDnRy21xSzxM";  // Replace with your actual refresh token

    static GoogleCredential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = DriveQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(HTTP_TRANSPORT)
                .setJsonFactory(JSON_FACTORY)
                .setClientSecrets(clientSecrets)
                .build()
                .setRefreshToken(REFRESH_TOKEN);

        // Refresh the token to get a new access token
        if (credential.refreshToken()) {
            System.out.println("Access token: " + credential.getAccessToken());
            return credential;
        } else {
            throw new IOException("Failed to refresh the token.");
        }
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = getCredentials(HTTP_TRANSPORT);

        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Print the names and IDs for up to 10 files.
        listFiles(service);

        // Upload an image file, replacing any existing file with the same name
        String filePath = "E:\\FPT UNI\\TamNP4-SU24-SWP391- SW project\\FPTeam\\target\\FPTeam-1.0\\assets\\images\\Honai.jpg";
        String mimeType = "image/jpeg";  // Change to the correct mime type
        String fileName = "Uploaded Image";
        String fileId = findFileByName(service, fileName);

        if (fileId != null) {
            updateFile(service, fileId, filePath, mimeType);
            System.out.println("File updated: " + fileId);
        } else {
            fileId = uploadFile(service, fileName, filePath, mimeType);
            System.out.println("File uploaded: " + fileId);
        }

        // Make the file public and get the file URL
        String fileUrl = createPublicPermissionAndReturnUrl(service, fileId);
        System.out.println("File URL: " + fileUrl);
    }

    private static void listFiles(Drive service) throws IOException {
        FileList result = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }
    }

    private static String findFileByName(Drive service, String fileName) throws IOException {
        FileList result = service.files().list()
                .setQ("name = '" + fileName + "'")
                .setFields("files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            return null;
        } else {
            return files.get(0).getId(); // Return the ID of the first file found
        }
    }

    private static String uploadFile(Drive service, String fileName, String filePath, String mimeType) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(fileName);

        java.io.File file = new java.io.File(filePath);
        FileContent mediaContent = new FileContent(mimeType, file);

        File uploadedFile = service.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        return uploadedFile.getId();
    }

    private static void updateFile(Drive service, String fileId, String filePath, String mimeType) throws IOException {
        java.io.File file = new java.io.File(filePath);
        FileContent mediaContent = new FileContent(mimeType, file);

        File updatedFile = service.files().update(fileId, null, mediaContent)
                .setFields("id")
                .execute();
    }

    private static String createPublicPermissionAndReturnUrl(Drive service, String fileId) throws IOException {
        Permission permission = new Permission()
                .setType("anyone")
                .setRole("reader");
        service.permissions().create(fileId, permission).execute();
        System.out.println("File is now public.");
        
        // Construct the file URL
        String fileUrl = "https://drive.google.com/file/d/" + fileId + "/view";
        return fileUrl;
    }
}
