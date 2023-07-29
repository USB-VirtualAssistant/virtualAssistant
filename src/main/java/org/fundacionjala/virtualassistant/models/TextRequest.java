package org.fundacionjala.virtualassistant.models;

import java.util.Date;

public class TextRequest {
  private long idRequest;
  private String text;
  private Date date;
  private long idAudioMongo;

  public TextRequest() {
  }

  public TextRequest(String text, Date date, long idAudioMongo) {
    this.text = text;
    this.date = date;
    this.idAudioMongo = idAudioMongo;
  }

  public long getIdRequest() {
    return idRequest;
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
