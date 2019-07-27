package com.erzhiqianyi.reactor.domain;

public class VipUser extends User {
    private final Integer level;

    public VipUser(String username, String firstname, String lastname, Integer level) {
        super(username, firstname, lastname);
        this.level = level;
    }

    public VipUser(User user, Integer level) {
        super(user.getUsername(), user.getFirstname(), user.getLastname());
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }
}
