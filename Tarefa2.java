/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercicios;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;
import projeto1.SimpleReader;

/**
 *
 * @author T-Gamer
 */
public class Tarefa2 {
    public static void main(String[] args) {
        
        SimpleReader f = new SimpleReader("C:\\Users\\T-Gamer\\Downloads\\sensors.txt");
        List<Sensor> sensors = new ArrayList<>();
        List<Central> centrals = new ArrayList<>();
        Integer n = 1;
        
        String line = f.readLine();
        
        while (line != null) {
            String[] coordinates = line.split(" ");
            
            sensors.add(new Sensor(n++ ,Double.valueOf(coordinates[0]), Double.valueOf(coordinates[1])));
            
            line = f.readLine();
        }
        
        centrals.add(new Central("Alpha", 0.4926, 0.6989));
        centrals.add(new Central("Bravo", 0.1253, 0.6775));
        centrals.add(new Central("Charlie", 0.8014, 0.6313));
        centrals.add(new Central("Delta", 0.4458, 0.2681));
        centrals.add(new Central("Echo", 0.4092, 0.0431));
        centrals.add(new Central("Foxtrot", 0.6645, 0.4028));
        centrals.add(new Central("Golf", 0.6751, 0.8003));
        centrals.add(new Central("Hotel", 0.4298, 0.6014));
        centrals.add(new Central("India", 0.4371, 0.9866));
        centrals.add(new Central("Juliet", 0.5729, 0.1587));
        
        for ( Sensor sensor : sensors ) {
            for ( Central central : centrals ) {
                
                Double distance;
                Double x;
                Double y;
                
                if ( Objects.equals(central.getLatitude(), sensor.getLatitude()) ) {
                    if ( central.getLongitude() > sensor.getLongitude() ) {
                        distance = central.getLongitude() - sensor.getLongitude();
                    } else {
                        distance = sensor.getLongitude() - central.getLongitude();
                    }
                } else if ( Objects.equals(central.getLongitude(), sensor.getLongitude()) ) {
                    if ( central.getLatitude() > sensor.getLatitude() ) {
                        distance = central.getLatitude() - sensor.getLatitude();
                    } else {
                        distance = sensor.getLatitude() - central.getLatitude();
                    }
                } else {
                    if ( central.getLatitude() > sensor.getLatitude() ) {
                        x = central.getLatitude() - sensor.getLatitude();
                    } else {
                        x = sensor.getLatitude() - central.getLatitude();
                    }
                    
                    if ( central.getLongitude() > sensor.getLongitude() ) {
                        y = central.getLongitude() - sensor.getLongitude();
                    } else {
                        y = sensor.getLongitude() - central.getLongitude();
                    }
                    
                    distance = Math.sqrt((x*x) + (y*y));
                }
                
                if ( distance <= 0.25 ) {
                    sensor.getCentrals().add(central);
                }
            }
        }
        
        System.out.println("");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("Unattended sensors:");
        System.out.println("");
        
        for ( Sensor sensor : sensors.stream().filter(s -> s.getCentrals().isEmpty()).collect(Collectors.toList()) ) {
            System.out.println("Nº: " + sensor.getNumber() + " Latitude: " + sensor.getLatitude() + " - Longitude: " + sensor.getLongitude() + " - Centrals: " + sensor.getCentrals().size());
        }
        
        System.out.println("");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("Multiple central attended sensors:");
        System.out.println("");
        
        for ( Sensor sensor : sensors.stream().filter(s -> s.getCentrals().size() > 1).collect(Collectors.toList()) ) {
            System.out.println("Nº: " + sensor.getNumber() + " Latitude: " + sensor.getLatitude() + " - Longitude: " + sensor.getLongitude() + " - Centrals: " + sensor.getCentrals().size());
        }
        
        System.out.println("");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("Single central attended sensors:");
        System.out.println("");
        
        Map<Central, Integer> map = new TreeMap<>();
        
        for ( Sensor sensor : sensors.stream().filter(s -> s.getCentrals().size() == 1).collect(Collectors.toList()) ) {
            for ( Central central : sensor.getCentrals() ) {
                if ( map.containsKey(central) ) {
                    map.put(central, map.get(central)+1);
                } else {
                    map.put(central, 1);
                }
            }
            
            System.out.println("Nº: " + sensor.getNumber() + " Latitude: " + sensor.getLatitude() + " - Longitude: " + sensor.getLongitude() + " - Centrals: " + sensor.getCentrals().size());
        }
        
        System.out.println("");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("Centrals attending most sensors:");
        System.out.println("");
        System.out.println("Central        Latitude        Longitude        Attended sensors");
        
        map.entrySet().stream()
           .sorted(Map.Entry.comparingByValue())
           .forEach(central -> System.out.println(String.format("%-" + 15 + "s", central.getKey().getName()) +
                                                  String.format("%-" + 16 + "s", central.getKey().getLatitude()) +
                                                  String.format("%-" + 16 + "s", central.getKey().getLongitude()) +
                                                  String.format("%" + 17 + "s", central.getValue())));
        
        SwingUtilities.invokeLater(()-> new Plot(sensors, centrals));
    }
    
    private static class Sensor {

        private Integer number;
        private Double latitude;
        private Double longitude;
        private List<Central> centrals;
        
        public Sensor(Integer number, Double latitude, Double longitude) {
            this.number = number;
            this.latitude = latitude;
            this.longitude = longitude;
            this.centrals = new ArrayList<>();
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
        
        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public List<Central> getCentrals() {
            return centrals;
        }

        public void setCentrals(List<Central> centrals) {
            this.centrals = centrals;
        }
        
    }
    
    private static class Central implements Comparable<Central>{
        
        private String name;
        private Double latitude;
        private Double longitude;
        
        public Central(String name, Double latitude, Double longitude) {
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        
        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        @Override
        public int compareTo(Central o) {
            return name.compareTo(o.getName());
        }
        
    }
    
    private static class Plot extends JFrame {
        public Plot(List<Sensor> sensors, List<Central> centrals) {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            PlotComponent pcomp = new PlotComponent(1000, 1000);
            
            add(pcomp);
            
            for ( Sensor sensor : sensors ) {
                pcomp.addSensor(sensor.getLatitude()*1000, sensor.getLongitude()*1000);
            }
            
            for ( Central central : centrals ) {
                pcomp.addCentral(central.getLatitude()*1000, central.getLongitude()*1000);
            }
            
            pack();
            setVisible(true);
        }
    }

    private static class PlotComponent extends JComponent {
        private ArrayList<Point2D> sensors = new ArrayList<Point2D>();
        private ArrayList<Point2D> centrals = new ArrayList<Point2D>();

        public PlotComponent(int width, int height) {
            setPreferredSize(new Dimension(width, height));
        }

        public void addSensor(double x, double y) {
            sensors.add(new Point2D.Double(x, y));
        }
        
        public void addCentral(double x, double y) {
            centrals.add(new Point2D.Double(x, y));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            g2d.setColor(Color.BLACK);
            
            for (Point2D p : centrals) {
                Shape central = new Ellipse2D.Double(p.getX() - 5, p.getY() - 5, 5*2, 5*2);
                Shape range = new Ellipse2D.Double(p.getX() - (0.25*1000), p.getY() - (0.25*1000), 0.25*2*1000, 0.25*2*1000);
                g2d.draw(range);
                g2d.fill(central);
            }
            
            g2d.setColor(Color.BLUE);

            for (Point2D p : sensors) {
                Shape point = new Ellipse2D.Double(p.getX() - 2, p.getY() - 2, 2*2, 2*2);
                g2d.fill(point);
            }
        }
    }
}
