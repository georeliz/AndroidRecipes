package edu.galileo.android.androidrecipes.libs;

/**
 * Created by Gerson on 23/06/2016.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);

}
