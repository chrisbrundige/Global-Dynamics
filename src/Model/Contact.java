package Model;

/** Contact class provides object model for contact.*/
public class Contact {

    private Integer ID;
    private String name;
    private String email;

    public Contact() {
    }

    public Contact(Integer ID, String name, String email) {
        this.ID = ID;
        this.name = name;
        this.email = email;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
