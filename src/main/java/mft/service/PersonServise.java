package mft.service;

import mft.model.entity.Person;
import mft.model.ropository.PersonRepository;

import java.time.LocalDate;
import java.util.List;

public class PersonServise {

    public static void save(Person person) throws Exception {
        try (PersonRepository personRepository = new PersonRepository()) {
            // قانون ۱: بررسی سن
            int age = LocalDate.now().getYear() - person.getBirthDate().getYear();
            if (age < 20) {
                throw new Exception("سن برای ثبت‌نام باید حداقل ۲۰ سال باشد.");
            }

            if (person.getUsername() == null || person.getUsername().isEmpty()) {
                throw new Exception("نام کاربری نمی‌تواند خالی باشد.");
            }

            if (person.getPassword() == null || person.getPassword().isEmpty()) {
                throw new Exception("رمز عبور نمی‌تواند خالی باشد.");
            }

            personRepository.save(person);
        }
    }

    public static void edit(Person person) throws Exception {
        try (PersonRepository personRepository = new PersonRepository()) {
            personRepository.edit(person);
        }
    }

    public static void delete(int id) throws Exception {
        try (PersonRepository personRepository = new PersonRepository()) {
            personRepository.delete(id);
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
