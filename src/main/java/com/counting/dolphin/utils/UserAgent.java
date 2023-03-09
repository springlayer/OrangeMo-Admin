package com.counting.dolphin.utils;

import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.UserAgentParser;
import com.blueconic.browscap.UserAgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAgent
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    /** 浏览器 */
    public String browser = "";

    /** 操作系统 */
    public String operatingSystem = "";

    /** 解析器 */
    private static UserAgentParser parser = null;

    static
    {
        try
        {
            parser = new UserAgentService().loadParser();
        }
        catch (Exception e)
        {
            log.error("获取用户代理异常 {}", e);
        }
    }

    public UserAgent(String userAgentString)
    {
        if (parser != null)
        {
            String userAgentLowercaseString = userAgentString == null ? null : userAgentString.toLowerCase();
            Capabilities capabilities = parser.parse(userAgentLowercaseString);
            this.browser = String.format("%s %s", capabilities.getBrowser(), capabilities.getBrowserMajorVersion());
            this.operatingSystem = capabilities.getPlatform();
        }
    }

    public static UserAgent parseUserAgentString(String userAgentString)
    {
        return new UserAgent(userAgentString);
    }

    public String getBrowser()
    {
        return browser;
    }

    public String getOperatingSystem()
    {
        return operatingSystem;
    }
}