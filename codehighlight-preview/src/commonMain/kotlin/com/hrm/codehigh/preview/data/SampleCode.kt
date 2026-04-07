package com.hrm.codehigh.preview.data

/**
 * 各语言示例代码常量，集中存放。
 * 每段示例代码覆盖该语言的主要 Token 类型（关键字、字符串、注释、注解等）。
 */
internal object SampleCode {

    val KOTLIN = """
        package com.example

        import androidx.compose.runtime.Composable
        import androidx.compose.material3.Text

        /**
         * 示例 Kotlin 代码，展示主要 Token 类型。
         */
        @Composable
        fun Greeting(name: String) {
            // 单行注释
            val message = "Hello, ${'$'}{name}!"
            val count: Int = 42
            val hex = 0xFF
            println(message)
        }

        data class User(
            val id: Long,
            val name: String,
            val isActive: Boolean = true
        )

        sealed class Result<out T> {
            data class Success<T>(val data: T) : Result<T>()
            data class Error(val message: String) : Result<Nothing>()
        }
    """.trimIndent()

    val JAVA = """
        package com.example;

        import java.util.List;
        import java.util.ArrayList;

        /**
         * 示例 Java 代码，展示主要 Token 类型。
         */
        @SuppressWarnings("unchecked")
        public class HelloWorld {
            private static final String GREETING = "Hello, World!";
            
            // 单行注释
            public static void main(String[] args) {
                List<String> names = new ArrayList<>();
                names.add("Alice");
                names.add("Bob");
                
                for (String name : names) {
                    System.out.println(GREETING + " " + name);
                }
                
                int count = 42;
                double pi = 3.14159;
                boolean flag = true;
            }
        }
    """.trimIndent()

    val PYTHON = """
        #!/usr/bin/env python3
        #示例 Python 代码，展示主要 Token 类型。

        from typing import List, Optional
        import os

        # 单行注释
        CONSTANT = 42

        @dataclass
        class User:
            name: str
            age: int
            email: Optional[str] = None

        def greet(name: str) -> str:
            ${"\"\"\""}问候函数${"\"\"\""}
            message = f"Hello, {name}!"
            return message

        users = [User("Alice", 30), User("Bob", 25)]
        for user in users:
            print(greet(user.name))

        result = [x ** 2 for x in range(10) if x % 2 == 0]
    """.trimIndent()

    val JAVASCRIPT = """
        // 示例 JavaScript 代码，展示主要 Token 类型
        import { useState, useEffect } from 'react';

        const API_URL = 'https://api.example.com';

        /**
         * 异步获取用户数据
         */
        async function fetchUsers() {
            try {
                const response = await fetch(`${'$'}{API_URL}/users`);
                const data = await response.json();
                return data;
            } catch (error) {
                console.error('Error:', error);
                return null;
            }
        }

        class UserService {
            constructor(baseUrl) {
                this.baseUrl = baseUrl;
                this.users = [];
            }

            async getUser(id) {
                const user = this.users.find(u => u.id === id);
                return user ?? null;
            }
        }

        const numbers = [1, 2, 3, 4, 5];
        const doubled = numbers.map(n => n * 2);
        console.log(doubled);
    """.trimIndent()

    val TYPESCRIPT = """
        // 示例 TypeScript 代码，展示主要 Token 类型
        import { Injectable } from '@angular/core';
        import { Observable, from } from 'rxjs';

        interface User {
            id: number;
            name: string;
            email?: string;
            roles: string[];
        }

        type ApiResponse<T> = {
            data: T;
            status: number;
            message: string;
        };

        @Injectable({ providedIn: 'root' })
        export class UserService {
            private readonly apiUrl = 'https://api.example.com';

            async getUser(id: number): Promise<User | null> {
                try {
                    const response = await fetch(`${'$'}{this.apiUrl}/users/${'$'}{id}`);
                    return response.json() as Promise<User>;
                } catch (error: unknown) {
                    console.error(error);
                    return null;
                }
            }

            getUsers(): Observable<User[]> {
                return from(fetch(this.apiUrl).then(r => r.json()));
            }
        }
    """.trimIndent()

