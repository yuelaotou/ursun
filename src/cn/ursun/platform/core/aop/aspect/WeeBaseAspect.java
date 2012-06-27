/**
 * 文件名：WeeBaseAspect.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Nov 12, 2008 12:11:42 AM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.aop.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.FieldSignature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * <p>
 * [描述信息：说明类的基本功能]
 * </p>
 * 
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Nov 12, 2008 12:11:42 AM
 */
public class WeeBaseAspect {

	protected <T extends Annotation> T getAnnotation(Signature signature, Class<T> annotationClass) {
		if (annotationClass == null) {
			throw new NullPointerException();
		}
		if (signature instanceof MethodSignature) {
			MethodSignature ms = (MethodSignature) signature;
			return ms.getMethod().getAnnotation(annotationClass);
		} else if (signature instanceof FieldSignature) {
			FieldSignature fs = (FieldSignature) signature;
			return fs.getField().getAnnotation(annotationClass);
		}
		return null;
	}

	protected Method getMethod(Signature signature) {
		if (!(signature instanceof MethodSignature))
			throw new IllegalArgumentException("signature is not instanceof org.aspectj.lang.reflect.MethodSignature");
		MethodSignature ms = (MethodSignature) signature;
		return ms.getMethod();
	}

	protected Field getField(Signature signature) {
		if (!(signature instanceof FieldSignature))
			throw new IllegalArgumentException("signature is not instanceof org.aspectj.lang.reflect.FieldSignature");
		FieldSignature fs = (FieldSignature) signature;
		return fs.getField();
	}

	protected Constructor getConstructor(Signature signature) {
		if (!(signature instanceof ConstructorSignature))
			throw new IllegalArgumentException(
					"signature is not instanceof org.aspectj.lang.reflect.ConstructorSignature");
		ConstructorSignature cs = (ConstructorSignature) signature;
		return cs.getConstructor();
	}
}
