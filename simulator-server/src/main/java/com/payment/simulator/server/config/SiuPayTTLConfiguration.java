package com.payment.simulator.server.config;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlCopier;
import com.payment.simulator.server.engine.ChainRequestContext;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;

@Slf4j
public class SiuPayTTLConfiguration {
    @PostConstruct
    public void init() {
        try {
            log.info("siupay pay TTLConfiguration enable start ..");
            Field field = ChainRequestContext.class.getDeclaredField("THREAD_LOCAL");
            field.setAccessible(true);
            ThreadLocal<ChainRequestContext> THREAD_LOCAL =
                    (ThreadLocal<ChainRequestContext>) field.get(new ChainRequestContext());
            TransmittableThreadLocal.Transmitter.registerThreadLocal(THREAD_LOCAL,
                    new TtlCopier<ChainRequestContext>() {
                /**
                 * 自定义线程对象copy方式，ChainRequestContext需要深拷贝，主线程的AOP会clear map
                 * @param chainRequestContext
                 * @return
                 */
                @Override
                public ChainRequestContext copy(ChainRequestContext chainRequestContext) {
                    ChainRequestContext crc = new ChainRequestContext();
                    crc.putAll(chainRequestContext);
                    return crc;
                }
            });
        } catch (Exception e) {
            log.error("siupay pay TTLConfiguration error:" + e.getMessage(), e);
        }
    }
}
