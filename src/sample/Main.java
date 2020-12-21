package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application  {

    Scene scene1,scene2;
    TableView<Arete> table;
    String src, dest;
    int destination_index, source_index;
    TextField poids;

    ArrayList<Arete> aretes = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {

        TextField node_textField = new TextField();
        Button validate_username_btn = new Button("Next");
        validate_username_btn.setOnAction( e-> {
            System.out.println(node_textField.getText());
            boolean result = is_int(node_textField.getText());
            if (!result){
                AlertBox.display("Invalid input", "Please enter a number");
            }
        });

    }

    private void deleteArete() {

        ObservableList<Arete> allAretes, selectedArete;

        allAretes = table.getItems();
        selectedArete = table.getSelectionModel().getSelectedItems();
        selectedArete.forEach(allAretes::remove);
    }

    private void addArete() {
        Arete arete = new Arete();
        arete.setSource(src);
        arete.setDestination(dest);
        arete.setPoids(Integer.parseInt(poids.getText()));

        aretes.add(arete);
        table.getItems().add(arete);

        for (Arete value : aretes) {
            System.out.println(value.getSource()+" -> "+value.getDestination()+" Poids :"+value.getPoids());
        }
    }

    private boolean is_int(String message) {
        try{
            int age = Integer.parseInt(message);
            System.out.println("user age is "+age);
            return true;
        }catch (NumberFormatException e){
            System.out.println("Error "+ message);
            return false;
        }
    }

    public ObservableList<Arete> getAretes(){

        return FXCollections.observableArrayList();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
