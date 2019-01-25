import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * JUnit 5在运行时要求Java 8(或更高)。但是，您仍然可以测试使用JDK的以前版本编译的代码。
 * <p>
 * JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage
 * JUnit Platform:在JVM上启动测试框架的基础
 * JUnit Jupiter:新的编程模型和扩展模型的组合，用于在JUnit 5中编写测试和扩展
 * JUnit Vintage:平台上运行基于JUnit 3和JUnit 4的测试提供了一个测试引擎(向下兼容)
 * <p>
 * maven
 * <dependency>
 * <groupId>org.junit.platform</groupId>
 * <artifactId>junit-platform-launcher</artifactId>
 * <scope>test</scope>
 * </dependency>
 * <dependency>
 * <groupId>org.junit.jupiter</groupId>
 * <artifactId>junit-jupiter-engine</artifactId>
 * <scope>test</scope>
 * </dependency>
 * <dependency>
 * <groupId>org.junit.vintage</groupId>
 * <artifactId>junit-vintage-engine</artifactId>
 * <scope>test</scope>
 * </dependency>
 * <p>
 * 执行顺序<a>https://blog.csdn.net/ryo1060732496/article/details/80837621</a>
 */
@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = IntegrationApplication.class)
@DisplayName("Junit5 单元测试展示")
public class JunitExampleTest {

    @Test
    @DisplayName("╯°□°）╯")
    public void normalTest() {
        System.out.println("最普通的测试");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("╯°□°）╯")
    public void junit5NormalTest() {
        System.out.println("最普通的Junit 5 测试");
    }

    /**
     * @param testInfo       由Junit注入
     * @param testReporter   由Junit注入
     * @param repetitionInfo 由Junit注入
     */
    @DisplayName("可重复测试")//需要IDE支持
    @RepeatedTest(value = 5, name = "{displayName} {currentRepetition} / {totalRepetitions}")
    public void repeatedTest(TestInfo testInfo, TestReporter testReporter, RepetitionInfo repetitionInfo) {
        System.out.println(testInfo.getDisplayName());
        System.out.println();
    }

    @TestFactory
    @DisplayName("集合动态测试")
    public Collection<DynamicTest> dynamicTests() {
        return Arrays.asList(
                dynamicTest("1st dynamic test", () -> assertTrue(true)),
                dynamicTest("2st dynamic test", () -> assertEquals(4, 2 * 2))
        );
    }

    @DisplayName("流动态测试")
    @TestFactory
    public Stream<DynamicTest> dynamicTestsFromIntStream() {
        // limit 10 循环10次
        return IntStream.iterate(0, n -> n + 2).limit(10)
                .mapToObj(n -> dynamicTest("动态测试，参数：" + n, () -> assertTrue(n % 2 == 0)));
    }

    /**
     * Container等效于一组测试的容器，见后去的nested test
     *
     * @return
     */
    @TestFactory
    @DisplayName("多层嵌套动态测试")
    public Stream<DynamicNode> dynamicTestsWithContainers() {
        return Stream.of("A", "B", "C")//入参，可使用Arguments.of()以达到多参数测试
                .map(input ->
                        //最终展开结构与此代码结构一致
                        dynamicContainer("测试集，参数： " + input, Stream.of(
                                dynamicTest("not null", () -> assertNotNull(input)),
                                dynamicContainer("嵌套集", Stream.of(
                                        dynamicTest("length > 0", () -> assertTrue(input.length() > 0)),
                                        dynamicTest("not empty", () -> assertFalse(input.isEmpty()))
                                ))
                        )));
    }

    @Disabled
    @DisabledOnOs(OS.LINUX)//条件禁用
    @Test
    @DisplayName("这是一个被禁用的测试")
    public void disabledTest() {
        throw new IllegalStateException("disabled!!!");
    }

    @Test
    @EnabledOnOs({OS.WINDOWS, OS.LINUX})//系统条件
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10})//jre条件
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")//64位CPU
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "uat")//环境
    @DisplayName("含条件启动的测试")
    public void conditionalTest() {
        String osName = System.getProperty("os.name");
        System.out.println("当前系统为：" + osName);
    }

    @DisplayName("普通参数值测试")
    @ParameterizedTest
    @ValueSource(strings = {"a", "b", "c"})
    public void parameterizedValueSourceTest(String arg) {
        System.out.println(arg);
    }

    @DisplayName("枚举测试")
    @EnumSource(TimeUnit.class)
    public void parameterizedEnumSourceTest(TimeUnit timeUnit) {
        System.out.println(timeUnit.name());
    }

    @DisplayName("部分枚举测试")
    @EnumSource(value = TimeUnit.class, names = {"DAYS", "HOURS"}, mode = EnumSource.Mode.INCLUDE)
    public void parameterizedOptionalEnumSourceTest(TimeUnit timeUnit) {
        System.out.println(timeUnit.name());
    }

    @DisplayName("静态参数工厂测试")
    @ParameterizedTest
    @MethodSource("methodArgFactory")//此类工厂方法必须返回流、可迭代、迭代器或参数数组
    public void parameterizedMethodSourceTest(String arg) {
        System.out.println(arg);
    }

    private static Stream<String> methodArgFactory() {
        return Stream.of("a", "b", "c");
    }

    @DisplayName("静态多参数工厂测试")
    @ParameterizedTest(name = "第{index}组参数：{arguments} ")
    @MethodSource("methodArgFactory2")//此类工厂方法必须返回流、可迭代、迭代器或参数数组
    //@ArgumentsSource(MyArgumentsProvider.class)   外部工厂
    public void parameterizedMethodSourceTestWithMultiArgs(String arg, Integer integer) {
        System.out.println(arg + integer);
    }

    private static Stream<Arguments> methodArgFactory2() {
        return Stream.of(
                Arguments.of("a", 1),
                Arguments.of("b", 2),
                Arguments.of("c", 3)
        );
    }

    @Tag("important")
    @DisplayName("csv格式参数测试")
    @ParameterizedTest(name = "{index} ==>{arguments}")
    @CsvSource({"1,2", "2,2"})
    public void parameterizedCsvSourceTest(String arg1, String arg2) {
        Assert.assertEquals("参数1于2不相等", arg1, arg2);//Junit自带的assert
        System.out.println(arg1 + "," + arg2);
    }

    /**
     * 参数单元测试样例
     *
     * @param arg1 1
     * @param arg2 2
     *             ParameterizedTest
     *             占位符    描述
     *             {index}	当前调用索引(基于1)
     *             {arguments}	完整的、逗号分隔的参数列表
     *             {0}, {1}, …​	单个参数
     */
    @Tag("normal")
    @DisplayName("csv文件参数测试")
    @ParameterizedTest(name = "{index} ==> first=''{0}'', second={1}")
    @CsvFileSource(resources = "/JunitExampleTest-0.csv")
    public void parameterizedCsvFileSourceTest(String arg1, String arg2) {
        System.out.println(arg1 + "," + arg2);
    }

    /**
     * 参数聚合(Argument Aggregation)
     *
     * @param arguments 聚合（可自定义聚合函数）
     */
    @DisplayName("聚合参数测试")
    @ParameterizedTest
    @CsvSource({
            "Jane, Doe, F, 1990-05-20",
            "John, Doe, M, 1990-10-22"
    })
    public void parameterizedWithArgumentsAccessorTest(ArgumentsAccessor arguments) {
        for (Object o : arguments.toList()) {
            System.out.println(o.toString());
        }
    }
