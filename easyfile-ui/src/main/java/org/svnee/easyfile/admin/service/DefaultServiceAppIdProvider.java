package org.svnee.easyfile.admin.service;

import org.svnee.easyfile.common.property.IEasyFileCommonProperties;

/**
 * DefaultServiceAppIdProvider
 *
 * @author svnee
 **/
public class DefaultServiceAppIdProvider implements ServerAppIdProvider {

    private final IEasyFileCommonProperties commonProperties;

    public DefaultServiceAppIdProvider(IEasyFileCommonProperties commonProperties) {
        this.commonProperties = commonProperties;
    }

    @Override
    public String getCurrentAppId() {
        return commonProperties.getAppId();
    }

    @Override
    public String getCurrentUnifiedAppId() {
        return commonProperties.getUnifiedAppId();
    }
}
