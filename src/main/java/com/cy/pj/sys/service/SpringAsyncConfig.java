package com.cy.pj.sys.service;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Setter
@Configuration
public class SpringAsyncConfig implements AsyncConfigurer{
	/**工厂对象为池中线程起名字*/
	private ThreadFactory threadFactory=new ThreadFactory() {
		//cas算法
		//线程安全对象
		private AtomicLong at=new AtomicLong(1000);
		@Override
		public Thread newThread(Runnable r) {
			// TODO Auto-generated method stub
			return new Thread(r,"线程名"+at.getAndIncrement());
		}
	};
	//表示这个方法要在独立于web服务器的外部线程中进行工作
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
		tpte.setCorePoolSize(20);
		tpte.setMaxPoolSize(1000);
		tpte.setKeepAliveSeconds(30);
		tpte.setQueueCapacity(1000);
		tpte.setThreadFactory(threadFactory);
		tpte.initialize();
		// TODO Auto-generated method stub
		return tpte;
	}
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		return new AsyncUncaughtExceptionHandler() {
			@Override
			public void handleUncaughtException(Throwable ex, Method method, Object... params) {
				log.error("线程池执行任务发生异常",ex);
				// TODO Auto-generated method stub
				
			}
		};
	}
}
