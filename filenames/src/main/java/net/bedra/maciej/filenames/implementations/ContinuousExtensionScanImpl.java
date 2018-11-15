package net.bedra.maciej.filenames.implementations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextArea;
import net.bedra.maciej.filenames.interfaces.ScanInterface;
import net.bedra.maciej.filenames.utils.DialogUtils;
import net.bedra.maciej.filenames.utils.FileUtils;
import net.bedra.maciej.filenames.utils.TimeUtils;
import net.bedra.maciej.filenames.utils.ValidationUtils;
import net.bedra.maciej.mblogging.Logger;

/**
 * Implementation of scan operation for modes: continuous names
 * and files with extension.
 *
 * @author Maciej Bedra
 */
public class ContinuousExtensionScanImpl implements ScanInterface {

	private static Logger log = Logger.getLogger();

	private String directory;
	private String coreNameValue;
	private String startNumberValue;
	private String extension;
	private TextArea userLogArea;

	/**
	 * ContinuousExtensionScanImpl constructor.
	 *
	 * @param directory        path to directory to preform scan
	 *                         operation
	 * @param coreNameValue    raw value of core name (renaming
	 *                         pattern)
	 * @param startNumberValue raw value of start number pattern
	 * @param extension        extension for which directory should be
	 *                         scanned
	 * @param userLogArea      text area for logs visible for user
	 */
	public ContinuousExtensionScanImpl(String directory, String coreNameValue, String startNumberValue, String extension, TextArea userLogArea) {
		this.directory = directory;
		this.coreNameValue = coreNameValue;
		this.startNumberValue = startNumberValue;
		this.extension = ValidationUtils.validateFileExtension(extension);
		this.userLogArea = userLogArea;
	}

	/**
	 * Preform scan operation for modes: continuous names and files
	 * with extension.
	 */
	@Override
	public void preformScan() {
		if (FileUtils.isDirectory(directory)) {
			String coreName = ValidationUtils.validateCoreName(coreNameValue);

			if (coreName != null) {
				Integer startNumber = ValidationUtils.validateSequencePattern(startNumberValue);

				if (startNumber != null) {
					Integer patternLength = ValidationUtils.getSequencePatternLength(startNumberValue);
					log.info(
						"Starting scan in mode [continuousMode,extFiles] for extension and directory [startNumber = {}, patternLength = {}, coreName = {}, extension = {}, path = {}]...",
						startNumber,
						patternLength,
						coreName,
						extension,
						directory
					);
					userLogArea.setText("");
					userLogArea.appendText("Chosen extension: " + extension + "\n");
					userLogArea.appendText("Chosen core name: " + coreName + "\n");
					userLogArea.appendText("Start sequence: " + FileUtils.convertToSequence(startNumber, patternLength) + "\n");
					userLogArea.appendText("Starting scan in directory: " + directory + "\n");
					long startTime = System.currentTimeMillis();
					File dir = new File(directory);
					File[] allFiles = dir.listFiles();
					List<String> filesContainPattern = new ArrayList<>();
					int allFilesQuantity = 0;
					int filesToRename = 0;

					if (allFiles != null) {
						for (File file : allFiles) {
							if (!FileUtils.isDirectory(file.getAbsolutePath())) {
								allFilesQuantity = allFilesQuantity + 1;

								if (FileUtils.areExtensionsEqual(file, extension)) {
									String rawFileName = FileUtils.getFileName(file);

									if (ValidationUtils.validatePatternsEquality(rawFileName, coreName, startNumberValue)) {
										filesContainPattern.add(rawFileName);
									} else {
										filesToRename = filesToRename + 1;
									}
								}
							}
						}
					}

					long endTime = System.currentTimeMillis();
					double operationTime = TimeUtils.convertToSeconds(endTime - startTime);
					log.info("Scan complected in {} seconds", operationTime);
					userLogArea.appendText("Scan completed in " + operationTime + " seconds\n");
					log.info("All files in directory [value = {}]", allFilesQuantity);
					userLogArea.appendText("All files in directory: " + allFilesQuantity + "\n");
					log.info("Files that fulfill pattern [value = {}]", filesContainPattern.size());
					userLogArea.appendText("Files that fulfill pattern: " + filesContainPattern.size() + "\n");
					log.info("Files to rename [value = {}]", filesToRename);
					userLogArea.appendText("Files to rename: " + filesToRename + "\n");
				} else {
					DialogUtils.alert("Number pattern is not valid");
				}
			} else {
				DialogUtils.alert("Invalid core name, excluded chars: (\\, / :, *, \", <, >, |)");
			}
		} else {
			DialogUtils.alert("Not a directory.");
		}
	}

}
