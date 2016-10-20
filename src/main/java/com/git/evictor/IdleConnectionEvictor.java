package com.git.evictor;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.git.controller.CheckController;

/*
 * 这是个开启以后可以自动进行清理http连接的的类
 * 总结：
 *     1.执行Thread中的start()命令可以开启，
 *     2.执行这里的shutdown()方法可以关闭
 *     3.
 */
public class IdleConnectionEvictor extends Thread {
	private static final Logger logger = Logger.getLogger(CheckController.class);
	@Autowired
	private HttpClientConnectionManager connMgr;

	private volatile boolean shutdown;

	public IdleConnectionEvictor() {
	}

	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					wait(5000);
					// 关闭失效的连接
					connMgr.closeExpiredConnections();
					logger.info("关闭线程");
				}
			}
		} catch (InterruptedException ex) {
			// 结束
		}
	}

	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
}