    val GO = """
        package main

        import (
            "fmt"
            "net/http"
            "encoding/json"
        )

        // User 用户结构体
        type User struct {
            ID    int    `json:"id"`
            Name  string `json:"name"`
            Email string `json:"email,omitempty"`
        }

        // GetUser 获取用户信息
        func GetUser(id int) (*User, error) {
            url := fmt.Sprintf("https://api.example.com/users/%d", id)
            resp, err := http.Get(url)
            if err != nil {
                return nil, err
            }
            defer resp.Body.Close()

            var user User
            if err := json.NewDecoder(resp.Body).Decode(&user); err != nil {
                return nil, err
            }
            return &user, nil
        }

        func main() {
            user, err := GetUser(1)
            if err != nil {
                fmt.Printf("Error: %v\n", err)
                return
            }
            fmt.Printf("User: %+v\n", user)
        }
    """.trimIndent()

    val RUST = """
        use std::collections::HashMap;
        use std::fmt;

        /// 用户结构体
        #[derive(Debug, Clone)]
        pub struct User {
            pub id: u64,
            pub name: String,
            pub email: Option<String>,
        }

        impl User {
            pub fn new(id: u64, name: impl Into<String>) -> Self {
                User {
                    id,
                    name: name.into(),
                    email: None,
                }
            }
        }

        impl fmt::Display for User {
            fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
                write!(f, "User({}: {})", self.id, self.name)
            }
        }

        fn main() {
            let mut users: HashMap<u64, User> = HashMap::new();
            let user = User::new(1, "Alice");
            println!("Created: {}", user);
            users.insert(user.id, user);

            if let Some(found) = users.get(&1) {
                println!("Found: {:?}", found);
            }
        }
    """.trimIndent()

    val C = """
        #include <stdio.h>
        #include <stdlib.h>
        #include <string.h>

        /* 用户结构体 */
        typedef struct {
            int id;
            char name[64];
            double score;
        } User;

        // 创建用户
        User* create_user(int id, const char* name, double score) {
            User* user = (User*)malloc(sizeof(User));
            if (user == NULL) return NULL;
            user->id = id;
            strncpy(user->name, name, sizeof(user->name) - 1);
            user->score = score;
            return user;
        }

        int main(void) {
            User* user = create_user(1, "Alice", 95.5);
            if (user != NULL) {
                printf("User: %d, %s, %.1f\n", user->id, user->name, user->score);
                free(user);
            }
            return 0;
        }
    """.trimIndent()

    val CPP = """
        #include <iostream>
        #include <vector>
        #include <memory>
        #include <string>

        // 用户类
        class User {
        public:
            User(int id, std::string name) : id_(id), name_(std::move(name)) {}
            
            virtual ~User() = default;
            
            int getId() const { return id_; }
            const std::string& getName() const { return name_; }
            
            virtual void greet() const {
                std::cout << "Hello, I'm " << name_ << std::endl;
            }

        private:
            int id_;
            std::string name_;
        };

        template<typename T>
        class Repository {
        public:
            void add(std::unique_ptr<T> item) {
                items_.push_back(std::move(item));
            }
            
            size_t size() const { return items_.size(); }

        private:
            std::vector<std::unique_ptr<T>> items_;
        };

        int main() {
            auto repo = std::make_unique<Repository<User>>();
            repo->add(std::make_unique<User>(1, "Alice"));
            std::cout << "Count: " << repo->size() << std::endl;
            return 0;
        }
    """.trimIndent()

    val SWIFT = """
        import Foundation
        import SwiftUI

        // 用户模型
        struct User: Identifiable, Codable {
            let id: UUID
            var name: String
            var email: String?
            
            init(name: String, email: String? = nil) {
                self.id = UUID()
                self.name = name
                self.email = email
            }
        }

        // 用户视图
        @MainActor
        class UserViewModel: ObservableObject {
            @Published var users: [User] = []
            @Published var isLoading = false
            
            func loadUsers() async {
                isLoading = true
                defer { isLoading = false }
                
                do {
                    let url = URL(string: "https://api.example.com/users")!
                    let (data, _) = try await URLSession.shared.data(from: url)
                    users = try JSONDecoder().decode([User].self, from: data)
                } catch {
                    print("Error: \(error)")
                }
            }
        }
    """.trimIndent()

