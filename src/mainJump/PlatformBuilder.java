package mainJump;

import java.io.Serializable;
import java.util.Random;

public class PlatformBuilder implements Serializable {
    private final VisibleArea visibleArea;
    private Random r;
    private int defaultHeight = 10;

    public PlatformBuilder(VisibleArea visibleArea) {
        this.visibleArea = visibleArea;
        r = new Random();
    }

    public void MakeInitialPlatforms()
    {
        for (int i = 0; i< 19; i++){
        Platform p = new Platform(i*10 ,visibleArea.getSize().width- i*10, visibleArea.getSize().height - i*10 - dataModel.INSTANCE.getHeightPercent(30));
        visibleArea.getObjects().add(p);
        }
        int width = visibleArea.getSize().width/3;
        for (int i = 0; i<10; i++){
            int x = 2*(visibleArea.getSize().width/3);
            Platform p = new Platform(x, x + width, defaultHeight);
            visibleArea.getObjects().add(p);

        }
}

    void TryMakePlatform() {
        if (ShouldMakePlatform()) {
            MakePlatform(defaultHeight);
        }
    }

    void MakePlatform(int height) {
        int width = (int) ((1 / Math.log(dataModel.INSTANCE.time)) * (visibleArea.getSize().width)) / 2;
        int x = SetXPositionForPlatform(width);
        Platform p = new Platform(x, x + width, height);
        visibleArea.getObjects().add(p);
    }

    int SetXPositionForPlatform(int width) {
        return r.nextInt(visibleArea.getSize().width - width + 1);
    }

    boolean ShouldMakePlatform() {
        return r.nextInt(10 * (1 + (int) (1 / Math.log(dataModel.INSTANCE.time)))) == 0;
    }
}