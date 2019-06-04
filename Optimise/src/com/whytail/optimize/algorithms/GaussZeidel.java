package com.whytail.optimize.algorithms;

import java.util.*;
import com.whytail.optimize.*;
public class GaussZeidel {
    private double previous, current;
    private double dx, dy;
    private ArrayList<Double> functionData;
    private ArrayList<Point> functionPoints;
    private Function analyzeFunc;
    private Point outPoint;
    
    public GaussZeidel(Function analyzeFunc, double dx, double dy){
        this.analyzeFunc = analyzeFunc;
        this.dx = dx;
        this.dy = dy;
        previous = analyzeFunc.functionInvoke(analyzeFunc.getInitialPoint());
        current = analyzeFunc.functionInvoke(analyzeFunc.getInitialPoint());
        outPoint = new Point(analyzeFunc.getInitialPoint());
        functionData = new ArrayList<>();
        functionPoints = new ArrayList<>();
    }
    
    public Point getAnalyzedPoint(){
        return outPoint;
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
    
    public void analyze(){
        boolean changed[] = {false, false};
        int iteratorCounter = 0;
        while(dx >= analyzeFunc.getEpsilon() && dy >= analyzeFunc.getEpsilon()){
            _analyze(dx, 0, changed, 0);
            if(!changed[0]){
                _analyze(- dx, 0, changed, 0);
            }
            _analyze(0, dy, changed, 1);
            if(!changed[1]){
                _analyze(0, -dy, changed, 1);
            }
            if(!changed[0] && !changed[1]){
                iteratorCounter++;
            }
            if(iteratorCounter > 1){
                dx /= 2;
                dy /= 2;
                iteratorCounter = 0;
            }
            changed[0] = changed[1] = false;
        }
    }
    private void _analyze(double dx, double dy, boolean g[], int i){
        outPoint.setX(outPoint.getX() + dx);
        outPoint.setY(outPoint.getY() + dy);
        while(true){
            current = analyzeFunc.functionInvoke(outPoint);
            if(previous > current){
                functionData.add(current);
                functionPoints.add(new Point(outPoint.getX(), outPoint.getY()));
                previous = current;
                outPoint.setX(outPoint.getX() + dx);
                outPoint.setY(outPoint.getY() + dy);
                g[i] = true;
            } else {
                outPoint.setX(outPoint.getX() - dx);
                outPoint.setY(outPoint.getY() - dy);
                break;
            }
        }
    }
}