    val SQL = """
        -- 示例 SQL 代码，展示主要 Token 类型
        CREATE TABLE users (
            id          SERIAL PRIMARY KEY,
            name        VARCHAR(100) NOT NULL,
            email       VARCHAR(255) UNIQUE,
            created_at  TIMESTAMP DEFAULT NOW(),
            is_active   BOOLEAN DEFAULT TRUE
        );

        /* 查询活跃用户 */
        SELECT 
            u.id,
            u.name,
            u.email,
            COUNT(o.id) AS order_count,
            SUM(o.amount) AS total_amount
        FROM users u
        LEFT JOIN orders o ON u.id = o.user_id
        WHERE u.is_active = TRUE
            AND u.created_at >= '2024-01-01'
        GROUP BY u.id, u.name, u.email
        HAVING COUNT(o.id) > 0
        ORDER BY total_amount DESC
        LIMIT 10 OFFSET 0;
    """.trimIndent()

    val JSON = """
        {
            "users": [
                {
                    "id": 1,
                    "name": "Alice",
                    "email": "alice@example.com",
                    "age": 30,
                    "isActive": true,
                    "roles": ["admin", "user"],
                    "address": {
                        "city": "Beijing",
                        "country": "China"
                    }
                },
                {
                    "id": 2,
                    "name": "Bob",
                    "email": null,
                    "age": 25,
                    "isActive": false,
                    "roles": ["user"],
                    "score": 3.14
                }
            ],
            "total": 2,
            "page": 1
        }
    """.trimIndent()

    val YAML = """
        # 示例 YAML 配置文件
        ---
        app:
          name: MyApp
          version: "1.0.0"
          debug: false

        server:
          host: localhost
          port: 8080
          ssl: true

        database:
          url: jdbc:postgresql://localhost:5432/mydb
          username: admin
          password: &secret "s3cr3t"
          pool:
            min: 5
            max: 20
            timeout: 30000

        features:
          - name: auth
            enabled: true
          - name: cache
            enabled: false
            ttl: 3600

        logging:
          level: INFO
          format: json
          password: *secret
        ...
    """.trimIndent()

    val BASH = """
        #!/bin/bash
        # 示例 Bash 脚本，展示主要 Token 类型

        set -euo pipefail

        # 配置变量
        APP_NAME="MyApp"
        LOG_DIR="/var/log/${'$'}{APP_NAME}"
        MAX_RETRIES=3

        # 日志函数
        log() {
            echo "[$(date '+%Y-%m-%d %H:%M:%S')] ${'$'}1"
        }

        # 检查依赖
        check_dependencies() {
            local deps=("curl" "jq" "docker")
            for dep in "${'$'}{deps[@]}"; do
                if ! command -v "${'$'}dep" &>/dev/null; then
                    log "ERROR: ${'$'}dep is not installed"
                    exit 1
                fi
            done
        }

        # 主函数
        main() {
            log "Starting ${'$'}{APP_NAME}..."
            check_dependencies
            
            mkdir -p "${'$'}{LOG_DIR}"
            
            for i in $(seq 1 ${'$'}{MAX_RETRIES}); do
                if curl -sf "https://api.example.com/health" > /dev/null; then
                    log "Health check passed"
                    break
                fi
                log "Retry ${'$'}i/${'$'}{MAX_RETRIES}..."
                sleep 5
            done
        }

        main "${'$'}@"
    """.trimIndent()

    val RUBY = """
        require "json"

        class User
          attr_accessor :name, :email

          def initialize(name, email)
            @name = name
            @email = email
          end

          def greet
            puts "Hello, #{@name}"
          end
        end
    """.trimIndent()

    val PHP = """
        <?php

        class UserController {
            public function show(${ '$' }id): void {
                ${ '$' }user = ["id" => ${ '$' }id, "name" => "Alice"];
                echo ${ '$' }user["name"];
            }
        }
    """.trimIndent()

    val DART = """
        import 'package:flutter/material.dart';

        class CounterPage extends StatefulWidget {
          const CounterPage({super.key});

          @override
          State<CounterPage> createState() => _CounterPageState();
        }

        class _CounterPageState extends State<CounterPage> {
          int count = 0;

          void increment() {
            setState(() {
              count += 1;
            });
          }
        }
    """.trimIndent()

    val SCALA = """
        case class User(name: String, age: Int)

        trait Greeter {
          def greet(user: User): String
        }

        object Main extends App {
          val user = User("Alice", 30)
          println(user)
        }
    """.trimIndent()

