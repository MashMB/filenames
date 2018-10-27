package net.bedra.maciej.filenames.services;

import java.io.File;
import java.util.regex.Matcher;
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
		String logPath = path.replaceAll(Matcher.quoteReplacement(File.separator), "/");
		log.info("Checking if path [{}] is directory...", !logPath.isEmpty() ? logPath : "null");
		boolean isDir = new File(path).isDirectory();
		log.info("Path checked [is directory = {}]", isDir);

		return isDir;
	}

}
