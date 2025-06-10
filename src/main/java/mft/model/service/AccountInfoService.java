package mft.model.service;

import mft.model.entity.AccountInfo;
import mft.model.ropository.AccountInfoRepository;

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

    public static AccountInfo findById(int id) throws Exception {
        try (AccountInfoRepository repository = new AccountInfoRepository()) {
            return repository.findById(id);
        }
    }

    public static List<AccountInfo> findAll() throws Exception {
        try (AccountInfoRepository repository = new AccountInfoRepository()) {
            return repository.findAll();
        }
    }

    public static List<AccountInfo> findByPersonId(int personId) throws Exception {
        try (AccountInfoRepository repository = new AccountInfoRepository()) {
            return repository.findByPersonId(personId);
        }
    }
//    todo : findByPersonNameAndFamily, findByTransactionType, findByDate(startDateTime, endDateTime)

}


