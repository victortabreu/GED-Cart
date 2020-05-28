package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Documento;
import model.services.DocumentoService;

public class DocumentoListController implements Initializable{

	@FXML
	private TableView<Documento> tableViewDocumento;
	
	@FXML
	private TableColumn<Documento, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Documento, String> tableColumnName;
	
	@FXML
	private Button btNovo;

	private DocumentoService service;
	
	private ObservableList<Documento> obsList;

	
	@FXML
	public void OnBtNovoAction() {
		System.out.println("OnBtNovoAction");
	}
		
	public void setDocumentoService(DocumentoService service) {
		this.service = service;
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
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service estava Nulo");
		}
		List<Documento> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDocumento.setItems(obsList);
	}

}
