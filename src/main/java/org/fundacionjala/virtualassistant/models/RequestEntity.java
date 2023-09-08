package org.fundacionjala.virtualassistant.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "request")
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRequest;
    @Column(name = "id_user")
    private Long idUser;
    @Column(name = "id_context")
    private Long idContext;
    @Column(name = "text")
    private String text;
    @Column(name = "date")
    private ZonedDateTime date;
    @Column(name = "id_audio_mongo")
    private String idAudioMongo;
    @OneToOne(mappedBy = "requestEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private ResponseEntity responseEntity;
}