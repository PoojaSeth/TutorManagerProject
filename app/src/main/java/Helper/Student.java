package Helper;

public class Student {
    public String email;
    public String id;
    public String imageURL;
    public String name;
    public String password;

    public Student() {
    }

    public Student(String email, String id, String imageURL, String name, String password) {
        this.email = email;
        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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
}
