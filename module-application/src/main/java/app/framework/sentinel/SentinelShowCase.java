//package app.framework.sentinel;
//
//import app.common.IShowCase;
//import com.alibaba.csp.sentinel.Entry;
//import com.alibaba.csp.sentinel.SphU;
//import com.alibaba.csp.sentinel.slots.block.BlockException;
//import com.alibaba.csp.sentinel.slots.block.RuleConstant;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
//import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
//import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
//import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
//import com.alibaba.csp.sentinel.slots.system.SystemRule;
//import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// * @Author pickjob@126.com
// * @Date 2020-10-21
// */
//public class SentinelShowCase implements IShowCase {
//    private static final Logger logger = LogManager.getLogger(SentinelShowCase.class);
//
//    @Override
//    public void showSomething() {
//        initFlowRules();
//        while (true) {
//            Entry entry = null;
//            try {
//                entry = SphU.entry("HelloWorld");
//                /*您的业务逻辑 - 开始*/
//                logger.info("hello world");
//                /*您的业务逻辑 - 结束*/
//            } catch (BlockException e1) {
//                /*流控逻辑处理 - 开始*/
//                logger.info("block!");
//                /*流控逻辑处理 - 结束*/
//            } finally {
//                if (entry != null) {
//                    entry.exit();
//                }
//            }
//        }
//    }
//
////    @Override
////    public boolean isShow() {
////        return true;
////    }
//
//    private void initFlowRules(){
//        List<FlowRule> rules = new ArrayList<>();
//        FlowRule rule = new FlowRule();
//        rule.setResource("HelloWorld");
//        rule.setCount(3);
//        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER);
//        rules.add(rule);
//        FlowRuleManager.loadRules(rules);
//    }
//
//    private void initParamRules() {
//        ParamFlowRule rule = new ParamFlowRule("HelloWorld")
//                .setParamIdx(0)
//                .setCount(5);
//        // 针对 int 类型的参数 PARAM_B，单独设置限流 QPS 阈值为 10，而不是全局的阈值 5.
//        ParamFlowItem item = new ParamFlowItem().setObject(String.valueOf("PARAM_B"))
//                .setClassType(int.class.getName())
//                .setCount(10);
//        rule.setParamFlowItemList(Collections.singletonList(item));
//
//        ParamFlowRuleManager.loadRules(Collections.singletonList(rule));
//    }
//
//    private void initDegradeRule() {
//        List<DegradeRule> rules = new ArrayList<>();
//        DegradeRule rule = new DegradeRule();
//        rule.setResource("HelloWorld");
//        rule.setCount(10);
//        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
//        rule.setTimeWindow(10);
//        rules.add(rule);
//        DegradeRuleManager.loadRules(rules);
//    }
//
//    private void initSystemRule() {
//        List<SystemRule> rules = new ArrayList<>();
//        SystemRule rule = new SystemRule();
//        rule.setHighestSystemLoad(10);
//        rules.add(rule);
//        SystemRuleManager.loadRules(rules);
//    }
//}
