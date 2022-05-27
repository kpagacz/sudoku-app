package org.sudokuclub.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserSession {
  private static final Logger logger = LogManager.getLogger(UserSession.class);

  private static UserSession instance;
  private String login;

  private UserSession() {}

  public static synchronized UserSession get() {
    return instance;
  }

  public static void clear() {
    instance = null;
  }

  public static void create(String login) {
    instance = new UserSession();
    instance.login = login;
    logger.debug("User session created with login: %s", login);
  }

  public String getLogin() {
    return login;
  }
}
