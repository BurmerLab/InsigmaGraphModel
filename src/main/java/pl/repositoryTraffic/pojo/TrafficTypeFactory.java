package pl.repositoryTraffic.pojo;

/**
 *
 * @author Michał
 */
public class TrafficTypeFactory {
  
  
  public ITrafficType obtainTrafficParameterInstance(String trafficParameterType){
    
    if(trafficParameterType.equals("double")){
        return new TrafficTypeDouble();
        
    }else if(trafficParameterType.equals("int")){
        return new TrafficTypeInt();
        
    }else if(trafficParameterType.equals("boolean")){
      return new TrafficTypeBoolean();
      
    }else if(trafficParameterType.equals("long")){
      return new TrafficTypeLong();
      
//      
//    }else if(trafficParameterType.equals("double")){
//      
//    }else if(trafficParameterType.equals("double")){
//      
//    }else if(trafficParameterType.equals("double")){
//      
//    }
    }else{
      System.out.println("Typ wpisany w bazie danych nie rozpoznawalny.");
    }
    return null;
  }
//class DogFactory
//{
//  public static Dog getDog(String criteria)
//  {
//    if ( criteria.equals("small") )
//      return new Poodle();
//    else if ( criteria.equals("big") )
//      return new Rottweiler();
//    else if ( criteria.equals("working") )
//      return new SiberianHusky();
// 
//    return null;
//  }
//}
}
