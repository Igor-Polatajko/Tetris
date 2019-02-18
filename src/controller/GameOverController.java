package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Score;



public class GameOverController {
    @FXML
    private Label currentScoreLabel;

    @FXML
    private Label bestScoreLabel;

    private Score score = Score.getObj();
    private SceneController sceneController = SceneController.getObj();

    @FXML
    private void initialize(){
        currentScoreLabel.setText("Your score: " + score.getValue());
        bestScoreLabel.setText("Best score: " + score.getBestScore());
    }


    @FXML
    private void onMenuButtonClick(){
       sceneController.setSceneState(SceneStates.MENU);
    }
}
