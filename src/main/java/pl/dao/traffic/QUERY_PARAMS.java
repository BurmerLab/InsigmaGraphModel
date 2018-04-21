package pl.dao.traffic;

/**
 *
 * @author Michał
 */
public class QUERY_PARAMS {
  
  
  public final static String SELECT_FOR_ALL_TRAFFIC_PARAMETERS = "SELECT *"
            + " FROM t_pomiar as t_pomiar"
          
            + " INNER JOIN t_miejsce_pomiaru as t_miejsce_pomiaru" 
            + " ON t_pomiar.id_unikalne_miejsca_pomiaru = t_miejsce_pomiaru.id"
          
            + " INNER JOIN t_typ_pomiaru as t_typ_pomiaru"
            + " ON t_typ_pomiaru.id_typu_pomiaru = t_miejsce_pomiaru.id_typu_pomiaru";
  
  
//  SELECT USER_ID, USERNAME FROM DBUSER WHERE USER_ID = ?";
  public final static String SELECT_AVERAGE_SPEED_PARAMETER_BY_ID  = "SELECT average_speed, actual_date, segment_id, start_point, end_point, wage, is_traffic_param  FROM t_average_speed_parameter WHERE segment_id = ?"
        + "AND actual_date = (select max(actual_date) FROM t_average_speed_parameter WHERE segment_id = ?)";
  
  //_low dodac!!!! 
  
  
  public final static String SELECT_LENGTH_OF_VEHICLE_QUEUE_BY_ID  =  "SELECT vehicle_queue, actual_date, segment_id, start_point, end_point, wage, is_traffic_param  FROM t_length_of_vehicle_queue_parameter WHERE segment_id = ?"
        + "AND actual_date = (select max(actual_date) FROM t_length_of_vehicle_queue_parameter WHERE segment_id = ?)";
  
  public final static String SELECT_WEATHER_CONDITIONS_BY_ID       = "SELECT wet_road, actual_date, segment_id, start_point, end_point, wage, is_traffic_param  FROM t_weather_conditions_parameter WHERE segment_id = ?"
        + "AND actual_date = (select max(actual_date) FROM t_weather_conditions_parameter WHERE segment_id = ?)";
  
  
  
  public final static String AVG_COST_FROM_TABLE = "SELECT AVG(cost) FROM ";
  
  
  public final static String MIN_COST_FROM_TABLE = "SELECT MIN(cost) FROM ";
  public final static String MAX_COST_FROM_TABLE = "SELECT MAX(cost) FROM ";
  public final static String FIND_NAMES_ALL_TABLES_IN_DB = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA= 'public'";
  
}
