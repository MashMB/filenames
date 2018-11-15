package net.bedra.maciej.filenames.services;

import javafx.scene.control.TextArea;
import net.bedra.maciej.filenames.factories.RenameFactory;
import net.bedra.maciej.filenames.factories.ScanFactory;
import net.bedra.maciej.filenames.interfaces.RenameInterface;
import net.bedra.maciej.filenames.interfaces.ScanInterface;
import net.bedra.maciej.mblogging.Logger;
import org.springframework.stereotype.Service;

/**
 * Files operations and app logic.
 *
 * @author Maciej Bedra
 */
@Service
public class AppService {

	private static Logger log = Logger.getLogger();

	/**
	 * Preform scan operation.
	 *
	 * @param directory   path to directory to perform scan
	 * @param coreName    pattern of core name that will be
	 *                    searched in directory
	 * @param startNumber pattern of sequence number
	 * @param extension   pattern for extension to scan
	 * @param mode        selected strategy
	 * @param userLogArea visible logs text area
	 */
	public void scan(String directory, String coreName, String startNumber, String extension, String mode, TextArea userLogArea) {
		log.info("Starting scan operation...");
		ScanFactory factory = new ScanFactory(directory, coreName, startNumber, extension, userLogArea);
		ScanInterface impl = factory.getImplementation(mode);
		impl.preformScan();
		log.info("Scan operation completed");
	}

	/**
	 * Preform rename operation.
	 *
	 * @param directory   path to directory to perform rename
	 *                    operation
	 * @param coreName    pattern of core name that will be
	 *                    searched in directory
	 * @param startNumber pattern of sequence number
	 * @param extension   pattern for extension to scan
	 * @param mode        selected strategy
	 * @param userLogArea visible logs text area
	 */
	public void start(String directory, String coreName, String startNumber, String extension, String mode, TextArea userLogArea) {
		log.info("Starting rename operation...");
		RenameFactory factory = new RenameFactory(directory, coreName, startNumber, extension, userLogArea);
		RenameInterface impl = factory.getImplementation(mode);
		impl.startRenaming();
		log.info("Rename operation completed");
	}

}
