import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.internal.Conditions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.tuple;

/**
 * <dependency>
 * <groupId>org.assertj</groupId>
 * <artifactId>assertj-core</artifactId>
 * <scope>test</scope>
 * </dependency>
 */
@Slf4j
@DisplayName("AssertJ 断言测试样例 之 链式断言")
public class AssertJExampleTest {

    @Test
    @DisplayName("字符串断言")
    public void normalTest() {
        String s = "abcde";
        assertThat(s).as("字符串满足")
                .startsWith("ab").endsWith("dc").contains("bcd")
                .isNotBlank()
                .hasSize(5);
    }

    @Test
    @DisplayName("数字断言")
    public void b() {
        Integer i = 50;
        assertThat(i).as("数字大小")
                .isGreaterThan(0).isLessThan(40);
    }

    @Test
    @DisplayName("日期断言")
    public void c() {
        Date date = new Date();
        assertThat(date).as("在规定日期内")
                .isAfter(new Date(date.getTime() + 2000)).isBefore(new Date(date.getTime() + 5000));
    }

    @Test
    @DisplayName("数组断言")
    public void d() {

        List<String> list = Arrays.asList("a", "b", "c", "f");
        assertThat(list).as("数组/列表")
                .startsWith("a").endsWith("d")
                .hasSize(4);
    }

    @Test
    @DisplayName("Map断言")
    public void e() {

        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 4);
        assertThat(map).as("Map键值对")
                .containsKeys("a", "b")
                .containsEntry("c", 3)
                .hasSize(3);
    }

    @Test
    @DisplayName("反射断言")
    public void f() {
        assertThat(AssertJExampleTest.class).as("此类是注解")
                .isAnnotation();
    }

    @Test
    @DisplayName("异常断言")
    public void testException() {
        assertThatExceptionOfType(IOException.class).as("异常断言").isThrownBy(() -> {
            throw new IOException("boom!!!!");
        })
                .withMessage("boom!")
                .withMessageContaining("oom")
                .withMessage("%s!", "boom")
                .withStackTraceContaining("IOException")
                .withNoCause();
    }

    @Test
    @DisplayName("自定义断言")
    public void g() {

        Condition<? super Class> condition = new Condition<>(aClass -> aClass.getAnnotation(Deprecated.class) != null, "Deprecated.class");
        assertThat(AssertJExampleTest.class).as("此类是被弃用的（Deprecated）")
                .has(condition);
    }


}
