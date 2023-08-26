package org.fundacionjala.virtualassistant.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import lombok.NoArgsConstructor;
import lombok.Value;

@Entity
@Table(name = "context")
@NoArgsConstructor(force = true)
@Value
public class ContextEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContext = 0L;

    @Column(name = "title")
    private String title;

    @Column(name = "id_user")
    private Long idUser;

    public ContextEntity(String title, Long idUser) {
        this.title = title;
        this.idUser = idUser;
    }
}