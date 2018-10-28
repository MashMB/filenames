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
	 * Convert string number pattern to real number (only numbers >= 0).
	 *
	 * @param numPattern number pattern as string
	 * @return Integer converted pattern
	 */
	public Integer convertPattern(String numPattern) {
		log.info("Converting start sequence pattern to number [pattern = {}]...", numPattern != null && !numPattern.trim().isEmpty() ? numPattern : null);
		Integer startSeq = null;

		try {
			startSeq = new Integer(numPattern);

			if (startSeq < 0) {
				startSeq = null;
			}
		} catch (Exception ex) {
			log.error("Invalid number pattern", ex);
		}

		log.info("Start sequence number converted [value = {}]", startSeq);

		return startSeq;
	}

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
