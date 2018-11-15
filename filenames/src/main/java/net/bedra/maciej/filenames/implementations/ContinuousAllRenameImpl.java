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
 * Implementation of rename operation for modes: continuous names
 * and all files.
 *
 * @author Maciej Bedra
 */
public class ContinuousAllRenameImpl implements RenameInterface {

	private static Logger log = Logger.getLogger();

	private String directory;
	private String coreNameValue;
	private String startNumberValue;
	private TextArea userLogArea;

	/**
	 * ContinuousAllRenameImpl constructor.
	 *
	 * @param directory        path to directory to preform rename operation
	 * @param coreNameValue    raw value of core name (renaming
	 *                         pattern)
	 * @param startNumberValue raw value of start number pattern
	 * @param userLogArea      text area for logs visible for user
	 */
	public ContinuousAllRenameImpl(String directory, String coreNameValue, String startNumberValue, TextArea userLogArea) {
		this.directory = directory;
		this.coreNameValue = coreNameValue;
		this.startNumberValue = startNumberValue;
		this.userLogArea = userLogArea;
	}

	/**
	 * Perform rename operation for mode: continuous names
	 * and all files.
	 */
	@Override
	public void startRenaming() {
		if (FileUtils.isDirectory(directory)) {
			String coreName = ValidationUtils.validateCoreName(coreNameValue);

			if (coreName != null) {
				Integer startNumber = ValidationUtils.validateSequencePattern(startNumberValue);

				if (startNumber != null) {
					Integer patternLength = ValidationUtils.getSequencePatternLength(startNumberValue);
					log.info(
						"Starting rename operation in mode [continuousMode,allFiles] for directory [startNumber = {}, patternLength = {}, coreName = {}, path = {}]...",
						startNumber,
						patternLength,
						coreName,
						directory
					);
					userLogArea.setText("");
					userLogArea.appendText("Chosen core name: " + coreName + "\n");
					userLogArea.appendText("Start sequence: " + FileUtils.convertToSequence(startNumber, patternLength) + "\n");
					userLogArea.appendText("Starting rename operation in directory: " + directory + "\n");
					long startTime = System.currentTimeMillis();
					File dir = new File(directory);
					File[] allFiles = dir.listFiles();
					List<File> filesToRename = new ArrayList<>();
					List<String> filesContainPattern = new ArrayList<>();
					int allFilesQuantity = 0;

					if (allFiles != null) {
						for (File file : allFiles) {
							if (!FileUtils.isDirectory(file.getAbsolutePath())) {
								allFilesQuantity = allFilesQuantity + 1;
								String rawFileName = FileUtils.getFileName(file);

								if (ValidationUtils.validatePatternsEquality(rawFileName, coreName, startNumberValue)) {
									filesContainPattern.add(rawFileName);
								} else {
									filesToRename.add(file);
								}
							}
						}
					}

					List<File> sortedFiles = FileUtils.sortByCreationDateAsc(filesToRename);
					int notRenamedFiles = allFilesQuantity - sortedFiles.size();
					int renamedFiles = 0;

					for (File file : sortedFiles) {
						String newFileName = coreName + FileUtils.convertToSequence(startNumber, patternLength);

						while (filesContainPattern.contains(newFileName)) {
							startNumber = startNumber + 1;
							newFileName = coreName + FileUtils.convertToSequence(startNumber, patternLength);
						}

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
					log.info("Files that fulfill pattern [value = {}]", filesContainPattern.size());
					userLogArea.appendText("Files that fulfill pattern: " + filesContainPattern.size() + "\n");
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
			DialogUtils.alert("Not a directory.");
		}
	}

}
