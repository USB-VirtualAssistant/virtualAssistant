package org.fundacionjala.virtualassistant.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.fundacionjala.virtualassistant.context.models.ContextEntity;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "user_info")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Value
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUser;

    @Column(name = "id_google")
    String idGoogle;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    List<ContextEntity> contextEntities;
}
