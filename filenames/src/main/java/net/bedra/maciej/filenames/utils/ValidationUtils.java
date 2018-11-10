package net.bedra.maciej.filenames.utils;

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
	 * Check length of pattern for string number sequence.
	 *
	 * @param numPattern raw pattern (user input)
	 * @return Integer length of pattern for string number sequence
	 */
	public static Integer getSequencePatternLength(String numPattern) {
		log.debug("Checking length of pattern for string number sequence [value = {}]...", numPattern != null && !numPattern.trim().isEmpty() ? numPattern.trim() : 0);
		Integer patternLength = numPattern != null && !numPattern.trim().isEmpty() ? numPattern.trim().length() : 0;
		log.debug("Length of pattern for string number sequence checked [length = {}]", patternLength);

		return patternLength;
	}

	/**
	 * Validate and get proper core name of the file from raw input
	 * (restrictions like in Windows system while creating new
	 * directory or file with special characters).
	 *
	 * @param input raw input
	 * @return String validated proper core name of file
	 */
	public static String validateCoreName(String input) {
		log.debug("Validating and getting core name from string [value = {}]", input != null && !input.trim().isEmpty() ? input.trim() : null);
		String coreName = null;

		if (input != null && !input.trim().isEmpty()) {
			Pattern restrictedChars = Pattern.compile("(\\\\)|(/)|(:)|(\\*)|(\")|(<)|(>)|(\\|)|(\\s+)");
			Matcher matcher = restrictedChars.matcher(input.trim());

			if (!matcher.find()) {
				coreName = input.trim();
			}
		}

		log.debug("Core name validated [value = {}]", coreName);

		return coreName;
	}

	/**
	 * Validate and get extension name from raw input.
	 *
	 * @param input raw input
	 * @return String validated file extension
	 */
	public static String validateFileExtension(String input) {
		log.debug("Validating and getting extension from string [value = {}]", input != null && !input.trim().isEmpty() ? input.trim() : null);
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

		log.debug("Extension validated [value = {}]", extension);

		return extension;
	}

	/**
	 * Validate and convert string sequence pattern to real number
	 * (only numbers >= 0).
	 *
	 * @param numPattern sequence pattern as string
	 * @return Integer validated and converted pattern
	 */
	public static Integer validateSequencePattern(String numPattern) {
		log.debug("Validating sequence pattern and converting it to number [pattern = {}]...", numPattern != null && !numPattern.trim().isEmpty() ? numPattern : null);
		Integer startSeq = null;

		try {
			startSeq = new Integer(numPattern);

			if (startSeq < 0) {
				startSeq = null;
			}
		} catch (Exception ex) {
			log.error("Invalid sequence pattern", ex);
		}

		log.debug("Sequence number validated and converted [value = {}]", startSeq);

		return startSeq;
	}

}
