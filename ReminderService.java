package library.libraryapp.service;

import library.libraryapp.model.Transaction;
import library.libraryapp.model.User;
import library.libraryapp.repository.TransactionRepository;
import library.libraryapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
//    private final JavaMailSender mailSender;

    @Autowired
    public ReminderService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
//        this.mailSender = mailSender;
    }

    public List<Transaction> getUpcomingTransactions() {
        // Implement logic to retrieve upcoming transactions
        // This could be transactions that are due within the next few days
        return transactionRepository.findTransactionsDueBefore(new java.util.Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000));
    }

//    public void sendReminderForTransaction(Long transactionId) {
//        Transaction transaction = transactionRepository.findById(transactionId);
//        if (transaction != null) {
//            User user = userRepository.findById(transaction.getUserId());
//            if (user != null) {
//                sendReminderEmail(user, transaction);
//            } else {
//                System.out.println("No user found for transaction ID: " + transactionId);
//            }
//        } else {
//            System.out.println("No transaction found with ID: " + transactionId);
//        }
//    }
//
//    private void sendReminderEmail(User user, Transaction transaction) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(user.getEmail());
//        message.setSubject("Reminder: Upcoming Due Date");
//        message.setText("Dear " + user.getUsername() + ",\n\nThis is a reminder that your borrowed item \"" + transaction.getModel() + "\" is due in 3 days (Due Date: " + transaction.getDueDate() + "). Please return it on time to avoid any penalties.\n\nThank you,\nLibrary Team");
//
//        mailSender.send(message);
//    }
//public void sendReminderForTransaction(Long transactionId) {
//    Transaction transaction = transactionRepository.findById(transactionId);
//    if (transaction != null) {
//        User user = userRepository.findById(transaction.getUserId());
//        if (user != null) {
//            sendReminderEmail(user, transaction);
//        } else {
//            System.out.println("No user found for transaction ID: " + transactionId);
//        }
//    } else {
//        System.out.println("No transaction found with ID: " + transactionId);
//    }
//}

//    private void sendReminderEmail(User user, Transaction transaction) {
//        System.out.println("Sending email to: " + user.getEmail()); // Debugging output
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(user.getEmail());
//        message.setSubject("Reminder: Upcoming Due Date");
//        message.setText("Dear " + user.getUsername() + ",\n\nThis is a reminder that your borrowed item \"" + transaction.getModel() + "\" is due in 3 days (Due Date: " + transaction.getDueDate() + "). Please return it on time to avoid any penalties.\n\nThank you,\nLibrary Team");
//
//        try {
//            mailSender.send(message);
//            System.out.println("Email sent successfully."); // Debugging output
//        } catch (Exception e) {
//            System.out.println("Failed to send email: " + e.getMessage()); // Debugging output
//        }
    }

