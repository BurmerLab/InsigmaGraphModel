package pl.repositoryTraffic.pojo;

/**
 *
 * @author Michał
 */
public class TrafficParameter {
  private int idPomiaru;
  private double pomiar;
  private int idMiejscaPomiar;
  private int idTypuPomiaru;
  private String nazwaTypuPomiaru;
  private String nazwaPomiaru;

  public TrafficParameter() {
  }

  public TrafficParameter(int idPomiaru, double pomiar, int idMiejscaPomiar, int idTypuPomiaru, String nazwaTypuPomiaru, String nazwaPomiaru) {
    this.idPomiaru = idPomiaru;
    this.pomiar = pomiar;
    this.idMiejscaPomiar = idMiejscaPomiar;
    this.idTypuPomiaru = idTypuPomiaru;
    this.nazwaTypuPomiaru = nazwaTypuPomiaru;
    this.nazwaPomiaru = nazwaPomiaru;
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

  public int getIdPomiaru() {
    return idPomiaru;
  }

  public void setIdPomiaru(int idPomiaru) {
    this.idPomiaru = idPomiaru;
  }

  public double getPomiar() {
    return pomiar;
  }

  public void setPomiar(double pomiar) {
    this.pomiar = pomiar;
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

  
}
