package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Tipo;
import model.services.TipoService;

public class TipoListController implements Initializable, DataChangeListener {

	@FXML
	private TableView<Tipo> tableViewTipo;

	@FXML
	private TableColumn<Tipo, Integer> tableColumnId;

	@FXML
	private TableColumn<Tipo, String> tableColumnName;

	@FXML
	private TableColumn<Tipo, Tipo> tableColumnEDIT;

	@FXML
	private TableColumn<Tipo, Tipo> tableColumnREMOVE;

	@FXML
	private Button btNovo;

	private TipoService service;

	private ObservableList<Tipo> obsList;

	@FXML
	public void OnBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Tipo obj = new Tipo();
		createDialogForm(obj, "/gui/TipoForm.fxml", parentStage);
	}

	public void setTipoService(TipoService service) {
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
		tableViewTipo.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service estava Nulo");
		}
		List<Tipo> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewTipo.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Tipo obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			TipoFormController controller = loader.getController();
			controller.setTipo(obj);
			controller.setTipoService(new TipoService());
			controller.subscribeDataChangeListener(this);
			controller.updateFomrData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados do tipo");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Tipo, Tipo>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Tipo obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/TipoForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Tipo, Tipo>() {
			private final Button button = new Button("remover");

			@Override
			protected void updateItem(Tipo obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Tipo obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o", "Tem certeza que quer remover este tipo?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("service est� nulo");
			}
			try {
				service.remove(obj);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
}