package edu.galileo.android.androidrecipes.recipemain;

import android.view.MotionEvent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import edu.galileo.android.androidrecipes.BaseTest;
import edu.galileo.android.androidrecipes.BuildConfig;
import edu.galileo.android.androidrecipes.recipemain.ui.SwipeGestureDetector;
import edu.galileo.android.androidrecipes.recipemain.ui.SwipeGestureListener;

import static org.mockito.Mockito.verify;

/**
 * Created by Lab1 on 01/08/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SwipeGestureDetectorTest extends BaseTest {
    @Mock
    private SwipeGestureListener listener;
    private SwipeGestureDetector detector;
    @Override
    public void setUp() throws Exception {
        super.setUp();
        detector = new SwipeGestureDetector(listener);
    }

    @Test
    public void testSwipeRight_ShouldCallKeepOnListener() throws Exception {
        long downTime = 0;
        long moveTime = downTime + 500;
        long upTime = downTime + 1000;
        float xStart = 200;
        float yStart = 200;
        float xEnd = 500;
        float yEnd = 250;

        MotionEvent e1 = MotionEvent.obtain(downTime, moveTime, MotionEvent.ACTION_MOVE, xStart, yStart, 0);
        MotionEvent e2 = MotionEvent.obtain(downTime, upTime, MotionEvent.ACTION_UP, xEnd, yEnd, 0);

        float velocityX = 120;

        detector.onFling(e1, e2, velocityX, 0);
        verify(listener).onKeep();


    }

    @Test
    public void testSwipeLeft_ShouldCallDismissOnListener() throws Exception {
        long downTime = 0;
        long moveTime = downTime + 500;
        long upTime = downTime + 1000;
        float xStart = 200;
        float yStart = 200;
        float xEnd = -500;
        float yEnd = 250;

        MotionEvent e1 = MotionEvent.obtain(downTime, moveTime, MotionEvent.ACTION_MOVE, xStart, yStart, 0);
        MotionEvent e2 = MotionEvent.obtain(downTime, upTime, MotionEvent.ACTION_UP, xEnd, yEnd, 0);

        float velocityX = 120;

        detector.onFling(e1, e2, velocityX, 0);
        verify(listener).onDismiss();
    }
}
