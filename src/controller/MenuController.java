package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {

    private SceneController sceneController = SceneController.getObj();

    @FXML
    private Button toGame;

    @FXML
    private void toGameBtnClick(){
        sceneController.setSceneState(SceneStates.GAME_FIELD);
    }

    @FXML
    private void BestScoresBtnClick(){
        sceneController.setSceneState(SceneStates.BEST_SCORES);
    }

    @FXML
    private void ExitBtnClick(){
        System.exit(0);
    }
}
