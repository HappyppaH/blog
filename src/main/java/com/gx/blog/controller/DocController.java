package com.gx.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

@Controller
public class DocController {

    private View htmlView(String html) {
        return new InternalResourceView(html) {
            @Override
            protected void renderMergedOutputModel(java.util.Map<String, Object> model,
                                                   jakarta.servlet.http.HttpServletRequest request,
                                                   jakarta.servlet.http.HttpServletResponse response) throws Exception {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(html);
            }
        };
    }

    @GetMapping("/doc")
    public View docPage() {
        String html = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>博客系统 API 文档</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 40px; background: #f9f9f9; }
                    .card { background: #fff; border: 1px solid #ddd; margin: 20px 0; padding: 20px; border-radius: 8px; }
                    .method { font-weight: bold; color: #fff; padding: 4px 8px; border-radius: 4px; }
                    .get { background: #61affe; } .post { background: #49cc90; }
                    .put { background: #fca130; } .delete { background: #f93e3e; }
                    .url { font-family: monospace; font-size: 16px; margin-left: 10px; }
                    .desc { margin-top: 10px; color: #555; }
                </style>
            </head>
            <body>
                <h1>📖 个人博客系统 API 文档</h1>
                <h2>👤 用户管理</h2>
                <div class="card">
                    <span class="method get">GET</span>
                    <span class="url">/users</span>
                    <div class="desc">查询所有用户列表</div>
                </div>
                <div class="card">
                    <span class="method get">GET</span>
                    <span class="url">/users/{id}</span>
                    <div class="desc">根据 ID 查询单个用户（如 /users/1）</div>
                </div>
                <div class="card">
                    <span class="method post">POST</span>
                    <span class="url">/users</span>
                    <div class="desc">新增用户（Body: JSON，需包含 username、password、email）</div>
                </div>
                <div class="card">
                    <span class="method put">PUT</span>
                    <span class="url">/users/{id}</span>
                    <div class="desc">根据 ID 修改用户信息（如 /users/1）</div>
                </div>
                <div class="card">
                    <span class="method delete">DELETE</span>
                    <span class="url">/users/{id}</span>
                    <div class="desc">根据 ID 删除用户（如 /users/1）</div>
                </div>
                <h2>📝 文章管理</h2>
                              <div class="card">
                                  <span class="method get">GET</span>
                                  <span class="url">/articles?page=1&size=5</span>
                                  <div class="desc">分页查询文章（可选参数：category、keyword）</div>
                              </div>
                              <div class="card">
                                  <span class="method get">GET</span>
                                  <span class="url">/articles/all</span>
                                  <div class="desc">查询所有文章（不分页）</div>
                              </div>
                              <div class="card">
                                  <span class="method get">GET</span>
                                  <span class="url">/articles/{id}</span>
                                  <div class="desc">根据 ID 查询单篇文章（如 /articles/1）</div>
                              </div>
                              <div class="card">
                                  <span class="method post">POST</span>
                                  <span class="url">/articles</span>
                                  <div class="desc">发布新文章（Body: JSON，需包含 title、content、category、authorId）</div>
                              </div>
                              <div class="card">
                                  <span class="method put">PUT</span>
                                  <span class="url">/articles/{id}</span>
                                  <div class="desc">根据 ID 修改文章（如 /articles/1）</div>
                              </div>
                              <div class="card">
                                  <span class="method delete">DELETE</span>
                                  <span class="url">/articles/{id}</span>
                                  <div class="desc">根据 ID 删除文章（如 /articles/1）</div>
                              </div>
                              <h2>💬 评论管理</h2>
                                  <div class="card">
                                      <span class="method get">GET</span>
                                      <span class="url">/comments?articleId=1</span>
                                      <div class="desc">查询某篇文章的所有评论</div>
                                  </div>
                                  <div class="card">
                                      <span class="method post">POST</span>
                                      <span class="url">/comments</span>
                                      <div class="desc">发表评论（Body: JSON，需包含 articleId、username、content）</div>
                                  </div>
                                  <div class="card">
                                      <span class="method delete">DELETE</span>
                                      <span class="url">/comments/{id}</span>
                                      <div class="desc">删除评论（如 /comments/1）</div>
                                  </div>
                <h2>🧪 测试接口</h2>
                <div class="card">
                    <span class="method get">GET</span>
                    <span class="url">/hello</span>
                    <div class="desc">测试接口，返回问候语</div>
                </div>
                <p>后续接口持续更新...</p>
                <h2>📋 操作日志</h2>
                <div class="card" style="background: #fffbe6;">
                    <span class="method" style="background:#999;">自动</span>
                    <span class="url">AOP 切面</span>
                    <div class="desc">所有带 @Log 注解的接口操作自动记录到 operation_log 表</div>
                </div>
                </body>
            </html>
            """;
        return htmlView(html);
    }
}