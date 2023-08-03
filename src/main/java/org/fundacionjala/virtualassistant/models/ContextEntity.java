package org.fundacionjala.virtualassistant.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;

@Entity
@Table(name = "context")
public class ContextEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idContext;

    @Column(name = "title")
    private String title;

    @Column(name = "id_user")
    private long idUser;

    public ContextEntity(String title, long idUser) {
        this.title = title;
        this.idUser = idUser;
    }
}