/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.repositoryTraffic.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import pl.database.Database;

/**
 *
 * @author Michał
 */
public interface ITrafficPrametersDAO<T> {
  public T obtainTrafficParameterFromTrafficMapById(Connection connection, Database database, int segmentId) throws SQLException;
  
}
