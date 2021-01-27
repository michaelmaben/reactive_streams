package com.jeevanmaben;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;

import java.util.Arrays;
import java.util.List;


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
    }
}
