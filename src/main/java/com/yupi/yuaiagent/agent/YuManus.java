package com.yupi.yuaiagent.agent;

import com.yupi.yuaiagent.advisor.MyLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Component;

/**
 * 【鱼皮 AI 超级智能体 - 最顶层业务 Agent】
 * 继承自 ToolCallAgent，拥有：思考、行动、工具调用、多步执行能力
 * 项目中真正使用的 AI 助手主类，可直接注入使用
 */
@Component // 交给 Spring 管理，可直接 @Autowired 注入使用
public class YuManus extends ToolCallAgent {

    /**
     * 构造方法：初始化超级智能体
     * @param allTools  Spring 注入的所有工具（来自 ToolRegistration 配置类）
     * @param dashscopeChatModel  通义千问大模型（Spring 自动注入）
     */
    public YuManus(ToolCallback[] allTools, ChatModel dashscopeChatModel) {
        // 调用父类 ToolCallAgent 构造方法，传入所有工具
        super(allTools);

        // 给智能体设置名称
        this.setName("sportsAssistant");

        // 系统提示词（设定 AI 身份：全能助手、可以调用工具）
        String SYSTEM_PROMPT = """
               You are a professional Sports Assistant, specialized in helping users with sports and fitness related questions.
               Your expertise includes exercise training, sports techniques, fitness planning, nutrition advice, and injury prevention.
               You have various tools at your disposal that you can call upon to efficiently complete complex sports-related requests.
               """;
        this.setSystemPrompt(SYSTEM_PROMPT);

        // 下一步行动提示词（指导 AI 如何思考、如何分步执行、如何结束）
        String NEXT_STEP_PROMPT = """
                Based on user's sports and fitness needs, proactively select the most appropriate tool or combination of tools.
                For complex training plans or sports questions, break down the problem and use different tools step by step to solve it.
                After using each tool, clearly explain the execution results and provide professional sports guidance.
                Always prioritize user safety and provide scientifically-based sports advice.
                If you want to stop the interaction at any point, use the `terminate` tool/function call.
                """;
        this.setNextStepPrompt(NEXT_STEP_PROMPT);

        // 设置最大执行步数（防止无限循环）
        this.setMaxSteps(20);

        // 初始化 Spring AI 聊天客户端
        ChatClient chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultAdvisors(new MyLoggerAdvisor()) // 添加自定义日志顾问
                .build();

        // 把聊天客户端设置给智能体
        this.setChatClient(chatClient);
    }
}