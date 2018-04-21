package pl.trush;

import java.sql.Date;

/**
 *
 * @author Michał
 */
public interface IMetaDataForTrafficParameters {
  Date getDate();
  int getStartPoint();
  int getEndPoint();
}
