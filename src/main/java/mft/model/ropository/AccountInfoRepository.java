package mft.model.ropository;

import mft.model.entity.AccountInfo;
import mft.model.entity.Person;
import mft.model.entity.TransactionType;
import mft.tools.ConnectionProvider;
import mft.tools.EntityMapper;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AccountInfoRepository implements AutoCloseable {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public AccountInfoRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    public void save(AccountInfo accountInfo) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT transaction_seq.NEXTVAL AS nextval FROM dual");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        accountInfo.setId(resultSet.getInt("nextval"));

        preparedStatement = connection.prepareStatement(
                "INSERT INTO account_info (id, person_id, amount, transaction_type, date_time) VALUES (?, ?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, accountInfo.getId());
        preparedStatement.setInt(2, accountInfo.getPerson().getId());
        preparedStatement.setInt(3, accountInfo.getAmount());
        preparedStatement.setString(4, accountInfo.getTransactionType().name());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(accountInfo.getDateTime()));
        preparedStatement.execute();

    }

    public void edit(AccountInfo accountInfo) throws SQLException {
        preparedStatement = connection.prepareStatement(
                "UPDATE account_info SET person_id = ?, amount = ?, transaction_type = ?, date_time = ? WHERE id = ?"
        );
        preparedStatement.setInt(1, accountInfo.getPerson().getId());
        preparedStatement.setInt(2, accountInfo.getAmount());
        preparedStatement.setString(3, accountInfo.getTransactionType().name());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(accountInfo.getDateTime()));
        preparedStatement.setInt(5, accountInfo.getId());
        preparedStatement.execute();
    }

    public void delete(int id) throws SQLException {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM account_info WHERE id = ?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public List<AccountInfo> findAll() throws SQLException {
        List<AccountInfo> accountList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM TRANSACTION_REPORT");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            accountList.add(EntityMapper.accountInfoMapper(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return accountList;
    }

    public AccountInfo findById(int id) throws SQLException {
        AccountInfo accountInfo = null;
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM TRANSACTION_REPORT WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            accountInfo = EntityMapper.accountInfoMapper(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return accountInfo;
    }

    public List<AccountInfo> findByPersonId(int personId) throws SQLException {
        List<AccountInfo> accountList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM TRANSACTION_REPORT WHERE person_id = ?"
        );
        preparedStatement.setInt(1, personId);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            accountList.add(EntityMapper.accountInfoMapper(resultSet));
        }

        resultSet.close();
        preparedStatement.close();
        return accountList;
    }

    public List<AccountInfo> findByTransactionType(TransactionType transactionType) throws SQLException {
        List<AccountInfo> accountList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM TRANSACTION_REPORT WHERE TRANSACTION_TYPE=?");
        preparedStatement.setString(1, transactionType.name());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            accountList.add(EntityMapper.accountInfoMapper(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return accountList;
    }

    public List<AccountInfo> findByDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) throws SQLException {
        List<AccountInfo> accountList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM TRANSACTION_REPORT where transaction_date_time between ? and ?");
        preparedStatement.setTimestamp(1, Timestamp.valueOf(startDateTime));
        preparedStatement.setTimestamp(2, Timestamp.valueOf(endDateTime));
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            accountList.add(EntityMapper.accountInfoMapper(resultSet));

        }
        resultSet.close();
        preparedStatement.close();
        return accountList;
    }

    public List<Person> findByPersonNameAndFamily(String name, String family) throws SQLException {
        List<Person> personList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM transaction_report WHERE name LIKE ? AND family LIKE ?"
        );
        preparedStatement.setString(1, name + "%");
        preparedStatement.setString(2, family + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Person person = EntityMapper.personMapper(resultSet);
            personList.add(person);
        }

        resultSet.close();
        preparedStatement.close();
        return personList;
    }


    @Override
    public void close() throws Exception {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
    }

}

