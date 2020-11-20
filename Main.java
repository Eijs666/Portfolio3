package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    static  String url = "jdbc:sqlite:D:/PC/Datalogi/5. Semester/PORT3/Portfolio.db";
    static StudentModel SDB = new StudentModel(url);

    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller control = new Controller(SDB);
        StudentView view=new StudentView(SDB, control);
        control.setView(view);

        primaryStage.setTitle("Student Course Management");
        primaryStage.setScene(new Scene(view.asParent(), 600, 475));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:D:/PC/Datalogi/5. Semester/Portfolio3/DATABASE/PortfolioDB.db";
            conn = DriverManager.getConnection(url);
            String var4 = "SELECT * FROM Student";
        } catch (SQLException var5) {
            var5.printStackTrace();
        }
    }
}
