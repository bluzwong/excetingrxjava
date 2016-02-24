package com.github.bluzwong.excitingrxjava;

import java.util.Random;

/**
 * Created by Bruce-Home on 2016/2/24.
 */
public class Mocks {

    public static int slowGetA() throws InterruptedException {
        /// ... bala bala
        Thread.sleep(200);
        return new Random().nextInt(5);
    }

    public static int slowGetB() throws InterruptedException {
        /// ... bala bala
        Thread.sleep(200);
        return new Random().nextInt(100);
    }

    public static int slowAdd(int a, int b) throws InterruptedException {
        /// ... bala bala
        Thread.sleep(200);
        return a + b;
    }

}
