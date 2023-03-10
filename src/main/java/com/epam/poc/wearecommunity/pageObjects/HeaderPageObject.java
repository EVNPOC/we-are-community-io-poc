package com.epam.poc.wearecommunity.pageObjects;

import com.epam.poc.wearecommunity.core.BasePage;
import com.epam.poc.wearecommunity.core.GlobalConstants;
import com.epam.poc.wearecommunity.pageUIs.BasePageUI;
import com.epam.poc.wearecommunity.pageUIs.HeaderPageUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import java.util.List;
import java.util.stream.Collectors;

public class HeaderPageObject extends BasePage {

    private final WebDriver driver;

    public HeaderPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void clickHomeButton() {
        clickToElement(driver, HeaderPageUI.HOME_BUTTON_BY);
    }

    public boolean isHomeButtonDisplayed() {
        return isElementDisplayed(driver, HeaderPageUI.HOME_BUTTON_BY);
    }

    public String getElementPositionCssValue(String linkName) {
        waitForElementUntilVisible(driver, By.xpath(String.format(HeaderPageUI.HEADER_BY_NAVIGATION_NAME_XPATH, linkName)), Long.parseLong(propertyReader.getValue(GlobalConstants.SHORT_TIMEOUT_KEY)));
        return getElementCssValue(driver, By.xpath(String.format(HeaderPageUI.HEADER_BY_NAVIGATION_NAME_XPATH, linkName)), "position");
    }

    public boolean isMainNavigationVisibleWhileScrolling() {
        boolean isVisible = true;
        if (isElementDisplayed(driver, HeaderPageUI.MAIN_NAVIGATION_BY)) {
            scrollToMiddle(driver);
            if (!isElementDisplayed(driver, HeaderPageUI.MAIN_NAVIGATION_BY)) {
                isVisible = false;
            }
            scrollToBottom(driver);
            if (!isElementDisplayed(driver, HeaderPageUI.MAIN_NAVIGATION_BY)) {
                isVisible = false;
            }
        } else {
            isVisible = false;
        }
        return isVisible;
    }

    public String getElementColor(String linkName, String colorAttribute) {
        return Color.fromString(getElementCssValue(driver, By.xpath(String.format(BasePageUI.LINK_XPATH, linkName)), colorAttribute)).asHex();
    }

    public void clickOnLanguageDropdownLink() {
        clickToElement(driver, HeaderPageUI.LANGUAGE_DROPDOWN_LINK_BY);
        waitForPageLoadedCompletely(driver, Long.parseLong(propertyReader.getValue(GlobalConstants.SHORT_TIMEOUT_KEY)));
    }

    public String getSelectedLanguageDropdown() {
        return getElementText(driver, HeaderPageUI.LANGUAGE_DROPDOWN_SELECTED_BY);
    }

    public List<String> getValueLanguageDropdown() {
        return getElements(driver, HeaderPageUI.LANGUAGE_DROPDOWN_VALUE_BY).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void waitForSelectedLanguageValueTobePresent(String label) {
        waitForTextToBePresentInElement(driver, HeaderPageUI.LANGUAGE_DROPDOWN_SELECTED_BY, label, Long.parseLong(propertyReader.getValue(GlobalConstants.SHORT_TIMEOUT_KEY)));
    }

    public String getTextByLinkHref(String href) {
        return getElementText(driver, By.xpath(String.format(HeaderPageUI.LINK_MAIN_NAVIGATION_BY_HREF_XPATH, href)));
    }

    public boolean isLanguageSelectionDisplayed() {
        return isElementDisplayed(driver, HeaderPageUI.LANGUAGE_DROPDOWN_LINK_BY);
    }

}
