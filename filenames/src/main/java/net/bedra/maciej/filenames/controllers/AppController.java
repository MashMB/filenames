package net.bedra.maciej.filenames.controllers;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import net.bedra.maciej.filenames.services.AppService;
import net.bedra.maciej.mblogging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Connect application logic layer with view layer.
 *
 * @author Maciej Bedra
 */
@Controller
public class AppController {

	private static Logger log = Logger.getLogger();

	private AppService appService;

	@FXML
	private TextArea userLogArea;

	@FXML
	private TextField dirTextField;

	@FXML
	private TextField coreNameField;

	@FXML
	private TextField startNumberField;

	@FXML
	private TextField extensionField;

	@FXML
	private ToggleGroup nameMode;

	@FXML
	private ToggleGroup extMode;

	/**
	 * AppController constructor.
	 *
	 * @param appService access to application logic layer
	 */
	@Autowired
	public AppController(AppService appService) {
		this.appService = appService;
	}

	/**
	 * Handler for all buttons in app window.
	 *
	 * @param event button event
	 */
	public void buttonsHandler(ActionEvent event) {
		String btnId = ((Button) event.getSource()).getId();
		log.debug("Button clicked [button id = {}]", btnId);

		switch (btnId) {
			case "choseButton":
				choseDir();
				break;
		}
	}

	/**
	 * Chose directory by directory chooser.
	 */
	private void choseDir() {
		log.debug("Opening directory chooser...");
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Chose directory");
		dirChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File chosenDir = dirChooser.showDialog(dirTextField.getScene().getWindow());
		dirTextField.setText(chosenDir != null ? chosenDir.getAbsolutePath() : "");
		log.debug("Directory chosen by directory chooser");
	}

	/**
	 * Handler for all radio buttons in app window.
	 *
	 * @param event radio button event
	 */
	public void radioButtonsHandler(ActionEvent event) {
		String radioId = ((RadioButton) event.getSource()).getId();
		log.debug("Radio button selected [radio id = {}]", radioId);

		switch (radioId) {
			case "extFiles":
				extensionField.setDisable(false);
				break;

			case "allFiles":
				extensionField.setDisable(true);
				break;
		}
	}

}
