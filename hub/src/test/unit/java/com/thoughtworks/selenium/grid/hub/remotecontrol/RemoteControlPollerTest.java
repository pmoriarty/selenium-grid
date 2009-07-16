package com.thoughtworks.selenium.grid.hub.remotecontrol;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.io.IOException;

public class RemoteControlPollerTest extends UsingClassMock {

    @Test
    public void activeIsTrueJustAfterThePollerHasBeenCreated() {
        assertTrue(new RemoteControlPoller(null, 0, 0).active());
    }

    @Test
    public void activeIsFalseOnceStopHasBeenCalled() {
        final RemoteControlPoller poller;

        poller = new RemoteControlPoller(null, 0, 0);
        poller.stop();
        assertFalse(poller.active());
    }

    @Test
    public void pollingIntervalInMillisecondsIsDerivedFromTheConstructorValueInSeconds() {
        assertEquals(1000, new RemoteControlPoller(null, 1, 0).pollingIntervalInMilliseconds());
    }

    @Test
    public void sessionMaxIdleTimeInSecondsIsTheOneProvidedInTheConstructor() {
        assertEquals(37.2, new RemoteControlPoller(null, 1, 37.2).sessionMaxIdleTimeInSeconds());
    }

    @Test
    public void pollAllRegisteredRemoteControlsCallsUnregisterAllUnresponsiveRemoteControlsOnThePool() throws IOException {
        final RemoteControlPoller poller;
        final Mock pool;

        pool = mock(DynamicRemoteControlPool.class);
        poller = new RemoteControlPoller((DynamicRemoteControlPool) pool, 0, 0);

        pool.expects("unregisterAllUnresponsiveRemoteControls");
        poller.pollAllRegisteredRemoteControls();

        verifyMocks();
    }

}
