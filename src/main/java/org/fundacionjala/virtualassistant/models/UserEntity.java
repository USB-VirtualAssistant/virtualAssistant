package org.fundacionjala.virtualassistant.models;

import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.GenerationType;


@Entity
@Table(name = "user_info")
@NoArgsConstructor(force = true)
@Value
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser = 0L;

    @Column(name = "id_google")
    private String idGoogle;
}
