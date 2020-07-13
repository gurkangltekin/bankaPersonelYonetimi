package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    private static Stage primaryStage;
    private static Parent root;
    private static Personel selectedPersonel;
    private static User user;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Main.primaryStage.setTitle("Giriş Yap");
        Main.primaryStage.setScene(new Scene(root, 600, 400));
        Main.primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void setTitle(String title){
        Main.primaryStage.setTitle(title);
    }

    public static void goToPage(String page) throws Exception{
        Main.root = FXMLLoader.load(Main.class.getResource(page));
        Main.primaryStage.setScene(new Scene(root, 600, 400));
        Main.primaryStage.show();
    }

    public static Personel getSelectedPersonel() {
        return Main.selectedPersonel;
    }

    public static void setSelectedPersonel(Personel selectedPersonel) {
        Main.selectedPersonel = selectedPersonel;
    }

    public static User getUser() {
        if(Main.user == null)
            Main.user = new User();
        return Main.user;
    }

    public static void setUser(User user) {
        Main.user = user;
    }
}
