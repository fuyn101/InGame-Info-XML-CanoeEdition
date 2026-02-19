package com.github.lunatrius.ingameinfo.core.reference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reference {
    public static final String MODID = "lunatriuscore";
    public static final String NAME = "LunatriusCore";
    public static final String VERSION = "${version}";
    public static final String FORGE = "${forgeversion}";
    public static final String MINECRAFT = "${mcversion}";
    public static final String PROXY_SERVER = "com.github.lunatrius.ingameinfo.core.proxy.ServerProxy";
    public static final String PROXY_CLIENT = "com.github.lunatrius.ingameinfo.core.proxy.ClientProxy";

    public static Logger logger = LogManager.getLogger(Reference.MODID);
}
