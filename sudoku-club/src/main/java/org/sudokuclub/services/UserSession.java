package org.sudokuclub.services;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserSession {
  private static final Logger logger = LogManager.getLogger(UserSession.class);

  private UserSession() {}

  private static final StringProperty login = new SimpleStringProperty("");

  public static void create(String login) {
    Platform.runLater(() -> {
      UserSession.login.setValue(login);
      logger.debug(String.format("User session created with login: %s", login));
    });
  }

  public static StringProperty getLogin() {
    return login;
  }

  public static void clear() {
    login.setValue("");
    logger.debug("User session ended");
  }
}
