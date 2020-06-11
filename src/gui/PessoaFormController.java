package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Pessoa;
import model.exceptions.ValidationException;
import model.services.PessoaService;

public class PessoaFormController implements Initializable {

	private Pessoa entity;

	private PessoaService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtCpf;
	
	@FXML
	private TextField txtRg;

	@FXML
	private Label labelErrorName;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	public void setPessoa(Pessoa entity) {
		this.entity = entity;
	}

	public void setPessoaService(PessoaService service) {
		this.service = service;
	}
	
	//Retorna os dados digitados no formul�rio
	private Pessoa getFormData() {
		Pessoa obj = new Pessoa();
		
		ValidationException exception = new ValidationException("Erro de valida��o");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "O Campo n�o pode ser vazio");
		}
		obj.setName(txtName.getText());
		
		if(txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addError("cpf", "O Campo n�o pode ser vazio");
		}
		obj.setCpf(txtCpf.getText());
		
		obj.setRg(txtRg.getText());
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return obj;
	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("A entidade estava nula");
		}
		if(service == null) {
			throw new IllegalStateException("O servi�o estava nulo");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch(DbException e) {
			Alerts.showAlert("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}		
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
		Constraints.setTextFieldMaxLength(txtName, 50);
		Constraints.setTextFieldMaxLength(txtCpf, 14);
		Constraints.setTextFieldMaxLength(txtRg, 20);
	}

	public void updateFomrData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtCpf.setText(entity.getCpf());
		txtRg.setText(entity.getRg());
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
		
	}

}