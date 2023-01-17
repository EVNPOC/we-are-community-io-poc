package com.epam.poc.wearecommunity.core;

import com.epam.poc.wearecommunity.utilities.PropertyReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage {
    protected Logger logger;
    private PropertyReader propertyReader = new PropertyReader(GlobalConstants.CONFIG_FILE_KEY);

    protected BasePage() {
        logger = LogManager.getLogger(getClass());
    }

    public String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public WebElement getElement(WebDriver driver, By by) {
        return driver.findElement(by);
    }


    public WebElement getElementByJavascriptExecutor(WebDriver driver, String executeScript) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        return (WebElement) jse.executeScript(executeScript);
    }

    public List<WebElement> getElements(WebDriver driver, By by) {
        return driver.findElements(by);
    }

    public String getElementText(WebDriver driver, By by) {
        return getElement(driver, by).getText();
    }

    public String getElementAttribute(WebDriver driver, By by, String attribute) {
        return driver.findElement(by).getAttribute(attribute);
    }

    public void clearByKeys(WebDriver driver, By by) {
        getElement(driver, by).sendKeys(Keys.CONTROL + "A" + Keys.DELETE);
    }

    public void sendKeyToElement(WebDriver driver, By by, String value) {
        clearByKeys(driver, by);
        getElement(driver, by).sendKeys(value);
    }

    public void clickToElement(WebDriver driver, By by) {
        getElement(driver, by).click();
    }

    public void hoverAndClickToElement(WebDriver driver, By by) {
        Actions builder = new Actions(driver);
        builder.moveToElement(getElement(driver, by)).perform();
        builder.moveToElement(getElement(driver, by)).click().perform();
    }

    public void hoverElement(WebDriver driver, By by) {
        new Actions(driver).moveToElement(getElement(driver, by)).perform();
    }

    public void waitAndClickToElement(WebDriver driver, By by) {
        waitForElementUntilClickable(driver, by);
        clickToElement(driver, by);
    }

    public void scrollToElementByJS(WebDriver driver, By by) {
        String script = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
        ((JavascriptExecutor) driver).executeScript(script, getElement(driver, by));
    }

    public void scrollToBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void scrollDownByOffset(WebDriver driver, int offset) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + offset + ")");
    }

    public void scrollThenClickToElement(WebDriver driver, By by) {
        scrollToElementByJS(driver, by);
        waitForElementUntilClickable(driver, getElement(driver, by));
        clickToElement(driver, by);
    }

    public boolean isElementDisplayed(WebDriver driver, By by) {
        try {
            return getElement(driver, by).isDisplayed();
        } catch (NoSuchElementException e) {
            logger.info("Element is not displayed.");
            return false;
        }
    }

    public void waitForPageLoadedCompletely(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(propertyReader.getValue(GlobalConstants.LONG_TIMEOUT_KEY)));
            wait.until(wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            logger.info("Page is not completed loaded.");
        }
    }

    public void staticWait(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            logger.info("Error in staticWait" + e);
        }
    }

    public void waitForElementUntilClickable(WebDriver driver, WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(propertyReader.getValue(GlobalConstants.LONG_TIMEOUT_KEY)));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.info("Element not clickable");
        }
    }

    public void waitForElementUntilClickable(WebDriver driver, By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(propertyReader.getValue(GlobalConstants.LONG_TIMEOUT_KEY)));
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            logger.info("Element not clickable");
        }
    }

    public void waitForElementUntilInvisible(WebDriver driver, WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(propertyReader.getValue(GlobalConstants.LONG_TIMEOUT_KEY)));
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            logger.info("Element is visibility");
        }
    }

    public void waitForElementUntilInvisible(WebDriver driver, By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(propertyReader.getValue(GlobalConstants.LONG_TIMEOUT_KEY)));
            wait.until(ExpectedConditions.invisibilityOf(getElement(driver, by)));
        } catch (Exception e) {
            logger.info("Element is visibility");
        }
    }

    public void waitForElementUntilVisible(WebDriver driver, By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(propertyReader.getValue(GlobalConstants.LONG_TIMEOUT_KEY)));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            logger.info("Element is invisibility");
        }
    }

    public void waitForElementUntilVisible(WebDriver driver, WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(propertyReader.getValue(GlobalConstants.LONG_TIMEOUT_KEY)));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            logger.info("Element is invisibility");
        }
    }

    public void clickToElementByJS(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public void clickToElementByJS(WebDriver driver, By by) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", getElement(driver, by));
    }

    public void waitForPageTitleAsExpectedText(WebDriver driver, String pageTitleValue) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(propertyReader.getValue(GlobalConstants.LONG_TIMEOUT_KEY)));
            wait.until(ExpectedConditions.titleIs(pageTitleValue));
        } catch (Exception e) {
            logger.info("Page title is not the same as expected text");
        }
    }

    public void waitForElementContainsText(WebDriver driver, WebElement element, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(propertyReader.getValue(GlobalConstants.SHORT_TIMEOUT_KEY)));
            wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (Exception e) {
            logger.info("Element doesn't contain expected text");
        }
    }

    public String getUrlPath(String url) {
        try {
            return new URL(url).getPath();
        } catch (MalformedURLException e) {
            logger.info("A malformed URL has occurred.");
            return null;
        }
    }

    public void switchToLastTab(WebDriver driver) {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
    }

    public void switchToFirstTab(WebDriver driver) {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }
}