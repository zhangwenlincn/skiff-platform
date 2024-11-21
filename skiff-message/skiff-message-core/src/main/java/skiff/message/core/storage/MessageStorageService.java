package skiff.message.core.storage;


import skiff.message.core.base.Message;

import java.util.List;

public interface MessageStorageService {

    /**
     * Lock a message in the storage.
     *
     * @param message the message to lock.
     * @return true if the message not locked go lock it, false otherwise.
     */
    boolean tryLockMessage(Message message);

    /**
     * Unlock a message in the storage.
     */
    void unlockMessage(Message message);

    /**
     * Get all messages from the storage.
     *
     * @return a list of all messages in the storage.
     */
    List<Message> getMessages();


    /**
     * Save a message to the storage.
     *
     * @param message the message to save.
     */
    void saveMessage(Message message);

    /**
     * Delete a message from the storage.
     *
     * @param key the key of the message to delete.
     */
    void deleteMessage(String key);

    /**
     * Log an error message.
     *
     * @param message      the error message to log.
     * @param errorMessage the error message.
     */
    void errorMessage(Message message, String errorMessage);
}
