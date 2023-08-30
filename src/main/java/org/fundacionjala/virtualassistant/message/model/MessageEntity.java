package org.fundacionjala.virtualassistant.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Value
@AllArgsConstructor
@Builder
@Table(name = "view_request_with_response")
public class MessageEntity {
    @Id
    @Column(name = "id_request")
    Long idRequest;
    @Column(name = "text_request")
    String textRequest;
    @Column(name = "date_request")
    ZonedDateTime dateRequest;
    @Column(name = "id_audio")
    Long idAudio;
    @Column(name = "id_user")
    Long idUser;
    @Column(name = "id_context")
    Long idContext;
    @Column(name = "text_response")
    Long idResponse;
    @Column(name = "date_response")
    ZonedDateTime dateResponse;
}
