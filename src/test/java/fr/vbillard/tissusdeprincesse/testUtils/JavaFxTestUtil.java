package fr.vbillard.tissusdeprincesse.testUtils;

import javafx.application.Platform;

public class JavaFxTestUtil {

    private static boolean plateformHasStarted = false;

    public static void initJavaFx() {
        if (!plateformHasStarted) {
            Platform.setImplicitExit(false);
            Platform.startup(() -> {
            });
            plateformHasStarted = true;
        }
    }
}