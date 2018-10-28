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
	 * Check length of pattern for number string.
	 *
	 * @param numPattern raw pattern (user input)
	 * @return Integer length of pattern for number string
	 */
	public Integer getPatternLength(String numPattern) {
		log.info("Checking length of pattern for number string [value = {}]...", numPattern != null && !numPattern.trim().isEmpty() ? numPattern.trim() : 0);
		Integer patternLength = numPattern != null && !numPattern.trim().isEmpty() ? numPattern.trim().length() : 0;
		log.info("Length of pattern for number string checked");

		return patternLength;
	}

	/**
	 * Check if given path is directory.
	 *
	 * @param path path to directory
	 * @return boolean logical value if given path is directory
	 */
	public boolean isDirectory(String path) {
		log.info("Checking if path [{}] is directory...", path != null && !path.trim().isEmpty() ? path.trim() : null);
		boolean isDir = path != null && new File(path).isDirectory();
		log.info("Path checked [is directory = {}]", isDir);

		return isDir;
	}

}
