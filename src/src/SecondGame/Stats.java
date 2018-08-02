
package SecondGame;

public class Stats {

    private double degree = (Math.random() / 2.0 + 0.25) * Math.PI;
    public void setStats(double degree) {
        this.degree = degree;
    }
    public double getStats() {
        return degree;
    }
}