package com.sanket.designPattern.LazySingletonInitialization;


/**
 * This is an example of creating a thread-safe singleton
 * instance of a class .
 */
public class LazySingletonInitialization {

    private static LazySingletonInitialization singletonInstance = null;

    private LazySingletonInitialization() {

    }

    /**
     * @return singletonInstance of this class.
     * <p>
     * We had to double check the existence of object in order to
     * prevent race condition.
     */
    public static LazySingletonInitialization getSingletonInstance() {
        if (singletonInstance == null) {
            synchronized (LazySingletonInitialization.class) {
                if (singletonInstance == null)
                    singletonInstance = new LazySingletonInitialization();
            }
        }
        return singletonInstance;
    }
}
