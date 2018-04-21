package pl.trush;

import java.sql.Date;
import javax.sound.midi.MetaEventListener;
import org.joda.time.DateTime;

/**
 *
 * @author Michał
 */
public interface ITrafficParameter<T>{
  public T getTrafficParameter();
  public T generateParameter(T minimumNumber, T maximalNumber);
  
  public Double obtainWage();
  public void setTrafficParam(boolean isTrafficExist);
  public boolean isTrafficParam();
  public DateTime getDate();
  public int getStartPoint();
  public int getEndPoint();
}
