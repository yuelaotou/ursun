package cn.ursun.platform.core.dto;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;

import common.Assert;

public class WeeQC {

	private final String SPACE = " ";

	public class QueryConditionResult {
		String queryCondition = null;

		Object[] paramValue = null;

		public String getQueryCondition() {
			return queryCondition;
		}

		public void setQueryCondition(String queryCondition) {
			this.queryCondition = queryCondition;
		}

		public Object[] getParamValue() {
			return paramValue;
		}

		public void setParamValue(Object[] paramValue) {
			this.paramValue = paramValue;
		}

	}

	public QueryConditionResult convertQueryCondition(String[] fields, String[] params, String[] condition) {
		Assert
				.verify(fields.length == params.length && fields.length == condition.length,
						"Inconsistent params length");
		StringBuffer queryCondition = new StringBuffer();
		List paramList = new ArrayList();
		Object paramValue = null;
		try {
			for (int i = 0; i < fields.length; i++) {

				paramValue = PropertyUtils.getProperty(this, params[i]);
				if (paramValue != null) {
					if (paramValue instanceof String && ((String) paramValue).trim().length() == 0)
						continue;
					if (condition[i].equalsIgnoreCase("like")) {
						String paramStr = BeanUtilsBean.getInstance().getConvertUtils().convert(paramValue);
						queryCondition.append("AND").append(SPACE).append(fields[i]).append(SPACE).append("like")
								.append(SPACE).append("\'%").append(
										paramStr.replaceAll("'", "''").replaceAll("\\\\", "\\\\\\\\").replaceAll("_",
												"\\\\_").replaceAll("%", "\\\\%")).append("%\'").append(SPACE).append(
										"ESCAPE '\\'").append(SPACE);
					} else {
						queryCondition.append("AND").append(SPACE).append(fields[i]).append(condition[i]).append('?')
								.append(SPACE);
						paramList.add(paramValue);
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		if (queryCondition.length() > 0) {
			QueryConditionResult result = new QueryConditionResult();
			result.setQueryCondition(queryCondition.toString().substring(3));
			result.setParamValue(paramList.toArray());
			return result;
		} else
			return null;
	}

}
