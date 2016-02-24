package com.github.bluzwong.excitingrxjava;

import rx.Observable;
import rx.functions.Func0;
import static com.github.bluzwong.excitingrxjava.Mocks.*;
/**
 * Created by Bruce-Home on 2016/2/24.
 */
public class RxjavaFunction {
    public static Observable<Integer> rxGetA() {
        return Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                int result = -1;
                try {
                    result = slowGetA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Observable.just(result);
            }
        });
    }

    public static Observable<Integer> rxGetB() {
        return Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                int result = -1;
                try {
                    result = slowGetB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Observable.just(result);
            }
        });
    }

    public static Observable<Integer> rxAdd(int a, int b) {
        return Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                int result = -1;
                try {
                    result = slowAdd(a, b);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Observable.just(result);
            }
        });
    }
}
