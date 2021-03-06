package org.pinae.nala.xb.unmarshal;

import java.util.Map;

import org.pinae.nala.xb.annotation.Root;
import org.pinae.nala.xb.exception.UnmarshalException;
import org.pinae.nala.xb.node.NodeConfig;
import org.pinae.nala.xb.unmarshal.creator.AnnotationObjectCreator;
import org.pinae.nala.xb.unmarshal.creator.DefaultObjectCreator;
import org.pinae.nala.xb.unmarshal.creator.MapCreator;

/**
 * <code>AbstractUnmarshaller</code>为<code>Unmarshaller</code>接口中的抽象类
 * 主要用于存放设置参数
 * 
 * @author Huiyugeng
 *
 */
public abstract class AbstractUnmarshaller implements Unmarshaller {
	
	@SuppressWarnings("rawtypes")
	private Class rootClass;
	private boolean validation = false;

	@SuppressWarnings("rawtypes")
	public void setRootClass(Class clazz) {
		this.rootClass = clazz;
	}

	@SuppressWarnings("rawtypes")
	protected Class getRootClass() {
		return this.rootClass;
	}

	public void validate(boolean validation) {
		this.validation = validation;
	}

	/**
	 * 将NodeConfig中间格式解析为对象
	 * 
	 * @param config NodeConfig中间格式
	 * 
	 * @return 解析后的对象
	 * 
	 * @throws UnmarshalException 解组异常
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Object creteObject(NodeConfig config) throws UnmarshalException {
		Object targetObject = null;

		if (rootClass.equals(Map.class)) {
			targetObject = (new MapCreator()).getMap(config);
		} else {
			Class targetClass = rootClass;
			if (targetClass.isAnnotationPresent(Root.class)) {
				targetObject = (new AnnotationObjectCreator(validation)).getObject(config, rootClass, null);
			} else {
				targetObject = (new DefaultObjectCreator(validation)).getObject(config, rootClass, null);
			}

		}

		return targetObject;
	}

}
