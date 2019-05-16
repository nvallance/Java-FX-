import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Player;
import model.WeightLiftModel;

public class PlayersList {

    @FXML
    ListView<Player> listData;
    @FXML
    Label playerName;
    @FXML
    Label gender;
    @FXML
    Label weight;
    @FXML
    Label bp1;
    @FXML
    Label bp2;
    @FXML
    Label bp3;
    @FXML
    Label sq1;
    @FXML
    Label sq2;
    @FXML
    Label sq3;
    @FXML
    Label dl1;
    @FXML
    Label dl2;
    @FXML
    Label dl3;
    @FXML
    Button startLift;
    @FXML
    Button addPlayer;

    MainController mainController;

    public void setController(MainController mainController) {
        this.mainController = mainController;
        refresh();

        listData.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Player>() {

            @Override
            public void changed(ObservableValue<? extends Player> observable, Player oldValue, Player newValue) {
                WeightLiftModel weightLiftModel = mainController.weightLiftModel;
                if (newValue != null) {
                    weightLiftModel.setActivePlayer(newValue);
                    refresh();
                }
            }
        });
    }

    @FXML
    public void startLift() {
        WeightLiftModel weightLiftModel = mainController.weightLiftModel;
        if (weightLiftModel.getActivePlayer() == null) {
            return;
        }

        mainController.displayWeightLift();
    }

    @FXML
    public void addPlayer() {
        mainController.displayAddPlayer(false);
    }

    public void refresh() {
        WeightLiftModel weightLiftModel = mainController.weightLiftModel;
        listData.setItems(FXCollections.observableArrayList(weightLiftModel.getPlayersList()));
        listData.refresh();
        Player activePlayer = weightLiftModel.getActivePlayer();

        playerName.setText("---");
        gender.setText("---");
        weight.setText("---");
        bp1.setText("---");
        bp2.setText("---");
        bp3.setText("---");
        sq1.setText("---");
        sq2.setText("---");
        sq3.setText("---");
        dl1.setText("---");
        dl2.setText("---");
        dl3.setText("---");

        if (activePlayer != null) {
            listData.getSelectionModel().select(activePlayer);
            Player.Lift[] lifts = activePlayer.getLiftAttempts();
            playerName.setText(activePlayer.getName());
            gender.setText(activePlayer.getGender());
            weight.setText(String.format("%.01f", activePlayer.getWeight()));
            bp1.setText(lifts[0] != null ? lifts[0].toString() : "---");
            bp2.setText(lifts[1] != null ? lifts[1].toString() : "---");
            bp3.setText(lifts[2] != null ? lifts[2].toString() : "---");
            sq1.setText(lifts[3] != null ? lifts[3].toString() : "---");
            sq2.setText(lifts[4] != null ? lifts[4].toString() : "---");
            sq3.setText(lifts[5] != null ? lifts[5].toString() : "---");
            dl1.setText(lifts[6] != null ? lifts[6].toString() : "---");
            dl2.setText(lifts[7] != null ? lifts[7].toString() : "---");
            dl3.setText(lifts[8] != null ? lifts[8].toString() : "---");
        }
    }

    public void editPlayer() {
        WeightLiftModel weightLiftModel = mainController.weightLiftModel;
        if (weightLiftModel.getActivePlayer() == null) {
            return;
        }

        mainController.displayAddPlayer(true);
    }

    public void deletePlayer() {
        WeightLiftModel weightLiftModel = mainController.weightLiftModel;
        if (weightLiftModel.getActivePlayer() == null) {
            return;
        }

        weightLiftModel.removePlayer(weightLiftModel.getActivePlayer());
        refresh();
    }
}
