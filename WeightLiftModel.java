package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WeightLiftModel {

    LoginData loginData = new LoginData();
    Player activePlayer = null;
    List<Player> playersList = new ArrayList<>();

    public WeightLiftModel() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("players.txt"));
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                playersList.add(new Player(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (playersList.size() > 0) {
            activePlayer = playersList.get(0);
        }
    }

    public boolean login(String userName, String password) {
        return loginData.login(userName, password);
    }

    public List<Player> getPlayersList() {
        return playersList;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void addPlayer(Player player) {
        playersList.add(player);
        this.activePlayer = player;
        save();
    }

    private void save() {
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream("players.txt"));
            for (Player player : playersList) {
                printStream.println(player.toLine());
            }
            printStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLift(Player.Lift lift) {
        if (activePlayer == null) {
            return;
        }

        activePlayer.addLift(lift);
        save();
    }

    public void editPlayer(Player player, String name, String gender, double weight) {
        if (player == null) {
            return;
        }

        player.setName(name);
        player.setGender(gender);
        player.setWeight(weight);
        save();
    }

    public void removePlayer(Player activePlayer) {
        playersList.remove(activePlayer);
        if (activePlayer.equals(this.activePlayer)) {
            this.activePlayer = null;
            if (playersList.size() > 0) {
                this.activePlayer = this.playersList.get(0);
            }
        }
        save();
    }
}
