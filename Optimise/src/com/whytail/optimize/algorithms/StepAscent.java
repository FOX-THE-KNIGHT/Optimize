package com.whytail.optimize.algorithms;

import java.util.*;
import com.whytail.optimize.*;

public class StepAscent {
    private Point[] experiment;
    private Function analyzeFunc;
    private double[] funcValues;
    private static final double[][] mask = {{-1, -1}, {1, -1}, {-1, 1}, {1, 1}};
    private Point currentPoint;
    private Point result;
    private Point[] expPoints;
    private double b1, b2; //коэффициенты регрессии
    
    public StepAscent(Function analyzeFunc){
        this.analyzeFunc = analyzeFunc;
        experiment = new Point[4];
        funcValues = new double[4];
        expPoints = new Point[4];
        currentPoint = new Point(analyzeFunc.getInitialPoint());
        b1 = b2 = 0;
    }
    
    //public getters
    //main algoeithm
    public void analyze(){
        do{
            _makeExperiment(currentPoint);
            _getRegressKoefficients();
            _calculateRegression();
            double dx = 0.01 * 1 * b1;
            double dy = 0.01 * 1 * b2;
            expPoints[0] = currentPoint;
            funcValues[0] = analyzeFunc.functionInvoke(currentPoint);
            for(int i = 1; i < expPoints.length; i++){
                expPoints[i] = new Point(expPoints[i - 1].getX() - dx, expPoints[i - 1].getY() - dy);
            }
            for(int i = 1; i < funcValues.length; i++){
                funcValues[i] = analyzeFunc.functionInvoke(expPoints[i]);
            }
            
            currentPoint = _getMinPoint();
            System.out.println("Текущая точка минимума - " + currentPoint);
        } while (Math.sqrt(b1*b1 + b2*b2) >= analyzeFunc.getEpsilon());
        result = currentPoint;
    }
    //printer
    public void printResult(){
        if(result == null){
            System.out.println("Функция еще не исследована");
        } else {
            System.out.println("Анализ по крутому восхождению определил точку минимума - " + result);
            System.out.println("Значение функции в этой точке f(x, y) = " + analyzeFunc.functionInvoke(result));
        }
    }
    //private algorithms
    private void _makeExperiment(Point p){
        for(int i = 0; i < 4; i++){
            experiment[i] = new Point(p.getX() + mask[i][0], p.getY() + mask[i][1]);
        }
    }
    private void _getRegressKoefficients(){
        for(int i = 0; i < experiment.length; i++){
            funcValues[i] = analyzeFunc.functionInvoke(experiment[i]);
        }
    }
    private void _calculateRegression(){
        b1 = (-funcValues[0] + funcValues[1] - funcValues[2] + funcValues[3])/4;
        b2 = (-funcValues[0] - funcValues[1] + funcValues[2] + funcValues[3])/4;
    }
    private Point _getMinPoint(){
        Point res = expPoints[0];
        for(Point p : expPoints)
            if(analyzeFunc.functionInvoke(p) < analyzeFunc.functionInvoke(res))
                res = p;
        return res;
    }
}
