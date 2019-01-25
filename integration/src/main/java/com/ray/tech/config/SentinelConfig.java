package com.ray.tech.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.ray.tech.controller.CommonController;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SentinelConfig {
    static {
        initFlowRules();
    }

    /**
     * {@link CommonController#sendImage()}
     */
    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();

        FlowRule captchaRule = new FlowRule();
        captchaRule.setResource("/captcha/image");
        captchaRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        captchaRule.setCount(1);

        FlowRule loginRule = new FlowRule();
        loginRule.setResource("/login");
        loginRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        loginRule.setCount(1);

        rules.add(captchaRule);
        rules.add(loginRule);

        FlowRuleManager.loadRules(rules);
    }
}
