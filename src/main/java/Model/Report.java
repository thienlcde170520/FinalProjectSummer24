/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LENOVO
 */
public class Report {

    private String reportId;
    private String timestamp;
    private String description;
    private String problemName;
    private String userId;
    private boolean isSearchable;
    private String respond;
    private String adminId;

    public Report( String reportId, String timestamp, String description, String problemName, String userId, boolean isSearchable, String respond, String adminId) {
      
        this.reportId = reportId;
        this.timestamp = timestamp;
        this.description = description;
        this.problemName = problemName;
        this.userId = userId;
        this.isSearchable = isSearchable;
        this.respond = respond;
        this.adminId = adminId;
    }
    public Report() {
    }

 

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isIsSearchable() {
        return isSearchable;
    }

    public void setIsSearchable(boolean isSearchable) {
        this.isSearchable = isSearchable;
    }

    public String getRespond() {
        return respond;
    }

    public void setRespond(String respond) {
        this.respond = respond;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "Report{" + "reportId=" + reportId + ", timestamp=" + timestamp + ", description=" + description + ", problemName=" + problemName + ", userId=" + userId + ", isSearchable=" + isSearchable + ", respond=" + respond + ", adminId=" + adminId + '}';
    }
    
   
    
    
}
