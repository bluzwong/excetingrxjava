package com.github.bluzwong.excitingrxjava;
import static com.github.bluzwong.excitingrxjava.Mocks.*;
/**
 * Created by Bruce-Home on 2016/2/24.
 */
public class AsyncFunction {

    interface FooListener {
        void onComplete(int result);
    }

    public static void asyncGetA(final FooListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int a = -1;
                try {
                    a = slowGetA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                listener.onComplete(a);
            }
        }).start();
    }

    public static void asyncGetB(final FooListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int a = -1;
                try {
                    a = slowGetB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                listener.onComplete(a);
            }
        }).start();
    }

    public static void asyncAdd(final int a, final int b, final FooListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int result = -1;
                try {
                    result = slowAdd(a, b);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                listener.onComplete(result);
            }
        }).start();
    }
}
