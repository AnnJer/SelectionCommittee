package com.kpi.javaee.servlet.entities;

import javax.persistence.*;
import java.nio.charset.Charset;
import java.sql.Date;
import java.time.Instant;
import java.util.Random;

@Entity
@Table(name = "sessions", schema = "public", catalog = "selectioncommittee")
public class SessionsEntity {

    private static final Integer sessionLifeTime = 60*60*3; // 3h

    private String token;
    private Date startDate;
    private Date endDate;
    private String type;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private UsersEntity usersByIdUser;


    public SessionsEntity() {
    }

    public SessionsEntity(String token, UsersEntity usersByIdUser) {
        this.token = token;
        this.usersByIdUser = usersByIdUser;
        this.type = usersByIdUser.getRole();
        this.startDate = new Date(Date.from(Instant.now()).getTime());
        this.endDate = new Date(Date.from(Instant.now()).getTime() + sessionLifeTime);
    }

    @Id
    @Column(name = "token", nullable = false, length = 100)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "start_date", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 30)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionsEntity that = (SessionsEntity) o;

        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }


    public UsersEntity getUsersByIdUser() {
        return usersByIdUser;
    }

    public void setUsersByIdUser(UsersEntity usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
    }


    private static String generateToken(int n) {
        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        for (int k = 0; k < randomString.length(); k++) {

            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9'))
                    && (n > 0)) {

                r.append(ch);
                n--;
            }
        }

        // return the resultant string
        return r.toString();
    }


    public static SessionsEntity createSession(UsersEntity usersEntity) {
        return new SessionsEntity(generateToken(16), usersEntity);
    }

}
