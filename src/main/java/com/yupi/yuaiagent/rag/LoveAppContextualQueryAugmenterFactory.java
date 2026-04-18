package com.yupi.yuaiagent.rag;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;

/**
 * 【查询增强器工厂】
 * 作用：创建一个自定义的 RAG 查询增强器
 * 核心功能：当 AI 没有从知识库检索到相关内容时，强制返回固定的提示语
 */
public class LoveAppContextualQueryAugmenterFactory {

    /**
     * 静态工厂方法：创建并返回一个查询增强器实例
     * 专门用于控制：当知识库无匹配内容时，AI 该如何回答
     *
     * @return 配置好的 ContextualQueryAugmenter 增强器
     */
    public static ContextualQueryAugmenter createInstance() {
        // 1. 定义【空上下文】提示模板
        // 当没有从知识库查到任何相关内容时，强制 AI 输出这段固定文案
        PromptTemplate emptyContextPromptTemplate = new PromptTemplate("""
                你应该输出下面的内容：
                抱歉，我只能回答体育运动的问题，别的没办法帮到您哦，
                有问题可以联系编程导航客服 https://codefather.cn
                """);

        // 2. 构建查询增强器（Spring AI RAG 标准组件）
        return ContextualQueryAugmenter.builder()
                .allowEmptyContext(false)  // 不允许空上下文（必须要有知识库内容才能回答）
                .emptyContextPromptTemplate(emptyContextPromptTemplate)  // 设置空上下文时的固定回复
                .build();
    }
}