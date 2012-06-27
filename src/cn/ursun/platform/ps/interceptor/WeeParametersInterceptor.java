package cn.ursun.platform.ps.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.NoParameters;
import com.opensymphony.xwork2.interceptor.ParameterNameAware;
import com.opensymphony.xwork2.interceptor.ParametersInterceptor;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.opensymphony.xwork2.util.OgnlContextState;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * <p>Parameter拦截器，处理JSON数据拦截器</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on Aug 16, 2008 2:31:54 PM
 */
public class WeeParametersInterceptor extends ParametersInterceptor {

	/**
	 * 日志处理
	 */
	private static final Log LOG = LogFactory.getLog(WeeParametersInterceptor.class);

	/**
	 * 提交类型KEY
	 */
	private static final String HEADER_SUBMIT_TYPE = "submit-type";

	/**
	 * AJAX提交
	 */
	private static final String HEADER_SUBMIT_TYPE_AJAX = "ajax";

	/**
	 * 刷新提交
	 */
	private static final String HEADER_SUBMIT_TYPE_FORM = "form";

	/**
	 * <p>增加处理传递的JSON类型的数据</p>
	 * 
	 * @see com.opensymphony.xwork2.interceptor.ParametersInterceptor#doIntercept(com.opensymphony.xwork2.ActionInvocation)
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Aug 16, 2008 2:33:43 PM
	 */
	public String doIntercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		HttpServletRequest request = ServletActionContext.getRequest();
		String submitType = request.getHeader(HEADER_SUBMIT_TYPE);

		if (!(action instanceof NoParameters)) {
			ActionContext ac = invocation.getInvocationContext();
			final Map parameters = ac.getParameters();
			String[] paramSubmitType = (String[]) parameters.get(HEADER_SUBMIT_TYPE);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Setting params " + getParameterLogMap(parameters));
			}