    val R = """
        library(ggplot2)

        users <- data.frame(
          name = c("Alice", "Bob"),
          score = c(95, 88)
        )

        print(users)
    """.trimIndent()

    val TOML = """
        title = "CodeHigh"

        [server]
        host = "127.0.0.1"
        port = 8080

        [[plugins]]
        name = "syntax"
        enabled = true
    """.trimIndent()

    val DOCKERFILE = """
        FROM eclipse-temurin:21-jre

        WORKDIR /app
        COPY build/libs/app.jar /app/app.jar
        ENV JAVA_OPTS="-Xms256m -Xmx512m"
        EXPOSE 8080
        CMD ["sh", "-c", "java ${'$'}JAVA_OPTS -jar /app/app.jar"]
    """.trimIndent()

    val LUA = """
        local User = {}
        User.__index = User

        function User.new(name)
          local self = setmetatable({}, User)
          self.name = name
          return self
        end

        print(require("game.config"))
    """.trimIndent()

    val HASKELL = """
        module Main where

        data User = User String Int

        greet :: User -> String
        greet (User name _) = "Hello, " ++ name

        main = putStrLn (greet (User "Alice" 30))
    """.trimIndent()

    val ELIXIR = """
        defmodule User do
          def greet(name) do
            name
            |> String.trim()
            |> IO.puts()
          end
        end

        User.greet(" Alice ")
    """.trimIndent()

    val DIFF = """
        diff --git a/UserService.kt b/UserService.kt
        index 1234567..89abcde 100644
        --- a/UserService.kt
        +++ b/UserService.kt
        @@ -1,6 +1,8 @@
         class UserService {
        -    fun findUser(id: Int): User? = null
        +    fun findUser(id: Int): User? {
        +        return repository.findById(id)
        +    }
         }
    """.trimIndent()

    val HTML = """
        <!DOCTYPE html>
        <html lang="zh-CN">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>示例 HTML 页面</title>
            <link rel="stylesheet" href="styles.css">
        </head>
        <body>
            <!-- 页面头部 -->
            <header class="header" id="main-header">
                <nav>
                    <a href="/" class="logo">MyApp</a>
                    <ul>
                        <li><a href="/about">关于</a></li>
                        <li><a href="/contact">联系</a></li>
                    </ul>
                </nav>
            </header>
            
            <main>
                <section class="hero">
                    <h1>欢迎使用 MyApp</h1>
                    <p>这是一个示例页面。</p>
                    <button type="button" onclick="handleClick()">点击我</button>
                </section>
                
                <img src="hero.png" alt="Hero Image" width="800" height="400">
            </main>
            
            <script src="app.js"></script>
        </body>
        </html>
    """.trimIndent()

    val CSS = """
        /* 示例 CSS 代码，展示主要 Token 类型 */
        :root {
            --primary-color: #007bff;
            --secondary-color: #6c757d;
            --font-size-base: 16px;
        }

        /* 基础样式 */
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
            font-size: var(--font-size-base);
            color: #333;
            background-color: #fff;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 16px;
        }

        /* 响应式布局 */
        @media (max-width: 768px) {
            .container {
                padding: 0 8px;
            }
        }

        /* 动画 */
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .card:hover {
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            transition: box-shadow 0.3s ease;
        }

        input[type="text"]::placeholder {
            color: var(--secondary-color);
        }
    """.trimIndent()

    val XML = """
        <?xml version="1.0" encoding="UTF-8"?>
        <!-- 示例 XML 文档 -->
        <project xmlns="http://maven.apache.org/POM/4.0.0"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
                 http://maven.apache.org/xsd/maven-4.0.0.xsd">
            
            <modelVersion>4.0.0</modelVersion>
            <groupId>com.example</groupId>
            <artifactId>my-app</artifactId>
            <version>1.0.0</version>
            <packaging>jar</packaging>
            
            <dependencies>
                <dependency>
                    <groupId>org.jetbrains.kotlin</groupId>
                    <artifactId>kotlin-stdlib</artifactId>
                    <version>2.0.0</version>
                </dependency>
            </dependencies>
            
            <![CDATA[
                这是 CDATA 内容，不会被解析为 XML。
            ]]>
        </project>
    """.trimIndent()
}
