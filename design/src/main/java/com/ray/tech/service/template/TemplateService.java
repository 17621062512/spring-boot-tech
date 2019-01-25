package com.ray.tech.service.template;

import com.ray.tech.service.adapter.CustomerAdapterService;

import java.util.concurrent.Callable;

/**
 * spring template
 * <p>
 * Bean
 * {@link org.springframework.beans.factory.InitializingBean}Bean属性都设置完毕后调用afterPropertiesSet()方法
 * {@link org.springframework.beans.factory.DisposableBean}在Bean生命周期结束前调用destory()方法做一些收尾工作
 * <p>
 * Aware
 * {@link org.springframework.beans.factory.BeanNameAware}在Bean加载的过程中可以获取到该Bean的id
 * {@link org.springframework.context.ApplicationContextAware}在Bean加载的过程中可以获取到Spring的ApplicationContext
 * {@link org.springframework.beans.factory.BeanFactoryAware}在Bean加载的过程中可以获取到加载该Bean的BeanFactory
 * <p>
 * Factory
 * {@link org.springframework.beans.factory.FactoryBean}定制自己想要实例化出来的Bean，方法就是实现FactoryBean接口
 * 最后得到的并不是FactoryBean本身，而是FactoryBean的泛型对象，这就是FactoryBean的作用
 * <p>
 * Processor
 * {@link org.springframework.beans.factory.config.BeanPostProcessor}
 * 针对每个Bean的生成前后做一些逻辑操作 @see src/main/resources/static/BeanPostProcessor.jpg
 * {@link org.springframework.beans.factory.config.BeanFactoryPostProcessor}
 * 允许在Bean创建之前，读取Bean的元属性，并根据自己的需求对元属性进行改变，比如将Bean的scope从singleton改变为prototype，
 * 最典型的应用应当是PropertyPlaceholderConfigurer，替换xml文件中的占位符，替换为properties文件中相应的key对应的value
 * BeanFactoryPostProcessor的执行优先级高于BeanPostProcessor
 * {@link org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor}
 * 实例化之前对Bean做自定义话处理,区分下Spring Bean的2个不同阶段，以免混淆
 * 1、实例化----实例化的过程是一个创建Bean的过程，即调用Bean的构造函数，单例的Bean放入单例池中
 * 2、初始化----初始化的过程是一个赋值的过程，即调用Bean的setter，设置Bean的属性
 */
//@Service
public class TemplateService extends CustomerAdapterService implements Callable<Object> {
    private String name;

    public TemplateService(String name) {
        this.name = name;
    }

    @Override
    public Object call() throws Exception {
        return null;
    }

//    @Override
//    public int compareTo(Object o) {
//        return 0;
//    }
}
