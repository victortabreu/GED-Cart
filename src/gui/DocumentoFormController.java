package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Documento;
import model.entities.Tipo;
import model.exceptions.ValidationException;
import model.services.DocumentoService;
import model.services.TipoService;

public class DocumentoFormController implements Initializable {

	private Documento entity;

	private DocumentoService service;

	private TipoService tipoService;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private ComboBox<Tipo> comboBoxTipo;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	private ObservableList<Tipo> obsList;

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	public void setDocumento(Documento entity) {
		this.entity = entity;
	}

	public void setServices(DocumentoService service, TipoService tipoService) {
		this.service = service;
		this.tipoService = tipoService;
	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("A entidade estava nula");
		}
		if (service == null) {
			throw new IllegalStateException("O serviço estava nulo");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}catch (DbException e) {
			Alerts.showAlert("Erro ao salvar objet", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Documento getFormData() {
		Documento obj = new Documento();

		ValidationException exception = new ValidationException("Erro de validação");

		obj.setId(Utils.tryParseToInt(txtId.getText()));

		obj.setTipo(comboBoxTipo.getValue());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		
		initializeComboBoxTipo();
	}

	public void updateFomrData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		
		if (entity.getTipo() == null) {
			comboBoxTipo.getSelectionModel().selectFirst();
		}else {
			comboBoxTipo.setValue(entity.getTipo());
		}
	}

	public void loadAssociatedObjects() {
		if (tipoService == null) {
			throw new IllegalStateException("Tipo estava nulo");
		}
		List<Tipo> list = tipoService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxTipo.setItems(obsList);
	}

	private void initializeComboBoxTipo() {
		Callback<ListView<Tipo>, ListCell<Tipo>> factory = lv -> new ListCell<Tipo>() {
			@Override
			protected void updateItem(Tipo item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		comboBoxTipo.setCellFactory(factory);
		comboBoxTipo.setButtonCell(factory.call(null));
	}
}
