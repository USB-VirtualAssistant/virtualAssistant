package org.fundacionjala.virtualassistant.context.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.models.UserEntity;

import java.util.List;

@Entity
@Table(name = "context")
@NoArgsConstructor()
@AllArgsConstructor
@Builder
@Data
public class ContextEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContext;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    private UserEntity userEntity;

    @OneToMany(mappedBy = "contextEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<RequestEntity> requestEntities;
}