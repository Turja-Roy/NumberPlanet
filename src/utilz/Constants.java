package utilz;

import main.GameFrame;
import main.ImplementedDS;

public class Constants {

    // public static final ImplementedDS IMPLEMENTED_DS = ImplementedDS.BINARYSEARCHTREE;
    public static final ImplementedDS IMPLEMENTED_DS = ImplementedDS.HASHTABLE;

    public static class GameConstants {
        public static final int GAMEWIDTH = 1300;
        public static final int GAMEHEIGHT = 800;
        public static final int SHOTS_PER_ROUND = 5;
        public static final int WESTPANEL_WIDTH = 50;
        public static final int WESTPANEL_STACK_LENGTH = 11;
        public static final int SOUTHPANEL_HEIGHT = 100;
    }

    public static class DropletConstants {
        public static final int DROPLET_WIDTH = 50;
        public static final int DROPLET_HEIGHT = 50;
        public static final double DROPLET_SPEED = 2.0;
    }

    public static class FireballConstants {
        public static final int FIREBALL_WIDTH = 50;
        public static final int FIREBALL_HEIGHT = 50;
        public static final double FIREBALL_SPEED = 1.0;
    }

    public static class BucketConstants {
        public static final int BUCKET_WIDTH = 50;
        public static final int BUCKET_HEIGHT = 50;
    }

    public static class CannonConstants {
        public static final int CANNON_WIDTH = 50;
        public static final int CANNON_HEIGHT = 60;
    }
}
