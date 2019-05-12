package Helper;

public class TutorUser {

    private String email;
    private String name;
    private String password;
    private String id;

    private String imageURL;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    //private String status;

    public TutorUser() {
    }

    public TutorUser(String email, String name, String password, String id, String imageURL) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.id = id;
        this.imageURL = imageURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
