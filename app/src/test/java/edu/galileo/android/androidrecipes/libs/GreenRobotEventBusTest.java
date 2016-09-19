package edu.galileo.android.androidrecipes.libs;

import org.junit.Test;
import org.mockito.Mock;

import edu.galileo.android.androidrecipes.BaseTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by Lab1 on 08/08/2016.
 */
public class GreenRobotEventBusTest extends BaseTest{
    private GreenRobotEventBus greenRobotEventBus;
    @Mock
    private org.greenrobot.eventbus.EventBus eventBus;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        greenRobotEventBus = new GreenRobotEventBus(eventBus);

    }

    @Test
    public void testRegister() throws Exception {
        String test = "";
        greenRobotEventBus.register(test);
        verify(eventBus).register(test);
    }

    @Test
    public void testUnRegister() throws Exception {
        String test = "";
        greenRobotEventBus.unregister(test);
        verify(eventBus).unregister(test);
    }

    @Test
    public void testPost() throws Exception {
        String event = "";
        greenRobotEventBus.post(event);
        verify(eventBus).post(event);
    }
}