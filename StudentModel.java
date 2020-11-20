package sample;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class StudentModel {
    Connection conn= null;
    Statement stmt =null;

    PreparedStatement pstmt=null;
    PreparedStatement pstmt2=null;
    PreparedStatement pstmt3=null;
    PreparedStatement pstmt4=null;

    String url;
    public StudentModel(String url){
        this.url=url;
    }

    public void connect() throws SQLException {
        conn=getConnection(this.url);
    }
    public void CreateStatement() throws SQLException{
        this.stmt=conn.createStatement();
    }
    public ArrayList<String> StudentNameQuerystmt(){
        ArrayList<String> StudentNames = new ArrayList<String>();
        String sql="SELECT Name FROM Student ORDER BY Name;";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            while (rs!=null && rs.next()){
                String name=rs.getString(1);
                StudentNames.add(name);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return StudentNames;
    }


    public ArrayList<String> CourseNameQuerystmt(){
        ArrayList<String> CourseNames = new ArrayList<String>();
        String sql="SELECT CourseID FROM Course ORDER BY CourseID;";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            while (rs!=null && rs.next()){
                String name=rs.getString(1);
                CourseNames.add(name);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return CourseNames;
    }

    public ArrayList<Student> getStudentInfo(String studentName_){
        ArrayList<Student> students = new ArrayList<Student>();
        try {
            pstmt.setString(1, studentName_);
            ResultSet rs = pstmt.executeQuery();
            while (rs!=null && rs.next()){
                Student student = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4));
                students.add(student);
            }
        }catch(SQLException e)
        {
            e.printStackTrace();

        }
        return students;
    }

    public ArrayList<Grade> getCourseGrades(String CourseGrade){
        ArrayList<Grade> courseGrades = new ArrayList<Grade>();
        try {
            pstmt3.setString(1, CourseGrade);
            ResultSet rs = pstmt3.executeQuery();
            while (rs!=null && rs.next()){
                Grade grade = new Grade(rs.getString(1), rs.getFloat(2));
                courseGrades.add(grade);
            }

        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Error ");

        }
        return courseGrades;

    }

    public  void preparedStmtToFromQuery(){
        String sql=" SELECT D1.StudentID, D1.Name, D2.CourseName, D3.Grade " +
                "FROM Grade as D3 " +
                "JOIN Student as D1 ON D3.StudentID = D1.StudentID " +
                "JOIN Course as D2 ON D3.CourseID = D2.CourseID " +
                "WHERE D1.Name = ?;";

        String sqlCourse=" SELECT D1.Name, D2.CourseName, D3.Grade " +
                "FROM Grade as D3 " +
                "JOIN Student as D1 ON D3.StudentID = D1.StudentID " +
                "JOIN Course as D2 ON D3.CourseID = D2.CourseID " +
                "WHERE D2.CourseName = ?; ";

        String sqlAverageGrade= " SELECT avg(D3.Grade) " +
                "FROM Grade as D3 " +
                "JOIN Student as D1 ON D3.StudentID = D1.StudentID " +
                "JOIN Course as D2 ON D3.CourseID = D2.CourseID " +
                "WHERE D1.Name = ?;";

        String sqlAverageCourseGrade= " SELECT Course.CourseName, avg(Grade) " +
                "FROM Grade as Grade " +
                "JOIN Course ON Grade.CourseID = Course.CourseID " +
                "WHERE CourseName = ?; ";
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt2 = conn.prepareStatement(sqlAverageGrade);
            pstmt3 = conn.prepareStatement(sqlAverageCourseGrade);
            pstmt4 = conn.prepareStatement(sqlCourse);

        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("This is bad");
            System.out.println(e.getMessage());
        }
    }
}

class Student{
    Integer StudentID;
    String studentName;
    String studentCourse;
    Float studentGrade;

    public Student(Integer StudentID_,String studentName_, String studentCourse_, float studentGrade_){
        this.StudentID = StudentID_;
        this.studentName=studentName_;
        this.studentCourse=studentCourse_;
        this.studentGrade = studentGrade_;
    }
}

class Grade{
    String courseName;
    float courseGrade;

    public Grade(String courseName_, float courseGrade_){
        this.courseName = courseName_;
        this.courseGrade = courseGrade_;
    }
}