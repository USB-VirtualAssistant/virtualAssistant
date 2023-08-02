package org.fundacionjala.virtualassistant.models;

import javax.persistence.*;

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

    public long getIdContext() {
        return idContext;
    }

    public void setIdContext(long idContext) {
        this.idContext = idContext;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
}