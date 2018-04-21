package pl.repositoryTraffic.pojo;

/**
 *
 * @author Michał
 */
public class TrafficParameters {
  private int idPomiaru;
  private int idMiejscaPomiar;
  private int idTypuPomiaru;
  private String nazwaTypuPomiaru;
  private String nazwaPomiaru;

  public int getIdPomiaru() {
    return idPomiaru;
  }

  public void setIdPomiaru(int idPomiaru) {
    this.idPomiaru = idPomiaru;
  }

  public int getIdMiejscaPomiar() {
    return idMiejscaPomiar;
  }

  public void setIdMiejscaPomiar(int idMiejscaPomiar) {
    this.idMiejscaPomiar = idMiejscaPomiar;
  }

  public int getIdTypuPomiaru() {
    return idTypuPomiaru;
  }

  public void setIdTypuPomiaru(int idTypuPomiaru) {
    this.idTypuPomiaru = idTypuPomiaru;
  }

  public String getNazwaTypuPomiaru() {
    return nazwaTypuPomiaru;
  }

  public void setNazwaTypuPomiaru(String nazwaTypuPomiaru) {
    this.nazwaTypuPomiaru = nazwaTypuPomiaru;
  }

  public String getNazwaPomiaru() {
    return nazwaPomiaru;
  }

  public void setNazwaPomiaru(String nazwaPomiaru) {
    this.nazwaPomiaru = nazwaPomiaru;
  }
  
  
}
