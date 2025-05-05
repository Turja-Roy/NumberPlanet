package actions.ds;

import ui.Fireball;

public class Pair {
    private Fireball droplet;
    private Fireball target;

    public Pair () {
        this.droplet = null;
        this.target = null;
    }
    public Pair (Fireball droplet) {
        this.droplet = droplet;
        this.target = null;
    }
    public Pair (Fireball droplet, Fireball target) {
        this.droplet = droplet;
        this.target = target;
    }

    // Getters
    public Fireball getDroplet() { return droplet; }
    public Fireball getTarget() { return target; }
    // Setters
    public void setDroplet(Fireball droplet) { this.droplet = droplet; }
    public void setTarget(Fireball target) { this.target = target; }
}
