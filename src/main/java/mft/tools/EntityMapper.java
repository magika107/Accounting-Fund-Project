package mft.tools;

import mft.model.entity.AccountInfo;
import mft.model.entity.Person;
import mft.model.entity.TransactionType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityMapper {


        public static Person personMapper(ResultSet resultSet) throws SQLException {
            return Person.builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .birthDate(resultSet.getDate("BIRTH_DATE") == null ? null : resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .phoneNumber(resultSet.getString("PHONE_NUMBER"))
                    .build();
        }


    public static AccountInfo accountInfoMapper(ResultSet resultSet) throws SQLException {
        return AccountInfo
                .builder()
                .id(resultSet.getInt("ID"))
//                .person.setId(resultSet.getInt("PERSON_ID"))
                .amount(resultSet.getInt("AMOUNT"))
                .transactionType(TransactionType.valueOf(resultSet.getString("TRANSACTION_TYPE")))
                .dateTime(resultSet.getTimestamp("DATE_TIME").toLocalDateTime())
                .build();
    }


}
