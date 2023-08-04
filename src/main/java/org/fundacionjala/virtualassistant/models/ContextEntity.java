package org.fundacionjala.virtualassistant.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;

import lombok.AllArgsConstructor;

@Entity
@Table(name = "context")
@AllArgsConstructor
public class ContextEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContext;

    @Column(name = "title")
    private String title;

    @Column(name = "id_user")
    private Long idUser;
}