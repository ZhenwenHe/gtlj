package cn.edu.cug.cs.gtl.app;/**
 * Created by ZhenwenHe on 2017/4/3.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane rootPane = new StackPane();
        Scene s = new Scene(rootPane, 400, 300);

        Button btn = new Button("Test");
        btn.setOnAction((e) ->
                {
                    System.out.println("Test");
                }
        );

        rootPane.getChildren().add(btn);


        primaryStage.setTitle("Test");
        primaryStage.setScene(s);

        primaryStage.show();
    }
}
