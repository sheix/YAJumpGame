package mainJump;

import java.io.Serializable;
import java.util.Random;

public class PlatformBuilder implements Serializable {
    private final VisibleArea visibleArea;

    public PlatformBuilder(VisibleArea visibleArea) {
        this.visibleArea = visibleArea;
    }

    void TryMakePlatform(Random r) {
        if (ShouldMakePlatform(r)) {
            MakePlatform(r);
        }
    }

    void MakePlatform(Random r) {
        int width = (int) ((1 / Math.log(visibleArea.getScore())) * (visibleArea.getSize().width)) / 3;
        int x = SetXPositionForPlatform(r, width);
        Platform p = new Platform(x, x + width, 10);
        visibleArea.getObjects().add(p);
    }

    int SetXPositionForPlatform(Random r, int width) {
        return r.nextInt(visibleArea.getSize().width - width);
    }

    boolean ShouldMakePlatform(Random r) {
        return r.nextInt(10 * (1 + (int) (1 / Math.log(visibleArea.getScore())))) == 0;
    }
}