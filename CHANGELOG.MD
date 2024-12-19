# CHANGELOG

## 1.4.0

更新日志：

- todo

感谢以下用户的贡献:

- 4ra1n (https://github.com/4ra1n)

可供下载的文件都由 `Github Actions` 构建，使用 `java -jar class-obf.jar` 启动

## 1.3.1

更新日志：

- [功能] 支持新配置参数忽略 `public` 方法的混淆 @4ra1n
- [功能] 新增 `quiet` 配置参数不打印调试信息 @4ra1n
- [优化] 加密和解密使用反转的 `AES KEY` 和数据 @4ra1n 

感谢以下用户的贡献:

- 4ra1n (https://github.com/4ra1n)
- lz520520 (https://github.com/lz520520)

可供下载的文件都由 `Github Actions` 构建，使用 `java -jar class-obf.jar` 启动

## 1.3.0

更新日志：

- [BUG] 修复之前版本参数混淆功能实际没有生效
- [优化] 优化 `AES KEY` 提到全局变量并支持配置
- [优化] 允许混淆 `main` 方法但默认在方法黑名单中
- [优化] 配置文件支持关闭某些 `ASM` 选项提高兼容性
- [优化] 启动前检查各种配置参数是否合法有错误退出

感谢以下用户的贡献:

- 4ra1n (https://github.com/4ra1n)

可供下载的文件都由 `Github Actions` 构建，使用 `java -jar class-obf.jar` 启动

## 1.2.0

更新日志：

- [重要] 支持字符串 `AES` 加密运行时解密 @4ra1n
- [BUG] 避免 `advanceStringName` 和混淆标识符冲突 @4ra1n
- [优化] 优化 `config.yaml` 模板排序和注释内容 @4ra1n

感谢以下用户的贡献:

- 4ra1n (https://github.com/4ra1n)

可供下载的文件都由 `Github Actions` 构建，使用 `java -jar class-obf.jar` 启动

## 1.1.1

更新日志：

- [BUG] 修复 `API` 调用多次报错的问题

感谢以下用户的贡献:

- 4ra1n (https://github.com/4ra1n)

可供下载的文件都由 `Github Actions` 构建，使用 `java -jar class-obf.jar` 启动

## 1.1.0

更新日志：

- [功能] 上传到 `jitpack` 供其他项目引入 @4ra1n
- [功能] 提供 `API` 通过代码方式进行混淆 @4ra1n
- [优化] 更新依赖并缩小体积 @4ra1n
- [其他] 使用 `actions` 构建 @4ra1n

感谢以下用户的贡献:

- 4ra1n (https://github.com/4ra1n)

可供下载的文件都由 `Github Actions` 构建，使用 `java -jar class-obf.jar` 启动

## 1.0.0

初始版本

使用 `java -jar class-obf.jar` 启动

感谢以下用户的贡献:

- 4ra1n (https://github.com/4ra1n)