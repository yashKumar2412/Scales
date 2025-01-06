package tech.scales.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SchedulerSettings {

    @Value("${config.settings.hc.frequency}")
    private int frequency;

    @Value("${config.settings.hc.timeout}")
    private int timeout;

    @Value("${config.settings.hc.retry}")
    private int retryLimit;

    public synchronized int getFrequency() {
        return frequency;
    }

    public synchronized void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public synchronized int getTimeout() {
        return timeout;
    }

    public synchronized void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public synchronized int getRetryLimit() {
        return retryLimit;
    }

    public synchronized void setRetryLimit(int retryLimit) {
        this.retryLimit = retryLimit;
    }

    @Override
    public String toString() {
        return "SchedulerSettings{" +
                "frequency=" + frequency +
                ", timeout=" + timeout +
                ", retryLimit=" + retryLimit +
                '}';
    }
}
