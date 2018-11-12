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
 * Implementation of scan operation for modes: all names and files
 * with extension.
 *
 * @author Maciej Bedra
 */
public class AllExtensionScanImpl implements ScanInterface {

	private static Logger log = Logger.getLogger();

	private String directory;
	private String extension;
	private TextArea userLogArea;

	/**
	 * AllExtensionScanImpl constructor.
	 *
	 * @param directory   path to directory to preform scan
	 * @param extension   extension for which directory should be
	 *                    scanned
	 * @param userLogArea text area for logs visible for user
	 */
	public AllExtensionScanImpl(String directory, String extension, TextArea userLogArea) {
		this.directory = directory;
		this.extension = ValidationUtils.validateFileExtension(extension);
		this.userLogArea = userLogArea;
	}

	/**
	 * Preform scan operation for modes: all names and files with
	 * extension.
	 */
	@Override
	public void preformScan() {
		if (FileUtils.isDirectory(directory)) {
			if (extension != null) {
				log.info("Starting scan in mode [allMode,extFiles] for extension and directory [extension = {}, path = {}]...", extension, directory);
				userLogArea.setText("");
				userLogArea.appendText("Chosen extension: " + extension + "\n");
				userLogArea.appendText("Starting scan in directory: " + directory + "\n");
				long startTime = System.currentTimeMillis();
				File dir = new File(directory);
				File[] allFiles = dir.listFiles();
				int allFilesQuantity = 0;
				List<File> filesToRename = new ArrayList<>();

				if (allFiles != null) {
					for (File file : allFiles) {
						if (!FileUtils.isDirectory(file.getAbsolutePath())) {
							allFilesQuantity = allFilesQuantity + 1;

							if (FileUtils.areExtensionsEqual(file, extension)) {
								filesToRename.add(file);
							}
						}
					}
				}

				long endTime = System.currentTimeMillis();
				double operationTime = TimeUtils.convertToSeconds(endTime - startTime);
				log.info("Scan completed in {} seconds", operationTime);
				userLogArea.appendText("Scan completed in " + operationTime + " seconds\n");
				log.info("All files in directory [value = {}]", allFilesQuantity);
				userLogArea.appendText("All files in directory: " + allFilesQuantity + "\n");
				log.info("Files to rename [value = {}]", filesToRename.size());
				userLogArea.appendText("Files to rename: " + filesToRename.size() + "\n");
			} else {
				DialogUtils.alert("Incorrect file extension");
			}
		} else {
			DialogUtils.alert("Not a directory");
		}
	}

}
