package testtask;

import testtask.controller.HttpController;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        HttpController controller = null;
        try {
            controller = new HttpController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        controller.start();
    }
}
