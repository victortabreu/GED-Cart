package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Documento;

public class DocumentoListController implements Initializable{

	@FXML
	private TableView<Documento> tableViewDocumento;
	
	@FXML
	private TableColumn<Documento, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Documento, String> tableColumnName;
	
	@FXML
	private Button btNovo;
	
	@FXML
	public void OnBtNovoAction() {
		System.out.println("OnBtNovoAction");
	}
		
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDocumento.prefHeightProperty().bind(stage.heightProperty());
	}

}
