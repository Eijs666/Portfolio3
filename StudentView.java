package sample;

import com.sun.prism.Graphics;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class StudentView {
    StudentModel model;
    Controller control;
    private GridPane Startview;
    Button exitBtn = new Button("Exit");

    Button FindStudent = new Button("Find Student");
    Button FindAVGGrade = new Button("Find Average Grade for the Course");

    Label SelectStudent = new Label("Select Student:");
    Label SelectCourse = new Label(" Select Course:");
    Label SelectGrade = new Label("Select Grade");

    //Label TimeLbl = new Label("Select earliest time:");
    TextArea textArea = new TextArea();

    ComboBox<String> StudentNames = new ComboBox<String>();
    ComboBox<String> CourseNames = new ComboBox<String>();
    ComboBox<Integer> Grades = new ComboBox<Integer>();

    public StudentView(StudentModel model, Controller control){
        this.model=model;
        this.control=control;
        createAndConfigure();
    }

    private void createAndConfigure(){
        Startview= new GridPane();
        Startview.setMinSize(300, 200);
        Startview.setPadding(new Insets(10,10,10,10));
        Startview.setVgap(5);
        Startview.setHgap(1);

        Startview.add(SelectStudent, 1,1);
        Startview.add(SelectCourse,1,3);
        Startview.add(SelectGrade, 1, 5);

        Startview.add(FindAVGGrade,14,6);
        Startview.add(FindStudent, 15, 6);

        Startview.add(textArea, 1,7,15,7);
        Startview.add(exitBtn,20,15);

        ObservableList<String> studentList = control.getStudentNames();
        ObservableList<String> courseList = control.getCourseNames();
        ObservableList<Integer> gradeList = control.getGrades();

        StudentNames.setItems(studentList);
        StudentNames.getSelectionModel().selectFirst();
        Startview.add(StudentNames,15,1);

        CourseNames.setItems(courseList);
        CourseNames.getSelectionModel().selectFirst();
        Startview.add(CourseNames,15,3);


        Grades.setItems(gradeList);
        Grades.getSelectionModel().selectFirst();
        Startview.add(Grades,15,5);

    }

    public Parent asParent(){
        return Startview;
    }
}