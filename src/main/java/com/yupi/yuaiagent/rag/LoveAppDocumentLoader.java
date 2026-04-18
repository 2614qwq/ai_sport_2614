package com.yupi.yuaiagent.rag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 恋爱大师应用文档加载器
 */
@Component
@Slf4j
public class LoveAppDocumentLoader {

    // spring自带的资源解析类
    private final ResourcePatternResolver resourcePatternResolver;

    public LoveAppDocumentLoader(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }

    /**
     * 加载多篇 Markdown 文档
     * @return
     */
    /**
     * 批量加载 classpath 下 document 目录里所有的 .md 格式文档
     * 并解析成 Spring AI 的 Document 对象，用于向量化存储
     */
    public List<Document> loadMarkdowns() {
        // 用于存放所有解析后的文档
        List<Document> allDocuments = new ArrayList<>();

        try {
            // 1. 从 classpath:document/ 目录下，读取所有 .md 后缀的文件
            Resource[] resources = resourcePatternResolver.getResources("classpath:document/*.md");

            // 2. 遍历每一个 MD 文件
            for (Resource resource : resources) {
                // 获取文件名，例如：产品说明_已完成.md
                String filename = resource.getFilename();

                // 3. 从文件名中截取状态标签
                // 例：文件名长度-6 到 长度-4 → 取倒数第 3、第 2 个字符
                // 比如文件名：xxx_已完成.md → 截取到 "已完"
                String status = filename.substring(filename.length() - 6, filename.length() - 4);

                // 4. 构建 Markdown 解析配置
                MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                        .withHorizontalRuleCreateDocument(true)   // 分割线（---）切割成独立文档
                        .withIncludeCodeBlock(false)              // 不包含代码块
                        .withIncludeBlockquote(false)             // 不包含引用块
                        .withAdditionalMetadata("filename", filename)  // 给文档加元数据：文件名
                        .withAdditionalMetadata("status", status)      // 给文档加元数据：状态
                        .build();

                // 5. 创建 Markdown 解析器，读取并解析当前文件
                MarkdownDocumentReader markdownDocumentReader = new MarkdownDocumentReader(resource, config);

                // 6. 把解析好的文档加入集合
                allDocuments.addAll(markdownDocumentReader.get());
            }
        } catch (IOException e) {
            // 捕获读取文件异常，打印错误日志
            log.error("Markdown 文档加载失败", e);
        }

        // 返回所有解析好的文档，用于向量化入库
        return allDocuments;
    }
}
