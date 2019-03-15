package user;

import dataAccess.Crypto;

import java.security.CryptoPrimitive;
import java.util.Date;

public class User {

    protected Long id;

    protected String name;
    protected String lastname;
    protected String surname;

    protected String login;
    protected byte[] password;


    public User(Long id, String name, String lastname, String surname, String login) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.surname = surname;
        this.login = login;
    }

    public User(String name, String lastname, String surname, String login) {
        this.name = name;
        this.lastname = lastname;
        this.surname = surname;
        this.login = login;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Crypto.encodePassword(login.getBytes(), password.getBytes());
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }


    public String getSurname() {
        return surname;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
