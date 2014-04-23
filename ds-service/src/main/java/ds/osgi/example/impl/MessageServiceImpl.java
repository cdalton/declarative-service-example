package ds.osgi.example.impl;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;
import ds.osgi.example.api.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component(name = MessageServiceImpl.COMPONENT_NAME)
public class MessageServiceImpl implements MessageService {
    public static final String COMPONENT_NAME = "MessageService";
    public static final String COMPONENT_LABEL = "Message Service";
    private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);
    private ExecutorService executor = Executors.newCachedThreadPool();
    private Worker worker = new Worker();

    @Activate
    public void activate() {
        LOG.info("Activating the " + COMPONENT_LABEL);
    }

    @Deactivate
    public void deactivate() {
        LOG.info("Deactivating the " + COMPONENT_LABEL);
    }


    @Override
    public void startMessageService() {
        LOG.info("Starting Worker for " + COMPONENT_LABEL);
        if(executor == null || executor.isTerminated() || executor.isShutdown()) {
            executor = Executors.newCachedThreadPool();
        }
        executor.execute(worker);
    }

    @Override
    public void stopMessageService() {
        LOG.info("Stopping Worker for " + COMPONENT_LABEL);
        if (!executor.isTerminated()) {
            executor.shutdownNow();
        }
    }

    @Override
    public void setMessage(String message) {
        worker.setMessage(message);
    }

    @Override
    public String getMessage() {
        return worker.getMessage();
    }

    private class Worker implements Runnable {

        private String message;

        public void run() {
            boolean running = true;
            int messageCount = 0;
            while (running) {
                try {
                    LOG.info("Message " + (++messageCount) + ": " + message);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    running = false;
                    LOG.info("Thread shutting down");
                }
            }
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
