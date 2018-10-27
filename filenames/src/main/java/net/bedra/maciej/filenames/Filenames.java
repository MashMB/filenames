package net.bedra.maciej.filenames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.bedra.maciej.mblogging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Main entry point for Spring Boot and JavaFX application.
 *
 * @author Maciej Bedra
 */
@SpringBootApplication
public class Filenames extends Application {

	private static Logger log = Logger.getLogger();

	private ConfigurableApplicationContext springContext;
	private FXMLLoader fxmlLoader;
	private static String applicationName;

	/**
	 * Initialize Spring Boot and JavaFX. Redirect JavaFX control to
	 * Spring Boot.
	 */
	@Override
	public void init() {
		log.info("Initializing application...");
		springContext = SpringApplication.run(Filenames.class);
		fxmlLoader = new FXMLLoader();
		fxmlLoader.setControllerFactory(springContext::getBean);
		log.info("Application initialized");
	}

	/**
	 * Main entry point (launch application).
	 *
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Set name for application window.
	 *
	 * @param appName application name from application properties
	 */
	@Value("${spring.application.name}")
	public void setApplicationName(String appName) {
		applicationName = appName;
	}

	/**
	 * Start JavaFX application integrated with Spring Boot.
	 *
	 * @param stage main stage of JavaFX application
	 * @throws Exception exception that can occur in application
	 *                   start time
	 */
	@Override
	public void start(Stage stage) throws Exception {
		log.info("Starting application...");
		fxmlLoader.setLocation(getClass().getResource("/views/app.fxml"));
		Parent root = fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.setTitle(applicationName);
		stage.setResizable(false);
		stage.show();
		log.info("Application started");
	}

	/**
	 * Stop application (additional Spring Boot terminating).
	 */
	@Override
	public void stop() {
		log.info("Stopping application...");
		springContext.stop();
		log.info("Application stopped");
	}

}
