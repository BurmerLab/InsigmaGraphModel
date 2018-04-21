package pl.repositoryTraffic.pojo;

/**
 *
 * @author Michał
 */
public interface ITrafficType<T> {
  
  public T getTrafficParameter();

  public void setTrafficParameter(T trafficParameter);

  public int getIdTypuPomiaru();

  public void setIdTypuPomiaru(int idTypuPomiaru);

  public String getNazwaTypuPomiaru();

  public void setNazwaTypuPomiaru(String nazwaTypuPomiaru);

  public String getNazwaPomiaru();

  public void setNazwaPomiaru(String nazwaPomiaru);
  
}
