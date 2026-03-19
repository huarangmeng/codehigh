package com.hrm.codehigh.ast

/**
 * Token 类型枚举，定义代码高亮中所有可能的 Token 分类
 */
enum class TokenType {
    /** 关键字：fun、class、if、def、import 等 */
    KEYWORD,

    /** 字符串字面量：单引号、双引号、三引号、模板字符串 */
    STRING,

    /** 数字字面量：整数、浮点数、十六进制、二进制 */
    NUMBER,

    /** 注释：单行 //、#，多行 /* */、""" """ */
    COMMENT,

    /** 运算符：+、-、*、/、=、==、!=、&&、|| 等 */
    OPERATOR,

    /** 标点符号：{、}、(、)、[、]、;、,、. */
    PUNCTUATION,

    /** 标识符：变量名、函数名、类名等通用标识 */
    IDENTIFIER,

    /** 类型名：Int、String、Boolean、List、Map 等 */
    TYPE,

    /** 函数调用名（可与 IDENTIFIER 合并，按语言特性决定） */
    FUNCTION,

    /** 变量名（部分语言可区分变量与标识符） */
    VARIABLE,

    /** 常量名：const、val、全大写命名等 */
    CONSTANT,

    /** 注解/装饰器：@Override、@Composable、@property */
    ANNOTATION,

    /** 装饰器：Python @decorator，与 ANNOTATION 语义相近 */
    DECORATOR,

    /** 内置函数/类型：println、print、len、range、None、true、false */
    BUILTIN,

    /** 纯文本：无法分类的字符，降级兜底 */
    PLAIN
}
