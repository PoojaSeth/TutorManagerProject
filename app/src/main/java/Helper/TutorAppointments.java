package Helper;

public class TutorAppointments {
    private String appointmentDay;
    private String appointmentTime;
    private String courseName;
    private String studentName;
    private String tutorName;
    private String TutorId;
    private String StudentId;
    private String id;


    public TutorAppointments() {
    }

    public TutorAppointments(String appointmentDay, String appointmentTime, String courseName, String studentName, String tutorName, String tutorId, String studentId, String id) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.courseName = courseName;
        this.studentName = studentName;
        this.tutorName = tutorName;
        TutorId = tutorId;
        StudentId = studentId;
        this.id = id;
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

    public String getTutorId() {
        return TutorId;
    }

    public void setTutorId(String tutorId) {
        TutorId = tutorId;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
