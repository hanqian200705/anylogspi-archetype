#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.IOException;

import com.github.jobop.anylog.common.javassist.CannotCompileException;
import com.github.jobop.anylog.common.javassist.ClassPool;
import com.github.jobop.anylog.common.javassist.CtClass;
import com.github.jobop.anylog.common.javassist.CtMethod;
import com.github.jobop.anylog.common.javassist.NotFoundException;
import com.github.jobop.anylog.spi.TransformDescriptor;
import com.github.jobop.anylog.spi.TransformHandler;

public class InsertLogAfterHandler implements TransformHandler {

	@Override
	public boolean canHandler(TransformDescriptor injectDescriptor) {
		return InsertLogAfterDescriptor.class.isAssignableFrom(injectDescriptor.getClass());
	}

	@Override
	public byte[] transform(TransformDescriptor injectDescriptor) {
		InsertLogAfterDescriptor descriptor = (InsertLogAfterDescriptor) injectDescriptor;
		System.out.println("${symbol_pound}${symbol_pound}${symbol_pound}" + descriptor.getNeedInjectClassName());
		System.out.println("${symbol_pound}${symbol_pound}${symbol_pound}" + descriptor.getMethodName());
		System.out.println("${symbol_pound}${symbol_pound}${symbol_pound}" + descriptor.getLogCode());
		try {
			// 通过包名获取类文件
			CtClass cc = ClassPool.getDefault().get(descriptor.getNeedInjectClassName());
			cc.defrost();
			// 获得指定方法名的方法
			CtMethod m = cc.getDeclaredMethod(descriptor.getMethodName());
			m.insertAfter(descriptor.getLogCode());
			return cc.toBytecode();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
}
