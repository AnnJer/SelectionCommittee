package user;

public enum UserRoles {
    ENROLLEE ("enrollee"),
    ADMINISTRATOR ("admin");

    private String role;

    UserRoles(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return role;
    }

}
