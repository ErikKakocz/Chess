package game;

import ControllerInterfaces.GameplayControllerInterface;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameViewController {

    GameplayControllerInterface gameContInt;

    String sourceField = null;
    String targetField = null;

    @FXML
    Button EndTurnButton;

    @FXML
    Button CancelMoveButton;

    @FXML
    Button CowardButton;

    GameViewController() {
        CancelMoveButton.setDisable(true);

    }

    public void FieldControl(ActionEvent e) {

        Button b = (Button) e.getSource();
        if (sourceField == null) {
            sourceField = b.getAccessibleHelp();
        } else {
            targetField = b.getAccessibleHelp();
            CancelMoveButton.setDisable(false);
        }
    }

    public void cancel() {
        sourceField = null;
        targetField = null;
    }

    public void setupInterface(Object gameContInt) {
        this.gameContInt = (GameplayControllerInterface) gameContInt;
        EndTurnButton.setOnAction(new EventHandler() {
            public void handle(Event event) {
                endTurn();
            }
        });
        CancelMoveButton.setOnAction(new EventHandler() {
            public void handle(Event event) {
                cancel();
            }
        });
        CowardButton.setOnAction(new EventHandler() {
            public void handle(Event event) {
                giveUp();
            }
        });

    }

    public void endTurn() {

        gameContInt.movePiece(
                Integer.parseInt(sourceField.split(",")[0]),
                Integer.parseInt(sourceField.split(",")[1]),
                Integer.parseInt(targetField.split(",")[0]),
                Integer.parseInt(targetField.split(",")[1]));

        cancel();
    }

    public void giveUp() {
        gameContInt.giveUp();
    }
}
