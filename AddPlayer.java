import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Player;

import java.util.Collections;


public class AddPlayer {

    @FXML
    TextField playerName;

    @FXML
    TextField gender;

    @FXML
    TextField weight;

    @FXML
    Button addButton;

    @FXML
    Label header;

    MainController mainController;
    private boolean isEditMode;

    public void setController(MainController mainController) {
        this.mainController = mainController;
    }

    public void refresh() {
        playerName.setText("");
        gender.setText("");
        weight.setText("");
        removeError(playerName);
        removeError(gender);
        removeError(weight);
        header.setText("Add a Player");

        if (isEditMode) {
            Player player = mainController.weightLiftModel.getActivePlayer();
            header.setText("Edit a Player");
            playerName.setText(player.getName());
            gender.setText(player.getGender());
            weight.setText("" + player.getWeight());
        }
    }

    @FXML
    public void addPlayer() {
        removeError(playerName);
        removeError(gender);
        removeError(weight);

        String name = playerName.getText();
        String gen = gender.getText();
        Double val = null;
        try {
            val = Double.parseDouble(weight.getText());
        } catch (Exception e) {
        }

        boolean good = true;
        if (name.trim().isEmpty()) {
            addError(playerName);
            good = false;
        }

        if (gen.trim().isEmpty()) {
            addError(gender);
            good = false;
        }

        if (val == null || val <= 0) {
            addError(weight);
            good = false;
        }

        Player player = new Player();
        if (isEditMode) {
            player = mainController.weightLiftModel.getActivePlayer();
        }

        if (good) {
            player.setName(name.trim());
            player.setGender(gen.trim());
            player.setWeight(val);

            if (isEditMode) {
                mainController.weightLiftModel.editPlayer(player, player.getName(), player.getGender(), player.getWeight());
            } else {
                mainController.weightLiftModel.addPlayer(player);
            }
            mainController.displayPlayersList();
        }
    }

    public void addError(TextField textField) {
        ObservableList<String> styleClass = textField.getStyleClass();
        styleClass.add("error");
    }

    public void removeError(TextField textField) {
        ObservableList<String> styleClass = textField.getStyleClass();
        styleClass.removeAll(Collections.singleton("error"));
    }

    public void setIsEdit(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }
}
