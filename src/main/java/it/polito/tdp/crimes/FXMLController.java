package it.polito.tdp.crimes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

//controller turno A --> switchare al branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxCategoria;

    @FXML
    private ComboBox<Integer> boxAnno;

    @FXML
    private Button btnAnalisi;

    @FXML
    private ComboBox<Adiacenza> boxArco;

    @FXML
    private Button btnPercorso;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	if(boxArco==null) {
    		txtResult.setText("Selezionare prima un arco dalla tendina");
    		return;
    	}
    	
    	List<String> cammino = model.ricorsione(boxArco.getValue());
    	
    	if(cammino.isEmpty()) {
    		txtResult.setText("Non e' stato trovato nessun cammino che tocchi tutti i vertici");
    		return;
    	}
    	
    	txtResult.setText("Trovato cammino che tocca tutti i vertici:");
    	for(String s : cammino) {
    		txtResult.appendText("\n"+s);
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	if(boxCategoria.getValue()==null || boxAnno.getValue()==null) {
    		txtResult.setText("Selezionare prima una categoria ed un anno dai menu' a tendina");
    		return;
    	}
    	
    	model.creaGrafo(boxCategoria.getValue(), boxAnno.getValue());
    	
    	txtResult.setText("Creato grafo con "+model.getNumVertici()+" vertici "+model.getNumArchi()+" archi");
    	
    	List<Adiacenza> adiacenze = model.getMax();
    	
    	for(Adiacenza a : adiacenze) {
    		txtResult.appendText("\n"+a);
    	}
    	
    	boxArco.getItems().clear();
    	boxArco.getItems().addAll(adiacenze);
    }

    @FXML
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		boxCategoria.getItems().addAll(model.listCategorie());
		boxAnno.getItems().addAll(model.listAnni());
	}
}