			if (parameters != null) {
				Map contextMap = ac.getContextMap();
				try {
					OgnlContextState.setCreatingNullObjects(contextMap, true);
					OgnlContextState.setDenyMethodExecution(contextMap, true);
					OgnlContextState.setReportingConversionErrors(contextMap, true);

					ValueStack stack = ac.getValueStack();
					// 判断HTTP HEADER是否为JSON数据
					if (submitType != null && submitType.trim().equalsIgnoreCase(HEADER_SUBMIT_TYPE_AJAX)
							|| paramSubmitType != null
							&& paramSubmitType[0].trim().equalsIgnoreCase(HEADER_SUBMIT_TYPE_AJAX)) {
						this.setParameters(action, stack, parameters);
					} else {
						super.setParameters(action, stack, parameters);
					}
				} finally {
					OgnlContextState.setCreatingNullObjects(contextMap, false);
					OgnlContextState.setDenyMethodExecution(contextMap, false);
					OgnlContextState.setReportingConversionErrors(contextMap, false);
				}
			}
		}
		return invocation.invoke();
	}

	/**
	 * <p>参数日志</p>
	 * 
	 * @param parameters
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Aug 16, 2008 2:34:17 PM
	 */
	private String getParameterLogMap(Map parameters) {
		if (parameters == null) {
			return "NONE";
		}

		StringBuffer logEntry = new StringBuffer();
		for (Iterator paramIter = parameters.entrySet().iterator(); paramIter.hasNext();) {
			Map.Entry entry = (Map.Entry) paramIter.next();
			logEntry.append(String.valueOf(entry.getKey()));
			logEntry.append(" => ");
			if (entry.getValue() instanceof Object[]) {
				Object[] valueArray = (Object[]) entry.getValue();
				logEntry.append("[ ");
				for (int indexA = 0; indexA < (valueArray.length - 1); indexA++) {
					Object valueAtIndex = valueArray[indexA];
					logEntry.append(valueAtIndex);
					logEntry.append(String.valueOf(valueAtIndex));
					logEntry.append(", ");
				}
				logEntry.append(String.valueOf(valueArray[valueArray.length - 1]));
				logEntry.append(" ] ");
			} else {
				logEntry.append(String.valueOf(entry.getValue()));
			}
		}

		return logEntry.toString();
	}

	/**
	 * <p>将参数添加到valueStack中</p>
	 * 
	 * @see com.opensymphony.xwork2.interceptor.ParametersInterceptor#setParameters(java.lang.Object, com.opensymphony.xwork2.util.ValueStack, java.util.Map)
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Aug 16, 2008 2:34:46 PM
	 */
	protected void setParameters(Object action, ValueStack stack, final Map parameters) {

		Map target = new HashMap();
		target.putAll(parameters);
		Set s = target.entrySet();
		Iterator it = s.iterator();
		// 循环取得所有的参数
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String[] tempstr = null;
			//update by duanp 2008-10-29 for fileupload parameters
			if (!(entry.getValue() instanceof String[]))
				continue;
			tempstr = (String[]) entry.getValue();
			JSONObject value = null;
			JSONArray arrValue = null;
			// 如果前台传递值不为空
			if (tempstr != null) {
				// 如果传递的值个数为一个元素
				if (tempstr.length == 1) {

					// 如果该数据类型为JSON数据，则解析到parameters中
					if (isJSONObject(tempstr[0])) {
						// 实例化一个JSONObject
						value = JSONObject.fromObject(tempstr[0]);
						// 并将JSON中的值遍历取出来，放到Map中，由super.setParameters统一处理
						parseJSONParameters(String.valueOf(entry.getKey()), value, parameters);
						parameters.remove(entry.getKey());
					}
					// 如果该数据类型为数组形式的字符串，则解析到parameters中，如：'[xxxxx,xxxx]'类型的值
					else if (isJSONArray(tempstr[0])) {
						arrValue = JSONArray.fromObject(tempstr[0]);
						parseJSONArrayParameters(String.valueOf(entry.getKey()), arrValue, parameters);
						parameters.remove(entry.getKey());
					}

				}
				// 如果传递的元素为多个元素
				else if (tempstr.length > 1) {
					for (int i = 0; i < tempstr.length; i++) {
						// 如果该数据类型为JSON数据，则解析到parameters中
						if (isJSONObject(tempstr[i])) {
							value = JSONObject.fromObject(tempstr[i]);
							parseJSONParameters(String.valueOf(entry.getKey()) + "[" + i + "]", value, parameters);
							parameters.remove(entry.getKey());
						}
						// 如果该数据类型为数组形式的字符串，则解析到parameters中，如：'[xxxxx,xxxx]'类型的值
						else if (isJSONArray(tempstr[i])) {
							arrValue = JSONArray.fromObject(tempstr[i]);
							parseJSONArrayParameters(String.valueOf(entry.getKey()) + "[" + i + "]", arrValue,
									parameters);
							parameters.remove(entry.getKey());
						}
					}

				}

			}

		}

		super.setParameters(action, stack, parameters);
	}

	/**
	 * <p>解释JSON类型的参数值</p>
	 * 
	 * @param objKey
	 * @param obj
	 * @param parameters
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Aug 16, 2008 2:35:08 PM
	 */
	private void parseJSONParameters(String objKey, JSONObject obj, Map parameters) {

		Iterator it = obj.keys();
		while (it.hasNext()) {
			// 将Key值进行拼装 拼装规则：如果在parameters下有变量为person,对应值为一个JSON数据格式的String串‘{name:jack, sex:male }’
			// 则拼装成person.name=jack 和 person.sex=male的键值对放到parameters中
			String jsonKey = String.valueOf(it.next());
			String newkey = new StringBuffer(objKey).append(".").append(jsonKey).toString();
			String value = String.valueOf(obj.get(jsonKey));
			// 如果JSON的Value为数组的处理方式如下：
			if (isJSONArray(value)) {
				JSONArray ja = JSONArray.fromObject(value);
				parseJSONArrayParameters(newkey, ja, parameters);
			}
			// 如果JSON元素的value仍然为JSON，则递归调用parseJSONParameters方法
			else if (isJSONObject(value)) {
				JSONObject jo = JSONObject.fromObject(value);
				parseJSONParameters(newkey, jo, parameters);
			}
			// 如果JSON元素的value已经为简单类型，则将值放入parameters中
			else {
				parameters.put(newkey, new String[] { value });
			}
		}

	}

	/**
	 * <p>解析JSON数据，并将解析出来的JSON中的值放到parameters中，由super.setParameters统一处理</p>
	 * 
	 * @param objKey
	 * @param obj
	 * @param parameters
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Aug 16, 2008 2:36:20 PM
	 */
	private void parseJSONArrayParameters(String objKey, JSONArray obj, Map parameters) {

		for (int j = 0; j < obj.size(); j++) {
			// 如果数组形式的字符串里的元素类型为JSON类型的数据
			if (isJSONObject(String.valueOf(obj.get(j)))) {
				JSONObject joElement = obj.getJSONObject(j);
				parseJSONParameters(objKey + "[" + j + "]", joElement, parameters);
			}
			// 如果数组形式的字段串里的元素类型为数组类型的数据
			else if (isJSONArray(String.valueOf(obj.get(j)))) {
				JSONArray joElement = obj.getJSONArray(j);
				parseJSONArrayParameters(objKey + "[" + j + "]", joElement, parameters);
			}
			// 如果数组形式的字符串里的元素为简单类型，则将数组中的值全部放入parameters中
			else {
				parameters.put(objKey, obj.toArray());
			}
		}
	}

	/**
	 * <p>字符串是否为JSON数据格式</p>
	 * 
	 * @param obj
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Aug 16, 2008 2:37:13 PM
	 */
	private boolean isJSONObject(String obj) {
		return obj != null && obj.trim().startsWith("{") && obj.trim().endsWith("}");
	}

	/**
	 * <p>字符串是否为JSONArray数据格式</p>
	 * 
	 * @param obj
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Aug 16, 2008 2:37:28 PM
	 */
	private boolean isJSONArray(String obj) {
		return obj != null && obj.trim().startsWith("[") && obj.trim().endsWith("]");
	}

}
