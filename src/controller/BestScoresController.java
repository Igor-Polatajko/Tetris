package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Score;

public class BestScoresController {
    @FXML
    private VBox bestScoresContainer;

    private Score score = Score.getObj();
    private SceneController sceneController = SceneController.getObj();
    private int scoresToShow = 50;

    @FXML
    private void initialize(){

       for(int i = 0; i < scoresToShow; i++){
           HBox item = new HBox();
           Label numberItem = new Label("" + (i + 1) + ". ");
           Label scoreItem;

           if(i < score.getBestScores().size()) {
               scoreItem = new Label("" + score.getBestScores().get(i));
           }
           else {
               scoreItem = new Label("-------");
           }


           scoreItem.setStyle("-fx-font-weight: bold");
           numberItem.setFont(Font.font("Comic Sans MS", 22));
           scoreItem.setFont(Font.font("Comic Sans MS", 22));
           item.getChildren().addAll(numberItem, scoreItem);
           bestScoresContainer.getChildren().add(item);
       }
    }

    @FXML
    private void onMenuButtonClick(){
        sceneController.setSceneState(SceneStates.MENU);
    }
}
