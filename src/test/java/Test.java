
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


import static util.DriverManager.getDriver;

public class Test {


    private static final Logger log = Logger.getLogger(String.valueOf(Test.class));

    @org.junit.Test
    public void testMethod() throws InterruptedException {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 30);

        //Карты для сравнения характеристик
        HashMap<String, String> dataSet1 = new HashMap<>();
        HashMap<String, String> dataSet2 = new HashMap<>();

        //1.	Открывается браузер
        //2.	Выполняется переход на сайт https://dns-shop.ru/
        log.info("Открываем главную страницу");
        driver.get("https://dns-shop.ru/");

        //3.	По клику в левом меню выбирается пункт «Компьютеры и периферия» -> «Компьютерные системы» -> «Системные блоки»
        log.info("Кликаем на элемент 'Компьютеры и периферия'");
        driver.findElement(By.xpath("//div[@class = 'w-menu-widget']//*[text() = 'Компьютеры и периферия']")).click();
        log.info("Кликаем на элемент 'Компьютерные системы'");
        driver.findElement(By.xpath("//div[@class = 'link-wrap']//*[text() = 'Компьютерные системы']")).click();
        log.info("Кликаем на элемент 'Системные блоки'");
        driver.findElement(By.xpath("//div[@class = 'link-wrap']//*[text() = 'Системные блоки']")).click();

        //4.	На странице выбираем сортировку «По убыванию цены»
        log.info("На странице выбираем сортировку «По убыванию цены»");
        driver.findElement(By.xpath("//button[@class='dropdown']//*[text()=' По возрастанию цены ']")).click();
        driver.findElement(By.xpath("//*[text()=' По убыванию цены ']")).click();
        driver.findElement(By.xpath("//button[@class='dropdown']//*[text()=' По убыванию цены ']")).click();
        driver.findElement(By.xpath("//*[text()=' По убыванию цены ']")).click();
        driver.findElement(By.xpath("//button[@class='dropdown']//*[text()=' По убыванию цены ']")).click();
        driver.findElement(By.xpath("//button[@class='dropdown']//*[text()=' По убыванию цены ']")).click();

        //5.	Выбираем третий продукт в списке, открываем его.
        log.info("Выбираем третий продукт в списке, открываем его.");
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[@data-position-index = '2']//a[@class='show-popover ec-price-item-link']")));

