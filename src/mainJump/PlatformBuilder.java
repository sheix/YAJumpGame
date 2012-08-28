package mainJump;

import java.io.Serializable;
import java.util.Random;

public class PlatformBuilder implements Serializable {
    private final VisibleArea visibleArea;

    public PlatformBuilder(VisibleArea visibleArea) {
        this.visibleArea = visibleArea;
    }

    public void MakeInitialPlatforms()
    {
        for (int i = 0; i< 10; i++){
        Platform p = new Platform(i*10 ,visibleArea.getSize().width- i*10, visibleArea.getSize().height - i*10 - dataModel.INSTANCE.getHeightPercent(30));
        visibleArea.getObjects().add(p);
        }
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
        return r.nextInt(visibleArea.getSize().width - width + 1);
    }

    boolean ShouldMakePlatform(Random r) {
        return r.nextInt(10 * (1 + (int) (1 / Math.log(visibleArea.getScore())))) == 0;
    }
}