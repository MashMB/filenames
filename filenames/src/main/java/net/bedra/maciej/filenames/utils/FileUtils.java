package net.bedra.maciej.filenames.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import net.bedra.maciej.mblogging.Logger;

/**
 * Utils to perform operations on files.
 *
 * @author Maciej Bedra
 */
public class FileUtils {

	private static Logger log = Logger.getLogger();

	/**
	 * Convert raw number to sequence with proper length.
	 *
	 * @param number sequence raw number
	 * @param patternLength result pattern length
	 * @return String converted sequence with proper length
	 */
	public static String convertToSequence(Integer number, Integer patternLength) {
		log.debug("Converting number to sequence [number = {}, patternLength = {}]...", number, patternLength);
		String sequencePattern = number.toString();

		if (sequencePattern.length() < patternLength) {
			StringBuilder zeros = new StringBuilder();
			int lackQuantity = patternLength - sequencePattern.length();

			for (int i = 0; i < lackQuantity; i++) {
				zeros.append("0");
			}

			sequencePattern = zeros.append(sequencePattern).toString();
		}

		log.debug("Number to sequence converted [sequence = {}]", sequencePattern);

		return sequencePattern;
	}

	/**
	 * Get extension name from path of file.
	 *
	 * @param path path of file
	 * @return String file extension
	 */
	public static String getExtension(String path) {
		log.debug("Getting file extension from path [path = {}]", path != null && !path.trim().isEmpty() ? path.trim() : null);
		String extension = null;

		if (path != null && !path.trim().isEmpty()) {
			int dotIndex = path.trim().lastIndexOf('.');

			if (dotIndex >= 0) {
				extension = path.trim().substring(dotIndex + 1).toLowerCase();
			}
		}

		log.debug("File extension found [value = {}]", extension);

		return extension;
	}

	/**
	 * Get file creation date (note that this will not work on most of
	 * the unix systems because of no information about file creation
	 * date on some kind of file systems for example ext partitions).
	 *
	 * @param file file that will be used to get creation date
	 * @return Long file creation date in milliseconds
	 */
	public static Long getFileCreationMills(File file) {
		log.debug("Getting file creation date [file = {}]...", file.getAbsolutePath());
		Long creationTime = null;

		try {
			BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
			creationTime = attributes.creationTime().toInstant().toEpochMilli();
			Date creationDate = new Date(creationTime);
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy (HH:mm:ss)");
			log.debug("Creation date of file found [date = {}, milliseconds = {}]", sdf.format(creationDate), creationTime);
		} catch (IOException ex) {
			log.error("Can not get file creation date", ex);
		}

		return creationTime;
	}

	/**
	 * Get raw name of file (without extension).
	 *
	 * @param file file to analyse
	 * @return String raw name of file (without extension)
	 */
	public static String getFileName(File file) {
		log.debug("Getting file name from path [path = {}]...", file.getAbsolutePath());
		String[] splitPath = file.getAbsolutePath().split(Matcher.quoteReplacement(File.separator));
		String fileNameWithExtension = splitPath[splitPath.length - 1];
		int lastDotIndex = fileNameWithExtension.lastIndexOf('.');
		String rawFileName = null;

		if (lastDotIndex > 0) {
			rawFileName = fileNameWithExtension.substring(0, lastDotIndex);
		}

		log.debug("Filename without extension processed [value = {}]", rawFileName);

		return rawFileName;
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

	/**
	 * Rename file with new name.
	 *
	 * @param file file to rename
	 * @param name new name for file
	 * @return boolean logical value if file was renamed
	 */
	public static boolean renameFile(File file, String name) {
		log.debug("Renaming file [newName = {}, file = {}]...", name, file.getAbsolutePath());
		String[] splitPath = file.getAbsolutePath().split(Matcher.quoteReplacement(File.separator));
		String fileNameWithExtension = splitPath[splitPath.length - 1];
		String fileExtension = getExtension(fileNameWithExtension);
		StringBuilder fileDirPath = new StringBuilder();

		for (int i = 0; i < splitPath.length - 1; i++) {
			fileDirPath.append(splitPath[i]).append(File.separator);
		}

		File newFile = new File(fileDirPath.append(File.separator).append(name).append(".").append(fileExtension).toString());
		boolean isRenamed = file.renameTo(newFile);
		log.debug("File renamed [isRenamed = {}, newFile = {}]", isRenamed, newFile.getAbsolutePath());

		return isRenamed;
	}

	/**
	 * Ascending sort list of files by creation date.
	 *
	 * @param files list of files to sort
	 * @return List sorted list of files by creation date ascending
	 */
	public static List<File> sortByCreationDateAsc(List<File> files) {
		log.debug("Sorting list of files by creation date in ascending order...");

		files.sort((f1, f2) -> {
			Long f1CreationTime = getFileCreationMills(f1);
			Long f2CreationTime = getFileCreationMills(f2);

			return f1CreationTime.compareTo(f2CreationTime);
		});

		log.debug("List of files sorted by creation date in ascending order");

		return files;
	}

}