        //6.	Открываем характеристики продукта и записываем следующую информацию о продукте:
        log.info("Получаем информацию о продукте");
        driver.findElement(By.xpath("//li[@data-tab-name='characteristics']//*[text()='Характеристики' and @role='tab']")).click();
        //•	Название
        dataSet1.put("Название", driver.findElement(By.xpath("//div[@class='price_item_description']")).getText().replace("Характеристики ", ""));
        //•	Цена
        dataSet1.put("Цена", driver.findElement(By.xpath("//p[@class='text-muted']/parent::div/preceding-sibling::div//span[@class='current-price-value']")).getText());
        //•	Срок гарантии
        dataSet1.put("Срок гарантии", driver.findElement(By.xpath("//*[text()='Гарантия:']/following-sibling::span")).getText());
        //•	Операционная система
        dataSet1.put("Операционная система", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Операционная система']/ancestor::td/following-sibling::td")).getText());
        //•	Модель процессора, количество ядер и тактовая частота
        dataSet1.put("Модель процессора", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Модель процессора']/ancestor::td/following-sibling::td")).getText());
        dataSet1.put("Количество ядер процессора", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Количество ядер процессора']/ancestor::td/following-sibling::td")).getText());
        dataSet1.put("Частота процессора", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Частота процессора']/ancestor::td/following-sibling::td")).getText());
        //•	Модель дискретной видеокарты и объём видеопамяти(если есть)
        if (driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Модель дискретной видеокарты']/ancestor::td/following-sibling::td")).isDisplayed()) {
            dataSet1.put("Модель дискретной видеокарты", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Модель дискретной видеокарты']/ancestor::td/following-sibling::td")).getText());
        }
        if (driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Объем видеопамяти']/ancestor::td/following-sibling::td")).isDisplayed()) {
            dataSet1.put("Объем видеопамяти", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Объем видеопамяти']/ancestor::td/following-sibling::td")).getText());
        }
        //•	Размер и тип оперативной памяти
        dataSet1.put("Размер оперативной памяти", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Размер оперативной памяти']/ancestor::td/following-sibling::td")).getText());
        dataSet1.put("Тип оперативной памяти", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Тип оперативной памяти']/ancestor::td/following-sibling::td")).getText());
        //•	Объём дисков HDD(если есть)
        if (driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Суммарный объем жестких дисков (HDD)']/ancestor::td/following-sibling::td")).isDisplayed()) {
            dataSet1.put("Суммарный объем жестких дисков (HDD)", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Суммарный объем жестких дисков (HDD)']/ancestor::td/following-sibling::td")).getText());
        }
        //•	Объём дисков SSD(если есть)
        if (driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Объем твердотельного накопителя (SSD)']/ancestor::td/following-sibling::td")).isDisplayed()) {
            dataSet1.put("Объем твердотельного накопителя (SSD)", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Объем твердотельного накопителя (SSD)']/ancestor::td/following-sibling::td")).getText());
        }


        //7.	Возвращаемся на главную страницу
        //8.	По клику в левом меню выбирается пункт «Компьютеры и периферия» -> «Компьютерные системы» -> «Системные блоки»
        log.info("Открываем главную страницу");
        driver.get("https://dns-shop.ru/");
        log.info("Кликаем на элемент'Компьютеры и периферия'");
        driver.findElement(By.xpath("//div[@class = 'w-menu-widget']//*[text() = 'Компьютеры и периферия']")).click();
        log.info("Кликаем на элемент'Компьютерные системы'");
        driver.findElement(By.xpath("//div[@class = 'link-wrap']//*[text() = 'Компьютерные системы']")).click();
        log.info("Кликаем на элемент'Системные блоки'");
        driver.findElement(By.xpath("//div[@class = 'link-wrap']//*[text() = 'Системные блоки']")).click();

        //9.	Убеждаемся, что выбрана сортировка «По возрастанию цены», если нет, выбираем
        log.info("Убеждаемся, что выбрана сортировка «По возрастанию цены», если нет, выбираем");
        driver.findElement(By.xpath("//button[@class='dropdown']//*[text()=' По возрастанию цены ']")).click();
        driver.findElement(By.xpath("//*[text()=' По возрастанию цены ']")).click();
        driver.findElement(By.xpath("//button[@class='dropdown']//*[text()=' По возрастанию цены ']")).click();
        driver.findElement(By.xpath("//button[@class='dropdown']//*[text()=' По возрастанию цены ']")).click();
        driver.findElement(By.xpath("//button[@class='dropdown']//*[text()=' По возрастанию цены ']")).click();
        driver.findElement(By.xpath("//button[@class='dropdown']//*[text()=' По возрастанию цены ']")).click();

        //10.	Листаем страницу вниз, жмём кнопку «В конец»
        log.info("Листаем страницу вниз, жмём кнопку «В конец»");
        executor.executeScript("return arguments[0].scrollIntoView(false);", driver.findElement(By.xpath("//span[text()='В начало']/preceding-sibling::span[text()='В конец']")));
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[text()='В начало']/preceding-sibling::span[text()='В конец']")))).click();

        //11.	Листаем страницу вниз, выбираем третий снизу продукт, открываем его.
        log.info("Листаем страницу вниз, выбираем третий снизу продукт, открываем его.");
        Thread.sleep(300);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@data-position-index = '0']//a[@class='show-popover ec-price-item-link']"))));
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[@data-position-index = '0']//a[@class='show-popover ec-price-item-link']")));

        //12.	Повторяем пункт 6.
        log.info("Повторяем пункт 6.");
        driver.findElement(By.xpath("//li[@data-tab-name='characteristics']//*[text()='Характеристики' and @role='tab']")).click();
        dataSet2.put("Название", driver.findElement(By.xpath("//div[@class='price_item_description']")).getText().replace("Характеристики ", ""));
        dataSet2.put("Цена", driver.findElement(By.xpath("//p[@class='text-muted']/parent::div/preceding-sibling::div//span[@class='current-price-value']")).getText());
        dataSet2.put("Срок гарантии", driver.findElement(By.xpath("//*[text()='Гарантия:']/following-sibling::span")).getText());
        dataSet2.put("Операционная система", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Операционная система']/ancestor::td/following-sibling::td")).getText());
        dataSet2.put("Модель процессора", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Модель процессора']/ancestor::td/following-sibling::td")).getText());
        dataSet2.put("Количество ядер процессора", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Количество ядер процессора']/ancestor::td/following-sibling::td")).getText());
        dataSet2.put("Частота процессора", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Частота процессора']/ancestor::td/following-sibling::td")).getText());
        if (driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Модель дискретной видеокарты']/ancestor::td/following-sibling::td")).isDisplayed()) {
            dataSet2.put("Модель дискретной видеокарты", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Модель дискретной видеокарты']/ancestor::td/following-sibling::td")).getText());
        }
        if (driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Объем видеопамяти']/ancestor::td/following-sibling::td")).isDisplayed()) {
            dataSet2.put("Объем видеопамяти", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Объем видеопамяти']/ancestor::td/following-sibling::td")).getText());
        }
        dataSet2.put("Размер оперативной памяти", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Размер оперативной памяти']/ancestor::td/following-sibling::td")).getText());
        dataSet2.put("Тип оперативной памяти", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Тип оперативной памяти']/ancestor::td/following-sibling::td")).getText());
        if (driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Суммарный объем жестких дисков (HDD)']/ancestor::td/following-sibling::td")).isDisplayed()) {
            dataSet2.put("Суммарный объем жестких дисков (HDD)", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Суммарный объем жестких дисков (HDD)']/ancestor::td/following-sibling::td")).getText());
        }
        if (driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Объем твердотельного накопителя (SSD)']/ancestor::td/following-sibling::td")).isDisplayed()) {
            dataSet2.put("Объем твердотельного накопителя (SSD)", driver.findElement(By.xpath("//div[@class='price_item_description']/following-sibling::div//span[text()='Объем твердотельного накопителя (SSD)']/ancestor::td/following-sibling::td")).getText());
        }

        //13.	Сравниваем характеристики продукта с полученными в 6 и 12 пунктах. Все характеристики должны совпадать
        log.info("Сравниваем характеристики продукта с полученными в 6 и 12 пунктах. Все характеристики должны совпадать");
        List<String> values1 = new ArrayList<>(dataSet1.values());
        List<String> values2 = new ArrayList<>(dataSet2.values());
        Collections.sort(values1);
        Collections.sort(values2);
        if (dataSet1.equals(dataSet2)) {
            log.info("Сравнение прошло успешно");
        } else {
            log.error("Характеристики отличаются");
        }
        driver.close();
    }
}
