package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Controller {
    StudentModel model;
    StudentView view;
    public Controller(StudentModel model){
        this.model=model;
        try{
            model.connect();
            model.CreateStatement();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<String> getStudentNames(){
        ArrayList<String> Names = model.StudentNameQuerystmt();
        ObservableList<String> StudentNames = FXCollections.observableArrayList(Names);
        return StudentNames;
    }

    public ObservableList<String> getCourseNames(){
        ArrayList<String> Names = model.CourseNameQuerystmt();
        ObservableList<String> Coursenames = FXCollections.observableArrayList(Names);
        return Coursenames;
    }

    public ObservableList<Integer> getGrades(){
        ArrayList<Integer> grades = new ArrayList<Integer>(Arrays.asList(00, 02, 04, 07, 10, 12));

        ObservableList<Integer> gradesOBSList = FXCollections.observableArrayList(grades);
        return gradesOBSList;
    }


    public void setView(StudentView view){
        this.view=view;
        view.exitBtn.setOnAction(e-> Platform.exit());

        EventHandler<ActionEvent> PrintStudents = e->HandlePrintStudent(view.StudentNames.getValue(), view.textArea);
        view.FindStudent.setOnAction(PrintStudents);

        EventHandler<ActionEvent> PrintAvgGrade = e->HandlePrintAvereage(view.CourseNames.getValue(), view.textArea);
        view.FindAVGGrade.setOnAction(PrintAvgGrade);

    }

    public  void HandlePrintStudent(String studentName, TextArea textArea){
        textArea.clear();
        textArea.appendText("\n");
        model.preparedStmtToFromQuery();
        ArrayList<Student> students = model.getStudentInfo(studentName);

        for(int i=0;i<students.size();i++)
        {
            String studentname = students.get(i).studentName;
            String studentcourse = students.get(i).studentCourse;
            Float studentgrade = students.get(i).studentGrade;
            textArea.appendText(students.get(i).studentName +" - "+ students.get(i).studentCourse + " - " + students.get(i).studentGrade + "\n");
        }
    }

    public  void HandlePrintAvereage(String averageGrade, TextArea textArea){
        textArea.clear();
        textArea.appendText("\n");
        model.preparedStmtToFromQuery();
        ArrayList<Grade> grades = model.getCourseGrades(averageGrade);

        for(int i=0;i<grades.size();i++)
        {
            String studentcourse = grades.get(i).courseName;
            Float studentgrade = grades.get(i).courseGrade;
            textArea.appendText(grades.get(i).courseName +" - "+ grades.get(i).courseGrade + " - " + "\n");

        }

    }
}