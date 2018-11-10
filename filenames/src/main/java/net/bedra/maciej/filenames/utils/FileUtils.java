package net.bedra.maciej.filenames.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.bedra.maciej.mblogging.Logger;

/**
 * Utils to perform operations on files.
 *
 * @author Maciej Bedra
 */
public class FileUtils {

	private static Logger log = Logger.getLogger();

	/**
	 */
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
