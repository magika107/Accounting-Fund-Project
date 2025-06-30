package mft.model.ropository;

import lombok.extern.log4j.Log4j2;
import mft.controller.exceptions.UserNotFoundException;
import mft.controller.exceptions.InvalidPersonDataException;
import mft.model.entity.Person;
import mft.tools.ConnectionProvider;
import mft.tools.EntityMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2

public class PersonRepository implements AutoCloseable {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public PersonRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    public void save(Person person) throws SQLException {
        preparedStatement = connection.prepareStatement("select person_seq.nextval from dual");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        person.setId(resultSet.getInt("nextval"));

        preparedStatement = connection.prepareStatement(
                "insert into person (id, name, family, username, password, birth_date, phone_number) values (?, ?, ?, ?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, person.getId());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setString(3, person.getFamily());
        preparedStatement.setString(4, person.getUsername());
        preparedStatement.setString(5, person.getPassword());
        preparedStatement.setDate(6, java.sql.Date.valueOf(person.getBirthDate()));
        preparedStatement.setString(7, person.getPhoneNumber());
        preparedStatement.execute();
    }

    public void edit(Person person) throws SQLException {
        preparedStatement = connection.prepareStatement(
                "update person set name=?, family=?, username=?, password=?, birth_date=?, phone_number=? where id=?"
        );
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getFamily());
        preparedStatement.setString(3, person.getUsername());
        preparedStatement.setString(4, person.getPassword());
        preparedStatement.setDate(5, java.sql.Date.valueOf(person.getBirthDate()));
        preparedStatement.setString(6, person.getPhoneNumber());
        preparedStatement.setInt(7, person.getId());
        preparedStatement.execute();
    }

    public void delete(int id) throws SQLException {
        preparedStatement = connection.prepareStatement(
                "delete from person where id=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public List<Person> findAll() throws SQLException {
        List<Person> personList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("select * from person");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            personList.add(EntityMapper.personMapper(resultSet));
        }
        return personList;
    }

    public Person findById(int id) throws SQLException {
        Person person = null;
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("select * from person where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            person = EntityMapper.personMapper(resultSet);
        }
        return person;
    }

    public List<Person> findByNameAndFamily(String name, String family) throws SQLException {
        List<Person> personList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM person WHERE name LIKE ? AND family LIKE ?"
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

    public Person login(String username, String password) throws UserNotFoundException, SQLException, InvalidPersonDataException {
        Person person = null;
        connection = ConnectionProvider.getConnectionProvider().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM PERSON WHERE USERNAME = ? AND PASSWORD = ?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            person = Person.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .family(resultSet.getString("family"))
                    .username(resultSet.getString("username"))
                    .password(resultSet.getString("password"))
                    .birthDate(resultSet.getDate("birth_date").toLocalDate())
                    .phoneNumber(resultSet.getString("phone_number"))
                    .build();
        }

        if (person == null) {
            log.error("Login - No User With username/password " + username + ":" + password);
            throw new UserNotFoundException("No User With username/password");
        }

        log.info("Login " + person);
        return person;
    }

    @Override
    public void close() throws Exception {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
    }
}
