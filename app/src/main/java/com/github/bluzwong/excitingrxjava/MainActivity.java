package com.github.bluzwong.excitingrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import java.util.concurrent.CountDownLatch;

import static com.github.bluzwong.excitingrxjava.AsyncFunction.*;
import static com.github.bluzwong.excitingrxjava.RxjavaFunction.*;

public class MainActivity extends AppCompatActivity {

    TextView tvNormal, tvRxjava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_normal).setOnClickListener(normalOnClick);
        findViewById(R.id.btn_rxjava).setOnClickListener(rxjavaWithLambdaOnClick);
        tvNormal = (TextView) findViewById(R.id.tv_normal);
        tvRxjava = (TextView) findViewById(R.id.tv_rxjava);
    }

    View.OnClickListener normalOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final CountDownLatch latch = new CountDownLatch(2);
            final int[] a = new int[1];
            final int[] b = new int[1];
            asyncGetA(new FooListener() {
                @Override
                public void onComplete(int _a) {
                    a[0] = _a;
                    latch.countDown();
                }
            });
            asyncGetB(new FooListener() {
                @Override
                public void onComplete(int _b) {
                    b[0] = _b;
                    latch.countDown();
                }
            });
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    asyncAdd(a[0], b[0], new FooListener() {
                        @Override
                        public void onComplete(final int result) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvNormal.setText("result is : " + result);
                                }
                            });
                        }
                    });
                }
            }).start();

        }
    };

    static class AnB {
        public AnB(int a, int b) {
            this.a = a;
            this.b = b;
        }

        int a;
        int b;
    }

    View.OnClickListener rxjavaOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Observable.zip(rxGetA(), rxGetB(),
                    new Func2<Integer, Integer, AnB>() {
                        @Override
                        public AnB call(Integer a, Integer b) {
                            return new AnB(a, b);
                        }
                    })
                    .flatMap(new Func1<AnB, Observable<Integer>>() {
                        @Override
                        public Observable<Integer> call(AnB anB) {
                            return rxAdd(anB.a, anB.b);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Integer>() {
                        @Override
                        public void call(Integer result) {
                            tvNormal.setText("result is : " + result);
                        }
                    });
        }
    };

    View.OnClickListener rxjavaWithLambdaOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Observable.zip(rxGetA(), rxGetB(),
                    AnB::new)
                    .flatMap(anB -> rxAdd(anB.a, anB.b))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> tvRxjava.setText("result is : " + result));
        }
    };
}
