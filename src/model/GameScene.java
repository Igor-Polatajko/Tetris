package model;

import javafx.scene.Scene;

public class GameScene {
    private Scene scene;
    private Object controller;

    public GameScene(Scene scene, Object controller) {
        this.scene = scene;
        this.controller = controller;
    }

    public Scene getScene() {
        return scene;
    }

    public Object getController() {
        return controller;
    }
}
