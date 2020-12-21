package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class Main extends Application {

    Scene scene1,scene2;
    TableView<Arete> table;
    String src, dest;
    int destination_index, source_index;
    TextField poids;

    ArrayList<Arete> aretes = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("New window");

        Button button = new Button();
        button.setText("Click me !");
        button.setOnAction(e->System.out.println("Clicked"));

        Label label_scene1 = new Label("Welcome to the first scene");
        Button scene1_btn = new Button("go validate your age");
        scene1_btn.setOnAction(e -> primaryStage.setScene(scene2));

        Label label_scene2 = new Label("Welcome to the second scene");
        Button scene2_btn = new Button("Take me to scene 1");
        scene2_btn.setOnAction( e-> primaryStage.setScene(scene1));


        Button alert_btn = new Button("Alert");
        alert_btn.setOnAction(e->AlertBox.display("Alert box","Please be alert !"));

        Button confirm_btn = new Button("Confirm");
        confirm_btn.setOnAction(e -> {
            boolean result = ConfirmBox.display("Confirm box", "Are you sure you want to go ?");
            System.out.println(result);
        });

        TextField age = new TextField();
        Button validate_username_btn = new Button("Submit");
        validate_username_btn.setOnAction( e-> {
            System.out.println(age.getText());
            boolean result = is_int(age.getText());
            if (!result){
                AlertBox.display("Invalid ", "Please enter  number");
            }
        });

        //Supposedly dynamic array
        ArrayList<String> data = new ArrayList<>();
        data.add("Country 1");
        data.add("Country 2");
        data.add("Country 3");
        data.add("Country 4");

        ChoiceBox<String> list = new ChoiceBox<>();
        list.setValue(data.get(0));

        //Fill list dynamically
        for (String datum : data) {
            list.getItems().addAll(datum);
        }

        //Get selected Item
        list.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            source_index = data.indexOf(newValue);
            System.out.println(source_index);
        } );

        //Create Table
        //Source Column
        TableColumn<Arete,String>  sourceColumn = new TableColumn<>("Source");
        sourceColumn.setMaxWidth(200);
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("Source"));
        //Destination Column
        TableColumn<Arete,String>  destinationColumn = new TableColumn<>("Destination");
        destinationColumn.setMaxWidth(200);
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("Destination"));
        //Destination Column
        TableColumn<Arete,String>  poidsColumn = new TableColumn<>("Poids");
        poidsColumn.setMaxWidth(200);
        poidsColumn.setCellValueFactory(new PropertyValueFactory<>("Poids"));


        Label source = new Label("Source");
        Label destination = new Label("Destination");

        poids = new TextField();
        poids.setPromptText("Poids entre les deux sommets");

        //Supposedly dynamic array
        ArrayList<String> nodes = new ArrayList<>();
        nodes.add("Staff");
        nodes.add("Etudiants");
        nodes.add("Admin");
        nodes.add("Tech");

        ChoiceBox<String> nodesList_source = new ChoiceBox<>();

        //Fill list dynamically
        for (String datum : nodes) {
            nodesList_source.getItems().addAll(datum);
        }
        //Get selected Item
        nodesList_source.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            src = newValue;
            int index = nodes.indexOf(newValue);
            System.out.println("Source "+index);
        } );

        ChoiceBox<String> nodesList_destination = new ChoiceBox<>();

        //Fill list dynamically
        for (String datum : nodes) {
            nodesList_destination.getItems().addAll(datum);
        }
        //Get selected Item
        nodesList_destination.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            dest = newValue;
            destination_index = nodes.indexOf(newValue);
            System.out.println("Destination "+destination_index);
        } );

        Button add_btn = new Button("Add item");
        Button delete_btn = new Button("Delete selected");

        add_btn.setOnAction(e->addArete());
        delete_btn.setOnAction(e -> deleteArete());
        //Finishing the table
        table = new TableView<>();
        table.setItems(getAretes());
        table.getColumns().addAll(sourceColumn,destinationColumn,poidsColumn);

        VBox layout_scene1 = new VBox(20);

        layout_scene1.getChildren().addAll(label_scene1,scene1_btn,alert_btn,confirm_btn,table,source,nodesList_source,destination,nodesList_destination,poids,add_btn,delete_btn);
        scene1 = new Scene(layout_scene1,800,600);

        VBox layout_scene2 = new VBox(20);
        layout_scene2.getChildren().addAll(label_scene2,age,validate_username_btn,scene2_btn,list);
        scene2 = new Scene(layout_scene2,400,300);

        primaryStage.setScene(scene1);
        primaryStage.show();
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
