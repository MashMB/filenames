package net.bedra.maciej.filenames.utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.bedra.maciej.mblogging.Logger;

/**
 * Utils to perform all kind of validation.
 *
 * @author Maciej Bedra
 */
public class ValidationUtils {

	private static Logger log = Logger.getLogger();

	/**
	 * Convert string number pattern to real number (only numbers >= 0).
	 *
	 * @param numPattern number pattern as string
	 * @return Integer converted pattern
	 */
	public static Integer convertPattern(String numPattern) {
		log.debug("Converting start sequence pattern to number [pattern = {}]...", numPattern != null && !numPattern.trim().isEmpty() ? numPattern : null);
		Integer startSeq = null;

		try {
			startSeq = new Integer(numPattern);

			if (startSeq < 0) {
				startSeq = null;
			}
		} catch (Exception ex) {
			log.error("Invalid number pattern", ex);
		}

		log.debug("Start sequence number converted [value = {}]", startSeq);

		return startSeq;
	}

	/**
	 * Get proper core name of the file from raw input (restrictions
	 * like in Windows system while creating new folder or file with
	 * special characters).
	 *
	 * @param input raw input
	 * @return String proper core name of file
	 */
	public static String getCoreName(String input) {
		log.debug("Getting core name from string [value = {}]", input != null && !input.trim().isEmpty() ? input.trim() : null);
		String coreName = null;

		if (input != null && !input.trim().isEmpty()) {
			Pattern restrictedChars = Pattern.compile("(\\\\)|(/)|(:)|(\\*)|(\")|(<)|(>)|(\\|)|(\\s+)");
			Matcher matcher = restrictedChars.matcher(input.trim());

			if (!matcher.find()) {
				coreName = input.trim();
			}
		}

		log.debug("Core name found [value = {}]", coreName);

		return coreName;
	}

	/**
	 * Get extension name from raw input.
	 *
	 * @param input raw input
	 * @return String proper file extension
	 */
	public static String getExtension(String input) {
		log.debug("Getting extension from string [value = {}]", input != null && !input.trim().isEmpty() ? input.trim() : null);
		String extension = null;

		if (input != null && !input.trim().isEmpty()) {
			String[] splitInput = input.trim().split("\\.");

			if (!(splitInput.length > 2)) {
				if (splitInput.length == 2) {
					if (splitInput[0].isEmpty()) {
						extension = input.trim().toLowerCase().substring(1, input.trim().length());
					}
				} else {
					extension = input.trim().toLowerCase();
				}
			}
		}

		log.debug("Extension found [value = {}]", extension);

		return extension;
	}

	/**
	 * Check length of pattern for number string.
	 *
	 * @param numPattern raw pattern (user input)
	 * @return Integer length of pattern for number string
	 */
	public static Integer getPatternLength(String numPattern) {
		log.debug("Checking length of pattern for number string [value = {}]...", numPattern != null && !numPattern.trim().isEmpty() ? numPattern.trim() : 0);
		Integer patternLength = numPattern != null && !numPattern.trim().isEmpty() ? numPattern.trim().length() : 0;
		log.debug("Length of pattern for number string checked");

		return patternLength;
	}

	/**
	 * Check if given path is directory.
	 *
	 * @param path path to directory
	 * @return boolean logical value if given path is directory
	 */
	public static boolean isDirectory(String path) {
		log.debug("Checking if path [{}] is directory...", path != null && !path.trim().isEmpty() ? path.trim() : null);
		boolean isDir = path != null && new File(path).isDirectory();
		log.debug("Path checked [is directory = {}]", isDir);

		return isDir;
	}

}
