package com.ray.tech.config;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * See also the differences between @Value and @ConfigurationProperties.
 * <a>https://docs.spring.io/spring-boot/docs/2.2.0.BUILD-SNAPSHOT/reference/html/spring-boot-features.html#boot-features-external-config-vs-value</a>
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "customer")
public class CustomerConfig {
    @NotNull
    private String name;
    private List<String> nickName;
    private Map<String, String> address;
    /**
     * ns for nanoseconds
     * us for microseconds
     * ms for milliseconds
     * s for seconds
     * m for minutes
     * h for hours
     * d for days
     */
    @DurationUnit(ChronoUnit.DAYS)
    private Duration age = Duration.ofDays(1);
    /**
     * B for bytes
     * KB for kilobytes
     * MB for megabytes
     * GB for gigabytes
     * TB for terabytes
     */
    @DataSizeUnit(DataUnit.MEGABYTES)
    private DataSize archiveSize = DataSize.ofMegabytes(1L);
    @Valid
    private Occupation occupation = new Occupation();

    public void setAddress(Map<String, String> address) {
        this.address = address;
    }

    public JSONObject getData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("nick_name", nickName);
        jsonObject.put("address", address);
        return jsonObject;
    }

    @Data
    public static class Occupation {
        @NotEmpty
        private String name;
        @DurationUnit(ChronoUnit.DAYS)
        private Duration duration = Duration.ofDays(1);
    }
}
