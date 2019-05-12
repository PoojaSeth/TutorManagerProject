package Helper;

public class  Course {
    String courseName;
    String experience;
    String id;
    String tutorEmail;
    String tutorName;
    String mondayAvailability;
    String tuesdayAvailability;
    String wednesdayAvailability;
    String thursdayAvailability;
    String fridayAvailability;
    String tutorId;

    public Course() {
    }

    public Course(String courseName, String experience, String id, String tutorEmail, String tutorName, String mondayAvailability, String tuesdayAvailability, String wednesdayAvailability, String thursdayAvailability, String fridayAvailability, String tutorId) {
        this.courseName = courseName;
        this.experience = experience;
        this.id = id;
        this.tutorEmail = tutorEmail;
        this.tutorName = tutorName;
        this.mondayAvailability = mondayAvailability;
        this.tuesdayAvailability = tuesdayAvailability;
        this.wednesdayAvailability = wednesdayAvailability;
        this.thursdayAvailability = thursdayAvailability;
        this.fridayAvailability = fridayAvailability;
        this.tutorId = tutorId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTutorEmail() {
        return tutorEmail;
    }

    public void setTutorEmail(String tutorEmail) {
        this.tutorEmail = tutorEmail;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getMondayAvailability() {
        return mondayAvailability;
    }

    public void setMondayAvailability(String mondayAvailability) {
        this.mondayAvailability = mondayAvailability;
    }

    public String getTuesdayAvailability() {
        return tuesdayAvailability;
    }

    public void setTuesdayAvailability(String tuesdayAvailability) {
        this.tuesdayAvailability = tuesdayAvailability;
    }

    public String getWednesdayAvailability() {
        return wednesdayAvailability;
    }

    public void setWednesdayAvailability(String wednesdayAvailability) {
        this.wednesdayAvailability = wednesdayAvailability;
    }

    public String getThursdayAvailability() {
        return thursdayAvailability;
    }

    public void setThursdayAvailability(String thursdayAvailability) {
        this.thursdayAvailability = thursdayAvailability;
    }

    public String getFridayAvailability() {
        return fridayAvailability;
    }

    public void setFridayAvailability(String fridayAvailability) {
        this.fridayAvailability = fridayAvailability;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }
}