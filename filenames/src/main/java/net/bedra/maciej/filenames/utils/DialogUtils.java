package net.bedra.maciej.filenames.utils;

import javafx.scene.control.Alert;
import net.bedra.maciej.mblogging.Logger;

/**
 * Construct all kind of simplified dialog boxes.
 *
 * @author Maciej Bedra
 */
public class DialogUtils {

	private static Logger log = Logger.getLogger();

	/**
	 * Construct alert dialog box with message.
	 *
	 * @param message message about alert
	 */
	public static void alert(String message) {
		log.debug("Constructing alert dialog box with message [message = {}]...", message);
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		log.debug("Alert dialog box constructed, showing it to user...");
		alert.showAndWait();
		log.debug("Alert dialog box closed");
	}

}
