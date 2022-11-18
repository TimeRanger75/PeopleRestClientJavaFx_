package hu.petrik.peoplerestclientjavafx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class CreatePersonController extends Controller {

    @FXML
    private Spinner<Integer> agefield;
    @FXML
    private TextField namefield;
    @FXML
    private TextField emailfield;
    @FXML
    private Button submitbutton;

    @FXML
    private void initialize(){
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,200,30);
        agefield.setValueFactory(valueFactory);
    }

    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String name=namefield.getText().trim();
        String email=emailfield.getText().trim();
        int age=agefield.getValue();
        if (name.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Figyelmeztetés");
            alert.setHeaderText("Név megadása kötelező");
            alert.showAndWait();
            return;
        }
        if (email.isEmpty()){
            warning("Email megadása kötelező");
            return;
        }
        Person newPerson=new Person(0,name,email,age);
        Gson converter=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json=converter.toJson(newPerson);
        try {
            Response response= RequestHandler.post(App.BASE_URL, json);
            if (response.getResponseCode()==201){
                namefield.setText("");
                emailfield.setText("");
                agefield.getValueFactory().setValue(30);
            }else{
                error("Hiba történt a felvétel során");

            }
        } catch (IOException e) {
            error("Nem sikerült kapcsolódni a szerverhez");

        }
    }
}
