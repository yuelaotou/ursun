package cn.ursun.platform.ps.interceptor;

/*
 * $Id: BackgroundProcess.java 471756 2006-11-06 15:01:43Z husted $ Licensed to the Apache Software Foundation (ASF)
 * under one or more contributor license agreements. See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing permissions and limitations
 * under the License.
 */

import java.io.Serializable;

import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;

import cn.ursun.platform.core.action.WeeBackgroundAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * Background thread to be executed by the ExecuteAndWaitInterceptor.
 * 
 */
public class WeeBackgroundProcess implements Serializable {

	private static final long serialVersionUID = 3884464776311686443L;

	protected Object action;

	protected ActionInvocation invocation;

	protected String result;

	protected Exception exception;

	protected boolean done;

	private BackgroundThread t;

	/**
	 * Constructs a background process
	 * 
	 * @param threadName
	 *            The thread name
	 * @param invocation
	 *            The action invocation
	 * @param threadPriority
	 *            The thread priority
	 */
	public WeeBackgroundProcess(String threadName, final ActionInvocation invocation, int threadPriority) {
		// final String levle = BizLogContext.getInstance().getModuleLevel();
		// final String ip = BizLogContext.getInstance().getAccessIP();
		final ActionContext fac = ActionContext.getContext();
		final SecurityContext s = SecurityContextHolder.getContext();
		this.invocation = invocation;
		this.action = invocation.getAction();
		try {
			t = new BackgroundThread(fac, s);
			t.setName(threadName);
			t.setPriority(threadPriority);
			t.start();
		} catch (Exception e) {
			exception = e;
		}
	}

	protected void destory() {
		if (t != null) {
			t.destory();
		}
	}

	/**
	 * Called before the background thread determines the result code from the ActionInvocation.
	 * 
	 * @throws Exception
	 *             any exception thrown will be thrown, in turn, by the ExecuteAndWaitInterceptor
	 */
	protected void beforeInvocation() throws Exception {
	}

	/**
	 * Called after the background thread determines the result code from the ActionInvocation, but before the
	 * background thread is marked as done.
	 * 
	 * @throws Exception
	 *             any exception thrown will be thrown, in turn, by the ExecuteAndWaitInterceptor
	 */
	protected void afterInvocation() throws Exception {
	}

	/**
	 * Retrieves the action.
	 * 
	 * @return the action.
	 */
	public Object getAction() {
		return action;
	}

	/**
	 * Retrieves the action invocation.
	 * 
	 * @return the action invocation
	 */
	public ActionInvocation getInvocation() {
		return invocation;
	}

	/**
	 * Gets the result of the background process.
	 * 
	 * @return the result; <tt>null</tt> if not done.
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Gets the exception if any was thrown during the execution of the background process.
	 * 
	 * @return the exception or <tt>null</tt> if no exception was thrown.
	 */
	public Exception getException() {
		return exception;
	}

	/**
	 * Returns the status of the background process.
	 * 
	 * @return <tt>true</tt> if finished, <tt>false</tt> otherwise
	 */
	public boolean isDone() {
		return done;
	}

	class BackgroundThread extends Thread {
		String levle = null;

		String ip = null;

		ActionContext fac = null;

		SecurityContext s = null;

		public BackgroundThread( ActionContext fac, SecurityContext s) {
			this.fac = fac;
			this.s = s;
		}

		public void run() {
			// BizLogContext.getInstance().setAccessIP(ip);
			// BizLogContext.getInstance().setModuleLevel(levle);
			ActionContext.setContext(fac);
			SecurityContextHolder.setContext(s);
			try {
				beforeInvocation();
				result = invocation.invokeActionOnly();
				afterInvocation();
			} catch (Exception e) {
				exception = e;
			}

			done = true;
		}

		public void destory() {
			super.suspend();
			Object action = invocation.getAction();
			if (action instanceof WeeBackgroundAction) {
				WeeBackgroundAction weebackgroundAction = (WeeBackgroundAction) action;
				weebackgroundAction.destory(invocation.getProxy().getMethod());
			}
			super.stop();
		}
	}
}
