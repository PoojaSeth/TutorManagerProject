package Helper;

public class Appointments {

    private String StudentId;
    private String TutorId;
    private String appointmentDay;
    private String appointmentTime;
    private String courseName;
    private String id;
    private String studentName;
    private String tutorName;

    public Appointments() {
    }

    public Appointments(String studentId, String tutorId, String appointmentDay, String appointmentTime, String courseName, String id, String studentName, String tutorName) {
        StudentId = studentId;
        TutorId = tutorId;
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.courseName = courseName;
        this.id = id;
        this.studentName = studentName;
        this.tutorName = tutorName;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getTutorId() {
        return TutorId;
    }

    public void setTutorId(String tutorId) {
        TutorId = tutorId;
    }

    public String getAppointmentDay() {
        return appointmentDay;
    }

    public void setAppointmentDay(String appointmentDay) {
        this.appointmentDay = appointmentDay;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }
}