package com.yupi.yuaiagent.agent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 【ReAct 智能代理抽象类】
 * ReAct = Reasoning (思考) + Acting (行动)
 * 作用：定义 AI 智能体的核心运行流程：先思考 -> 再行动
 * 这是所有具体智能体（如聊天、工具调用、RAG 智能体）的父类
 */
@EqualsAndHashCode(callSuper = true) //  Lombok 注解：继承父类的 equals/hashCode 方法
@Data // Lombok 注解：自动生成 getter、setter、toString 等方法
@Slf4j // Lombok 注解：自动生成日志对象 log
public abstract class ReActAgent extends BaseAgent {

    /**
     * 【思考方法】（子类必须实现）
     * 让 AI 分析当前状态、用户问题、上下文，决定下一步要不要行动、做什么行动
     *
     * @return boolean 是否需要执行行动
     *         true = 需要执行 act()
     *         false = 不需要行动，直接结束
     */
    public abstract boolean think();

    /**
     * 【行动方法】（子类必须实现）
     * 根据 think() 思考的结果，执行具体动作
     * 例如：联网搜索、调用工具、查询知识库、生成回答等
     *
     * @return 行动执行后的结果
     */
    public abstract String act();

    /**
     * 【执行单步流程】重写父类的 step() 方法
     * 固定流程：先思考 think() → 再行动 act()
     *
     * @return 步骤执行结果
     */
    @Override
    public String step() {
        try {
            // 1. 先让 AI 思考：是否需要行动？
            boolean shouldAct = think();

            // 2. 如果不需要行动，直接返回结果
            if (!shouldAct) {
                return "思考完成 - 无需行动";
            }

            // 3. 如果需要行动，就执行行动并返回结果
            return act();
        } catch (Exception e) {
            // 异常处理：打印错误栈，并返回失败提示
            e.printStackTrace();
            return "步骤执行失败：" + e.getMessage();
        }
    }

}