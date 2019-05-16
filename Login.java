import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Login {

    @FXML
    TextField userName;

    @FXML
    TextField password;

    @FXML
    Label error;

    @FXML
    Button loginButton;

    MainController mainController;

    public void setController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void login() {
        String userName = this.userName.getText();
        String password = this.password.getText();
        if (mainController.weightLiftModel.login(userName, password)) {
            mainController.displayPlayersList();
        } else {
            error.setVisible(true);
        }
    }

    public void refresh() {
    }
}
