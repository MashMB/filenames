package net.bedra.maciej.filenames.implementations;

import java.io.File;
import javafx.scene.control.TextArea;
import net.bedra.maciej.filenames.interfaces.ScanInterface;
import net.bedra.maciej.filenames.utils.DialogUtils;
import net.bedra.maciej.filenames.utils.TimeUtils;
import net.bedra.maciej.filenames.utils.ValidationUtils;
import net.bedra.maciej.mblogging.Logger;

/**
 * Implementation of scan operation for modes: all names and all files.
 *
 * @author Maciej Bedra
 */
public class AllAllScanImpl implements ScanInterface {

	private static Logger log = Logger.getLogger();

	private String directory;
	private TextArea userLogArea;

	/**
	 * AllAllScanImpl constructor.
	 *
	 * @param directory   path to directory to preform scan
	 * @param userLogArea text area for logs visible for user
	 */
	public AllAllScanImpl(String directory, TextArea userLogArea) {
		this.directory = directory;
		this.userLogArea = userLogArea;
	}

	/**
	 * Preform scan operation for modes: all names and all files.
	 */
	@Override
	public void preformScan() {
		if (ValidationUtils.isDirectory(directory)) {
			log.info("Starting scan in mode [allMode,allFiles] for directory [path = {}]...", directory);
			userLogArea.setText("");
			userLogArea.appendText("Starting scan in directory: " + directory + "\n");
			long startTime = System.currentTimeMillis();
			File dir = new File(directory);
			File[] files = dir.listFiles();
			int filesQuantity = 0;

			if (files != null) {
				for (File file : files) {
					if (!ValidationUtils.isDirectory(file.getAbsolutePath())) {
						filesQuantity = filesQuantity + 1;
					}
				}
			}

			long endTime = System.currentTimeMillis();
			double operationTime = TimeUtils.convertToSeconds(endTime - startTime);
			log.info("Scan completed in {} seconds", operationTime);
			userLogArea.appendText("Scan completed in " + operationTime + " seconds\n");
			log.info("All files in directory [value = {}]", filesQuantity);
			userLogArea.appendText("All files in directory: " + filesQuantity + "\n");
			log.info("Files to rename [value = {}]", filesQuantity);
			userLogArea.appendText("Files to rename: " + filesQuantity + "\n");
		} else {
			DialogUtils.alert("Not a directory.");
		}
	}

}
