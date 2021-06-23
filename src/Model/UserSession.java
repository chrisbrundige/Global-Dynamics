package Model;

public final class UserSession {

    private static UserSession instance;

    private User user;

    private UserSession(User user) {
        this.user = user;
    }

    public static UserSession getInstance(User user) {
        if(instance == null){
            instance = new UserSession(user);
        }
        return instance;

    }


    public String getUsername() {
        return user.getUserName();
    }

    public int getUserID() {
        return getUserID();
    }


}
