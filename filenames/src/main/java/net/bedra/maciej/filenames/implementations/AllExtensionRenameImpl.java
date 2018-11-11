package net.bedra.maciej.filenames.implementations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextArea;
import net.bedra.maciej.filenames.interfaces.RenameInterface;
import net.bedra.maciej.filenames.utils.DialogUtils;
import net.bedra.maciej.filenames.utils.FileUtils;
import net.bedra.maciej.filenames.utils.TimeUtils;
import net.bedra.maciej.filenames.utils.ValidationUtils;
import net.bedra.maciej.mblogging.Logger;

/**
 * Implementation of rename operation for modes: all names and
 * files with extension.
 *
 * @author Maciej Bedra
 */
public class AllExtensionRenameImpl implements RenameInterface {

	private static Logger log = Logger.getLogger();

	private String directory;
	private String coreNameValue;
	private String startNumberValue;
	private String extension;
	private TextArea userLogArea;

	/**
	 * AllExtensionRenameImpl constructor.
	 *
	 * @param directory        path to directory to preform rename
	 *                         operation
	 * @param coreNameValue    raw value of core name (renaming
	 *                         pattern)
	 * @param startNumberValue raw value of start number pattern
	 * @param extension        extension for which directory should be
	 *                         scanned
	 * @param userLogArea      text area for logs visible for user
	 */
	public AllExtensionRenameImpl(String directory, String coreNameValue, String startNumberValue, String extension, TextArea userLogArea) {
		this.directory = directory;
		this.coreNameValue = coreNameValue;
		this.startNumberValue = startNumberValue;
		this.extension = ValidationUtils.validateFileExtension(extension);
		this.userLogArea = userLogArea;
	}

	/**
	 * Perform rename operation for mode: all names and files with
	 * extension.
	 */
	@Override
	public void startRenaming() {
		if (FileUtils.isDirectory(directory)) {
			if (extension != null) {
				String coreName = ValidationUtils.validateCoreName(coreNameValue);

				if (coreName != null) {
					Integer startNumber = ValidationUtils.validateSequencePattern(startNumberValue);

					if (startNumber != null) {
						Integer patternLength = ValidationUtils.getSequencePatternLength(startNumberValue);
						log.info(
							"Starting rename operation in mode [allMode,extFiles] for extension and directory [startNumber = {}, patternLength = {}, coreName = {}, extension = {}, path = {}]...",
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
						userLogArea.appendText("Starting rename operation in directory: " + directory + "\n");
						long startTime = System.currentTimeMillis();
						File dir = new File(directory);
						File[] allFiles = dir.listFiles();
						List<File> filesToRename = new ArrayList<>();
						int allFilesQuantity = 0;

						if (allFiles != null) {
							for (File file : allFiles) {
								if (!FileUtils.isDirectory(file.getAbsolutePath())) {
									allFilesQuantity = allFilesQuantity + 1;
									String fileExtension = FileUtils.getExtension(file.getAbsolutePath());

									if (extension.equals(fileExtension)) {
										log.info("Extension matched for file [path = {}]", file.getAbsolutePath());
										filesToRename.add(file);
									} else {
										log.info("Extension not matched for file [path = {}]", file.getAbsolutePath());
									}
								}
							}
						}

						List<File> sortedFiles = FileUtils.sortByCreationDateAsc(filesToRename);
						int filesToRenameQuantity = filesToRename.size();
						int notRenamedFiles = filesToRenameQuantity - sortedFiles.size();
						int renamedFiles = 0;

						for (File file : sortedFiles) {
							String newFileName = coreName + FileUtils.convertToSequence(startNumber, patternLength);

							if (FileUtils.renameFile(file, newFileName)) {
								renamedFiles = renamedFiles + 1;
							} else {
								userLogArea.appendText("Error occurred, file not renamed: " + file.getAbsolutePath() + "\n");
								notRenamedFiles = notRenamedFiles + 1;
							}

							startNumber = startNumber + 1;
						}

						long endTime = System.currentTimeMillis();
						double operationTime = TimeUtils.convertToSeconds(endTime - startTime);
						log.info("Renaming operation completed in {} seconds", operationTime);
						userLogArea.appendText("Renaming operation completed in " + operationTime + " seconds\n");
						log.info("All files in directory [value = {}]", allFilesQuantity);
						userLogArea.appendText("All files in directory: " + allFilesQuantity + "\n");
						log.info("Files to rename [value = {}]", filesToRenameQuantity);
						userLogArea.appendText("Files to rename: " + filesToRenameQuantity + "\n");
						log.info("Files renamed [value = {}]", renamedFiles);
						userLogArea.appendText("Files renamed: " + renamedFiles + "\n");
						log.info("Files not renamed [value = {}]", notRenamedFiles);
						userLogArea.appendText("Files not renamed: " + notRenamedFiles + "\n");
					} else {
						DialogUtils.alert("Number pattern is not valid");
					}
				} else {
					DialogUtils.alert("Invalid core name, excluded chars: (\\, / :, *, \", <, >, |)");
				}
			} else {
				DialogUtils.alert("Incorrect file extension");
			}
		} else {
			DialogUtils.alert("Not a directory.");
		}
	}

}
