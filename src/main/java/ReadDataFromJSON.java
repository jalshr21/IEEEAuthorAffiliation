import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class ReadDataFromJSON {
    WebDriver driver;
    public static void main(String[] args) {
        ReadDataFromJSON obj = new ReadDataFromJSON();
        try {
            Collection<Record> data = obj.readDataFromJSON();
            Iterator<Record> iterator = data.iterator();
            Collection<Record> result = new ArrayList<>();
            int i = 0;
            while (iterator.hasNext()) {
                obj.driver = new FirefoxDriver();
                Record tmp = iterator.next();
                String link = tmp.getLink();
                obj.driver.get(link);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                link = obj.driver.getCurrentUrl();
                Set<String> tmp2 = obj.getIEEEKeywords(link);
                tmp.setIEEEKeywords(String.join(";", tmp2));
                List<String> tmp1 = obj.getActualAuthorAffiliation(link);
                tmp.setActualAuthorAffiliation(String.join("#", tmp1));
                result.add(tmp);
                obj.driver.quit();
                i++;
                if(i == 3)
                    break;
            }
            System.out.println("---------------" + i + "-------------");

            RecordToCSV recordToCSV = new RecordToCSV();
            recordToCSV.writeToCSV(result);

            } catch(FileNotFoundException e){
                e.printStackTrace();
            }
            finally {
            obj.driver.quit();
        }
    }

    Collection<Record> readDataFromJSON () throws FileNotFoundException {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader("/Users/shrutijalan/Downloads/IEEEAuthorAffiliation/src/main/resources/data.json"));
            Type collectionType = new TypeToken<Collection<Record>>() {
            }.getType();
            Collection<Record> data = gson.fromJson(reader, collectionType);
            return data;
    }

    private List<String> getActualAuthorAffiliation(String link) {
        List<String> authorLinks = getAuthorLinks(link);
        List<String> authorAffiliation = new ArrayList<>();
        for (String authorLink : authorLinks) {
            authorAffiliation.add(getAffiliation(authorLink));
        }
        return authorAffiliation;
    }

    private String getAffiliation(String authorLink) {
        List<String> affiliation = new ArrayList<>();;
        driver.get(authorLink);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement tmp = driver.findElement(By.xpath("/html/body/div[4]/div/div/div/div[4]/div/xpl-root/div/xpl-search-results/xpl-author-profile/div[1]/div[4]/div[1]/div[2]/div[2]/h1/span"));
        String author = tmp.getAttribute("innerHTML");
        affiliation.add(author);
        List<WebElement> list = driver.findElements(By.xpath("/html/body/div[4]/div/div/div/div[4]/div/xpl-root/div/xpl-search-results/xpl-author-profile/div[1]/div[4]/div[1]/div/div/div/div/span"));
        for(WebElement el : list) {
            affiliation.add(el.getAttribute("innerHTML"));
        }
        return String.join(";", affiliation);
    }

    private List<String> getAuthorLinks(String link) {
        List<String> authorLinks = new ArrayList<>();
        driver.get(link+"/authors#authors");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> list = driver.findElements(By.xpath("/html/body/div[4]/div/div/div/div[4]/div/xpl-root/div/xpl-document-details/div/div[1]/div/div[2]/section/div[2]/xpl-accordian-section/div/xpl-document-accordion/div[1]/div[2]/div/xpl-author-item/div/div/div/div/a"));
        for(WebElement el : list) {
            String l = el.getAttribute("href");
            authorLinks.add(l);
        }
        return authorLinks;
    }

    private Set<String> getIEEEKeywords(String link) {
        driver.get(link+"/keywords#keywords");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> list = driver.findElements(By.xpath(".//*[@class='stats-keywords-list-item']"));
        Set<String> keywords = new HashSet<>();
        Gson g = new Gson();
        String jsonString;
        for(WebElement el : list) {
            jsonString = el.getAttribute("data-tealium_data");
            IEEEKeyWord p = g.fromJson(jsonString, IEEEKeyWord.class);
            if(p.keywordType.equals("IEEE Keywords"))
                keywords.add(p.keyword);
        }
        return keywords;
    }
}
