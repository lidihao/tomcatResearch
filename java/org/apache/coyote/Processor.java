/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.coyote;

import java.io.IOException;
import java.util.concurrent.Executor;

import org.apache.coyote.http11.upgrade.servlet31.HttpUpgradeHandler;
import org.apache.tomcat.util.net.AbstractEndpoint.Handler.SocketState;
import org.apache.tomcat.util.net.SSLSupport;
import org.apache.tomcat.util.net.SocketStatus;
import org.apache.tomcat.util.net.SocketWrapper;


/**
 * 协议的处理器
 * Common interface for processors of all protocols.
 */
public interface Processor<S> {
    // 获取线程池
    Executor getExecutor();

    // 处理请求
    SocketState process(SocketWrapper<S> socketWrapper) throws IOException;

    // comet 服务器推送技术
    SocketState event(SocketStatus status) throws IOException;

    // 处理异步请求
    SocketState asyncDispatch(SocketStatus status);
    SocketState asyncPostProcess();

    /**
     * @deprecated  Will be removed in Tomcat 8.0.x.
     */
    @Deprecated
    org.apache.coyote.http11.upgrade.UpgradeInbound getUpgradeInbound();
    /**
     * @deprecated  Will be removed in Tomcat 8.0.x.
     */
    @Deprecated
    SocketState upgradeDispatch() throws IOException;

    // 处理http升级的请求, websocket
    HttpUpgradeHandler getHttpUpgradeHandler();
    SocketState upgradeDispatch(SocketStatus status) throws IOException;
    
    void errorDispatch();

    // 是否是comet请求
    boolean isComet();
    // 是否异步请求
    boolean isAsync();
    // 是否升级
    boolean isUpgrade();

    // http请求
    Request getRequest();

    // 回收该处理器
    void recycle(boolean socketClosing);

    // 支持https
    void setSslSupport(SSLSupport sslSupport);
}
