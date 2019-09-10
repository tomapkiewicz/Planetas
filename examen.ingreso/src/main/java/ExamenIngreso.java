import com.sun.jdi.VMCannotBeModifiedException;
import com.sun.org.apache.xpath.internal.functions.FuncFloor;
import jdk.vm.ci.meta.LineNumberTable;
import sun.awt.image.PixelConverter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.MissingFormatArgumentException;

public class ExamenIngreso {
  static   double velFerengi = 1; //1 grado son 0.0174533 radianes
    static   double disFerengi = 500;
    static     double  velBetasoide = 3; //3 grados son 0.0523599
    static     double disBetasoide = 2000;
    static double velVulcano = -5; //5 grados son 0.08727 radianes (*Math.PI/180)
    static double disVulcano = 1000;
    static     Point2D posFerengi;
    static Point2D posVulcano;
    static  Point2D posBetasoide;
    static  int sequias = 0 ;
    static int lluvias = 0;
    static int optimas = 0;
    static double perimetros[];

    static public void resolver(){
        int picoLluvia=0;
        double max=0;

        for (int i = 0; i < 3650; ) {
            boolean haySequia =false;
            boolean hayOptimas =false;

            double XFerengi = disFerengi * Math.cos(velFerengi*Math.PI/180 * i);
            double YFerengi = disFerengi * Math.sin(velFerengi*Math.PI/180 * i);
            double MFerengi =YFerengi / XFerengi;
            posFerengi=new Point2D.Double(XFerengi,YFerengi);


            double XBetasoide = disBetasoide * Math.cos(velBetasoide*Math.PI/180 * i);
            double YBetasoide = disBetasoide * Math.sin(velBetasoide*Math.PI/180 * i);
            double MBetsaoide =  YBetasoide / XBetasoide ;
            posBetasoide =new Point2D.Double(XBetasoide,YBetasoide);

            double XVulcano = disVulcano * Math.cos(velVulcano*Math.PI/180 * i);
            double YVulcano = disVulcano * Math.sin(velVulcano*Math.PI/180 * i);
            double MVulcano =  YVulcano / XVulcano ;
            posVulcano =new Point2D.Double(XVulcano,YVulcano);

            //Pendiente entre dos puntos AB es igual a: Mab = (Ya-Yb)/(Xa-Xb)
            double MFB = (YFerengi-YBetasoide)/(XFerengi- XBetasoide);
            double MVB = (YVulcano-YBetasoide)/(XVulcano- XBetasoide);
            if(Math.abs(MBetsaoide-MFB)>0.0001 && Math.abs(MFB-MVB)<0.01 ){
                System.out.println("Hay optimas condiciones de presion");
                optimas++;
                hayOptimas=true;
            }


            int ii= (int) Math.floorMod(i,360);
            double auxFerengi=  Math.floorMod((int)Math.abs(velFerengi*ii),180);
            double auxBetasoide=  Math.floorMod((int)Math.abs(velBetasoide*ii),180);
            double auxVulcano= Math.floorMod((int)Math.abs(velVulcano*ii),180);

            if(auxFerengi==auxBetasoide && auxBetasoide == auxVulcano)
             {
                 System.out.println("Hay sequia en el dia " + i);
                 haySequia=true;
                 sequias++;
             }

//            System.out.println("en el dia " + i + " hay pendiente en MFerengi" + MFerengi+ " MVulcano " +MVulcano+ " MBetasoide "+MBetsaoide);
//           System.out.println("Posicion Ferengi " + posFerengi+ " posVulcano " +posVulcano+ " posBetasoide "+posBetasoide);

            i++;

            Point2D posSol =  new Point2D.Double(0,0);
            boolean hayLluvia = ptInTriangle(posSol,posVulcano,posBetasoide,posFerengi);
            if(hayLluvia && hayOptimas==false  && haySequia == false )lluvias++;
            if(hayLluvia)System.out.println("Hay lluvia el dia " + i );

            double ladoA = Math.hypot(posFerengi.getX()-posBetasoide.getX(), posFerengi.getY()-posBetasoide.getY());
            double ladoB = Math.hypot(posFerengi.getX()-posVulcano.getX(), posFerengi.getY()-posVulcano.getY());
            double ladoC = Math.hypot(posBetasoide.getX()-posVulcano.getX(), posBetasoide.getY()-posVulcano.getY());
            double perimetro = ladoA + ladoB + ladoC ;

            if (perimetro > max)
            {
                max = perimetro;
                picoLluvia = i;
            }
        }
        System.out.println("Sequias: " + sequias );
        System.out.println("Lluvias: " + lluvias );
        System.out.println("Condiciones optimas: " + optimas );

        //Punto 2 cuántos periodos de lluvia habrá y qué día será el pico máximo de lluvia?
        //Un periodo de lluvia es aquel donde el sol esta ubicado dentro del triangulo generado por los 3 planetas.
        //Un triangulo entre los 3 planetas ocurre siempre y cuando no esten alineados

        //Habría que obtener dado el punto del sol, cuál es el valor de X que corresponde con un valor válido dentro del triangulo
        //Dado un triangulo obtener si el punto 0,0 se encuentra dentro del triangulo
        //Habría que saber si el triangulo contiene el punto 0,0

        //El calculo de la orientacion de un triangulo se puede realizar segun la siguiente formula:
        // (A1.X - A3.X) * (A2.Y - A3.Y) - (A1.Y -A3.Y)*(A2.X - A3.X)

    }
    public static void main() {
       resolver();
     //conectar();
   }

   static public void conectar(){
        conexionSql miConexion = new conexionSql();
        miConexion.conectar();
   }

    static boolean ptInTriangle (Point2D p, Point2D p0, Point2D  p1, Point2D p2) {
        double A =  (-p1.getY() * p2.getX() + p0.getY() * (-p1.getX() + p2.getX()) + p0.getX() * (p1.getY() - p2.getY()) + p1.getX() * p2.getY())/2;
        double sign = A < 0 ? -1 : 1;
        double s = (p0.getY() * p2.getX() - p0.getX() * p2.getY() + (p2.getY() - p0.getY()) * p.getX() + (p0.getX() - p2.getX()) * p.getY()) * sign;
        double t = (p0.getX() * p1.getY() - p0.getY() * p1.getX() + (p0.getY() - p1.getY()) * p.getX() + (p1.getX() - p0.getX()) * p.getY()) * sign;

        boolean r = s > 0 && t > 0 && (s + t) < 2 * A * sign;

        return r;
    }

}
