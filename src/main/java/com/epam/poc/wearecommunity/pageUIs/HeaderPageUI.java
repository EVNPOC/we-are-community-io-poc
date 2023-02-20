package com.epam.poc.wearecommunity.pageUIs;

import org.openqa.selenium.By;

public class HeaderPageUI {
    public static final By HOME_BUTTON_BY = By.cssSelector("a.evnt-brand-logo");
    public static final String HEADER_BY_NAVIGATION_NAME_XPATH = "//a[text()='%s']/ancestor::header";
    public static final By MAIN_NAVIGATION_BY = By.cssSelector(".evnt-navigation");
    public static final By EVENTS_CALENDAR_XPATH_BY = By.xpath("//h1[text()='Events Calendar']");
}
