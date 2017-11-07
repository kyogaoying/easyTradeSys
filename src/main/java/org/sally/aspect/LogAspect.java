package org.sally.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.sally.entities.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * 日志切面
 *
 * @author Sally
 * @since 2017-10-25
 */
@Aspect
@Component
public class LogAspect
{
	private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	// 定义切入点为LoginService的loginVerify方法
	@Pointcut("execution(* org.sally.service.LoginService.loginVerify(..))")
	public void pointcutForLoginVerify()
	{
	}

	// 定义切入点为所有service包下的所有以Service结尾的类的add方法
	@Pointcut("execution(* org.sally.service.*.*Service.add*(..))")
	public void pointcutForAdd()
	{
	}

	// 定义切入点为所有service包下的所有以Service结尾的类的delete方法
	@Pointcut("execution(* org.sally.service.*.*Service.delete*(..))")
	public void pointcutForDelete()
	{
	}

	// 定义切入点为所有service包下的所有以Service结尾的类的update方法
	@Pointcut("execution(* org.sally.service.*.*Service.update*(..))")
	public void pointcutForUpdate()
	{
	}

	// 定义切入点为所有service包下的所有以Service结尾的类的find方法
	@Pointcut("execution(* org.sally.service.*.*Service.find*(..)) || execution(* org.sally.service.*.*Service.query*(..)) ")
	public void pointcutForFind()
	{
	}

	// LoginService的loginVerify方法的前置增强
	@Before(value = "pointcutForLoginVerify()")
	public void beforeLoginVerify(JoinPoint jp)
	{
		logger.info("正在验证用户:" + jp.getArgs()[0]);
	}

	// LoginService的loginVerify方法的后置返回增强
	@AfterReturning(value = "pointcutForLoginVerify()", returning = "userInfo")
	public void afterReturningLoginVerify(JoinPoint jp, UserInfo userInfo)
	{
		if (userInfo == null)
		{
			logger.info("用户" + jp.getArgs()[0] + "不存在");
		}
		else
		{
			logger.info("用户" + jp.getArgs()[0] + "已验证成功");
		}
	}

	// 所有service包下的所有以Service结尾的类的find方法的前置增强
	@Before(value = "pointcutForFind()")
	public void beforeFind(JoinPoint jp)
	{
		Signature signature = jp.getSignature();
		String fun_desc = AspectConstants.getFunDesc(signature);
		logger.info("正在查询"+fun_desc+",调用" + signature.getDeclaringTypeName() + "的" + signature.getName());
	}

	// 所有service包下的所有以Service结尾的类的find方法的后置返回增强
	@AfterReturning(value = "pointcutForFind()", returning = "result")
	public void afterReturningFind(JoinPoint jp, Object result)
	{
		Signature signature = jp.getSignature();
		String fun_desc = AspectConstants.getFunDesc(signature);
		logger.info("查询"+fun_desc+"结果:" + JSON.toJSONString(result));
	}

	// 定义切入点为所有service包下的所有以Service结尾的类的add方法的前置增强
	@Before(value = "pointcutForAdd()")
	public void beforeAdd(JoinPoint jp)
	{
		Signature signature = jp.getSignature();
		String fun_desc = AspectConstants.getFunDesc(signature);
		logger.info("正在添加"+fun_desc+":"+JSON.toJSONString(jp.getArgs()[0])+",调用"
				+ signature.getDeclaringTypeName() + "的" + signature.getName());
	}

	// 定义切入点为所有service包下的所有以Service结尾的类的add方法的后置增强
	@After(value = "pointcutForAdd()")
	public void afterAdd(JoinPoint jp)
	{
		Signature signature = jp.getSignature();
		String fun_desc = AspectConstants.getFunDesc(signature);
		logger.info("添加"+fun_desc+"成功!");
	}

	// 定义切入点为所有service包下的所有以Service结尾的类的delete方法的前置增强
	@Before(value = "pointcutForDelete()")
	public void beforeDelete(JoinPoint jp)
	{
		Signature signature = jp.getSignature();
		String fun_desc = AspectConstants.getFunDesc(signature);
		logger.info("正在删除"+fun_desc+":"+JSON.toJSONString(jp.getArgs()[0])+",调用"
				+ signature.getDeclaringTypeName() + "的" + signature.getName());
	}

	// 定义切入点为所有service包下的所有以Service结尾的类的delete方法的后置增强
	@After(value = "pointcutForDelete()")
	public void afterDelete(JoinPoint jp)
	{
		Signature signature = jp.getSignature();
		String fun_desc = AspectConstants.getFunDesc(signature);
		logger.info("删除"+fun_desc+"成功!");
	}

	// 定义切入点为所有service包下的所有以Service结尾的类的update方法的前置增强
	@Before(value = "pointcutForUpdate()")
	public void beforeUpdate(JoinPoint jp)
	{
		Signature signature = jp.getSignature();
		String fun_desc = AspectConstants.getFunDesc(signature);

		logger.info("正在修改"+fun_desc+":"+JSON.toJSONString(jp.getArgs()[0])
				+",调用" + signature.getDeclaringTypeName() + "的" + signature.getName());
	}

	// 定义切入点为所有service包下的所有以Service结尾的类的update方法的后置增强
	@After(value = "pointcutForUpdate()")
	public void afterUpdate(JoinPoint jp)
	{
		Signature signature = jp.getSignature();
		String fun_desc = AspectConstants.getFunDesc(signature);
		logger.info("修改"+fun_desc+"成功!");
	}

}
