package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbException;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.entities.Documento;
import model.entities.Pessoa;
import model.entities.Tipo;
import model.exceptions.ValidationException;
import model.services.DocumentoService;
import model.services.PessoaService;
import model.services.TipoService;

public class DocumentoFormController implements Initializable {

	private Documento entity;

	private DocumentoService service;
	
	private PessoaService services;

	private TipoService tipoService;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private ComboBox<Tipo> comboBoxTipo;

	@FXML
	private Button btSave;
	
	@FXML
	private Button addPessoa;

	@FXML
	private Button btCancel;
	
	@FXML
	private TableView<Pessoa> tableViewPessoa;

	@FXML
	private TableColumn<Pessoa, Integer> tableColumnId;

	@FXML
	private TableColumn<Pessoa, String> tableColumnName;
	
	@FXML
	private TableColumn<Pessoa, String> tableColumnCpf;
	
	@FXML
	private TableColumn<Pessoa, String> tableColumnRg;

	@FXML
	private TableColumn<Pessoa, Pessoa> tableColumnEDIT;

	@FXML
	private TableColumn<Pessoa, Pessoa> tableColumnREMOVE;

	private ObservableList<Tipo> obsList;
	
	private ObservableList<Pessoa> obsLists;
	
	public void setPessoaService(PessoaService services) {
		this.services = services;
	}
	
	public void updateTableView() {
		if (services == null) {
			throw new IllegalStateException("Service estava nulo");
		}
		List<Pessoa> list = services.findAll();
		obsLists = FXCollections.observableArrayList(list);
		tableViewPessoa.setItems(obsLists);
		initEditButtons();
		initRemoveButtons();
	}
	
	// Adciona um botão de editar em cada linha da lista
		private void initEditButtons() {
			tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
			tableColumnEDIT.setCellFactory(param -> new TableCell<Pessoa, Pessoa>() {
				private final Button button = new Button("Editar");

				@Override
				protected void updateItem(Pessoa obj, boolean empty) {
					super.updateItem(obj, empty);
					if (obj == null) {
						setGraphic(null);
						return;
					}
					setGraphic(button);
					button.setOnAction(
							event -> createDialogForm(obj, "/gui/PessoaForm.fxml", Utils.currentStage(event)));
				}
			});
		}

		// Adciona um botão de remover em cada linha da lista
		private void initRemoveButtons() {
			tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
			tableColumnREMOVE.setCellFactory(param -> new TableCell<Pessoa, Pessoa>() {
				private final Button button = new Button("remover");

				@Override
				protected void updateItem(Pessoa obj, boolean empty) {
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
		
	private void removeEntity(Pessoa obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza que quer remover a pessoa?");
			
		if(result.get() == ButtonType.OK){
			if(services == null) {
				throw new IllegalStateException("service está nulo");
			}
			try {
				services.remove(obj);
				updateTableView();
			}catch(DbIntegrityException e){
				Alerts.showAlert("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}		

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
			Alerts.showAlert("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void createDialogForm(Pessoa obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			PessoaFormController controller = loader.getController();
			controller.setPessoa(obj);
			controller.setPessoaService(new PessoaService());
			controller.subscribeDataChangeListener();
			controller.updateFomrData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados do pessoa");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	public void onDataChanged() {
		updateTableView();
	}
	
	@FXML
	public void onBtAddPessoaAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Pessoa obj1 = new Pessoa();
		createDialogForm(obj1, "/gui/PessoaForm.fxml", parentStage);
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
		
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableColumnRg.setCellValueFactory(new PropertyValueFactory<>("rg"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewPessoa.prefHeightProperty().bind(stage.heightProperty());
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
