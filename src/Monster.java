public class Monster {
    private String name;
    private double Power;

    public Monster(String name, double power) {
        this.name = name;
        Power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPower() {
        return Power;
    }

    public void setPower(double power) {
        Power = power;
    }
}
