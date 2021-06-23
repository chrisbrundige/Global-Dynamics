package Model;

public class Schedule {

    private String type;
    private String month;
    private int number;

    public Schedule() {
    }

    public Schedule(String type, String month, int number) {
        this.type = type;
        this.month = month;
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
