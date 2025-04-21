package com.ll.back250421.global.redis;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Profile("!prod")
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.data.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws IOException {
        int port = isRedisRunning(redisPort) ? findAvailablePort() : redisPort;
        redisServer = new RedisServer(port);
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() throws IOException {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    public int findAvailablePort() {
        for (int port = 10_000; port <= 65_535; port++) {
            if (!isRedisRunning(port)) {
                return port;
            }
        }
        throw new IllegalStateException("Could not find available port " + redisPort);
    }

    /**
     * Embedded Redis가 현재 실행중인지 확인
     */
    private boolean isRedisRunning(int port) {
        Process process = executeGrepProcessCommand(port);
        return isRunning(process);
    }

    /**
     * OS에 따라 적절한 명령어 실행 (윈도우/리눅스 구분)
     */
    private Process executeGrepProcessCommand(int port) {
        String os = System.getProperty("os.name").toLowerCase();
        String command;
        String[] shell;

        if (os.contains("win")) {
            command = "netstat -ano | findstr :" + port;
            shell = new String[]{"cmd", "/c", command};
        } else {
            command = "netstat -nat | grep LISTEN | grep " + port;
            shell = new String[]{"/bin/sh", "-c", command};
        }

        try {
            return Runtime.getRuntime().exec(shell);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to execute command to check port " + port, e);
        }
    }

    private boolean isRunning(Process process) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String output = reader.lines().reduce("", (acc, line) -> acc + line);
            return !output.isBlank();
        } catch (Exception e) {
            throw new IllegalStateException("Error occurred while checking redis port " + redisPort, e);
        }
    }
}
