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

    Scene home_scene,insert_nodesSize_scene,nodeName_scene,kruskal_scene;

    TableView<Arete> table;

    String src, dest;
    int destination_index, source_index;
    TextField poids_textField;

    int nodes;
    int[][] matrice = new int[nodes][nodes];
    int[] parent = new int[nodes];
    int min;
    int u = 0;
    int v = 0;
    int noOfEdges = 1;
    int poids = 0;

    ArrayList<Arete> aretes = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("New window");

        Button button = new Button();
        button.setText("Click me !");
        button.setOnAction(e->System.out.println("Clicked"));

        Label label_home_scene = new Label("Home");
        Button home_scene_btn = new Button("Please insert your nodes' number");
        home_scene_btn.setOnAction(e -> primaryStage.setScene(insert_nodesSize_scene));

        Label label_insert_nodesSize_scene = new Label("Nodes' size");
        Button insert_nodesSize_scene_btn = new Button("Nodes' names");
        insert_nodesSize_scene_btn.setOnAction( e-> primaryStage.setScene(nodeName_scene));

        Label label_nodeName_scene = new Label("Nodes' size");
        Button nodeName_scene_btn = new Button("Nodes' names");
        nodeName_scene_btn.setOnAction( e-> primaryStage.setScene(kruskal_scene));


        Button alert_btn = new Button("Alert");
        alert_btn.setOnAction(e->AlertBox.display("Alert box","Please be alert !"));

        Button confirm_btn = new Button("Confirm");
        confirm_btn.setOnAction(e -> {
            boolean result = ConfirmBox.display("Confirm box", "Are you sure you want to go ?");
            System.out.println(result);
        });

        TextField node_textField = new TextField();
        Button validate_username_btn = new Button("Submit");
        validate_username_btn.setOnAction( e-> {
            System.out.println(node_textField.getText());
            boolean result = is_int(node_textField.getText());
            if (!result){
                AlertBox.display("Invalid input", "Please enter a valid number");
            }
        });

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

        poids_textField = new TextField();
        poids_textField.setPromptText("Poids entre les deux sommets");

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

        layout_scene1.getChildren().addAll(label_home_scene,home_scene_btn,alert_btn,confirm_btn,table,source,nodesList_source,destination,nodesList_destination,poids_textField,add_btn,delete_btn);
        home_scene = new Scene(layout_scene1,800,600);

        VBox layout_insert_nodesSize_scene= new VBox(20);
        layout_insert_nodesSize_scene.getChildren().addAll(label_insert_nodesSize_scene,node_textField,validate_username_btn,insert_nodesSize_scene_btn);
        insert_nodesSize_scene = new Scene(layout_insert_nodesSize_scene,800,600);

        VBox layout_nodeName_scene = new VBox(20);
        layout_nodeName_scene.getChildren().addAll(label_nodeName_scene,node_textField,validate_username_btn,insert_nodesSize_scene_btn);
        nodeName_scene = new Scene(layout_nodeName_scene,400,300);

        primaryStage.setScene(home_scene);
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
        arete.setPoids(Integer.parseInt(poids_textField.getText()));

        aretes.add(arete);
        table.getItems().add(arete);

        for (Arete value : aretes) {
            System.out.println(value.getSource()+" -> "+value.getDestination()+" Poids :"+value.getPoids());
        }
    }

    private boolean is_int(String message) {
        try{
            nodes = Integer.parseInt(message);
            System.out.println("user age is "+nodes);
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
