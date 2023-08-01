package org.fundacionjala.virtualassistant.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "request")
public class RequestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long idRequest;

  @Column(name = "id_user")
  private long idUser;
  @Column(name = "text")
  private String text;
  @Column(name = "date")
  private Date date;
  @Column(name = "id_audio_mongo")
  private long idAudioMongo;

  public RequestEntity() {
  }

  public RequestEntity(String text, Date date, long idAudioMongo) {
    this.text = text;
    this.date = date;
    this.idAudioMongo = idAudioMongo;
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

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
