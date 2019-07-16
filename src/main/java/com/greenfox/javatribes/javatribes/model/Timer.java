package com.greenfox.javatribes.javatribes.model;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
public class Timer {

    public long time;

    public Timer(long time) {
        this.time = time;
    }

    public Timer() {
    }

    @Scheduled(fixedRate = 1000)
    public void increaseTime () {

        time += 1;

    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
