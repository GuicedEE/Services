package com.guicedee.client.implementations;

import com.guicedee.client.IGuiceContext;
import com.guicedee.guicedinjection.interfaces.IGuicePostStartup;
import com.guicedee.guicedservlets.websockets.options.IGuicedWebSocket;
import com.guicedee.guicedservlets.websockets.services.IWebSocketMessageReceiver;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GuicedEEClientPostStartup implements IGuicePostStartup<GuicedEEClientPostStartup>
{

    @Override
    public List<CompletableFuture<Boolean>> postLoad()
    {
        IGuicedWebSocket.loadWebSocketReceivers();
        return List.of(CompletableFuture.supplyAsync(()->{
/*

            ScanResult scan = IGuiceContext.instance()
                                                 .getScanResult();
            ClassInfoList classesImplementing = scan.getClassesImplementing(IWebSocketMessageReceiver.class);
            for (ClassInfo classInfo : classesImplementing)
            {
                if(classInfo.isAbstract() || classInfo.isStatic() || classInfo.isInnerClass())
                {
                    System.out.println("Abstract class for web socket receivers - " + classInfo.getName() + ", please add manually to IGuicedWebSocket.addReceiver");
                }
                else {
                    Class<IWebSocketMessageReceiver> aClass = (Class<IWebSocketMessageReceiver>) classInfo.loadClass();
                    IWebSocketMessageReceiver iWebSocketMessageReceiver = IGuiceContext.get(aClass);
                    for (String s : iWebSocketMessageReceiver.messageNames())
                    {
                        IGuicedWebSocket.addReceiver(iWebSocketMessageReceiver,s);
                    }
                }
            }
*/

            return true;
        }));
    }

    @Override
    public Integer sortOrder()
    {
        return Integer.MIN_VALUE + 650;
    }
}
