package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author allen
 * @date 2020-04-01 0:14
 */
@Component
public class MyLb implements LoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncrement(){
        int current;
        int next;
        do {
          current = this.atomicInteger.get();
          next = current >= 2147483647 ? 0 : current +1;    //Integer.MAX_VALUE
        } while (!this.atomicInteger.compareAndSet(current,next));
        System.out.println("第几次访问次数next"+next);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();

        return serviceInstances.get(index);
    }
}