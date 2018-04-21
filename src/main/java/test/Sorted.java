/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.*;

/**
 *
 * @author Michał
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
 
public class Sorted {
 
    public static void main(String[] args) {
        Sorted main = new Sorted();
        main.program();
    }
 
    private void program() {
        List<Czlowiek> ludzie = new ArrayList<Czlowiek>();
        ludzie.add(new Czlowiek('K', "Asia", "Wczorajsza"));
        ludzie.add(new Czlowiek('M', "Marcin", "Nikczemny"));
        ludzie.add(new Czlowiek('M', "Slawek", "Abacki"));
        ludzie.add(new Czlowiek('K', "Kasia", "Borowik"));
        ludzie.add(new Czlowiek('K', "Dorota", "Borowik"));
        ludzie.add(new Czlowiek('M', "Tomek", "Daszek"));
        ludzie.add(new Czlowiek('K', "Ania", "Daszek"));
 
        System.out.println("Nieposortowanie: ");
        for(Czlowiek czlowiek : ludzie) {
            System.out.println(czlowiek);
        }
 
        Collections.sort(ludzie);
 
        System.out.println("\nPosortowane: ");
        for(Czlowiek czlowiek : ludzie) {
            System.out.println(czlowiek);
        }
 
        Collections.sort(ludzie, new KomparatorPlec());
 
        System.out.println("\nPosortowane po plci: ");
        for(Czlowiek czlowiek : ludzie) {
            System.out.println(czlowiek);
        }
    }
 
    private class KomparatorPlec implements Comparator<Czlowiek> {
 
        @Override
        public int compare(Czlowiek o1, Czlowiek o2) {
            int plec =  o1.getPlec() - o2.getPlec();
            if(plec == 0) {
                return o1.compareTo(o2);
            }
            return plec;
        }
 
    }
 
}

