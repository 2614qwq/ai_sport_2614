# AI 运动健康助手 (AI Sport Agent)

基于 Spring AI 和阿里云通义千问大模型构建的智能运动健康问答系统，提供个性化的运动建议和健康指导。

## 🚀 项目简介

本项目是一个 AI 驱动的运动健康助手应用，结合了 RAG（检索增强生成）技术和多种 AI Agent 架构，能够回答用户关于有氧运动、无氧运动、球类运动等各类体育相关问题的智能助手。

## ✨ 核心功能

- **智能对话**：支持同步和流式（SSE）两种方式与 AI 助手对话
- **RAG 知识库**：基于体育运动常见问题文档的知识库检索增强
- **超级智能体**：集成 Manus 超级智能体，具备工具调用能力
- **多模型支持**：支持阿里云通义千问（DashScope）和本地 Ollama 模型
- **API 文档**：集成 Knife4j/Swagger 在线 API 文档

## 🛠️ 技术栈

### 后端框架
- **Spring Boot 3.4.4** - 核心框架
- **Java 21** - 开发语言
- **Maven** - 项目管理

### AI 相关
- **Spring AI 1.0.0** - Spring AI 核心框架
- **Spring AI Alibaba 1.0.0.2** - 阿里云 AI 集成
- **DashScope SDK** - 阿里云通义千问大模型
- **Ollama** - 本地大模型运行环境
- **LangChain4J** - Java 版 LangChain

### 向量数据库
- **PGVector** - PostgreSQL 向量存储（可选）

### 工具库
- **Jsoup** - HTML 解析
- **iText PDF** - PDF 文档生成
- **Hutool** - Java 工具类库
- **Kryo** - 序列化框架

### API 文档
- **Knife4j 4.4.0** - OpenAPI 3 文档界面

## 📋 前置要求

- JDK 21 或更高版本
- Maven 3.6+
- （可选）PostgreSQL + PGVector 扩展
- （可选）Ollama 本地模型服务
- 阿里云 DashScope API Key


本地运行后打开网页:http://localhost:8123/api/
