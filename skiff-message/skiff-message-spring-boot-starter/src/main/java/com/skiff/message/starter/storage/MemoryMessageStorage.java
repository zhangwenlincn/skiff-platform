package com.skiff.message.starter.storage;

import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import skiff.message.core.base.Message;
import skiff.message.core.storage.MessageStorageService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Order(-1)
public class MemoryMessageStorage implements MessageStorageService {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MemoryMessageStorage.class);


    private static final ConcurrentMap<String, Message> executes = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Message> locks = new ConcurrentHashMap<>();

    @Override
    public boolean tryLockMessage(Message message) {
        Message exits = locks.putIfAbsent(message.getId(), message);
        return exits == null;
    }

    @Override
    public void unlockMessage(Message message) {
        locks.remove(message.getId());
    }

    @Override
    public List<Message> getMessages() {
        return executes.values().stream().toList();
    }

    @Override
    public void saveMessage(Message message) {
        executes.put(message.getId(), message);
    }

    @Override
    public void deleteMessage(String key) {
        locks.remove(key);
        executes.remove(key);
    }

    @Override
    public void errorMessage(Message message, String errorMessage) {
        logger.debug("Error message: key {} error message {}", message.getId(), errorMessage);
    }
}
