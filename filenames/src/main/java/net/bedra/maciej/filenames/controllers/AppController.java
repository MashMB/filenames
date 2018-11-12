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
import net.bedra.maciej.filenames.enums.FilesMode;
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
	 * Get chosen by user mode from radio buttons.
	 *
	 * @return String complete mode strategy shortcut
	 */
	private String getMode() {
		log.debug("Getting strategy chosen by user...");
		RadioButton nameRadio = (RadioButton) nameMode.getSelectedToggle();
		RadioButton extRadio = (RadioButton) extMode.getSelectedToggle();
		String fullMode = nameRadio.getId() + "," + extRadio.getId();
		log.debug("Strategy chosen by user [strategy = {}]", fullMode);

		switch (fullMode) {
			case "allMode,allFiles":
				return FilesMode.ALL_ALL.getMode();

			case "allMode,extFiles":
				return FilesMode.ALL_EXTENSION.getMode();

			case "continuousMode,allFiles":
				return FilesMode.CONTINUOUS_ALL.getMode();

			case "continuousMode,extFiles":
				return FilesMode.CONTINUOUS_EXTENSION.getMode();

			default:
				throw new RuntimeException("Mode do not exists for: " + fullMode + " toggles");
		}
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

			case "scanButton":
				appService.scan(
					dirTextField.getText().trim(),
					coreNameField.getText().trim(),
					startNumberField.getText().trim(),
					extensionField.getText().trim(),
					getMode(),
					userLogArea
				);
				break;

			case "startButton":
				appService.start(
					dirTextField.getText().trim(),
					coreNameField.getText().trim(),
					startNumberField.getText().trim(),
					extensionField.getText().trim(),
					getMode(),
					userLogArea
				);
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
		File chosenDir = dirChooser.showDialog(dirTextField.getScene().getWindow());

		if (chosenDir != null) {
			dirTextField.setText(chosenDir.getAbsolutePath());
		}

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
