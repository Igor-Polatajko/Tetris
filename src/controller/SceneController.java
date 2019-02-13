package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.GameScene;

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
        stage.setOnCloseRequest(ae -> System.exit(0));
    }

    public void setSceneState(SceneStates sceneState) {
        currentSceneState = sceneState;
        handleSceneState();
    }

    private void handleSceneState() {
        switch (currentSceneState) {
            case MENU:
                GameScene menuScene = getMenuScene();
                stage.setScene(menuScene.getScene());
                break;
            case BEST_SCORES:

                break;
            case GAME_FIELD:
                GameScene gameFieldScene = getGameFieldScene();
                GameFieldController controller = (GameFieldController) gameFieldScene.getController();
                controller.setListeners(gameFieldScene.getScene());
                stage.setScene(gameFieldScene.getScene());
                break;
            case GAME_OVER:
                GameScene gameOverScene = getGameOverScene();
                stage.setScene(gameOverScene.getScene());
                break;
        }
        stage.show();
    }


    private GameScene getMenuScene() {
        Scene menuScene = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
            VBox root = (VBox) loader.load();
            menuScene = new Scene(root);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return menuScene != null ? new GameScene(menuScene, loader.getController()) : new GameScene(new Scene(new Label("Some errors!"), 450, 300), null);
    }

    private GameScene getGameFieldScene() {
        Scene gameFieldScene = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("/view/gameField.fxml"));
            HBox root = (HBox) loader.load();
            gameFieldScene = new Scene(root);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return gameFieldScene != null ? new GameScene(gameFieldScene, loader.getController()) : new GameScene(new Scene(new Label("Some errors!"), 450, 300), null);
    }

    private GameScene getGameOverScene() {
        Scene gameOverScene = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("/view/gameOver.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            gameOverScene = new Scene(root);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return gameOverScene != null ? new GameScene(gameOverScene, loader.getController()) : new GameScene(new Scene(new Label("Some errors!"), 450, 300), null);

    }
}
