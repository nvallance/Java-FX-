import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeightLift {

    @FXML
    ChoiceBox<String> liftType;
    @FXML
    RadioButton liftStatusSuccess;
    @FXML
    RadioButton liftStatusFailed;
    @FXML
    TextField liftedWeight;
    @FXML
    Label lifterName;
    @FXML
    Label lifterGender;
    @FXML
    Label lifterWeight;
    @FXML
    Button submitButton;

    @FXML
    Label lab1;
    @FXML
    Label lab2;
    @FXML
    Label lab3;
    @FXML
    Label lab4;
    @FXML
    Label lab5;
    @FXML
    Label lab6;
    @FXML
    Label lab7;
    @FXML
    Label lab8;
    @FXML
    Label lab9;
    @FXML
    Label lab10;


    MainController mainController;

    public void setController(MainController mainController) {
        this.mainController = mainController;

        liftedWeight.textProperty().addListener((observable, oldValue, newValue) -> {
            onTextChange();
        });
    }

    public void refresh() {
        Player activePlayer = mainController.weightLiftModel.getActivePlayer();
        lifterName.setText(activePlayer.getName());
        lifterGender.setText(activePlayer.getGender());
        lifterWeight.setText(String.format("%.01f KG", activePlayer.getWeight()));

        List<String> liftTypes = new ArrayList<>();
        if (activePlayer.getPendingIndex(Player.LiftType.BENCH_PRESS) >= 0) {
            liftTypes.add("Bench Press " + (activePlayer.getPendingIndex(Player.LiftType.BENCH_PRESS) + 1));
        }
        if (activePlayer.getPendingIndex(Player.LiftType.SQUAT) >= 0) {
            liftTypes.add("Squat " + (activePlayer.getPendingIndex(Player.LiftType.SQUAT) + 1));
        }
        if (activePlayer.getPendingIndex(Player.LiftType.DEADLIFT) >= 0) {
            liftTypes.add("Dead Lift " + (activePlayer.getPendingIndex(Player.LiftType.DEADLIFT) + 1));
        }
        liftType.setItems(FXCollections.observableArrayList(liftTypes));
    }

    @FXML
    public void addLift() {
        String selectedItem = liftType.getSelectionModel().getSelectedItem();
        boolean good = true;

        removeError(liftType);
        removeError(liftedWeight);

        if (selectedItem == null || selectedItem.isEmpty()) {
            addError(liftType);
            good = false;
        }

        Double weight = null;
        try {
            weight = (Double.parseDouble(liftedWeight.getText()));
        } catch (Exception e) {
        }

        int[] dist = null;
        if (weight == null) {
            addError(liftedWeight);
            good = false;
        } else {
            dist = getDistribution(weight);
        }

        if (dist == null) {
            addError(liftedWeight);
            good = false;
        }

        boolean isSuccess = liftStatusSuccess.isSelected();
        if (good) {
            Player.Lift lift = new Player.Lift();
            if (selectedItem.contains("Bench")) {
                lift.liftType = Player.LiftType.BENCH_PRESS;
            } else if (selectedItem.contains("Squat")) {
                lift.liftType = Player.LiftType.SQUAT;
            } else {
                lift.liftType = Player.LiftType.DEADLIFT;
            }

            lift.success = isSuccess;
            lift.weight = weight;

            mainController.weightLiftModel.addLift(lift);
            mainController.displayPlayersList();
        }
    }

    @FXML
    public void onTextChange() {
        Double weight = null;
        try {
            weight = Double.parseDouble(liftedWeight.getText());
        } catch (Exception e) {
        }
        int[] arr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] realArr = null;

        if (weight != null) {
            realArr = getDistribution(weight);
        }

        if (realArr != null) {
            arr = realArr;
        }

        lab1.setText("" + arr[0]);
        lab2.setText("" + arr[1]);
        lab3.setText("" + arr[2]);
        lab4.setText("" + arr[3]);
        lab5.setText("" + arr[4]);
        lab6.setText("" + arr[5]);
        lab7.setText("" + arr[6]);
        lab8.setText("" + arr[7]);
        lab9.setText("" + arr[8]);
        lab10.setText("" + arr[9]);
    }

    public void addError(TextField textField) {
        ObservableList<String> styleClass = textField.getStyleClass();
        styleClass.add("error");
    }

    public void removeError(TextField textField) {
        ObservableList<String> styleClass = textField.getStyleClass();
        styleClass.removeAll(Collections.singleton("error"));
    }

    public void addError(ChoiceBox textField) {
        ObservableList<String> styleClass = textField.getStyleClass();
        styleClass.add("error");
    }

    public void removeError(ChoiceBox textField) {
        ObservableList<String> styleClass = textField.getStyleClass();
        styleClass.removeAll(Collections.singleton("error"));
    }
    public int[] getDistribution(double weight) {
weight=(weight/2)-10;
        int[] arr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] avbl = {50, 25, 20, 15, 10, 5, 2.5, 1.25, 0.5, 0.25};
        int index = 0;
        while (index < avbl.length) {
            if (weight < avbl[index]) {
                index++;
            } else {
                arr[index] +=2;
                weight = (weight) - avbl[index];
            }
        }
        if (weight == 0) {
            return arr;
        }
        return null;
    }
}
