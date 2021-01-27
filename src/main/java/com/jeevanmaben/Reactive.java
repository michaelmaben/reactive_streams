package com.jeevanmaben;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Reactive {
    public static void main(String[] args){
        System.out.println("Reactive streams::");
        Integer feed[] = {1,2,3,4,5,6,7};
        Flowable<Integer> source = Flowable.fromArray(feed);
        //subscribe to even numbers
        source.filter(e-> e%2 == 0)
                .subscribe(msg -> System.out.println("Sub1:" + msg));

        //subscribe to odd numbers - second pipeline
        source.filter(e-> e%2 != 0)
                .subscribe(msg -> System.out.println("Sub2:" + msg));

        bufferStrategy();
        dropStrategy();
    }

    public static void bufferStrategy(){
        System.out.println("Buffered Strategy");
        Flowable.<Integer>create(emitter -> emit(emitter), BackpressureStrategy.BUFFER)
                .observeOn(Schedulers.computation())
                .subscribe(msg -> process(msg));
        sleep(10000);
    }

    public static void dropStrategy(){
        System.out.println("Drop Strategy");
        Flowable.<Integer>create(emitter -> emit(emitter), BackpressureStrategy.DROP)
                .observeOn(Schedulers.computation(), true, 2)
                .subscribe(msg -> process(msg));
        sleep(10000);
    }


    private static void  emit(FlowableEmitter<Integer> emitter){
        int count = 0;
        while (count < 10){
            count ++;
            System.out.println("Emitted " + count);
            emitter.onNext(count);
            sleep(500);
        }
        emitter.onComplete();
    }

    private static void process(Integer msg){
        System.out.println("Subscriber " + msg);
        sleep(1000);
    }

    private static boolean sleep(long period){
        try {
            Thread.sleep(period);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

}
