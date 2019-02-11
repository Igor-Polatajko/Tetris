package controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneController sceneController = SceneController.getObj();
        sceneController.init(primaryStage);
        sceneController.setSceneState(SceneStates.MENU);
    }
}
