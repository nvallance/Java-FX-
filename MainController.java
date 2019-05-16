import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import model.WeightLiftModel;

public class MainController {

    @FXML
    AnchorPane list;

    @FXML
    PlayersList listController;

    @FXML
    AnchorPane addPlayer;

    @FXML
    AddPlayer addPlayerController;

    @FXML
    AnchorPane login;

    @FXML
    Login loginController;

    @FXML
    SplitPane weightLift;

    @FXML
    WeightLift weightLiftController;

    WeightLiftModel weightLiftModel = new WeightLiftModel();

    @FXML
    public void initialize() {
        weightLiftController.setController(this);
        loginController.setController(this);
        addPlayerController.setController(this);
        listController.setController(this);

        displayLogin();
    }

    public void displayLogin() {
        list.setVisible(false);
        login.setVisible(true);
        loginController.refresh();
        addPlayer.setVisible(false);
        weightLift.setVisible(false);
    }

    public void displayPlayersList() {
        list.setVisible(true);
        listController.refresh();
        login.setVisible(false);
        addPlayer.setVisible(false);
        weightLift.setVisible(false);
    }

    public void displayWeightLift() {
        list.setVisible(false);
        login.setVisible(false);
        addPlayer.setVisible(false);
        weightLift.setVisible(true);
        weightLiftController.refresh();
    }

    public void displayAddPlayer(boolean isEditMode) {
        list.setVisible(false);
        login.setVisible(false);
        addPlayer.setVisible(true);
        addPlayerController.setIsEdit(isEditMode);
        addPlayerController.refresh();
        weightLift.setVisible(false);
    }
}
