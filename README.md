# glfw-patcher

本工具用于帮助**Minecraft 启动器/启动器开发者**，在用户本地自动 patch [LWJGL](https://www.lwjgl.org/) 的 `lwjgl-glfw.jar`，以修复在 macOS Minecraft 1.17- 上出现的 GLFW Error 65548 问题。

> **本工具设计用于被集成进启动器，而非直接面向终端用户。**

## 集成说明

- 本工具不会分发 LWJGL 官方 JAR 文件，也不会分发 patch 后的 JAR。
- 所有 patch 操作均在集成方应用或最终用户本地执行。
- 集成本工具时，开发者有责任提醒最终用户遵守 LWJGL 及其依赖的开源许可证（BSD 3-Clause、zlib）。
- 如果你的产品会分发经过 patch 的 JAR，**请保留原始 LICENSE 文件，并在发布说明中注明已做本地修改**。

## License

本工具采用 [MIT License](LICENSE)。  
可自由集成到开源或闭源项目中，无需开源你的产品代码。

LWJGL 及其 GLFW 模块采用 [BSD 3-Clause License](https://github.com/LWJGL/lwjgl3/blob/master/LICENSE.md) 和 [zlib License](https://github.com/glfw/glfw/blob/master/LICENSE.md)。  
如需了解原始许可证内容，请查阅相关仓库或 JAR 包内 LICENSE 文件。

## 免责声明

本工具及作者与 LWJGL/GLFW 官方无直接关联。如有合规疑问请联系维护者。

## 使用方法

```
java -jar glfw-patcher.jar /path/to/your/lwjgl-glfw.jar
```
