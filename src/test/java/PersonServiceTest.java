import mft.model.entity.Person;
import mft.model.service.PersonService;

import java.time.LocalDate;

public class PersonServiceTest {

    public static void main(String[] args) throws Exception {
        Person person = Person
                .builder()
                .id(7)
                .name("reza")
                .family("rezaii")
                .username("reza")
                .password("reza123")
                .phoneNumber("09120000000")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

//        PersonService.save(person);
//        PersonService.edit(person);
        PersonService.delete(1);
        System.out.println(PersonService.findAll());
        System.out.println(PersonService.findById(7));
    }

}
