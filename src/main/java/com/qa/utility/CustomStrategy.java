package com.qa.utility;

import org.junit.platform.engine.ConfigurationParameters;
import org.junit.platform.engine.support.hierarchical.ParallelExecutionConfiguration;
import org.junit.platform.engine.support.hierarchical.ParallelExecutionConfigurationStrategy;

public class CustomStrategy implements ParallelExecutionConfiguration, ParallelExecutionConfigurationStrategy {
    @Override
    public int getParallelism() {
        return 2;
    }

    @Override
    public int getMinimumRunnable() {
        return 2;
    }

    @Override
    public int getMaxPoolSize() {
        return 2;
    }

    @Override
    public int getCorePoolSize() {
        return 2;
    }

    @Override
    public int getKeepAliveSeconds() {
        return 30;
    }

    @Override
    public ParallelExecutionConfiguration createConfiguration(ConfigurationParameters configurationParameters) {

        return this;
    }
}
