import mft.model.entity.AccountInfo;
import mft.model.entity.TransactionType;
import mft.model.service.AccountInfoService;

import java.time.LocalDateTime;

public class AccountInfoTest {


    public static void main(String[] args) throws Exception {
        AccountInfo accountInfo = AccountInfo
                .builder()
                //.id(1234)
               // .personId(19)
                .amount(100000)
                .transactionType(TransactionType.receipt)
                .dateTime(LocalDateTime.now())
                .build();

//        AccountInfoService.save(accountInfo);

        System.out.println(AccountInfoService.findAll());
    }
}
