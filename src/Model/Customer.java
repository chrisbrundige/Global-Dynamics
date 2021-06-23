package Model;

import javafx.scene.control.Button;

import java.sql.Date;
import java.sql.Timestamp;
/** Customer class provides object model for Customer.*/
public class Customer {


        private int ID;
        private String name;
        private  String address;
        private String postalCode;
        private String phone;
        private Date createDate;
        private String createdBy;
        private Timestamp lastUpdate;
        private String lastUpdatedBy;
        private int division;



    public Customer() {
    }

    public Customer(int ID, String name, String address, String postalCode, String phone, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int division) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.division = division;

    }

    public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public Timestamp getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(Timestamp lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public String getLastUpdatedBy() {
            return lastUpdatedBy;
        }

        public void setLastUpdatedBy(String lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
        }

        public  int getDivision() {
            return division;
        }

        public void setDivision(int division) {
            this.division = division;
        }
    @Override
    public String toString(){
        return this.getName();
    }
    }

