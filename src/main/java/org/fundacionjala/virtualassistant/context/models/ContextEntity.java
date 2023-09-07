package org.fundacionjala.virtualassistant.context.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fundacionjala.virtualassistant.models.UserEntity;

@Entity
@Table(name = "context")
@NoArgsConstructor()
@AllArgsConstructor
@Builder
@Data
public class ContextEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContext = 0L;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    private UserEntity userEntity;

}