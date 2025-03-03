//package com.artbite008.common.thread;
//
//import java.util.Map;
//import java.util.concurrent.Callable;
//import java.util.concurrent.Future;
//
//import org.slf4j.MDC;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.util.concurrent.ListenableFuture;
//
//import com.artbite008.starter.chaincontext.ChainRequestContext;
//
//public class TracingThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
//
//    @Override
//    public void execute(Runnable task) {
//        super.execute(tracingRunnable(task));
//    }
//
//    @Override
//    public void execute(Runnable task, long startTimeout) {
//        super.execute(tracingRunnable(task), startTimeout);
//    }
//
//    @Override
//    public Future<?> submit(Runnable task) {
//        return super.submit(tracingRunnable(task));
//    }
//
//    @Override
//    public <T> Future<T> submit(Callable<T> task) {
//        return super.submit(tracingRunnable(task));
//    }
//
//    @Override
//    public ListenableFuture<?> submitListenable(Runnable task) {
//        return super.submitListenable(tracingRunnable(task));
//    }
//
//    @Override
//    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
//        return super.submitListenable(tracingRunnable(task));
//    }
//
//
//    public Runnable tracingRunnable(Runnable task) {
//        final Map<String, String> originalContextCopy = MDC.getCopyOfContextMap();
//        ChainRequestContext currentContext = ChainRequestContext.getCurrentContext();
//        return () -> {
//            final Map<String, String> localContextCopy = MDC.getCopyOfContextMap();
//            try {
//                before(originalContextCopy, currentContext);
//                task.run();
//            } finally {
//                after(localContextCopy, currentContext);
//            }
//        };
//    }
//
//
//    public <T> Callable<T> tracingRunnable(Callable<T> task) {
//        final Map<String, String> originalContextCopy = MDC.getCopyOfContextMap();
//        ChainRequestContext currentContext = ChainRequestContext.getCurrentContext();
//        return () -> {
//            final Map<String, String> localContextCopy = MDC.getCopyOfContextMap();
//            try {
//                before(originalContextCopy, currentContext);
//                T ret = task.call();
//                return ret;
//            } finally {
//                after(localContextCopy, currentContext);
//            }
//        };
//    }
//
//
//    private void before(final Map<String, String> originalContextCopy, Map<String, Object> chaincontextAttributes) {
//        MDC.clear();
//        if (originalContextCopy != null) {
//            MDC.setContextMap(originalContextCopy);
//        }
//        if (chaincontextAttributes != null) {
//            ChainRequestContext.getCurrentContext().putAll(chaincontextAttributes);
//        }
//    }
//
//    private void after(final Map<String, String> localContextCopy, Map<String, Object> chaincontextAttributes) {
//        MDC.clear();
//        if (localContextCopy != null) {
//            MDC.setContextMap(localContextCopy);
//        }
//
//        if (chaincontextAttributes != null) {
//            ChainRequestContext.getCurrentContext().putAll(chaincontextAttributes);
//        }
//    }
//}