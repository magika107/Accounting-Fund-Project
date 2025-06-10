import mft.model.entity.Person;
import mft.service.PersonServise;

import java.time.LocalDate;

public class PersonTest {

    public static void main(String[] args) throws Exception {
        Person person = new Person();
        person.setName("ali");
        person.setFamily("alipour");
        person.setUsername("ali123");
        person.setPassword("pass123");
        person.setPhone_number("09120000000");
        person.setBirthDate(LocalDate.of(2000, 1, 1));

//        PersonService.save(person);
        System.out.println(PersonServise.findAll());
        System.out.println(PersonServise.findById(person.getId()));
    }

}
