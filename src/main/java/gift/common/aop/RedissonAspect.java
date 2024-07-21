package gift.common.aop;

import gift.common.annotation.RedissonLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RedissonAspect {
    private final RedissonClient redissonClient;

    public RedissonAspect(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Around("@annotation(gift.common.annotation.RedissonLock)")
    public Object redissonLock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLock annotation = method.getAnnotation(RedissonLock.class);
        String lockKey = method.getName() + getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), annotation.value());
        RLock lock = redissonClient.getLock(lockKey);
        System.out.println(lockKey);

        try {
            boolean lockable = lock.tryLock(annotation.waitTime(), annotation.leaseTime(), TimeUnit.MILLISECONDS);

            if (!lockable) {
                System.out.println("Lock 획득 실패=" + lockKey);
                throw new Exception("Lock 획득 실패 임시 에러");
            }
            System.out.println("로직 수행");
            return joinPoint.proceed();
        } catch (InterruptedException e) {
            System.out.println("에러 발생");
            throw e;
        } finally {
            System.out.println("락 해제");
            lock.unlock();
        }
    }

    public Object getDynamicValue(String[] parameterNames, Object[] args, String key) {
        SpelExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for(int i=0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, Object.class);
    }
}
