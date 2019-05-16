package model;

public class Player {

    String name;
    double weight;
    String gender;
    Lift[] liftAttempts = new Lift[9];

    public Player() {
    }

    public Player(String line) {
        if (line.isEmpty()) {
            return;
        }

        String[] parts = line.split(",");
        if (parts.length != 30) {
            throw new IllegalArgumentException("" + parts.length + line);
        }

        name = parts[0];
        weight = Double.parseDouble(parts[1]);
        gender = parts[2];

        int liftIndex = 0;
        for (int i = 3; i < parts.length; i += 3) {
            if (parts[i].equals("x")) {
                liftAttempts[liftIndex++] = null;
                continue;
            }

            liftAttempts[liftIndex] = new Lift();
            liftAttempts[liftIndex].liftType = LiftType.valueOf(parts[i]);
            liftAttempts[liftIndex].success = Boolean.valueOf(parts[i + 1]);
            liftAttempts[liftIndex].weight = Double.parseDouble(parts[i + 2]);
            liftIndex++;
        }
    }

    public String toLine() {
        StringBuilder s = new StringBuilder();
        s.append(name).append(",").append(weight).append(",").append(gender);
        for (int i = 0; i < liftAttempts.length; i++) {
            if (liftAttempts[i] == null) {
                s.append(",x,x,x");
            } else {
                s.append(",").append(liftAttempts[i].liftType.name()).append(",").append(liftAttempts[i].success).append(",").append(liftAttempts[i].weight);
            }
        }
        return s.toString();
    }

    public void addLift(Lift lift) {
        int startIndex = lift.liftType == LiftType.DEADLIFT ? 6 : lift.liftType == LiftType.BENCH_PRESS ? 0 : 3;
        int offset = getPendingIndex(lift.liftType);

        if (offset < 0) {
            return;
        }

        liftAttempts[startIndex + offset] = lift;
    }

    public static class Lift {
        public LiftType liftType;
        public boolean success;
        public double weight;

        @Override
        public String toString() {
            return String.format("%.01f (%s)", weight, success ? "Success" : "Failed");
        }
    }

    public enum LiftType {
        DEADLIFT,
        BENCH_PRESS,
        SQUAT
    }

    public int getPendingIndex(LiftType liftType) {
        if (liftType == LiftType.BENCH_PRESS) {
            if (liftAttempts[0] == null) {
                return 0;
            }
            if (liftAttempts[1] == null) {
                return 1;
            }
            if (liftAttempts[2] == null) {
                return 2;
            }
            return -1;
        }

        if (liftType == LiftType.SQUAT) {
            if (liftAttempts[3] == null) {
                return 0;
            }
            if (liftAttempts[4] == null) {
                return 1;
            }
            if (liftAttempts[5] == null) {
                return 2;
            }
            return -1;
        }

        if (liftType == LiftType.DEADLIFT) {
            if (liftAttempts[6] == null) {
                return 0;
            }
            if (liftAttempts[7] == null) {
                return 1;
            }
            if (liftAttempts[8] == null) {
                return 2;
            }
            return -1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (Weight %.01f)", name, gender, weight);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Lift[] getLiftAttempts() {
        return liftAttempts;
    }

    public void setLiftAttempts(Lift[] liftAttempts) {
        this.liftAttempts = liftAttempts;
    }
}
