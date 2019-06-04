package com.whytail.optimize;

import com.whytail.optimize.algorithms.*;

public class Application {
    
    public static void main(String[] args) {
        double EA1[] = {1, 9, 0};
        double EA2[] = {0, 1, -1};
        double initCoords[] = {-6, 5};
//        double EA1[] = {1, 1, 0};
//        double EA2[] = {0, 1, -1};
//        double initCoords[] = {5, 6};
        double epsilon = 0.25;
        Function func = new Function(EA1, EA2, initCoords, epsilon);
        GaussZeidel gz = new GaussZeidel(func, 2, 2);
        gz.analyze();
        System.out.println("Результат метода Гаусса-Зейделя");
        gz.printResult();
        HoockGievs hg = new HoockGievs(func, 2, 2);
        hg.analyze();
        System.out.println("Результат метода Хука-Дживса");
        hg.printResult();
        SimplexMethod sm = new SimplexMethod(func, 2, true);
        sm.analyze();
        System.out.println("Результат простого симплекс-метода");
        sm.printResult();
        NulderMid nm = new NulderMid(func, 2);
        nm.analyze();
        System.out.println("Результат метода Нальдера-Мида");
        nm.printResult();
        StepAscent sa = new StepAscent(func);
        sa.analyze();
        System.out.println("Результат метода крутого восхождения");
        sa.printResult();
    }
}
