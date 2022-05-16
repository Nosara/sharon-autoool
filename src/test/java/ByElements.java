import org.openqa.selenium.By;

public class ByElements {
    public static By idInput =
            By.name(
                    "ctl00$phMainContent$CustomListPart$CustomListPart_Grid$ctl00$ctl02$ctl01$Filter_InvoiceNumber"
            );
    public static  By idInputFilterButton =
            By.name(
                    "ctl00$phMainContent$CustomListPart$CustomListPart_Grid$ctl00$ctl02$ctl01$ctl16"
            );

    public static By dataRows =
            By.xpath(
                    "/html/body/form/div[2]/div[3]/div/div/div/div[2]/div[1]/table/tbody/tr[1]/td/div/table/tbody/tr"
            );

    public static By td =
            By.xpath(".//td");

    public static By equalToOption =
            By.xpath(
                    "/html/body/form/div[1]/ul/li[10]/a"
            );
}
