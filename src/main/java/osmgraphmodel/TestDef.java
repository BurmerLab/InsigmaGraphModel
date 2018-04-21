package osmgraphmodel;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import osmgraphmodel.Algorithms.AStar;
import pl.pojo.GeneralPathWithStatisticsResult;

/**
 *
 * @author Michał
 */

    public class TestDef{
      
        public int start;
        public int goal;
        public String description;
        public List<Integer> result = null;
        public long executionTime = 0;
        public int numberOfIterations = 0;
        public int openListMaxCount = 0;
        public double distance = 0;
        public double time = 0;
        public double speed = 0;
        
        public TestDef(int start, int goal, String description){
            this.start = start;
            this.goal = goal;
            this.description = description;
        }
        public static List<Integer> blackHoles = new ArrayList<Integer>();

        public TestDef() {}
        
//        public delegate void RunTest(TestDef t);
//        public abstract void RunTest(TestDef t);

        public static TestDef[] testDefs = {
            new TestDef(13822576,13822694,"Two neigbhbour nodes nodes on a way"),
            new TestDef(13822576,282909676,"Two nodes nodes at way ends"),
            new TestDef(1240403602,1740753958,"Rozrywka - komisariat Strzelców (drugiego punktu brak)"),
            new TestDef(1240403602,282872001,"Rozrywka - Skrzyżowanie Sabały/Reduta "),
            new TestDef(282872001,236313351,"Skrzyżowanie Sabały/Reduta - Czarnowiejska"),
            new TestDef(282872001,519548638,"Skrzyżowanie Sabały/Reduta - Homolacsa"),
            new TestDef(282872001,799397482,"Skrzyżowanie Sabały/Reduta - gdzieś w Ustrzykach Dolnych"),
            new TestDef(282872001,301673526,"Skrzyżowanie Sabały/Reduta - Gdańsk Wrzeszcz w okolicach Politechniki"),
            
            new TestDef(261732023,207426792,"Gdzies w Krakowie/ gdzies indziej w Krakowie"),
            
        };

//        public List<Integer> RunAstarTest(TestDef def) throws Exception{
//          
//          //start: 261732023, goal: 207426792
//          AStar aStar = new AStar(def.start, def.goal);
//          System.out.println("Running test (A*): " + def.description + " >>>");
//          long before = new Date().getTime();
//          
//          System.out.println("Before time >>> " + milisecondsToTimeHMS(before));
//
//          def.result = aStar.Run();
//
//          long after = new Date().getTime();
//          System.out.println("After time >>> " + milisecondsToTimeHMS(after));
//
//          def.executionTime = (after - before);
//          def.numberOfIterations = aStar.progress;
//          def.openListMaxCount = aStar.openListMaxCount;
//          
//          ReportResult(def);
//          
//          return def.result;
//        }
        
        public GeneralPathWithStatisticsResult RunAstarTest(int idStart, int idEnd) throws Exception{
          
          //start: 261732023, goal: 207426792
          TestDef def = new TestDef(idStart, idEnd, "");
          
          AStar aStar = new AStar(def.start, def.goal);
          System.out.println("Running test (A*): " + def.description + " >>>");
          long before = new Date().getTime();
          
          System.out.println("Before time >>> " + milisecondsToTimeHMS(before));

          def.result = aStar.Run();

          long after = new Date().getTime();
          System.out.println("After time >>> " + milisecondsToTimeHMS(after));

          def.executionTime = (after - before);
          def.numberOfIterations = aStar.progress;
          def.openListMaxCount = aStar.openListMaxCount;
          
          ReportResult(def);
          
          GeneralPathWithStatisticsResult generalPathWithStatisticsResult = 
                new GeneralPathWithStatisticsResult(def.result, def.time, def.distance, 0, AStar.allParametersToCountPath);
          
          return generalPathWithStatisticsResult;
        }
        

        public static String milisecondsToTimeHMS(long milliseconds){
          DecimalFormat twoDigit = new DecimalFormat("00");
          long seconds = milliseconds/1000;
          String secondsString = twoDigit.format(seconds%60);

          long minutes = seconds/60;
          String minutesString = twoDigit.format(minutes%60);
          
          long hours = minutes/60;
          String hoursString = twoDigit.format(hours%24);
          return hoursString + ":" + minutesString + ":" + secondsString;
        }
        
//        static void RunAdjointAstarTest(TestDef def)
//        {
//            AdjointAStar a = new AdjointAStar(def.start, def.goal);
//            Console.Write("Running test (A*Adjoint): " + def.description + " >>>");
//            long before = DateTime.UtcNow.Ticks;
//            def.result = a.Run();
//            long after = DateTime.UtcNow.Ticks;
//            def.executionTime = after - before;
//            def.numberOfIterations = a.progress;
//            def.openListMaxCount = a.openListMaxCount;
//            ReportResult(def);
//        }

//        public static void TryAdjointAStar(int i){
//            RunAdjointAstarTest(testDefs[i]);
//        }
        
//        public List<Integer> TryAStar(int i) throws Exception{
//            return RunAstarTest(testDefs[i]);
//        }
//        public static void RunDedicatedAstarTests(){
//            RunDedicatedTests("astar-test-dedicated.txt", RunAstarTest);
//        }
        
//        public static void RunDedicatedAdjointAstarTests(){
//            RunDedicatedTests("astar-adjoint-test-dedicated.txt", RunAdjointAstarTest);
//        }
        
//        public static void RunDedicatedTests(String filename, RunTest runTest)
//        {
//            for (TestDef t : testDefs)
//            {
//                runTest(t);
//            }
//            if (TestDef.blackHoles.Count() != 0)
//            {
//                Console.Write("Black holes: ");
//                for (int i : TestDef.blackHoles)
//                {
//                    Console.Write(i + " ");
//                }
//                Console.WriteLine();
//            }
//            StreamWriter writer = new StreamWriter(filename);
//            int testSuiteNumber = 0;
//
//            for (TestDef t : testDefs)
//            {
//                testSuiteNumber++;
//                writer.WriteLine("\n\n");
//                writer.WriteLine(String.Format("^Test#{0}^{1}^", testSuiteNumber, t.description));
//                writer.WriteLine("\n\n");
//                TestHeaderAsWiki(writer);
//                ReportResultAsWiki(t, writer);
//            }
//            writer.Close();
//
//        }
//        public static void RunRandomAstarTests()
//        {
//            RunRandomTests("astar-random-test-results.txt",RunAstarTest);
//        }
        
//        public static void RunRandomAdjointAstarTests(){
//            RunRandomTests("astar-adjoint-random-test-results.txt", RunAstarTest);
//        }
        
//        public static void RunRandomTests(String filename, RunTest runTest){
//            StreamWriter writer = new StreamWriter(filename);
//            int testSuiteNumber = 0;
//            String description;
//
//            testSuiteNumber++;
//            description = "Wyznaczanie 100 tras pomiędzy losowo wybranymi punktami w promieniu 0.5 km od Rynku Głównego w Krakowie.";
//            writer.WriteLine("\n\n");
//            writer.WriteLine(String.Format("^Test#{0}^{1}^", testSuiteNumber, description));
//            writer.WriteLine("\n\n");
//            TestHeaderAsWiki(writer);
//            RunRandomTestSuite(100, 0.5, description, writer, runTest);
//
//            testSuiteNumber++;
//            description = "Wyznaczanie 100 tras pomiędzy losowo wybranymi punktami w promieniu 1 km od Rynku Głównego w Krakowie.";
//            writer.WriteLine("\n\n");
//            writer.WriteLine(String.Format("^Test#{0}^{1}^", testSuiteNumber, description));
//            writer.WriteLine("\n\n");
//            TestHeaderAsWiki(writer);
//            RunRandomTestSuite(100, 1, description, writer, runTest);
//
//            testSuiteNumber++;
//            description = "Wyznaczanie 100 tras pomiędzy losowo wybranymi punktami w promieniu 2 km od Rynku Głównego w Krakowie.";
//            writer.WriteLine("\n\n");
//            writer.WriteLine(String.Format("^Test#{0}^{1}^", testSuiteNumber, description));
//            writer.WriteLine("\n\n");
//            TestHeaderAsWiki(writer);
//            RunRandomTestSuite(100, 2, description, writer, runTest);
//
//            testSuiteNumber++;
//            description = "Wyznaczanie 100 tras pomiędzy losowo wybranymi punktami w promieniu 5 km od Rynku Głównego w Krakowie.";
//            writer.WriteLine("\n\n");
//            writer.WriteLine(String.Format("^Test#{0}^{1}^", testSuiteNumber, description));
//            writer.WriteLine("\n\n");
//            TestHeaderAsWiki(writer);
//            RunRandomTestSuite(100, 5, description, writer, runTest);
//
//            testSuiteNumber++;
//            description = "Wyznaczanie 100 tras pomiędzy losowo wybranymi punktami w promieniu 10 km od Rynku Głównego w Krakowie.";
//            writer.WriteLine("\n\n");
//            writer.WriteLine(String.Format("^Test#{0}^{1}^", testSuiteNumber, description));
//            writer.WriteLine("\n\n");
//            TestHeaderAsWiki(writer);
//            RunRandomTestSuite(100, 10, description, writer, runTest);
//
//            writer.Close();
//
//            if (TestDef.blackHoles.Count() != 0)
//            {
//                Console.Write("Black holes: ");
//                for (int i : TestDef.blackHoles)
//                {
//                    Console.Write(i + " ");
//                }
//                Console.WriteLine();
//            }
//
//        }

//        public static void ReportBlackHoles(){
//            StreamWriter writer = new StreamWriter("black-holes.txt");
//            writer.WriteLine("=== Black holes ===");
//            writer.WriteLine("^Id węzła (OSM)^Stopień węzła^");
//            for (int i : TestDef.blackHoles)
//            {
//                writer.WriteLine(String.Format("|{0}|{1}|", i, OSMGraphModelImp.Get().getNodeDegree(i)));
//
//            }
//            writer.Close();
//        }

        

//        public static void RunRandomTestSuite(int count, double radius, String description, StreamWriter writer, RunTest runTest){
//            TestDef[] randomTests = new TestDef[count];
//            List<int> nodes = OSMGraphModelImp.Get().drawSomeRandomNodes(2 * count, 50.06146779, 19.93799775, radius);
//            for (int i = 0; i < count; i++)
//            {
//                randomTests[i] = new TestDef(nodes.ElementAt(2 * i), nodes.ElementAt(2 * i + 1), description);
//            }
//
//            for (TestDef t : randomTests)
//            {
//                runTest(t);
//                ReportResultAsWiki(t, writer);
//
//            }
//            int failsCount = 0;
//            int successCount = 0;
//            double avgNodes = 0;
//            double avgLength = 0;
//            double avgSpeed = 0;
//            double avgTime = 0;
//            int maxIterations = 0;
//            double avgIterations = 0;
//            double avgIterationsPerLengths = 0;
//            double avgExecutionTime = 0;
//            long maxExecutionTime = 0;
//            int maxFootPrint = 0;
//            double avgFootprint = 0;
//
//            for (TestDef t : randomTests)
//            {
//                if (t.result == null)
//                {
//                    failsCount++;
//                    continue;
//                }
//                successCount++;
//                avgNodes += t.result.Count();
//                avgLength += t.distance;
//                avgSpeed += t.speed;
//                avgTime += t.time;
//                if (maxIterations < t.numberOfIterations) maxIterations = t.numberOfIterations;
//                avgIterations += t.numberOfIterations;
//                if (maxExecutionTime < t.executionTime) maxExecutionTime = t.executionTime;
//                avgExecutionTime += t.executionTime;
//                if (t.distance != 0) avgIterationsPerLengths += (t.numberOfIterations / t.distance);
//                if (maxFootPrint < t.openListMaxCount) maxFootPrint = t.openListMaxCount;
//                avgFootprint += t.openListMaxCount;
//            }
//            if (successCount == 0) return;
//            avgNodes /= successCount;
//            avgLength /= successCount;
//            avgSpeed /= successCount;
//            avgTime /= successCount;
//            avgIterations /= successCount;
//            avgExecutionTime /= successCount;
//
//            avgIterationsPerLengths /= successCount;
//            avgFootprint /= successCount;
//
//            int timesec = (int)(avgTime * 3600);
//            int sec = timesec % 60;
//            int min = (timesec / 60) % 60;
//            int h = (timesec / 3600) % 24;
//            TimeSpan travelTime = new TimeSpan(h, min, sec);
//
//
//            writer.WriteLine();
//            writer.WriteLine(description + "-- podsumowanie (podane wartości dotyczą zadań wyznaczania trasy zakończonych sukcesem)");
//            writer.WriteLine();
//
//            writer.WriteLine("^Własność^Wartość^");
//            writer.WriteLine("|Testy zakończone suksesem|{0}|", successCount);
//            writer.WriteLine("|Testy zakończone porażką|{0}|", failsCount);
//            writer.WriteLine("|Średnia liczba węzłów w wyznaczonej trasie|{0:0.00}|", avgNodes);
//            writer.WriteLine("|Średnia długość wyznaczonej trasy (km)|{0:0.00}|", avgLength);
//            writer.WriteLine("|Średni czas przejazdu|{0}|", travelTime.ToString());
//            writer.WriteLine("|Średnia prędkość (km/h) |{0:0.00}|", avgSpeed);
//
//            writer.WriteLine("|Maksymalna liczba iteracji|{0:0.00}|", maxIterations);
//            writer.WriteLine("|Średnia liczba iteracji|{0:0.00}|", avgIterations);
//            writer.WriteLine("|Średnia liczba iteracji na długość trasy (1/km)|{0:0.00}|", avgIterationsPerLengths);
//
//            writer.WriteLine("|Maksymany czas wyznaczania trasy |{0}|", new TimeSpan(maxExecutionTime));
//            writer.WriteLine("|Średni czas wyznaczania trasy |{0}|", new TimeSpan((long)avgExecutionTime));
//
//            writer.WriteLine("|Maksymalny rozmiar zbioru otwartych węzłów|{0}|", maxFootPrint);
//            writer.WriteLine("|Średni rozmiar zbioru otwartych węzłów|{0:0.00}|", avgFootprint);
//            writer.WriteLine();
//        }
        
        
//        static voide
        static void ReportResult(TestDef def){
          long interval = def.executionTime;
            
            if (def.result == null){
                System.out.println("FAIL: can't find path from: " + def.start + " to: " + def.goal);
                System.out.println("Execution time: " + milisecondsToTimeHMS(interval));
                return;
            }

            System.out.println("SUCCESS");
            System.out.print("[");
            double distance = 0;
            double time = 0;
            
            for (int i = 0; i < def.result.size(); i++){
//              System.out.print(def.result.get(i) + (i != def.result.size() - 1 ? " " : ""));  
              System.out.print(" OR OSM_id = " + def.result.get(i) + (i != def.result.size() - 1 ? " " : ""));  
              if (i == 0){
                  continue;
              }
              int edge = OSMGraphModelImp.Get().obtainSegmentIdFromAllPointsSegmentsMap(def.result.get(i - 1), def.result.get(i));
              double d = OSMGraphModelImp.Get().getEdgeDistance(edge);
              distance += d;
              double v = OSMGraphModelImp.Get().getEdgeSpeed(edge);
              time += (d / v);

              if (i >= 1 && i < def.result.size() - 1){
                  time += OSMGraphModelImp.Get().getTurnCost(def.result.get(i-1), def.result.get(i), def.result.get(i+1));
              }
                
            }

            def.distance = distance;
            def.time = time;
            def.speed = distance / time;

            System.out.println("]");
            int timesec = (int)(time * 3600);
            int sec = timesec % 60;
            int min = (timesec / 60) % 60;
            int h = (timesec / 3600) % 24;
            String travelTime = (h+ ":" +min+":"+sec);

            System.out.println("COST: " +  " Distance: " + distance + "km\n");
            System.out.println("Time: " + travelTime);
            
            double avgSpeed = distance / time;
            
            DecimalFormat twoDigit = new DecimalFormat("00");
            String avgSpeedText = twoDigit.format(avgSpeed);
            
//            System.out.println("Avg. speed: " + distance / time + "km/h");
            System.out.println("Avg. speed: " + avgSpeedText + "km/h");
            
            double time_iteration = 1e-7 * def.executionTime / def.numberOfIterations;
            System.out.println("Execution time: " + milisecondsToTimeHMS(interval) + " Number of iterations:" + def.numberOfIterations + " Iteration time:" + time_iteration);
        }
        
        
        }

