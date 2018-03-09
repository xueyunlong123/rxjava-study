package com.rxjava.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by xyl on 2018/2/26.
 */
public class test {
    public static void main(String[] args) throws InterruptedException {

        final AtomicLong failureCount = new AtomicLong();

        PublishSubject<Integer> publishSubject = PublishSubject.create();
        publishSubject.window(1, TimeUnit.SECONDS)
                .flatMap(o -> o.reduce((integer, integer2) -> integer + integer2).toObservable())
                .window(1)
                .flatMap(o -> o.reduce((integer, integer2) -> integer + integer2).toObservable())
                .subscribe(
                        integer -> {
                            System.out.println("[" + Thread.currentThread().getName() + "] call ...... " + integer + "");
                            failureCount.set(integer);
                        }
                );

        for (int i = 0; i < 100; i++) {
            if (i == 10){
//                publishSubject.onNext(0);
            }else {
                publishSubject.onNext(1);
            }

            Thread.sleep(1000);
            System.out.println("当前 数值为 ：{}" + failureCount.get());
        }
    }
}



