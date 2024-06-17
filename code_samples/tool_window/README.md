# Tool Window Sample [![JetBrains IntelliJ Platform SDK Docs](https://jb.gg/badges/docs.svg)][docs]
*Reference: [Tool Windows in IntelliJ SDK Docs][docs:tool_windows]*

## Quickstart

工具窗口是用于显示信息的 IDE 的子窗口。
这些窗口通常在主窗口的外边缘具有工具栏（称为工具窗口栏），其中包含一个或多个工具窗口按钮，这些按钮可激活显示在主 IDE 窗口的左侧、底部和右侧的面板。

当前实现显示一个“JPanel”组件，其中包含简单的图标以及有关实际系统日期、时间和时区的信息。
Component is provided by the `CalendarToolWindowFactory.CalendarToolWindowContent` class through the `getContentPanel()` method invoked inside the `CalendarToolWindowFactory` implementation.

### Extension Points

| Name                      | Implementation                                              | Extension Point Class |
|---------------------------|-------------------------------------------------------------|-----------------------|
| `com.intellij.toolWindow` | [CalendarToolWindowFactory][file:CalendarToolWindowFactory] | `ToolWindowFactory`   |

*Reference: [Plugin Extension Points in IntelliJ SDK Docs][docs:ep]*


[docs]: https://plugins.jetbrains.com/docs/intellij/
[docs:tool_windows]: https://plugins.jetbrains.com/docs/intellij/tool-windows.html
[docs:ep]: https://plugins.jetbrains.com/docs/intellij/plugin-extensions.html

[file:CalendarToolWindowFactory]: ./src/main/java/org/intellij/sdk/toolWindow/CalendarToolWindowFactory.java
