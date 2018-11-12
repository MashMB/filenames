package net.bedra.maciej.filenames.factories;

import javafx.scene.control.TextArea;
import net.bedra.maciej.filenames.implementations.AllAllScanImpl;
import net.bedra.maciej.filenames.implementations.AllExtensionScanImpl;
import net.bedra.maciej.filenames.implementations.ContinuousAllScanImpl;
import net.bedra.maciej.filenames.interfaces.ScanInterface;

/**
 * Factory to ensure proper scan implementation.
 *
 * @author Maciej Bedra
 */
public class ScanFactory {

	private String directory;
	private String coreNameValue;
	private String startNumberValue;
	private String extension;
	private TextArea userLogArea;

	/**
	 * ScanFactory constructor.
	 *
	 * @param directory        path to directory
	 * @param coreNameValue    raw value of core name (renaming
	 *                         pattern)
	 * @param startNumberValue raw value of start number pattern
	 * @param extension        extension of file
	 * @param userLogArea      visible user logs
	 */
	public ScanFactory(String directory, String coreNameValue, String startNumberValue, String extension, TextArea userLogArea) {
		this.directory = directory;
		this.coreNameValue = coreNameValue;
		this.startNumberValue = startNumberValue;
		this.extension = extension;
		this.userLogArea = userLogArea;
	}

	/**
	 * Get proper scan implementation that depends on chosen mode.
	 *
	 * @param mode chosen scan mode
	 * @return ScanInterface scan implementation for chosen scan mode
	 */
	public ScanInterface getImplementation(String mode) {
		switch (mode) {
			case "AA":
				return new AllAllScanImpl(directory, userLogArea);

			case "AE":
				return new AllExtensionScanImpl(directory, extension, userLogArea);

			case "CA":
				return new ContinuousAllScanImpl(directory, coreNameValue, startNumberValue, userLogArea);

			default:
				throw new RuntimeException("Implementation of ScanInterface not found (mode = " + mode + ")");
		}
	}

}
