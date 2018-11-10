package net.bedra.maciej.filenames.factories;

import javafx.scene.control.TextArea;
import net.bedra.maciej.filenames.implementations.AllAllRenameImpl;
import net.bedra.maciej.filenames.interfaces.RenameInterface;

/**
 * Factory to ensure proper rename operation implementation.
 *
 * @author Maciej Bedra
 */
public class RenameFactory {

	private String directory;
	private String coreNameValue;
	private String startNumberValue;
	private TextArea userLogArea;

	/**
	 * RenameFactory constructor.
	 *
	 * @param directory        path to directory
	 * @param coreNameValue    raw value of core name (renaming
	 *                         pattern)
	 * @param startNumberValue raw value of start number pattern
	 * @param userLogArea      text area for logs visible for user
	 */
	public RenameFactory(String directory, String coreNameValue, String startNumberValue, TextArea userLogArea) {
		this.directory = directory;
		this.coreNameValue = coreNameValue;
		this.startNumberValue = startNumberValue;
		this.userLogArea = userLogArea;
	}

	/**
	 * Get proper rename operation implementation that depends
	 * on chosen mode.
	 *
	 * @param mode chosen rename operation mode
	 * @return RenameInterface rename operation implementation
	 * for chosen scan mode
	 */
	public RenameInterface getImplementation(String mode) {
		switch (mode) {
			case "AA":
				return new AllAllRenameImpl(directory, coreNameValue, startNumberValue, userLogArea);

			default:
				throw new RuntimeException("Implementation of RenameInterface not found (mode = " + mode + ")");
		}
	}

}
