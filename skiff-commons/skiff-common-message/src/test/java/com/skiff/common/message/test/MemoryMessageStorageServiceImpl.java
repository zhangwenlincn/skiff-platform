package com.skiff.common.message.test;

import com.skiff.common.message.core.Message;
import com.skiff.common.message.storage.MessageStorageService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MemoryMessageStorageServiceImpl implements MessageStorageService {

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

    }
}
