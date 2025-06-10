package mft.model.ropository;

import mft.model.entity.AccountInfo;
import mft.tools.ConnectionProvider;
import mft.tools.EntityMapper;

import java.sql.*;
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
                "INSERT INTO accountinfo (id, person_id, amount, transaction_type, date_time) VALUES (?, ?, ?, ?, ?)"
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
                "UPDATE accountinfo SET person_id = ?, amount = ?, transaction_type = ?, date_time = ? WHERE id = ?"
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
                "DELETE FROM accountinfo WHERE id = ?"
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

//    todo : findByPersonNameAndFamily, findByTransactionType, findByDate(startDateTime, endDateTime)

    @Override
    public void close() throws Exception {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
    }

}