//    /**
//     * 自定义聚合函数
//     */
//    public class PersonAggregator implements ArgumentsAggregator {
//        @Override
//        public Person aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
//            return new Person(arguments.getString(0),
//                    arguments.getString(1),
//                    arguments.get(2, Gender.class),
//                    arguments.get(3, LocalDate.class));

//        }

//    }


    /**
     * 模板化测试
     *
     * @param parameter
     */
    @DisplayName("模板化测试")
    @TestTemplate
    @ExtendWith(MyTestTemplateInvocationContextProvider.class)
    void testTemplate(String parameter) {
        assertEquals(3, parameter.length());
    }

    @BeforeAll
    static public void beforeAllTest() {
        System.out.println("单元测试开始" + System.currentTimeMillis());
    }

    @AfterAll
    static public void afterAllTest() {
        System.out.println("单元测试结束" + System.currentTimeMillis());
    }

    /**
     * TODO 未生效！！！
     */
    @Nested
    @DisplayName("嵌套测试")
    class OuterClass {
        @Nested
        @DisplayName("嵌套内部类")
        class InnerClass {
            @Test
            @DisplayName("嵌套第二层测试用例")
            public void innerTestMethod() {
                System.out.println("这是一个嵌套的内部测试用例");
            }
        }

        @Test
        @DisplayName("嵌套第一层测试用例")
        public void innerTestMethod() {
            System.out.println("这是一个嵌套的内部测试用例");
        }
    }

}
