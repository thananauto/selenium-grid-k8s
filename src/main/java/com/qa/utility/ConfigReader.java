package com.qa.utility;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:runner.properties"})
public interface ConfigReader extends Config {
    @Key("selenide.timeout")
    long timeout();

    @Key("selenide.browser")
    @DefaultValue("chrome")
    String browser();

    @Key("selenide.reportsFolder")
    String reportFolder();

    @Key("selenide.pageLoadTimeout")
    long pageLoadTimeOut();

    @Key("selenide.downloadsFolder")
    String downloadsFolder();

    @Key("selenide.savePageSource")
    boolean savePageSource();

    @Key("app.baseUrl")
    String baseUrl();

    @Key("selenide.takescreenshots")
    boolean takeScreenShots();

    @Key("selenide.remoteUrl")
    String remoteUrl();


    @Key("influx.db.url")
    String influxUrl();
}
