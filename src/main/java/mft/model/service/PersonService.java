package mft.model.service;

import mft.controller.exceptions.PersonNotFoundException;
import mft.model.entity.Person;
import mft.model.ropository.PersonRepository;

import java.util.List;

public class PersonService {

    public static void save(Person person) throws Exception {
        try (PersonRepository personRepository = new PersonRepository()) {
            personRepository.save(person);
        }
    }

    public static void edit(Person person) throws Exception {
        try (PersonRepository personRepository = new PersonRepository()) {
            if (personRepository.findById(person.getId()) != null) {
                personRepository.edit(person);
            } else {
                throw new PersonNotFoundException();
            }
        }
    }

    public static void delete(int id) throws Exception {
        try (PersonRepository personRepository = new PersonRepository()) {
            if (personRepository.findById(id) != null) {
                personRepository.delete(id);
            } else {
                throw new PersonNotFoundException();
            }
        }
    }

    public static List<Person> findAll() throws Exception {
        try (PersonRepository personRepository = new PersonRepository()) {
            return personRepository.findAll();
        }
    }

    public static Person findById(int id) throws Exception {
        try (PersonRepository personRepository = new PersonRepository()) {
            return personRepository.findById(id);
        }
    }

    public static List<Person> findByNameAndFamily(String name, String family) throws Exception {
        try (PersonRepository personRepository = new PersonRepository()) {
            return personRepository.findByNameAndFamily(name, family);
        }
    }

}
