package com.erzhiqianyi.reactor.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ResourceLoader {

    private final List<User> users;

    private Logger logger;

    public ResourceLoader() {
        logger = Logger.getLogger("Foo");
        logger.info("flux:callable task executor: " + Thread.currentThread().getName() + " load resource ");
        users = new ArrayList<>(Arrays.asList(User.SKYLER, User.JESSE, User.WALTER, User.SAUL));
    }

    public void clean() {
        logger.info("flux:callable task executor: " + Thread.currentThread().getName() + " clean resource ");
        users.clear();
        logger = null;
    }

    public List<User> getAll() {
        return users;
    }

    public User getFirst() {
        return users.get(0);
    }
}
