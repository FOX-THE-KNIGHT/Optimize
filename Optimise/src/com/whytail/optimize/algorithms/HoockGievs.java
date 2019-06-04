package com.whytail.optimize.algorithms;

import java.util.*;
import com.whytail.optimize.*;

public class HoockGievs {
    private double previous, current;
    private double dx, dy;
    private ArrayList<Double> functionData;
    private ArrayList<Point> functionPoints;
    private Function analyzeFunc;
    private Point nextPoint;
    private Point startPoint;
    
    public HoockGievs(Function analyzeFunc, double dx, double dy){
        previous = current = analyzeFunc.functionInvoke(analyzeFunc.getInitialPoint());
        this.analyzeFunc = analyzeFunc;
        this.dx = dx;
        this.dy = dy;
        functionData = new ArrayList<>();
        functionData.add(previous);
        functionPoints = new ArrayList<>();
        functionPoints.add(analyzeFunc.getInitialPoint());
        startPoint = new Point(analyzeFunc.getInitialPoint());
        nextPoint = new Point(startPoint);
    }
   public void analyze(){
       boolean changedVars[] = {false, false};
       int iteratorCounter = 0;
       while(dx >= analyzeFunc.getEpsilon() && dy >= analyzeFunc.getEpsilon()){
           _analyze(dx, 0, changedVars, 0);
           if(!changedVars[0]){
                _analyze(-dx, 0, changedVars, 0);
           }
           _analyze(0, dy, changedVars, 1);
           if(!changedVars[0]){
               _analyze(0, -dy, changedVars, 0);
           }
           if(changedVars[0] || changedVars[1]){
               double distanceX = nextPoint.getX() - startPoint.getX();
               double distanceY = nextPoint.getY() - startPoint.getY();
               startPoint.setX(nextPoint.getX());
               startPoint.setY(nextPoint.getY());
               nextPoint.setX(nextPoint.getX() + distanceX);
               nextPoint.setY(nextPoint.getY() + distanceY);
               changedVars[0] = changedVars[1] = false;
               continue;
           }
           if(!changedVars[0] && !changedVars[1]){
                iteratorCounter++;
           }
           if(iteratorCounter > 1){
               dx /= 2;
               dy /= 2;
               iteratorCounter = 0;
           }
       }
   }
   
   public Point getAnalyzedPoint(){
        return startPoint;
    }
    
    public ArrayList<Double> getListOfFunctionValues(){
        return functionData;
    }
    
    public ArrayList<Point> getListOfFunctionPoints(){
        return functionPoints;
    }
   
   public void printResult(){
        System.out.println("Результат анализа, с требуемой точностью e = " + analyzeFunc.getEpsilon() + " :");
        for(int i = 0; i < functionData.size(); i++){
            System.out.println(i + " точка - " + functionPoints.get(i) + " Значение функции - " + functionData.get(i));
        }
        System.out.println("Точка минимума - " + getAnalyzedPoint());
   }
   
   private void _analyze(double dx, double dy, boolean changed[], int indx){
       nextPoint.setX(nextPoint.getX() + dx);
       nextPoint.setY(nextPoint.getY() + dy);
       current = analyzeFunc.functionInvoke(nextPoint);
        if(previous > current){
             functionData.add(current);
             functionPoints.add(new Point(nextPoint.getX(), nextPoint.getY()));
             previous = current;
             changed[indx] = true;
         } else {
             nextPoint.setX(nextPoint.getX() - dx);
             nextPoint.setY(nextPoint.getY() - dy);
         }
   }
}
