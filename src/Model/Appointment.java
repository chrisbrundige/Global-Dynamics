package Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
/** Appointment class provides object model for appointment.*/
public class Appointment {

    private int appointmentID;//INT(10) (PK)
    private String title;// VARCHAR(50)
    private String description;//VARCHAR(50)
    private String location;// VARCHAR(50)
    private String type;// VARCHAR(50)
    private LocalDateTime start;//DATETIME
    private LocalDateTime end;//DATETIME
    private LocalDate createDate;// DATETIME
    private String createdBy;// VARCHAR(50)
    private Timestamp lastUpdate;// TIMESTAMP
    private String lastUpdated_By;//VARCHAR(50)
    private int customerID;//INT(10) (FK)
    private int userID;//INT(10) (FK)
    private int contactID;//INT(10) (FK)
    private String contactName;
    private String email;


    /** Appointment Constructor.

     */
    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDate createDate, String createdBy, Timestamp lastUpdate, String lastUpdated_By, int customerID, int userID, int contactID, String contactName, String email) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdated_By = lastUpdated_By;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /** Appointment Constructor without parameters.*/
    public Appointment() {
    }

    /**
     *
     * @return appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     *
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * 
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */

    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */

    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     */

    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */

    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */

    public LocalDateTime getStart() {
        return start;
    }

    /**
     *
     * @param start
     */

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     *
     * @return
     */

    public LocalDateTime getEnd() {
        return end;
    }

    /**
     *
     * @param end
     */

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     *
     * @return
     */

    public LocalDate getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate
     */

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return
     */

    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     */

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return
     */

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate
     */

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return
     */

    public String getLastUpdated_By() {
        return lastUpdated_By;
    }

    /**
     *
     * @param lastUpdated_By
     */

    public void setLastUpdated_By(String lastUpdated_By) {
        this.lastUpdated_By = lastUpdated_By;
    }

    /**
     *
     * @return
     */

    public int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @param customerID
     */

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     *
     * @return
     */

    public int getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     */

    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     *
     * @return
     */

    public int getContactID() {
        return contactID;
    }

    /**
     *
     * @param contactID
     */

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     *
     * @return
     */

    public String getContactName() {
        return contactName;
    }

    /**
     *
     * @param contactName
     */

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     *
     * @return
     */

    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */

    public void setEmail(String email) {
        this.email = email;
    }





}