# class-obf

[CHANGE LOG](CHANGELOG.MD)

<img alt="gitleaks badge" src="https://img.shields.io/badge/protected%20by-gitleaks-blue">

![](https://img.shields.io/github/downloads/jar-analyzer/class-obf/total)
![](https://img.shields.io/github/v/release/jar-analyzer/class-obf)

`class-obf` 项目全称 `Class Obfuscator` 项目，专门用来混淆单个 `Class` 文件

示例图：混淆前 -> 混淆后

![](img/000.png)

可以自定义中文混淆字符，以及方法隐藏等技巧

![](img/004.png)

## 背景

`jar-analyzer` 系列曾有一款工具 `jar-obfuscator` 实现 `jar` 包的混淆

链接：https://github.com/jar-analyzer/jar-obfuscator

但是该工具有以下问题：

- 配置文件非常复杂，不熟悉的情况下难以上手使用
- 不同的 `Jar` 文件需要考虑各种不同的情况否则无法启动
- 实际需求更大的是对单个 `Class` 文件的混淆（内存马等需求）

**于是有了这款工具**

- 配置大幅简化，仅针对单个 `Class` 文件
- 命令行输出改善，详细展示混淆细节

## 快速开始

生成配置文件：`java -jar class-obf.jar --generate`

使用指定配置文件混淆当前目录的 `Test.class` 

```shell
java -jar class-obf.jar --config config.yaml --input Test.class
```

一个普通的类

```java
public class Test {
    private String a = "cal";
    private String b = "c.exe";
    private int c = 1;

    public static void eval() throws Exception {
        Test test = new Test();
        Runtime rt = Runtime.getRuntime();
        rt.exec(test.a + test.b);
        System.out.println(test.c);
    }

    public static void main(String[] args) throws Exception {
        eval();
    }
}
```

你可以随意搭配配置文件，得到以下集中混淆结果

效果一（默认配置）

![](img/001.png)

效果二（使用最高级别的花指令参数）

![](img/002.png)

自定义混淆字符（例如使用中文）

![](img/003.png)

开启隐藏方法和字段功能（反编译看不到方法）

![](img/004.png)

但是！可以成功执行

![](img/005.png)

## 配置文件

可以根据你的需求修改配置文件

```yaml
!!me.n1ar4.clazz.obfuscator.config.BaseConfig
# 日志级别
logLevel: info

# 全局方法黑名单
methodBlackList:
  - "test"

# 混淆字符组合
obfuscateChars:
  - "i"
  - "l"
  - "L"
  - "1"
  - "I"

# 是否开启删除编译信息
enableDeleteCompileInfo: true
# 是否开启字段混淆
enableFieldName: true
# 是否开启字段隐藏
enableHideField: false
# 是否开启方法隐藏
enableHideMethod: false
# 是否开启方法名混淆
enableMethodName: true
# 是否开启方法参数名混淆
enableParamName: true
# 是否对数字进行异或混淆
enableXOR: true

# 是否启用全局字符串提取混淆
enableAdvanceString: true
# 全局提取字符串的变量名可以自定义
advanceStringName: ME_N1AR4_CLAZZ_OBF_PROJECT

# 是否开启花指令混淆
enableJunk: true
# 花指令混淆级别 1-5
junkLevel: 3
# 一个类中花指令最多数量
maxJunkOneClass: 1000
```

## Thanks

<img src="https://resources.jetbrains.com/storage/products/company/brand/logos/IntelliJ_IDEA.svg" alt="IntelliJ IDEA logo.">


This project is developed using JetBrains IDEA.
Thanks to JetBrains for providing me with a free license, which is a strong support for me.

## Star

<div align="center">

<img src="https://api.star-history.com/svg?repos=jar-analyzer/class-obfr&type=Date" width="600" height="400" alt="Star History Chart" valign="middle">

</div>