//        static void TestHeaderAsWiki(StreamWriter writer){
//            writer.WriteLine("^Status^Start (OSM id)^Koniec (OSM id)^Liczba węzłow w trasie^Odległość (km)^Czas przejazdu^Prędkość (km/h)^Liczba iteracji^Czas obliczen^Open list footprint^");
//        }
        
//        static void ReportResultAsWiki(TestDef def, StreamWriter writer){
//            // |status|start|goal|number of nodes|distance| travel time| avg speed|liczba iteracji| czas obliczen|
//            TimeSpan interval = new TimeSpan(def.executionTime);
//
//            if (def.result == null)
//            {
//                Object[] args1 = { def.start, def.goal, "-", "-", "-", "-", def.numberOfIterations, interval.ToString(), def.openListMaxCount };
//                String line1 = String.Format("|FAIL|{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|", args1);
//                writer.WriteLine(line1);
//                return;
//            }
//
//            double distance = 0;
//            double time = 0;
//            for (int i = 1; i < def.result.Count(); i++)
//            {
//                int edge = OSMGraphModelImp.Get().obtainSegmentIdFromAllPointsSegmentsMap(def.result.ElementAt(i - 1), def.result.ElementAt(i));
//                double d = OSMGraphModelImp.Get().getEdgeDistance(edge);
//                distance += d;
//                double v = OSMGraphModelImp.Get().getEdgeSpeed(edge);
//                time += (d / v);
//            }
//            int timesec = (int)(time * 3600);
//            int sec = timesec % 60;
//            int min = (timesec / 60) % 60;
//            int h = (timesec / 3600) % 24;
//            TimeSpan travelTime = new TimeSpan(h, min, sec);
//
//
//            double time_iteration = 1e-7 * def.executionTime / def.numberOfIterations;
//            // |status|start|goal|number of nodes|distance| travel time| avg speed|liczba iteracji| czas obliczen|
//
//            Object[] args = { def.start, def.goal, def.result.Count(), distance, travelTime.ToString(), distance / time, def.numberOfIterations, interval.ToString(), def.openListMaxCount };
//            String line2 = String.Format("|OK|{0}|{1}|{2}|{3:0.00}|{4}|{5:0.00}|{6}|{7}|{8}|", args);
//            writer.WriteLine(line2);
//        }