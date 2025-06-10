package mft.tools;

import mft.model.entity.AccountInfo;
import mft.model.entity.Person;
import mft.model.entity.TransactionType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityMapper {


        public static Person personMapper(ResultSet resultSet) throws SQLException {
            return Person.builder()
                    .id(resultSet.getInt("TRANSACTION_ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .birthDate(resultSet.getDate("BIRTH_DATE") == null ? null : resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .phoneNumber(resultSet.getString("PHONE_NUMBER"))
                    .build();
        }


    public static AccountInfo accountInfoMapper(ResultSet resultSet) throws SQLException {
            Person person = Person
                    .builder()
                    .id(resultSet.getInt("PERSON_ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .birthDate(resultSet.getDate("birth_date") == null ? null : resultSet.getDate("birth_date").toLocalDate())
                    .phoneNumber(resultSet.getString("PHONE_NUMBER"))
                    .build();

        return AccountInfo
                .builder()
                .id(resultSet.getInt("TRANSACTION_ID"))
                .person(person)
                .amount(resultSet.getInt("AMOUNT"))
                .transactionType(TransactionType.valueOf(resultSet.getString("TRANSACTION_TYPE")))
                .dateTime(resultSet.getTimestamp("TRANSACTION_DATE_TIME").toLocalDateTime())
                .build();
    }


}
