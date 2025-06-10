import mft.model.entity.AccountInfo;
import mft.model.entity.Person;
import mft.model.entity.TransactionType;
import mft.model.service.AccountInfoService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AccountInfoTest {


    public static void main(String[] args) throws Exception {
        Person person = Person
                .builder()
                .id(2)
                .name("reza")
                .family("rezaii")
                .username("reza")
                .password("reza123")
                .phoneNumber("09120000000")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();


        AccountInfo accountInfo = AccountInfo
                .builder()
                .id(2)
                .person(person)
                .amount(500000)
                .transactionType(TransactionType.receipt)
                .dateTime(LocalDateTime.now())
                .build();

//    AccountInfoService.save(accountInfo);
//        AccountInfoService.edit(accountInfo);
        //   AccountInfoService.delete(4);

//        System.out.println(AccountInfoService.findAll());
//           System.out.println(AccountInfoService.findById(5));
//            System.out.println(AccountInfoService.findByPersonId(2));
         System.out.println(AccountInfoService.findByPersonNameAndFamily("r", "r"));


//        System.out.println(AccountInfoService.findByDateTime(
//                LocalDateTime.of(2020,1,1,0,0,0),
//                LocalDateTime.of(2025,12,31,0,0,0)
//        ));
    }
}

