package mft.model.service;

import mft.model.entity.AccountInfo;
import mft.model.entity.Person;
import mft.model.entity.TransactionType;
import mft.model.ropository.AccountInfoRepository;

import java.time.LocalDateTime;
import java.util.List;

public class AccountInfoService {
    public static void save(AccountInfo accountInfo) throws Exception {
        try (AccountInfoRepository repository = new AccountInfoRepository()) {
            repository.save(accountInfo);
        }
    }

    public static void edit(AccountInfo accountInfo) throws Exception {
        try (AccountInfoRepository repository = new AccountInfoRepository()) {
            repository.edit(accountInfo);
        }
    }

    public static void delete(int id) throws Exception {
        try (AccountInfoRepository repository = new AccountInfoRepository()) {
            repository.delete(id);
        }
    }

    public static List<AccountInfo> findAll() throws Exception {
        try (AccountInfoRepository repository = new AccountInfoRepository()) {
            return repository.findAll();
        }
    }

    public static AccountInfo findById(int id) throws Exception {
        try (AccountInfoRepository repository = new AccountInfoRepository()) {
            return repository.findById(id);
        }
    }


    public static List<AccountInfo> findByPersonId(int personId) throws Exception {
        try (AccountInfoRepository repository = new AccountInfoRepository()) {
            return repository.findByPersonId(personId);
        }
    }

    public static List<AccountInfo> findByPersonId(TransactionType transactionType) throws Exception {
        try (AccountInfoRepository repository = new AccountInfoRepository()) {
            return repository.findByTransactionType(transactionType);
        }
    }

    public static List<AccountInfo> findByDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) throws Exception {
        try (AccountInfoRepository repository = new AccountInfoRepository()) {
            return repository.findByDateTime(startDateTime, endDateTime);
        }
    }

    public static List<Person> findByPersonNameAndFamily(String name, String family) throws Exception {
        try (AccountInfoRepository repository = new AccountInfoRepository()) {
            return repository.findByPersonNameAndFamily(name, family);
        }
    }
}


