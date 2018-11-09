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
	 * Ascending sort list of files by creation date.
	 *
	 * @param files list of files to sort
	 * @return List sorted list of files by creation date ascending
	 */
	public static List<File> sortByCreationDateAsc(List<File> files) {
		log.debug("Sorting list of files...");

		files.sort((f1, f2) -> {
			Long f1CreationTime = getFileCreationMills(f1);
			Long f2CreationTime = getFileCreationMills(f2);

			return f1CreationTime.compareTo(f2CreationTime);
		});

		log.debug("List of files sorted by creation date in ascending order");

		return files;
	}

}
