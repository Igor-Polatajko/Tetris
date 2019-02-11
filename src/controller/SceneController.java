package controller;

import javafx.stage.Stage;

public class SceneController {

    private static SceneController sceneController = new SceneController();
    private Stage stage;
    private SceneStates currentSceneState;

    private SceneController() {
    }

    public static SceneController getObj() {
        return sceneController;
    }

    public void init(Stage stage) {
        this.stage = stage;
    }

    public void setSceneState(SceneStates sceneState) {
        currentSceneState = sceneState;
        handleSceneState();
    }

    private void handleSceneState() {

    }
}
