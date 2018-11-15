package net.bedra.maciej.filenames.enums;

/**
 * Enumeration with all available modes of operations to perform.
 *
 * @author Maciej Bedra
 */
public enum FilesMode {

	CONTINUOUS_EXTENSION("CE"),
	CONTINUOUS_ALL("CA"),
	ALL_ALL("AA"),
	ALL_EXTENSION("AE");

	private String mode;

	/**
	 * FilesMode constructor.
	 *
	 * @param mode operation mode
	 */
	FilesMode(String mode) {
		this.mode = mode;
	}

	/**
	 * Get operation mode.
	 *
	 * @return String operation mode
	 */
	public String getMode() {
		return mode;
	}

}
