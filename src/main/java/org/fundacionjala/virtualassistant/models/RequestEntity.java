package org.fundacionjala.virtualassistant.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import java.time.ZonedDateTime;

@Entity
@Data
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
  private Long idAudioMongo;

  public RequestEntity() {
  }

  public RequestEntity(String text, ZonedDateTime date, Long idAudioMongo, Long idContext, Long idUser) {
    this.text = text;
    this.date = date;
    this.idAudioMongo = idAudioMongo;
    this.idContext = idContext;
    this.idUser = idUser;
  }

  public long getIdRequest() {
    return idRequest;
  }

  public long getIdUser() {
    return idUser;
  }

  public long getIdAudioMongo() {
    return idAudioMongo;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public ZonedDateTime getDate() {
    return date;
  }

  public void setDate(ZonedDateTime date) {
    this.date = date;
  }

  public long getIdContext() {
    return idContext;
  }
}