package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DocumentoService;
import model.services.TipoService;

public class MainViewController implements Initializable{

	@FXML
	private MenuItem menuItemDocumento;
	
	@FXML
	private MenuItem menuItemFuncionario;
	
	@FXML
	private MenuItem menuItemTipo;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemDocumentoAction() {
		loadView("/gui/DocumentosList.fxml", (DocumentoListController controller) -> {
			controller.setDocumentoService(new DocumentoService());
			controller.updateTableView();
		});
	}
	@FXML
	public void onMenuItemFuncionarioAction() {
		System.out.println("onMenuItemFuncionarioAction");
	}
	@FXML
	public void onMenuItemTipoAction() {
		loadView("/gui/TipoList.fxml", (TipoListController controller) -> {
			controller.setTipoService(new TipoService());
			controller.updateTableView();
		});
	}
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {	
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {  
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			Scene mainScene = Main.getMainScene();
			VBox mainVbox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVbox.getChildren().get(0);
			mainVbox.getChildren().clear();
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVbox.getChildren());
			
			T controller = loader.getController();
			initializingAction.accept(controller);
		} 
		catch (IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar página", e.getMessage(), AlertType.ERROR);
		}
	}
}
