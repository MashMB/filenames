package net.bedra.maciej.filenames.services;

import java.io.File;
import net.bedra.maciej.mblogging.Logger;
import org.springframework.stereotype.Service;

/**
 * Files operations and logic.
 *
 * @author Maciej Bedra
 */
@Service
public class AppService {

	private static Logger log = Logger.getLogger();

	/**
	 * Check if given path is directory.
	 *
	 * @param path path to directory
	 * @return boolean logical value if given path is directory
	 */
	public boolean isDirectory(String path) {
		log.info("Checking if path [{}] is directory...", !path.isEmpty() ? path : null);
		boolean isDir = new File(path).isDirectory();
		log.info("Path checked [is directory = {}]", isDir);

		return isDir;
	}

}
