/* Colin Walsh
// Problem set 3
// Fractal problem
*/ 9/20/16



import java.awt.Graphics;
import java.util.Scanner;
import java.util.regex.Matcher;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class FractalFern {

    public static int MAX_DIMENSION = 500;

    public static int option = 0;
    public static int speed = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter speed (an integer greater than 0): ");
        speed = sc.nextInt();
        System.out.println("Enter fractal type (1,2,3,4): ");
        option = sc.nextInt();

        JFrame frame = new JFrame("Fractal");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(MAX_DIMENSION, MAX_DIMENSION);
        Fractal fractal = new Fractal();
        frame.add(fractal);
        frame.setVisible(true);
        new Thread(fractal).start();
    }
}

class Fractal extends JPanel implements Runnable {
    private static final long serialVersionUID = 1L;
    private static int componentsDrawn = 0;
    private static int componentsToDraw = 0;

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int initX = (int) (FractalFern.MAX_DIMENSION * 0.3);
        int initY = (int) (FractalFern.MAX_DIMENSION * 0.6);
        int initLen = (int) (FractalFern.MAX_DIMENSION * 0.1);
        switch (FractalFern.option) {
            case 1:
                drawFractal1(g, initX, initY, initLen, (Math.PI/3));
                break;
            case 2:
                drawFractal2(g, initX, initY, initLen, (Math.PI/3));
                break;
            case 3:
                drawFractal3(g, initX, initY, initLen, (Math.PI/3));
                break;
            case 4:
                drawFractal4(g, initX, initY, initLen, (Math.PI/3));
                break;
        }
    }

    private void drawFractal3(Graphics g, int x1, int y1, int segmentLength, double angle) {
        int currX = x1;
        int currY = y1;
        int nextX = currX + (int) (segmentLength * Math.cos(angle));
        int nextY = currY - (int) (segmentLength * Math.sin(angle));

        if(segmentLength <= 1) return;
        drawFractal3(g, nextX, nextY, (int)(segmentLength*0.6), (angle - ((Math.PI) / 2)));//right
        drawFractal3(g, nextX, nextY, (int)(segmentLength*0.6), (angle + ((Math.PI) / 2)));//left
        drawFractal3(g, nextX, nextY, (int)(segmentLength*0.9), (angle - ((Math.PI) / 36)) );//center
        // Note that whenever you draw a line, it should be in an if statement
        // like the one below, so that you can progressively render the fractal
        if (componentsDrawn < componentsToDraw) {
            componentsDrawn++;
            g.drawLine(currX, currY, nextX, nextY);

        }
    }

    private void drawFractal2(Graphics g, int x1, int y1, int segmentLength, double angle) {
        int currX = x1;
        int currY = y1;
        int nextX = currX + (int) (segmentLength * Math.cos(angle));
        int nextY = currY - (int) (segmentLength * Math.sin(angle));

        // Note that whenever you draw a line, it should be in an if statement
        // like the one below, so that you can progressively render the fractal
        if(segmentLength <= 1) return;
    if(componentsDrawn < componentsToDraw){
            componentsDrawn++;

        int tcurrX = currX;
        int tcurrY = currY;
        int tnextX = nextX;
        int tnextY = nextY;
        int tSegment = segmentLength;
        double tAngle = angle;
        //draws center stem.
        while(tSegment > 1){
        componentsDrawn++;
            g.drawLine(tcurrX, tcurrY, tnextX, tnextY);
            tSegment =  (int)(tSegment * 0.9);
            tAngle -= ((Math.PI) / 36);
            tcurrX = tnextX;
            tcurrY = tnextY;
            tnextX = tcurrX + (int) (tSegment * Math.cos(tAngle));
            tnextY = tcurrY - (int) (tSegment * Math.sin(tAngle));
        }

        drawFractal2(g, nextX, nextY, (int)(segmentLength*0.6), (angle + ((Math.PI) / 2)));//left
        drawFractal2(g, nextX, nextY, (int)(segmentLength*0.6), (angle - ((Math.PI) / 2)));//right
        drawFractal2(g, nextX, nextY, (int)(segmentLength*0.9), (angle - ((Math.PI) / 36)) );//center

        }


    }

    private void drawFractal1(Graphics g, int x1, int y1, int segmentLength, double angle) {

        int currX = x1;
        int currY = y1;
        int nextX = currX + (int) (segmentLength * Math.cos(angle));
        int nextY = currY - (int) (segmentLength * Math.sin(angle));
        // Note that whenever you draw a line, it should be in an if statement
        // like the one below, so that you can progressively render the fractal
        if(segmentLength <= 1) return;

        if (componentsDrawn < componentsToDraw) {
            componentsDrawn++;
            g.drawLine(currX, currY, nextX, nextY);
            //left
            drawFractal1(g, nextX, nextY, (int)(segmentLength*0.6), (angle + ((Math.PI) / 2)) );
            //right
            drawFractal1(g, nextX, nextY, (int)(segmentLength*0.6), (angle - ((Math.PI) / 2)) );
            //center
            drawFractal1(g, nextX, nextY, (int)(segmentLength*0.9), (angle - ((Math.PI) / 36)) );
        }




    }

    private void drawFractal4(Graphics g, int x1, int y1, int segmentLength, double angle) {

        int currX = x1;
        int currY = y1;
        int nextX = currX + (int) (segmentLength * Math.cos(Math.PI/3));
        int nextY = currY - (int) (segmentLength * Math.sin(Math.PI/3));

        // Note that whenever you draw a line, it should be in an if statement
        // like the one below, so that you can progressively render the fractal
        if (componentsDrawn < componentsToDraw) {
            componentsDrawn++;
            g.drawLine(currX, currY, nextX, nextY);
        }
    }

    public void run() {
        try {
            while(true) {
                componentsDrawn = 0;
                componentsToDraw++;
                this.repaint();
                for (int i = 0; i < FractalFern.speed; i++) {
                    Thread.sleep(10);
                }
            }
        }
        catch (InterruptedException e) {}
    }
}