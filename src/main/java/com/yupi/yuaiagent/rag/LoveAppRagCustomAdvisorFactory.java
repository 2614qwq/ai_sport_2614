package com.yupi.yuaiagent.rag;

import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;

/**
 * 自定义 RAG 增强顾问 工厂类
 * 作用：统一创建【带过滤、带自定义逻辑】的 RAG 检索增强顾问
 * 不同于云端知识库配置，这里是【本地向量库】的 RAG 实现
 */
public class LoveAppRagCustomAdvisorFactory {

    /**
     * 静态工厂方法：创建一个自定义、可复用的 RAG 增强顾问
     * 特点：可以根据 status 动态过滤知识库文档
     *
     * @param vectorStore 向量库（存储向量化后的文档）
     * @param status 过滤条件：只检索 status 等于该值的文档
     * @return 构建完成的 RAG 增强顾问
     */
    public static Advisor createLoveAppRagCustomAdvisor(VectorStore vectorStore, String status) {
        // 1. 构建过滤表达式：只查询 metadata 中 status 字段 = 传入 status 的文档
        // 作用：实现“按条件查知识库”，比如只查公开/已审核/指定分类的内容
        Filter.Expression expression = new FilterExpressionBuilder()
                .eq("status", status)
                .build();

        // 2. 创建【向量库文档检索器】
        // 从向量库中做相似度检索 + 条件过滤
        DocumentRetriever documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)                // 指定使用的向量库
                .filterExpression(expression)            // 绑定过滤条件（只查符合 status 的）
                .similarityThreshold(0.5)                // 相似度阈值：高于 0.5 才认为相关
                .topK(3)                                 // 最多返回 3 条最相关的文档
                .build();

        // 3. 构建 RAG 检索增强顾问（Spring AI 标准）
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(documentRetriever)    // 设置文档检索器
                .queryAugmenter(LoveAppContextualQueryAugmenterFactory.createInstance()) // 设置自定义查询增强器
                .build();
    }
}