package edu.galileo.android.androidrecipes.libs;

/**
 * Created by Gerson on 23/06/2016.
 */
public class GreenRobotEventBus implements EventBus{
   org.greenrobot.eventbus.EventBus eventBus;

 public GreenRobotEventBus(org.greenrobot.eventbus.EventBus eventBus) {
  this.eventBus = eventBus;
 }

 @Override
    public void register(Object subscriber) {

    }

    @Override
    public void unregister(Object subscriber) {

    }

    @Override
    public void post(Object event) {

    }
}
