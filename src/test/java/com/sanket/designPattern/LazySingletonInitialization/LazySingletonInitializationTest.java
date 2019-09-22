package com.sanket.designPattern.LazySingletonInitialization;


import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static org.junit.Assert.assertTrue;

public class LazySingletonInitializationTest {

    LazySingletonInitialization lazySingletonInitialization1 ;
    LazySingletonInitialization lazySingletonInitialization2 ;
    public LazySingletonInitializationTest(){

    }
    @Test
    public void getSingletonInstance() {
        lazySingletonInitialization1 = LazySingletonInitialization.getSingletonInstance();
        lazySingletonInitialization2 = LazySingletonInitialization.getSingletonInstance();

        assertTrue(lazySingletonInitialization1.hashCode() == lazySingletonInitialization2.hashCode());
    }

    @Test
    public void checkThreadSafeInitialization() throws Exception{

        // We want to start just 2 threads at the same time, but let's control that
        // timing from the main thread. That's why we have 3 "parties" instead of 2.
        final CyclicBarrier gate = new CyclicBarrier(3);

        Thread t1 = new Thread() {
            public void run() {
                try {
                    gate.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                lazySingletonInitialization1 = LazySingletonInitialization.getSingletonInstance();
            }
        };
        Thread t2 = new Thread() {
            public void run() {
                try {
                    gate.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

                lazySingletonInitialization2 = LazySingletonInitialization.getSingletonInstance();

            }
        };

        t1.start();
        t2.start();

        // At this point, t1 and t2 are blocking on the gate.
        // Since we gave "3" as the argument, gate is not opened yet.
        // Now if we block on the gate from the main thread, it will open
        // and all threads will start to do stuff!

        gate.await();
        Thread.sleep(1000);
        assertTrue(lazySingletonInitialization1.hashCode() == lazySingletonInitialization2.hashCode());
    }
}