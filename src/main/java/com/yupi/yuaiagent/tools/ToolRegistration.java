package com.yupi.yuaiagent.tools;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 【AI 工具注册配置类】
 * 作用：把所有自定义的 AI 工具（联网搜索、文件操作、终端执行等）统一注册
 * 让 Spring AI 大模型可以自动调用这些工具（函数调用 Function Calling）
 */
@Configuration
public class ToolRegistration {

    /**
     * 从配置文件读取联网搜索的 API Key
     * 用于 WebSearchTool 联网搜索能力
     */
    @Value("${search-api.api-key}")
    private String searchApiKey;

    /**
     * 注册【所有AI工具数组】，交给 Spring 管理
     * 大模型会自动识别并调用这里注册的工具
     *
     * @return 封装好的所有工具回调数组
     */
    @Bean
    public ToolCallback[] allTools() {
        // 1. 创建各个工具实例
        FileOperationTool fileOperationTool = new FileOperationTool();                // 文件操作工具（读/写文件）
        WebSearchTool webSearchTool = new WebSearchTool(searchApiKey);              // 联网搜索工具（查最新信息）
        WebScrapingTool webScrapingTool = new WebScrapingTool();                    // 网页抓取工具（爬取网页内容）
        ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();    // 资源下载工具（下载图片/文件）
        TerminalOperationTool terminalOperationTool = new TerminalOperationTool();  // 终端命令执行工具（执行服务器命令）
        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();              // PDF 生成工具
        TerminateTool terminateTool = new TerminateTool();                          // 对话终止工具

        // 2. 把所有工具封装成 Spring AI 识别的 ToolCallback 数组并返回
        return ToolCallbacks.from(
                fileOperationTool,
                webSearchTool,
                webScrapingTool,
                resourceDownloadTool,
                terminalOperationTool,
                pdfGenerationTool,
                terminateTool
        );
    }
}