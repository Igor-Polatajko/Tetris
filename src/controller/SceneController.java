package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
        stage.setResizable(false);
    }

    public void setSceneState(SceneStates sceneState) {
        currentSceneState = sceneState;
        handleSceneState();
    }

    private void handleSceneState() {
        switch (currentSceneState) {
            case MENU:
                Scene menuScene = getMenuScene();
                stage.setScene(menuScene);
                stage.show();
                break;
            case BEST_SCORES:

                break;
            case GAME_FIELD:

                break;
            case GAME_OVER:

                break;
        }
    }


    private Scene getMenuScene() {
        Scene menuScene = null;
        try {
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("/view/menu.fxml"));
            menuScene = new Scene(root);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return menuScene != null ? menuScene : new Scene(new Label("Some errors!"), 450, 300);
    }
}
