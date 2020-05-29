package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Documento;
import model.services.DocumentoService;

public class DocumentoListController implements Initializable, DataChangeListener{

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
	public void OnBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Documento obj = new Documento();
		createDialogForm(obj, "/gui/DocumentoForm.fxml", parentStage);
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
	private void createDialogForm(Documento obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			DocumentoFormController controller = loader.getController();
			controller.setDocumento(obj);
			controller.setDocumentoService(new DocumentoService());
			controller.subscribeDataChangeListener(this);
			controller.updateFomrData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados do documento");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		}catch(IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

}
