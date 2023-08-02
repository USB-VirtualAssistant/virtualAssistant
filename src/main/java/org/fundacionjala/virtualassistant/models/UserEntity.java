package org.fundacionjala.virtualassistant.models;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_user;

    @Column(name = "google_id")
    private String googleID;

    public UserEntity(String googleID) {
        this.googleID = googleID;
    }

    public long getIdUser() {
        return id_user;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }
}
