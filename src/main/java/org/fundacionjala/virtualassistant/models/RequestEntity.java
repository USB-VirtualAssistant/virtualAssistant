package org.fundacionjala.virtualassistant.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
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

  public RequestEntity(String text, Date date, long idAudioMongo) {
    this.text = text;
    this.date = date;
    this.idAudioMongo = idAudioMongo;
  }
}
