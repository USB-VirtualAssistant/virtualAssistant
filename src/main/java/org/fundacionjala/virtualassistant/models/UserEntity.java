package org.fundacionjala.virtualassistant.models;

import lombok.Value;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.GenerationType;


@Entity
@Table(name = "user")
@Value
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(name = "google_id")
    private String googleID;

    public UserEntity(String googleID) {
        this.googleID = googleID;
    }
}
