package net.javaguides.springboot.email;

public interface EmailSender {
    void send(String to, String email);
}