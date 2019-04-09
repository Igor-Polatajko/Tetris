package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
                GameScene menuScene = loadScene("/view/menu.fxml");
                stage.setScene(menuScene.getScene());
                break;
            case BEST_SCORES:
                GameScene bestScoresScene = loadScene("/view/bestScores.fxml");
                stage.setScene(bestScoresScene.getScene());
                break;
            case GAME_FIELD:
                GameScene gameFieldScene = loadScene("/view/gameField.fxml");
                GameFieldController controller = (GameFieldController) gameFieldScene.getController();
                controller.setListeners(gameFieldScene.getScene());
                stage.setScene(gameFieldScene.getScene());
                break;
            case GAME_OVER:
                GameScene gameOverScene = loadScene("/view/gameOver.fxml");
                stage.setScene(gameOverScene.getScene());
                break;
        }
        stage.show();
    }


    private GameScene loadScene(String path){

        Scene scene = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            scene = new Scene(root);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return scene != null ? new GameScene(scene, loader.getController()) : new GameScene(new Scene(new Label("Some errors!"), 450, 300), null);
    }
}
