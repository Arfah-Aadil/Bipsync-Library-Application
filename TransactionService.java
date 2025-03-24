//package library.libraryapp.service;
//
//import library.libraryapp.model.Transaction;
//import library.libraryapp.repository.TransactionRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class TransactionService {
//
//    private final TransactionRepository transactionRepository;
//
//    public TransactionService(TransactionRepository transactionRepository) {
//        this.transactionRepository = transactionRepository;
//    }
//
//    public List<Transaction> getAllTransactions() {
//        return transactionRepository.findAll();
//    }
//
//    public List<Transaction> getUpcomingDueDates() {
//        // Define the date range for upcoming due dates (e.g., next 3 days)
//        Date threeDaysFromNow = new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000);
//        return transactionRepository.findTransactionsDueBefore(threeDaysFromNow);
//    }
//}
package library.libraryapp.service;

import library.libraryapp.model.Transaction;
import library.libraryapp.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }
    public List<Transaction> getUpcomingDueDates() {

        Date threeDaysFromNow = new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000);
        return transactionRepository.findTransactionsDueBefore(threeDaysFromNow);
    }
}
