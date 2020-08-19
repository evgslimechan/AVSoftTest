package com.slimechan.avsoft.manager;

import com.slimechan.avsoft.manager.impl.RenderManagerImpl;

public interface RenderManager {

    RenderManager Instance = new RenderManagerImpl();

    String getPage(String url);

}
